package com.example.baijunfeng.myapplication.utils;

/**
 * Created by baijunfeng on 17/6/9.
 */

public class Util {

    /**
     * 文学作品类型
     */
    public interface LITERATURE_TYPE {
        //诗，唐以后古诗
        public static final String SHI = "shi";
        //词，唐以后词
        public static final String CI = "ci";
        //古文，古文名篇
        public static final String GUWEN = "guwen";
        //古体诗，唐以前古诗作
        public static final String GUTISHI = "gutishi";
    }
    public static final String LITERATURE_TYPE_EXTRA = "type";
}
