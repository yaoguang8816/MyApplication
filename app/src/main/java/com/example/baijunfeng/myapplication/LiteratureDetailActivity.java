package com.example.baijunfeng.myapplication;

import android.content.Intent;
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
import com.example.baijunfeng.myapplication.R;
import com.example.baijunfeng.myapplication.network.NetworkConnection;
import com.example.baijunfeng.myapplication.utils.PoetryCardContent;
import com.example.baijunfeng.myapplication.utils.UrlUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LiteratureDetailActivity extends AppCompatActivity {
    public static final String TAG = "LiteratureDetail";

    public static final String CARD_ID = "id";
    public static final String CARD_TITLE = "title";
    public static final String CARD_AUTHOR = "author";
    public static final String CARD_CONTENT = "content";

    TextView mContentView;
    TextView mAnnotationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_literature_detail);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView authorView = (TextView) findViewById(R.id.author_name);
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

        collapsingToolbarLayout.setTitle(title);
        authorView.setText(author);

        if (content != null) {
            mContentView.setText(content);
        } else {
            getContent(id);
//            content =  "长安一片月，万户捣衣声。\n秋风吹不尽，总是玉关情。\n何日平胡虏，良人罢远征。";
//            String annotation = "注释：\n子夜吴歌：《子夜歌》系六朝乐府中的吴声歌曲。相传是晋代一名叫子夜的女子创制，多写哀怨眷恋之情，分春、夏、秋、冬四季。李白依格了四首，此首属秋歌。\n捣衣：将洗过的衣服放在砧石上，用木杵捣去碱质。这里指人们准备寒衣。\n玉关：即玉门关。虏：对敌方的蔑称。良人：丈夫。\n【赏析】：月色如银的京城，表面上一片平静，但捣衣声中却蕴含着千家万户的痛苦；秋风不息，也寄托着对边关思念的深情。读来让人怦然心动。结句是闺妇的期待，也是征人的心声。";
//            mContentView.setText(content);
//            mAnnotationView.setVisibility(View.VISIBLE);
//            mAnnotationView.setText(annotation);
        }
    }

    private void getContent(String id) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                NetworkConnection.getInstance().getJSONByVolley(UrlUtils.getUrlById(id), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String content_id = response.getString("id");
                            if (content_id.equals(id)) {
                                try {
                                    String content = response.getString("content");
                                    mContentView.setText(content);
                                } catch (JSONException e) {
                                    Log.d(TAG, "没有内容!");
                                }
                                try {
                                    String annotation = response.getString("annotation");
                                    mAnnotationView.setVisibility(View.VISIBLE);
                                    mAnnotationView.setText(annotation);
                                } catch (JSONException e) {
                                    Log.d(TAG, "没有注释!");
                                }
                            } else {
                                Log.d(TAG, "内容与所请求不匹配!");
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
