package com.example.baijunfeng.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.baijunfeng.myapplication.adapter.AncientPoetryAdapter;
import com.example.baijunfeng.myapplication.adapter.NoteAdapter;
import com.example.baijunfeng.myapplication.utils.PoetryCardContent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AncientPoetryActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView mRecyclerView;
    AncientPoetryAdapter mAdapter;
    NoteAdapter mNoteAdapter;

    ArrayList<PoetryCardContent> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ancient_poetry);
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

        //得到控件
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //设置适配器
        mAdapter = new AncientPoetryAdapter(this, getContentList(LIBAI, new ArrayList<>(Arrays.asList(0,1,2))));
//        mNoteAdapter = new NoteAdapter(this, mDatas);
        mRecyclerView.setAdapter(mAdapter);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            mAdapter.updateDatas(getContentList(DUMU, new ArrayList<>(Arrays.asList(0,2,3))));
        } else if (id == R.id.nav_gallery) {
            mAdapter.updateDatas(getContentList(SUSHI, new ArrayList<>(Arrays.asList(0,1,2))));
        } else if (id == R.id.nav_slideshow) {
            mAdapter.updateDatas(getContentList(LIBAI, new ArrayList<>(Arrays.asList(0,2,3,1))));
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        mAdapter.notifyDataSetChanged();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*
    Debug Start
     */
    public static final int DUMU = 1000;
    public static final int SUSHI = 2000;
    public static final int LIBAI = 3000;

    private List<PoetryCardContent> getContentList(int index, List<Integer> items) {
        List<PoetryCardContent> contentList = new ArrayList<>();
//        switch (index) {
//            case 1:
//                for (int i = 0; i < items.size(); i++) {
//                    contentList.add(getContent((index << 4) & items.get(i)));
//                }
//                break;
//            case 2:
//                content = new PoetryCardContent("遣怀","杜牧","落魄江湖载酒行，楚腰纤细掌中轻。");
//                break;
//            case 3:
//                content = new PoetryCardContent("遣怀","杜牧","落魄江湖载酒行，楚腰纤细掌中轻。");
//                break;
//        }
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
