package com.itheima.redbaby.bean;

import org.senydevpkg.net.resp.IResponse;

import java.util.List;

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
 * Created by lx on 2016/12/10.
 */
public class AddressLinkBean  implements IResponse{


    /**
     * response : addressArea
     * areaList : [{"id":"1","value":"北京"},{"id":"2","value":"安徽省"}]
     */

    public String response;
    /**
     * id : 1
     * value : 北京
     */

    public List<AreaListBean> areaList;

    public static class AreaListBean {
        public String id;
        public String value;
    }
}
