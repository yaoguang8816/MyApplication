package com.example.baijunfeng.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Response;
import com.example.baijunfeng.myapplication.adapter.AncientPoetryAdapter;
import com.example.baijunfeng.myapplication.adapter.NoteAdapter;
import com.example.baijunfeng.myapplication.database.LiteratureContentProvider;
import com.example.baijunfeng.myapplication.database.LiteratureDatabaseHelper;
import com.example.baijunfeng.myapplication.network.NetworkConnection;
import com.example.baijunfeng.myapplication.network.NetworkStatus;
import com.example.baijunfeng.myapplication.utils.Author;
import com.example.baijunfeng.myapplication.utils.PoetryCardContent;
import com.example.baijunfeng.myapplication.utils.UrlUtils;
import com.example.baijunfeng.myapplication.utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
/**
 * Created by baijunfeng on 17/3/17.
 *
 * 古诗词列表activity
 * 按照不同作者列出相关作品的简要目录
 *
 * 导航列表列出支持的作者，点击作者的时候列表更新为该作者的作品
 */
public class AncientPoetryActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final String TAG = "AncientPoetryActivity";

    RecyclerView mRecyclerView;
    AncientPoetryAdapter mAdapter;
    NoteAdapter mNoteAdapter;

    //NavigationView所要使用的数据map，数据为作者ID和Author对象，该数据用来点击Menu的时候找到正确的作者信息
    HashMap<String, Author> mMenuDataMap = new HashMap<>();

    /**
     * 文学作品类型 {@link Util.LITERATURE_TYPE}
     */
    String mType;

    /**
     * 当前页面对应作者 {@link Author}
     */
    Author mCurrentAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                              setContentView(R.layout.activity_ancient_poetry);

        mType = getIntent().getStringExtra(Util.LITERATURE_TYPE_EXTRA);
        if (mType == null || mType.length() <= 0) {
            mType = Util.LITERATURE_TYPE.SHI;
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getWindow().setAllowEnterTransitionOverlap(false);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        /*
         * 初始化adapter
         */
        //得到控件
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //设置适配器
        mAdapter = new AncientPoetryAdapter(this, getContentList(LIBAI, new ArrayList<>(Arrays.asList(0,1,2))));
        //设置适配器Item点击回调接口
        mAdapter.setOnItemClickListener(new AncientPoetryAdapter.CardItemClickListener() {
            @Override
            public void onItemClick(PoetryCardContent content) {
                if (mCurrentAuthor == null) {
                    return;
                }
                Intent intent = new Intent(AncientPoetryActivity.this, LiteratureDetailActivity.class);

                //ID 由 作者ID + 作品类型 + 作品ID构成
                intent.putExtra(LiteratureDetailActivity.CARD_ID, mCurrentAuthor.mId + Util.literatureTypeToInt(mType) + content.mId);
                intent.putExtra(LiteratureDetailActivity.CARD_TITLE, content.mTitle);
                intent.putExtra(LiteratureDetailActivity.CARD_AUTHOR, content.mAuthor);
                intent.putExtra(LiteratureDetailActivity.CARD_CONTENT, content.mContent);
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        /*
         * 导航栏配置
         */
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getMenuDatas(navigationView);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ancient_poetry, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 从服务器获取作者列表，用获得数据初始化NavigationView
     * @param view
     */
    private void getMenuDatas(NavigationView view) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                NetworkConnection.getInstance().getJSONByVolley(UrlUtils.getAuthorList(mType), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int code = response.optInt("code");
                            if (code == 0) {
                                ArrayList<Author> authorList = new ArrayList<Author>();
                                JSONObject json;

                                JSONArray array = response.getJSONArray("data");
                                for (int i = 0; i < array.length(); i++) {
                                    json =  (JSONObject) array.get(i);
                                    Author author = new Author();
                                    try {
                                        author.mId = json.getString("id");
                                        author.mIndex = json.getString("index");
                                        author.mTitle = json.getString("title");
                                        authorList.add(author);
                                    } catch (JSONException e) {
                                        Log.d(TAG, "Find an error for some author, skip this.");
                                    }
                                }
                                updateMenuData(authorList);
                                updateNavigationView(view, authorList);
                            } else {
                                Log.d(TAG, "Response error!");
                            }

                        } catch (Exception e) {
                            Log.e(TAG, e.toString());
                        }
                    }
                }, null);
            }
        });
        thread.start();
    }

    /**
     * 更新NavigationView列表数据
     * @param authorList 作者列表
     */
    private void updateMenuData(ArrayList<Author> authorList) {
        for (Author author : authorList) {
            mMenuDataMap.put(author.mId, author);
        }
    }

    /**
     * 更新NavigationView列表，其中menu的itemId为从服务器获取的作者id首位加1，显示的menu为作者title
     * @param view
     * @param authorList
     */
    private void updateNavigationView(NavigationView view, ArrayList<Author> authorList) {
        Menu menu = view.getMenu();
        for (Author author : authorList) {
            view.getMenu().add(1, encodeMenuId(author.mId), Menu.NONE, author.mTitle);
//            view.getMenu().findItem(10001).setIcon(R.drawable.ic_menu_camera);
        }
        view.getMenu().notify();
    }

    /**
     * 为防止从服务器获取的作者Id以0开头导致字符串转数字时发生丢失，所以在获取的作者id之前加上"1"，然后再转为数字
     * 同理，把相关数字转回字符串的时候需要去掉首位的"1" {@link #decodeMenuId}
     */
    private int encodeMenuId(String id) {
        return Integer.valueOf("1" + id);
    }
    private String decodeMenuId(int id) {
        return String.valueOf(id).substring(1);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        getAndUpdateContentDatas(mType, mMenuDataMap.get(decodeMenuId(id)));

        mAdapter.notifyDataSetChanged();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 从服务器获取相关作品
     * @param type 作品类型 {@link Util.LITERATURE_TYPE}
     * @param author 作者名称 {@link Author#mIndex}
     */
    private void getAndUpdateContentDatas(String type, Author author) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                NetworkConnection.getInstance().getJSONByVolley(UrlUtils.getLiteratureListUrlByAuthor(type, author.mIndex), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int code = response.optInt("code");
                            if (code == 0) {
                                ArrayList<PoetryCardContent> contentList = new ArrayList<PoetryCardContent>();
                                String authorName = response.getString("name");
                                JSONObject json;

                                JSONArray array = response.getJSONArray("data");
                                for (int i = 0; i < array.length(); i++) {
                                    json =  (JSONObject) array.get(i);
                                    PoetryCardContent content = new PoetryCardContent();
                                    content.mId = json.getString("id");
                                    content.mAuthor = authorName;
                                    content.mTitle = json.getString("title");
                                    content.mAbbr = json.getString("abbr");
                                    contentList.add(content);
                                }
                                mAdapter.updateDatas(contentList);
                                mAdapter.notifyDataSetChanged();
                                mCurrentAuthor = author;

                                //下载当前作者的所有作品并存放到数据库
                                updateDatabases(contentList);
                            } else {
                                Log.d(TAG, "Response error!");
                            }

                        } catch (Exception e) {
                            Log.e(TAG, e.toString());
                        }
                    }
                }, null);
            }
        });
        thread.start();
    }

    /**
     * 通过当前作者作品 {@link PoetryCardContent} 列表来获取所有作品的详细内容 {@link Author.LiteratureDetail},
     * 把获取到的内容存储到数据库表 {@link LiteratureDatabaseHelper.LiteratureColumns#TABLE_NAME} 中。
     * @param contentList 作品列表
     */
    private void updateDatabases(ArrayList<PoetryCardContent> contentList) {
        if (!(new NetworkStatus()).isWifiConnected()) {
            return;
        }
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (PoetryCardContent content : contentList) {
                    NetworkConnection.getInstance().getJSONByVolley(UrlUtils.getLiteratureContentUrlById(mCurrentAuthor.mId + Util.literatureTypeToInt(mType) + content.mId), new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Author.LiteratureDetail detail = new Author.LiteratureDetail();
                                try {
                                    String id = response.getString("id");
                                    if (id != null && id.length() > 0) {
                                        detail.mId = id;
                                    }
                                } catch (JSONException e) {
                                    Log.d(TAG, "没有id!");
                                    return;
                                }
                                try {
                                    String author = response.getString("author");
                                    if (author != null && author.length() > 0) {
                                        detail.mAuthor = author;
                                    }
                                } catch (JSONException e) {
                                    Log.d(TAG, "没有author!");
                                }
                                try {
                                    String title = response.getString("title");
                                    if (title != null && title.length() > 0) {
                                        detail.mTitle = title;
                                    }
                                } catch (JSONException e) {
                                    Log.d(TAG, "没有title!");
                                }
                                try {
                                    String from = response.getString("from");
                                    if (from != null && from.length() > 0) {
                                        detail.mFrom = from;
                                    }
                                } catch (JSONException e) {
                                    Log.d(TAG, "没有From!");
                                }
                                try {
                                    String content = response.getString("content");
                                    detail.mContent = content;
                                } catch (JSONException e) {
                                    Log.d(TAG, "没有内容!");
                                }
                                try {
                                    String annotation = response.getString("annotation");
                                    detail.mAnnotation = annotation;
                                } catch (JSONException e) {
                                    Log.d(TAG, "没有注释!");
                                }

                                //更新到数据库
                                LiteratureDatabaseHelper.getInstance().insertLiterature(detail);
                            } catch (Exception e) {
                                Log.e(TAG, e.toString());
                            }
                        }
                    }, null);
                }
            }
        });
        thread.start();
    }

