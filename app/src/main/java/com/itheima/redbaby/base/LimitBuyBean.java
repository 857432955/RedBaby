package com.itheima.redbaby.base;

import org.senydevpkg.net.resp.IResponse;

import java.util.List;

/**
 * 作者：徐鹏 on 2016/12/7 15:46
 * 电话：15866972236
 */
public class LimitBuyBean implements IResponse {

    /**
     * listCount : 30
     * productList : [{"id":2,"leftTime":17000,"limitPrice":1,"name":"粉色毛衣","pic":"/images/product/detail/q1.jpg","price":100},{"id":3,"leftTime":16000,"limitPrice":90,"name":"女裙","pic":"/images/product/detail/c1.jpg","price":300},{"id":4,"leftTime":15000,"limitPrice":98,"name":"帽子","pic":"/images/product/detail/b1.jpg","price":168},{"id":5,"leftTime":14000,"limitPrice":68,"name":"时尚女裙","pic":"/images/product/detail/a1.jpg","price":108},{"id":6,"leftTime":13000,"limitPrice":36,"name":"时尚秋装","pic":"/images/product/detail/w2.jpg","price":52},{"id":7,"leftTime":14000,"limitPrice":120,"name":"韩版外套","pic":"/images/product/detail/qun1.jpg","price":160},{"id":8,"leftTime":14000,"limitPrice":120,"name":"情女装","pic":"/images/product/detail/q6.jpg","price":160},{"id":9,"leftTime":14000,"limitPrice":120,"name":"女鞋","pic":"/images/product/detail/q7.jpg","price":200},{"id":10,"leftTime":14000,"limitPrice":120,"name":"韩版棉袄","pic":"/images/product/detail/q8.jpg","price":160},{"id":11,"leftTime":14000,"limitPrice":120,"name":"韩版秋装","pic":"/images/product/detail/q9.jpg","price":160},{"id":12,"leftTime":14000,"limitPrice":120,"name":"女士外套","pic":"/images/product/detail/q10.jpg","price":160},{"id":13,"leftTime":14000,"limitPrice":120,"name":"时尚女装","pic":"/images/product/detail/q11.jpg","price":200},{"id":14,"leftTime":14000,"limitPrice":120,"name":"萌妹外套","pic":"/images/product/detail/q12.jpg","price":160},{"id":15,"leftTime":14000,"limitPrice":120,"name":"韩版粉嫩外套","pic":"/images/product/detail/q13.jpg","price":160},{"id":16,"leftTime":14000,"limitPrice":120,"name":"韩版秋装","pic":"/images/product/detail/q14.jpg","price":300},{"id":17,"leftTime":14000,"limitPrice":120,"name":"春装新款","pic":"/images/product/detail/q15.jpg","price":200},{"id":18,"leftTime":14000,"limitPrice":120,"name":"短裙","pic":"/images/product/detail/q16.jpg","price":160},{"id":19,"leftTime":14000,"limitPrice":120,"name":"新款秋装","pic":"/images/product/detail/q17.jpg","price":160},{"id":20,"leftTime":14000,"limitPrice":120,"name":"妈妈新款","pic":"/images/product/detail/q18.jpg","price":160},{"id":21,"leftTime":14000,"limitPrice":120,"name":"春秋新款外套","pic":"/images/product/detail/q19.jpg","price":200},{"id":22,"leftTime":14000,"limitPrice":120,"name":"新款毛衣","pic":"/images/product/detail/q20.jpg","price":160},{"id":23,"leftTime":14000,"limitPrice":120,"name":"新款打底上衣","pic":"/images/product/detail/q21.jpg","price":160},{"id":24,"leftTime":14000,"limitPrice":120,"name":"春秋新款外套","pic":"/images/product/detail/q22.jpg","price":160},{"id":25,"leftTime":14000,"limitPrice":120,"name":"新款秋装","pic":"/images/product/detail/q23.jpg","price":160},{"id":26,"leftTime":14000,"limitPrice":120,"name":"粉色系暖心套装","pic":"/images/product/detail/q24.jpg","price":200},{"id":27,"leftTime":14000,"limitPrice":120,"name":"韩版粉嫩外套","pic":"/images/product/detail/q25.jpg","price":160},{"id":28,"leftTime":14000,"limitPrice":120,"name":"春装新款","pic":"/images/product/detail/q26.jpg","price":200},{"id":29,"leftTime":14000,"limitPrice":120,"name":"日本奶粉","pic":"/images/product/detail/q26.jpg","price":160},{"id":30,"leftTime":14000,"limitPrice":120,"name":"超凡奶粉","pic":"/images/product/detail/q26.jpg","price":160},{"id":31,"leftTime":14000,"limitPrice":120,"name":"天籁牧羊奶粉","pic":"/images/product/detail/q26.jpg","price":200}]
     * response : limitbuy
     */

    public int listCount;
    public String response;
    /**
     * id : 2
     * leftTime : 17000
     * limitPrice : 1
     * name : 粉色毛衣
     * pic : /images/product/detail/q1.jpg
     * price : 100
     */

    public List<ProductListBean> productList;

    public static class ProductListBean {
        public String id;
        public String leftTime;
        public String limitPrice;
        public String name;
        public String pic;
        public String price;
    }
}
