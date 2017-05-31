package com.example.baijunfeng.myapplication.utils;

/**
 * Created by baijunfeng on 17/5/9.
 */

public class UrlUtils {
    public static final String URL = "https://yaoguang8816.github.io/";
    public static final String JSON_PATH = "json/";
//    //体裁 诗
//    public static final String TYPES_1 = "shi/";
//    //体裁 词
//    public static final String TYPES_2 = "ci/";
    public static final String JSON_SUFFIX = ".json";

    public static String getUrlByAuthor (String type, String author) {
        return URL + JSON_PATH + type.toLowerCase() + "/" + author.toLowerCase() + JSON_SUFFIX;
    }
}
