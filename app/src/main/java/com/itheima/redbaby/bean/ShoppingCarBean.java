package com.itheima.redbaby.bean;

import org.senydevpkg.net.resp.IResponse;

import java.util.List;

/**
 * 作者：徐鹏 on 2016/12/6 22:38
 * 电话：15866972236
 */
public class ShoppingCarBean implements IResponse {

    /**
     * cart : [{"prodNum":3,"product":{"buyLimit":10,"id":1,"name":"韩版时尚蕾丝裙","number":"100","pic":"/images/product/detail/c3.jpg","price":350,"productProperty":[{"id":1,"k":"颜色","v":"红色"},{"id":2,"k":"颜色","v":"绿色"},{"id":3,"k":"尺码","v":"M"},{"id":4,"k":"尺码","v":"XXL"}]}},{"prodNum":2,"product":{"buyLimit":10,"id":10,"name":"韩版棉袄","number":"15","pic":"/images/product/detail/q8.jpg","price":160,"productProperty":[{"id":2,"k":"颜色","v":"绿色"},null]}}]
     * prom : ["促销信息一","促销信息二"]
     * response : cart
     * totalCount : 5
     * totalPoint : 100
     * totalPrice : 1370
     */

    public String response;
    public String totalCount;
    public String totalPoint;
    public String totalPrice;
    /**
     * prodNum : 3
     * product : {"buyLimit":10,"id":1,"name":"韩版时尚蕾丝裙","number":"100","pic":"/images/product/detail/c3.jpg","price":350,"productProperty":[{"id":1,"k":"颜色","v":"红色"},{"id":2,"k":"颜色","v":"绿色"},{"id":3,"k":"尺码","v":"M"},{"id":4,"k":"尺码","v":"XXL"}]}
     */

    public List<CartBean> cart;
    public List<String> prom;

    public static class CartBean {
        public String prodNum;
        /**
         * buyLimit : 10
         * id : 1
         * name : 韩版时尚蕾丝裙
         * number : 100
         * pic : /images/product/detail/c3.jpg
         * price : 350
         * productProperty : [{"id":1,"k":"颜色","v":"红色"},{"id":2,"k":"颜色","v":"绿色"},{"id":3,"k":"尺码","v":"M"},{"id":4,"k":"尺码","v":"XXL"}]
         */

        public ProductBean product;

        public static class ProductBean {
            public String buyLimit;
            public String id;
            public String name;
            public String number;
            public String pic;
            public String price;
            /**
             * id : 1
             * k : 颜色
             * v : 红色
             */

            public List<ProductPropertyBean> productProperty;

            public static class ProductPropertyBean {
                public int id;
                public String k;
                public String v;
            }
        }
    }
}
