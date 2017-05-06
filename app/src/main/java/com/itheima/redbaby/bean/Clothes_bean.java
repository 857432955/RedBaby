package com.itheima.redbaby.bean;

import org.senydevpkg.net.resp.IResponse;

import java.util.List;

/**
 * Created by Administrator on 2016/12/7.
 */
public class Clothes_bean implements IResponse {

    /**
     * product : {"available":true,"bigPic":[],"buyLimit":10,"commentCount":10,"id":40,"inventoryArea":"全国","leftTime":14000,"limitPrice":120,"marketPrice":180,"name":"智力图形记忆起","pics":[],"price":160,"productProperty":[{"id":1,"k":"颜色","v":"红色"},{"id":2,"k":"颜色","v":"绿色"},{"id":3,"k":"尺码","v":"M"},{"id":4,"k":"尺码","v":"XXL"},{"id":5,"k":"尺码","v":"XXXL"}],"score":5}
     * response : product
     */

    public ProductBean product;
    public String response;

    public static class ProductBean {
        /**
         * available : true
         * bigPic : []
         * buyLimit : 10
         * commentCount : 10
         * id : 40
         * inventoryArea : 全国
         * leftTime : 14000
         * limitPrice : 120
         * marketPrice : 180
         * name : 智力图形记忆起
         * pics : []
         * price : 160
         * productProperty : [{"id":1,"k":"颜色","v":"红色"},{"id":2,"k":"颜色","v":"绿色"},{"id":3,"k":"尺码","v":"M"},{"id":4,"k":"尺码","v":"XXL"},{"id":5,"k":"尺码","v":"XXXL"}]
         * score : 5
         */

        public boolean available;
        public String buyLimit;
        public int commentCount;
        public int id;
        public String inventoryArea;
        public int leftTime;
        public String limitPrice;
        public String marketPrice;
        public String name;
        public String price;
        public float score;
        public List<String> bigPic;
        public List<String> pics;
        public List<ProductPropertyBean> productProperty;

        public static class ProductPropertyBean {
            /**
             * id : 1
             * k : 颜色
             * v : 红色
             */
            public int id;
            public String k;
            public String v;
        }
    }
}
