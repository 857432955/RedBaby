package com.itheima.redbaby.constant;
import com.itheima.redbaby.utils.LogUtils;

/**
 * .::::.
 * .::::::::.
 * :::::::::::
 * ..:::::::::::'
 * '::::::::::::'
 * .::::::::::
 * '::::::::::::::..
 * ..::::::::::::.
 * ``::::::::::::::::
 * ::::``:::::::::'        .:::.
 * ::::'   ':::::'       .::::::::.
 * .::::'      ::::     .:::::::'::::.
 * .:::'       :::::  .:::::::::' ':::::.
 * .::'        :::::.:::::::::'      ':::::.
 * .::'         ::::::::::::::'         ``::::.
 * ...:::           ::::::::::::'              ``::.
 * ```` ':.          ':::::::::'                  ::::..
 * '.:::::'                    ':'````..
 * Created by lx on 2016/11/14.
 */
public class Constants {
    /*
       LogUtils.LEVEL_ALL:打开日志(显示所有的日志输出)
       LogUtils.LEVEL_OFF:关闭日志(屏蔽所有的日志输出)
        */
    public static final long PROTOCOLTIMEOUT = 5 * 60 * 1000;//5分钟
    public static final int DEBUGLEVEL = LogUtils.LEVEL_ALL;
    public static final class URLS {
        public static final String BASEURL = "http://192.168.16.58:8080/RedBabyServer/";
        public static final String IMGBASEURL = BASEURL + "image?name=";
    }
    public static final class REQ {

    }

    public static final class RES {

    }

    public static final class PAY {
        public static final int PAYTYPE_ZHIFUBAO = 0;
        public static final int PAYTYPE_WEIXIN = 1;
    }
}
