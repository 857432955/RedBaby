package com.itheima.redbaby.bean;

import org.senydevpkg.net.resp.IResponse;

import java.util.List;

/**
 * Created by ZhangRuxing on 2016-12-09.
 */

public class SearchListBean implements IResponse {

    /**
     * productList : [{"id":30,"marketPrice":200,"name":"超凡奶粉","pic":"/images/product/detail/q26.jpg","price":160},{"id":31,"marketPrice":260,"name":"天籁牧羊奶粉","pic":"/images/product/detail/q26.jpg","price":200},{"id":32,"marketPrice":300,"name":"fullcare奶粉","pic":"/images/product/detail/q26.jpg","price":300},{"id":33,"marketPrice":300,"name":"雀巢奶粉","pic":"/images/product/detail/q26.jpg","price":200},{"id":34,"marketPrice":200,"name":"安婴儿奶粉","pic":"/images/product/detail/q26.jpg","price":200},{"id":35,"marketPrice":200,"name":"贝贝羊金装奶粉","pic":"/images/product/detail/q26.jpg","price":160},{"id":36,"marketPrice":200,"name":"飞雀奶粉","pic":"/images/product/detail/q26.jpg","price":160}]
     * response : search
     */

    public String response;
    public List<ProductListBean> productList;

    @Override
    public String toString() {
        return "SearchListBean{" +
                "response='" + response + '\'' +
                ", productList=" + productList +
                '}';
    }

    public static class ProductListBean {
        /**
         * id : 30
         * marketPrice : 200
         * name : 超凡奶粉
         * pic : /images/product/detail/q26.jpg
         * price : 160
         */

        public int id;
        public int marketPrice;
        public String name;
        public String pic;
        public int price;

        @Override
        public String toString() {
            return "ProductListBean{" +
                    "id=" + id +
                    ", marketPrice=" + marketPrice +
                    ", name='" + name + '\'' +
                    ", pic='" + pic + '\'' +
                    ", price=" + price +
                    '}';
        }
    }
}
