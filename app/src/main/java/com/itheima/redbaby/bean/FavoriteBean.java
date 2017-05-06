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
 * Created by lx on 2016/12/8.
 */
public class FavoriteBean implements IResponse {

    /**
     * response : favorites
     * productList : [{"id":"1","name":"韩版时尚蕾丝裙","pic":"","marketPrice":"500","price":"350"},{"id":"2","name":"粉色毛衣","pic":"","marketPrice":"79","price":"78"}]
     * listCount : 2
     */

    public String response;
    public String listCount;
    /**
     * id : 1
     * name : 韩版时尚蕾丝裙
     * pic :
     * marketPrice : 500
     * price : 350
     */

    public List<ProductListBean> productList;

    public static class ProductListBean {
        public String id;
        public String name;
        public String pic;
        public String marketPrice;
        public String price;
    }


}
