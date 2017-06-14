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
    public static final int LITERATURE_TYPE_SHI = 0;
    public static final int LITERATURE_TYPE_CI = 1;
    public static final int LITERATURE_TYPE_GUWEN = 2;
    public static final int LITERATURE_TYPE_GUTISHI = 3;

    /**
     * 朝代信息
     */
    public interface DYNASTY_TYPE {
        //其它
        public static final int OTHER = 9;
        //先秦
        public static final int XIANQIN = 0;
        //两汉
        public static final int LIANGHAN = 1;
        //三国、两晋、南北朝
        public static final int LIANGJIN = 2;
        //隋唐
        public static final int SUITANG = 3;
        //五代、金、辽
        public static final int WUDAI = 4;
        //宋元
        public static final int SONGYUAN = 5;
        //明清
        public static final int MINGQING = 6;
        //近现代
        public static final int JINXIANDAI = 7;
        //备用
        public static final int UNDEFINE = 8;
    }
    public static final String DYNASTY_TYPE_EXTRA = "dynasty_type";

    public static int literatureTypeToInt (String type) {
        if (type.equals(LITERATURE_TYPE.SHI)) {
            return LITERATURE_TYPE_SHI;
        } else if (type.equals(LITERATURE_TYPE.CI)) {
            return LITERATURE_TYPE_CI;
        } else if (type.equals(LITERATURE_TYPE.GUWEN)) {
            return LITERATURE_TYPE_GUWEN;
        } else if (type.equals(LITERATURE_TYPE.GUTISHI)) {
            return LITERATURE_TYPE_GUTISHI;
        }
        return LITERATURE_TYPE_SHI;
    }
}
