package com.itheima.redbaby.protocol;

import com.android.volley.Request;
import com.itheima.redbaby.base.MyApplication;
import com.itheima.redbaby.bean.AddAndUpdateAddressBean;
import com.itheima.redbaby.bean.AddressListBean;
import com.itheima.redbaby.bean.CategoryBean;
import com.itheima.redbaby.bean.CheckoutResponse;
import com.itheima.redbaby.bean.BrandPListBean;
import com.itheima.redbaby.bean.HomeBean;
import com.itheima.redbaby.bean.LimitBean;
import com.itheima.redbaby.bean.NewProductBean;
import com.itheima.redbaby.bean.OrderCancelBean;
import com.itheima.redbaby.bean.OrderDetailBean;
import com.itheima.redbaby.bean.RecommendBrandBean;
import com.itheima.redbaby.bean.SearchBean;
import com.itheima.redbaby.bean.SearchListBean;
import com.itheima.redbaby.bean.TopicPListBean;
import com.itheima.redbaby.bean.TopicResponse;
import com.itheima.redbaby.bean.UserInfoBean;
import com.itheima.redbaby.constant.RBConstants;
import com.itheima.redbaby.utils.LogUtils;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;
import org.senydevpkg.net.resp.IResponse;

/**
 * Created by yanjingpan on 2016/8/5.
 */
public class Api {


    /**
     * 获取促销快报的网络请求
     *
     * @param listener
     * @return
     */
    public static Request getTopicData(HttpLoader.HttpListener listener) {
        String url = RBConstants.URL_TOPIC;
        HttpParams params = new HttpParams();
        params.put("page", "1").put("pageNum", "20");
        Class<? extends IResponse> clazz = TopicResponse.class;
        int requestCode = RBConstants.REQUEST_CODE_TOPIC;
        return MyApplication.HL.get(url, params, clazz, requestCode, listener);
    }

    /**
     * 获取促销快报点进去后商品列表的网络请求
     *
     * @param listener
     * @return
     */
    public static Request getTopicPListData(HttpLoader.HttpListener listener, String id, String orderby) {
        String url = RBConstants.URL_TOPIC + "/plist";
        HttpParams params = new HttpParams();
        params.put("page", "1").put("pageNum", "20").put("id", id).put("orderby", orderby);
        Class<? extends IResponse> clazz = TopicPListBean.class;
        int requestCode = RBConstants.REQUEST_CODE_TOPIC;
        return MyApplication.HL.get(url, params, clazz, requestCode, listener);
    }

    /**
     * 获取新品上架的网络请求
     *
     * @param listener
     * @return
     */
    public static Request getNewProductData(HttpLoader.HttpListener listener, String orderby) {
        String url = RBConstants.URL_NEWPRODUCT;
        HttpParams params = new HttpParams();
        params.put("page", "1").put("pageNum", "20").put("orderby", orderby);
        Class<? extends IResponse> clazz = NewProductBean.class;
        int requestCode = RBConstants.REQUEST_CODE_TOPIC;
        return MyApplication.HL.get(url, params, clazz, requestCode, listener);
    }

    /**
     * 获取热门单品的网络请求
     *
     * @param listener
     * @return
     */
    public static Request getHotProductData(HttpLoader.HttpListener listener, String orderby) {
        String url = RBConstants.URL_HOTPRODUCT;
        HttpParams params = new HttpParams();
        params.put("page", "1").put("pageNum", "20").put("orderby", orderby);
        Class<? extends IResponse> clazz = NewProductBean.class;
        int requestCode = RBConstants.REQUEST_CODE_TOPIC;
        return MyApplication.HL.get(url, params, clazz, requestCode, listener);
    }

    /**
     * 获取限时抢购的网络请求
     *
     * @param listener
     * @return
     */
    public static Request getLimitData(HttpLoader.HttpListener listener) {
        String url = RBConstants.URL_LIMIT;
        HttpParams params = new HttpParams();
        params.put("page", "1").put("pageNum", "20");
        Class<? extends IResponse> clazz = LimitBean.class;
        int requestCode = RBConstants.REQUEST_CODE_TOPIC;
        return MyApplication.HL.get(url, params, clazz, requestCode, listener);
    }

    public static Request getCheckoutData(HttpLoader.HttpListener listener, String sku, String userid) {
        String url = RBConstants.URL_CHECKOUT;
        HttpParams params = new HttpParams();
        params.put("sku", sku);
        params.addHeader("userid", userid);
        Class<? extends IResponse> clazz = CheckoutResponse.class;
        int requestcode = RBConstants.REQUEST_CODE_TOPIC;
        return MyApplication.HL.get(url, params, clazz, requestcode, listener);
    }

    /**
     * 获取账户信息的网络数据
     *
     * @param listener
     * @return
     */
    public static Request getUserInfo(HttpLoader.HttpListener listener) {
        String url = RBConstants.URL_USERINFO;
        HttpParams params = new HttpParams();
        params.addHeader("userid", RBConstants.USERID);
        Class<? extends IResponse> clazz = UserInfoBean.class;
        int requestcode = RBConstants.REQUEST_CODE_USERINFO;
        return MyApplication.HL.get(url, params, clazz, requestcode, listener);
    }

    /**
     * 获取地址列表的网络数据
     *
     * @param listener
     * @return
     */
    public static Request getAddressList(HttpLoader.HttpListener listener) {
        String url = RBConstants.URL_ADDRESS_LIST;
        HttpParams params = new HttpParams();
        params.addHeader("userid", RBConstants.USERID);
        Class<? extends IResponse> clazz = AddressListBean.class;
        int requestcode = RBConstants.REQUEST_CODE_ADDRESS_LIST;
        return MyApplication.HL.get(url, params, clazz, requestcode, listener);
    }

