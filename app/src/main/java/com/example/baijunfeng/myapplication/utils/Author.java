package com.example.baijunfeng.myapplication.utils;

/**
 * Created by baijunfeng on 17/6/1.
 */

/**
 * 作者信息类
 */
public class Author {
    //作者名称，如"李白"
    public String mTitle;
    //作者index，如"libai"，用以获取该作者对应的数据，如"libai.json"
    public String mIndex;
    //作者代号 如000代表无名氏
    public String mId;

    public static class LiteratureDetail {
        //ID， 朝代 + 作者ID + 作品类型 + 作品ID
        public String mId = "";
        //作者，如"李白""左丘明"
        public String mAuthor = "";
        //名称，如《关雎》《浪淘沙》《白雪歌送武判官归京》
        public String mTitle = "";
        //出处，比如《劝学》出自《荀子》，《关雎》出自《诗经》
        public String mFrom = "";
        //内容
        public String mContent = "";
        //注释，包括译文等
        public String mAnnotation = "";
    }
}
