package com.example.baijunfeng.myapplication.utils;

/**
 * Created by baijunfeng on 17/5/9.
 */

public class UrlUtils {
    public static final String URL = "https://yaoguang8816.github.io/";
    public static final String JSON_PATH = "json/";

    //体裁 诗
    public static final String TYPES_1 = "shi/";
    //体裁 词
    public static final String TYPES_2 = "ci/";

    //作品详细内容存放路径：RRL/json/literature/<id>.json
    public static final String LITERATURE = "literature";

    //
    public static final String AUTHOR_LIST = "author_list";

    public static final String JSON_SUFFIX = ".json";

    /*
     * https://yaoguang8816.github.io/json/<s>.json
     */
    private static String generelJsonUrl (String s) {
        return URL + JSON_PATH + s + JSON_SUFFIX;
    }

    public static String getUrlByAuthor (String type, String author) {
        return generelJsonUrl(type.toLowerCase() + "/" + author.toLowerCase());
    }

    public static String getUrlById (String id) {
        return generelJsonUrl(LITERATURE + "/" + id);
    }

    public static String getShiAuthorList () {
        return generelJsonUrl(TYPES_1 + AUTHOR_LIST);
    }

    public static String getCiAuthorList () {
        return generelJsonUrl(TYPES_2 + AUTHOR_LIST);
    }
}
