package com.itheima.redbaby.utils;

import com.android.volley.Request;
import com.itheima.redbaby.base.MyApplication;
import com.itheima.redbaby.bean.Clothes_bean;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.HttpParams;
import org.senydevpkg.net.resp.IResponse;

/**
 * Created by Administrator on 2016/12/7.
 */
public class ll_Api {

    public static Request getClothesData(HttpLoader.HttpListener listener,String num) {
        String url = ProductDetailContains.URL_product;//localhost:8080/RedBabyServer/product?pId=1
        HttpParams params = new HttpParams();
        params.put("pId",num);
        Class<? extends IResponse> clazz = Clothes_bean.class;
        int requestCode = ProductDetailContains.REQUEST_CODE_URL_product;
        return MyApplication.HL.get(url, params, clazz, requestCode, listener);
    }
}
