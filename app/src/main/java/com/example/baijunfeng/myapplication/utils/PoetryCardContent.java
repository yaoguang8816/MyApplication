package com.example.baijunfeng.myapplication.utils;

/**
 * Created by baijunfeng on 17/3/22.
 */

public class PoetryCardContent {
    private String mTitle;
    private String mAuthor;
    private String mAbbr;
    private String mContent;
    private String mNote;

    public PoetryCardContent() {
    }

    public PoetryCardContent(String title, String author, String abbr) {
        this(title, author, abbr, abbr, null);
    }

    public PoetryCardContent(String title, String author, String abbr, String content) {
        this(title, author, abbr, content, null);
    }

    public PoetryCardContent(String title, String author, String abbr, String content, String note) {
        mTitle = title;
        mAuthor = author;
        mContent = content;
        mAbbr = abbr;
        mNote = note;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthor () {
        return mAuthor;
    }

    public String getContent () {
        return mContent;
    }

    public String getAbbr () {
        return mAbbr;
    }

    public String getNote () {
        return mNote;
    }
}

