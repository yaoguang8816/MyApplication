package com.example.baijunfeng.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.baijunfeng.myapplication.R;

public class LiteratureDetailActivity extends AppCompatActivity {
    public static final String CARD_TITLE = "title";
    public static final String CARD_AUTHOR = "author";
    public static final String CARD_CONTENT = "content";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_literature_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent intent = getIntent();
        String title = intent.getStringExtra(CARD_TITLE);
        String author = intent.getStringExtra(CARD_AUTHOR);
        String content = intent.getStringExtra(CARD_CONTENT);
    }
}
