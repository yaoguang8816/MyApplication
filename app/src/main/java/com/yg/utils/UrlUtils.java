package com.yg.utils;

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

    //导航栏列表呈现方式
    //以作者形式呈现
    public static final String AUTHOR_LIST = "author_list";
    //以作品名称呈现
    public static final String LITERATURE_LIST = "title_list";

    public static final String JSON_SUFFIX = ".json";

    /*
     * https://yaoguang8816.github.io/json/<s>.json
     */
    private static String generelJsonUrl (String s) {
        return URL + JSON_PATH + s + JSON_SUFFIX;
    }

    /**
     * 通过类型和作者获取作品列表URL
     * @param type 类型 {@Util.LITERATURE_TYPE}
     * @param author 作者
     * @return
     */
    public static String getLiteratureListUrlByAuthor (String type, String author) {
        return generelJsonUrl(type.toLowerCase() + "/" + author.toLowerCase());
    }

    /**
     * 通过作品ID获取作品URL
     * @param id 作者id {@PoetryCardContent.mId}
     * @return
     */
    public static String getLiteratureContentUrlById (String id) {
        return generelJsonUrl(LITERATURE + "/" + id);
    }

    public static String getAuthorList (String type) {
        return generelJsonUrl(type + "/" + AUTHOR_LIST);
    }

    public static String getLiteratureTitleList (String type) {
        return generelJsonUrl(type + "/" + LITERATURE_LIST);
    }

    /**
     * 通过类型获取NavigationView列表数据URL
     * @param type 类型 {@Util.LITERATURE_TYPE}
     * @return
     */
    public static String getMenuList (String type) {
        if (type == Util.LITERATURE_TYPE.SHI || type == Util.LITERATURE_TYPE.CI) {
            return generelJsonUrl(type + "/" + AUTHOR_LIST);
        } else {
            return getLiteratureTitleList(type + "/" + AUTHOR_LIST);
        }
    }
}
