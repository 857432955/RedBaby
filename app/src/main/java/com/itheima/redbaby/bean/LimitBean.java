package com.itheima.redbaby.bean;

import org.senydevpkg.net.resp.IResponse;

import java.util.List;

/**
 * @author 王帅峰
 * @time 2016/12/7  15:12
 * @des
 */
public class LimitBean implements IResponse{

    /**
     * listCount : 10
     * productList : [{"id":11,"leftTime":14000,"limitPrice":120,"name":"韩版秋装","pic":"/images/product/detail/q9.jpg","price":160},{"id":12,"leftTime":14000,"limitPrice":120,"name":"女士外套","pic":"/images/product/detail/q10.jpg","price":160},{"id":13,"leftTime":14000,"limitPrice":120,"name":"时尚女装","pic":"/images/product/detail/q11.jpg","price":200},{"id":14,"leftTime":14000,"limitPrice":120,"name":"萌妹外套","pic":"/images/product/detail/q12.jpg","price":160},{"id":15,"leftTime":14000,"limitPrice":120,"name":"韩版粉嫩外套","pic":"/images/product/detail/q13.jpg","price":160},{"id":16,"leftTime":14000,"limitPrice":120,"name":"韩版秋装","pic":"/images/product/detail/q14.jpg","price":300},{"id":17,"leftTime":14000,"limitPrice":120,"name":"春装新款","pic":"/images/product/detail/q15.jpg","price":200},{"id":18,"leftTime":14000,"limitPrice":120,"name":"短裙","pic":"/images/product/detail/q16.jpg","price":160},{"id":19,"leftTime":14000,"limitPrice":120,"name":"新款秋装","pic":"/images/product/detail/q17.jpg","price":160},{"id":20,"leftTime":14000,"limitPrice":120,"name":"妈妈新款","pic":"/images/product/detail/q18.jpg","price":160}]
     * response : limitbuy
     */

    public int listCount;
    public String response;
    /**
     * id : 11
     * leftTime : 14000
     * limitPrice : 120
     * name : 韩版秋装
     * pic : /images/product/detail/q9.jpg
     * price : 160
     */

    public List<ProductListBean> productList;

    public static class ProductListBean {
        public int id;

        public void setLeftTime(int leftTime) {
            this.leftTime = leftTime;
        }

        public int leftTime;
        public double limitPrice;
        public String name;
        public String pic;
        public double price;
    }
}
