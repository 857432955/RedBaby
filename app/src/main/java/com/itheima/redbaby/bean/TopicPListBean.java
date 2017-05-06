package com.itheima.redbaby.bean;

import org.senydevpkg.net.resp.IResponse;

import java.util.List;

/**
 * @author 王帅峰
 * @time 2016/12/9  12:02
 * @des
 */
public class TopicPListBean implements IResponse{
    /**
     * productList : [{"id":15,"marketPrice":300,"name":"韩版粉嫩外套","pic":"/images/product/detail/q13.jpg","price":160},{"id":16,"marketPrice":400,"name":"韩版秋装","pic":"/images/product/detail/q14.jpg","price":300},{"id":22,"marketPrice":200,"name":"新款毛衣","pic":"/images/product/detail/q20.jpg","price":160},{"id":23,"marketPrice":150,"name":"新款打底上衣","pic":"/images/product/detail/q21.jpg","price":160},{"id":37,"marketPrice":200,"name":"智力圆环","pic":"/images/product/detail/q26.jpg","price":200},{"id":38,"marketPrice":180,"name":"音乐小天才","pic":"/images/product/detail/q26.jpg","price":160},{"id":39,"marketPrice":180,"name":"小叮当","pic":"/images/product/detail/q26.jpg","price":160},{"id":40,"marketPrice":180,"name":"智力图形记忆起","pic":"/images/product/detail/q26.jpg","price":160},{"id":5,"marketPrice":98,"name":"时尚女裙","pic":"/images/product/detail/a1.jpg","price":108},{"id":6,"marketPrice":100,"name":"时尚秋装","pic":"/images/product/detail/w2.jpg","price":52},{"id":4,"marketPrice":198,"name":"帽子","pic":"/images/product/detail/b1.jpg","price":168}]
     * response : topicProductList
     */

    public String response;
    /**
     * id : 15
     * marketPrice : 300
     * name : 韩版粉嫩外套
     * pic : /images/product/detail/q13.jpg
     * price : 160
     */

    public List<ProductListBean> productList;

    public static class ProductListBean {
        public int id;
        public int marketPrice;
        public String name;
        public String pic;
        public int price;
    }
}