//    String getJson(URL url) {
//        String JsonStr = "";
//        try {
//            HttpURLConnection httpconn = (HttpURLConnection) url
//                    .openConnection();
//            InputStreamReader inputReader = new InputStreamReader(httpconn
//                    .getInputStream());
//
//            BufferedReader buffReader = new BufferedReader(inputReader);
//
//            String line = "";
//
//            while ((line = buffReader.readLine()) != null) {
////                lineIndex++;
//                JsonStr += line;
//
//            }
//            Log.d("baijf1", JsonStr);
////            resolveJson(JsonStr);
//
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return JsonStr;
//    }

    /*
    Debug Start
     */
    public static final int DUMU = 1000;
    public static final int SUSHI = 2000;
    public static final int LIBAI = 3000;

    //默认显示列表
    private List<PoetryCardContent> getContentList(int index, List<Integer> items) {
        List<PoetryCardContent> contentList = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            contentList.add(getContent(index | items.get(i).intValue()));
        }

        return contentList;
    }

    PoetryCardContent getContent(int item) {
        switch (item) {
            case 1000:
                return new PoetryCardContent("遣怀","杜牧","落魄江湖载酒行，楚腰纤细掌中轻。");
            case 1001:
                return new PoetryCardContent("赠别其一","杜牧","春风十里扬州路，卷上珠帘总不如。");
            case 1002:
                return new PoetryCardContent("赠别其二","杜牧","蜡烛有心还惜别，替人垂泪到天明。");
            case 1003:
                return new PoetryCardContent("寄扬州韩绰判官","杜牧","二十四桥明月夜，玉人何处教吹箫。");
            case 2000:
                return new PoetryCardContent("念奴娇","苏轼","大江东去，浪淘尽，千古风流人物。");
            case 2001:
                return new PoetryCardContent("江城子","苏轼","十年生死两茫茫，不思量，自难忘。");
            case 2002:
                return new PoetryCardContent("惠崇春江晚景","苏轼","蒌蒿满地芦芽短，正是河豚欲上时。");
            case 3000:
                return new PoetryCardContent("静夜思","李白","床前明月光，疑是地上霜。");
            case 3001:
                return new PoetryCardContent("子夜吴歌","李白","长安一片月，万户捣衣声。");
            case 3002:
                return new PoetryCardContent("梦游天姥吟留别","李白","脚著谢公屐，身登青云梯。");
            case 3003:
                return new PoetryCardContent("蜀道难","李白","连峰去天不盈尺，枯松倒挂倚绝壁。\n飞湍瀑流争喧豗，砯崖转石万壑雷。");
        }
        return null;
    }
}