    /**
     * 新增地址
     *
     * @param listener
     * @param params
     * @return
     */
    public static Request PostAddAddress(HttpLoader.HttpListener listener, HttpParams params) {
        String url = RBConstants.URL_ADDRESSASAVE;
        Class<? extends IResponse> clazz = AddAndUpdateAddressBean.class;
        int requestcode = RBConstants.REQUEST_CODE_ADDRESSASAVE;
        return MyApplication.HL.post(url, params, clazz, requestcode, listener);
    }


    /**
     * 获取推荐品牌的网络数据
     *
     * @param listener
     */
    public static Request<?> getBrandData(HttpLoader.HttpListener listener) {
        String url = RBConstants.URL_BRAND;
        Class<? extends IResponse> clazz = RecommendBrandBean.class;
        int requestCode = RBConstants.REQUEST_CODE_TOPIC;
        return MyApplication.HL.get(url, null, clazz, requestCode, listener);
    }

    /**
     * 获取促销快报点进去后商品列表的网络请求
     *
     * @param listener
     * @return
     */
    public static Request getBrandPListData(HttpLoader.HttpListener listener, String id, String orderby) {
        String url = RBConstants.URL_BRAND + "/plist";
        HttpParams params = new HttpParams();
        params.put("page", "1").put("pageNum", "20").put("id", id).put("orderby", orderby);
        Class<? extends IResponse> clazz = BrandPListBean.class;
        int requestCode = RBConstants.REQUEST_CODE_TOPIC;
        return MyApplication.HL.get(url, params, clazz, requestCode, listener);
    }

    /**
     * 获取首页的网络数据
     *
     * @param listener
     */
    public static Request<?> getHomeData(HttpLoader.HttpListener listener) {
        String url = RBConstants.URL_HOME;
        Class<? extends IResponse> clazz = HomeBean.class;
        int requestCode = RBConstants.REQUEST_CODE_TOPIC;
        return MyApplication.HL.get(url, null, clazz, requestCode, listener);
    }

    /**
     * 添加订单
     *
     * @param listener
     * @param params
     */
    public static Request<?> PostAddOrder(HttpLoader.HttpListener listener, HttpParams params) {
        String url = RBConstants.URL_ORDERSAVE;
        Class<? extends IResponse> clazz = AddAndUpdateAddressBean.class;
        int requestcode = RBConstants.REQUEST_CODE_ORDERSAVE;
        return MyApplication.HL.post(url, params, clazz, requestcode, listener);
    }

    /**
     * 搜索数据
     *
     * @param listener
     * @return
     */
    public static Request getSearchData(HttpLoader.HttpListener listener) {
        String url = RBConstants.URL_SEARCH_LIST;
        Class<? extends IResponse> clazz = SearchBean.class;
        int requestCode = RBConstants.REQUEST_CODE_SEARCH_LIST;
        return MyApplication.HL.get(url, null, clazz, requestCode, listener);
    }

    public static Request getSearchDataList(HttpLoader.HttpListener listener, String keyword, String orderby) {
        String url = RBConstants.URL_SEARCH_LIST1;
        HttpParams params = new HttpParams();
        String mOrderby;
        if (orderby == null || orderby == "") {
            mOrderby = "saleDown";
        } else {
            mOrderby = orderby;
        }
        LogUtils.s("排序状态------>" + mOrderby);
        params.put("keyword", keyword).put("page", "1").put("pageNum", "20").put("orderby", mOrderby);
        Class<? extends IResponse> clazz = SearchListBean.class;
        int requestCode = RBConstants.REQUEST_CODE_SEARCH_LIST1;
        return MyApplication.HL.get(url, params, clazz, requestCode, listener);
    }

    /**
     * 分类数据
     *
     * @param listener
     * @return
     */
    public static Request getCatgoryData(HttpLoader.HttpListener listener) {
        String url = RBConstants.URL_CATEGORY;
        Class<? extends IResponse> clazz = CategoryBean.class;
        int requestCode = RBConstants.REQUEST_CODE_CATEGORY;
        return MyApplication.HL.get(url, null, clazz, requestCode, listener);
    }

    /**
     * 修改地址
     *
     * @param listener
     * @param params
     * @return
     */
    public static Request PostUpdateAddress(HttpLoader.HttpListener listener, HttpParams params) {
        String url = RBConstants.URL_ADDRESSASAVE;
        Class<? extends IResponse> clazz = AddAndUpdateAddressBean.class;
        int requestcode = RBConstants.REQUEST_CODE_ADDRESSASAVE;
        return MyApplication.HL.post(url, params, clazz, requestcode, listener);
    }


    /**
     * 获取订单详情
     *
     * @param listener
     * @param params
     * @return
     */
    public static Request getOrderDetail(HttpLoader.HttpListener listener, HttpParams params) {
        String url = RBConstants.URl_ORDERDETAIL;
        Class<? extends IResponse> clazz = OrderDetailBean.class;
        int requestcode = RBConstants.REQUEST_CODE_ORDERDETAIL;
        return MyApplication.HL.get(url, params, clazz, requestcode, listener);
    }

    public static Request postOrderCancel(HttpLoader.HttpListener listener, HttpParams params) {
        String url = RBConstants.URL_ORDERCANCEL;
        Class<? extends IResponse> clazz = OrderCancelBean.class;
        int requestcode = RBConstants.REQUEST_CODE_ORDERCANCEL;
        return MyApplication.HL.post(url, params, clazz, requestcode, listener);
    }


}

