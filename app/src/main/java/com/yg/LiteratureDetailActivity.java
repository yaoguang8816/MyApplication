package com.yg;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;
import com.yg.database.LiteratureDatabaseHelper;
import com.yg.network.NetworkConnection;
import com.yg.utils.Author;
import com.yg.utils.UrlUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class LiteratureDetailActivity extends AppCompatActivity {
    public static final String TAG = "LiteratureDetail";

    //卡片id，该id对应详细作品json文件名称，如 300100000 对应李白作品 300100000.json
    public static final String CARD_ID = "id";
    public static final String CARD_TITLE = "title";
    public static final String CARD_AUTHOR = "author";
    public static final String CARD_CONTENT = "content";

    CollapsingToolbarLayout mCollapsingToolbarLayout;
    TextView mAuthorView;
    TextView mContentView;
    TextView mAnnotationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_literature_detail);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuthorView = (TextView) findViewById(R.id.author_name);
        mContentView =  (TextView) findViewById(R.id.literature_content);
        mAnnotationView = (TextView) findViewById(R.id.literature_annotation);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent intent = getIntent();
        String id = intent.getStringExtra(CARD_ID);
        String title = intent.getStringExtra(CARD_TITLE);
        String author = intent.getStringExtra(CARD_AUTHOR);
        String content = intent.getStringExtra(CARD_CONTENT);

        if (title != null && title.length() > 0) {
            mCollapsingToolbarLayout.setTitle(title);
        }
        if (author != null && author.length() > 0) {
            mAuthorView.setText(author);
        }

        if (content != null) {
            mContentView.setText(content);
        } else {
            getContent(id);
        }
    }

    private void updateUI(Author.LiteratureDetail detail) {
        if (detail.mAuthor != null && detail.mAuthor.length() > 0) {
            mAuthorView.setText(detail.mAuthor);
        } else if (detail.mFrom != null && detail.mFrom.length() > 0) {
            mAuthorView.setText(detail.mFrom);
        } else {
            mAuthorView.setText(getIntent().getStringExtra(CARD_AUTHOR));
        }

        if (detail.mTitle != null && detail.mTitle.length() > 0) {
            mCollapsingToolbarLayout.setTitle(detail.mTitle);
        } else {
            mCollapsingToolbarLayout.setTitle(getIntent().getStringExtra(CARD_TITLE));
        }
        mContentView.setText(detail.mContent);
        mAnnotationView.setVisibility(View.VISIBLE);
        mAnnotationView.setText(detail.mAnnotation);
    }

    private void getContent(String id) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Author.LiteratureDetail detail = new Author.LiteratureDetail();

                /*
                 * 先查询本地数据库，如果数据库没有相关数据，再从网络获取
                 */
                SQLiteDatabase sqlDB = LiteratureDatabaseHelper.getInstance().getReadableDatabase();
                Cursor cursor = sqlDB.query(LiteratureDatabaseHelper.LiteratureColumns.TABLE_NAME, null,
                        LiteratureDatabaseHelper.LiteratureColumns.LITERATURE_INDEX + "=?", new String[] {id}, null, null, null);

//                //通过provider访问数据库，如果需要，打开即可
//                Cursor cursor = this.getContentResolver().query(LiteratureDatabaseHelper.LiteratureColumns.CONTENT_URI, null,
//                LiteratureDatabaseHelper.LiteratureColumns.LITERATURE_INDEX + "=?", new String[] {id}, null);
                try {
                    if (cursor != null) {
                        if (cursor.moveToFirst()) {
                            detail.mId = cursor.getString(cursor.getColumnIndex(LiteratureDatabaseHelper.LiteratureColumns.LITERATURE_INDEX));
                            detail.mAuthor = cursor.getString(cursor.getColumnIndex(LiteratureDatabaseHelper.LiteratureColumns.LITERATURE_AUTHOR));
                            detail.mTitle = cursor.getString(cursor.getColumnIndex(LiteratureDatabaseHelper.LiteratureColumns.LITERATURE_TITLE));
                            detail.mFrom = cursor.getString(cursor.getColumnIndex(LiteratureDatabaseHelper.LiteratureColumns.LITERATURE_FROM));
                            detail.mContent = cursor.getString(cursor.getColumnIndex(LiteratureDatabaseHelper.LiteratureColumns.LITERATURE_CONTENT));
                            detail.mAnnotation = cursor.getString(cursor.getColumnIndex(LiteratureDatabaseHelper.LiteratureColumns.LITERATURE_ANNOTATION));
                            updateUI(detail);
                            cursor.close();
                            return;
                        }
                    }
                } catch (Exception e) {
                    Log.d(TAG, e.toString());
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }

                NetworkConnection.getInstance().getJSONByVolley(UrlUtils.getLiteratureContentUrlById(id), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            try {
                                detail.mId = response.getString("id");
                                if (detail.mId == null || detail.mId.length() <= 0) {
                                    return;
                                }
                            } catch (JSONException e) {
                                Log.d(TAG, "没有author!");
                            }
                            try {
                                detail.mAuthor = response.getString("author");
                            } catch (JSONException e) {
                                Log.d(TAG, "没有author!");
                            }
                            try {
                                detail.mTitle = response.getString("title");
                            } catch (JSONException e) {
                                Log.d(TAG, "没有title!");
                            }
                            try {
                                detail.mFrom = response.getString("from");
                            } catch (JSONException e) {
                                Log.d(TAG, "没有From!");
                            }
                            try {
                                detail.mContent = response.getString("content");
                            } catch (JSONException e) {
                                Log.d(TAG, "没有内容!");
                            }
                            try {
                                detail.mAnnotation = response.getString("annotation");
                            } catch (JSONException e) {
                                Log.d(TAG, "没有注释!");
                            }
                            updateUI(detail);

                            //如果内容为空，则不进行存储
                            if (detail.mContent != null || detail.mContent.length() > 0) {
                                //从网络获取的新数据，更新到数据库
                                LiteratureDatabaseHelper.getInstance().insertLiterature(detail);
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
}
