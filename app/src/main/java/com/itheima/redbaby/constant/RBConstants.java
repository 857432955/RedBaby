package com.itheima.redbaby.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * 常量类
 * 用来保存一些几乎从不轻易改动的变量
 * <p/>
 * Created by xiongmc on 2016/4/28.
 */
public class RBConstants {


    public static String USERID = "";

    public static String LOGINNAME ="";

    public static boolean MISREGISTER = true;

    public static List<String> listCar = new ArrayList<>();
    public static List<Integer> listPosition = new ArrayList<>();

    public static final String URL_SERVER = "http://192.168.16.100:8080/RedBabyServer/";
    /**
     * 首页
     */
    public static final String URL_HOME = URL_SERVER + "home";
    /**
     * 限时抢购
     */
    public static final String URL_LIMIT = URL_SERVER + "limitbuy";
    /**
     * 促销快报
     */
    public static final String URL_TOPIC = URL_SERVER + "topic";
    public static final int REQUEST_CODE_TOPIC = 1;
    /**
     * 推荐品牌
     */
    public static final String URL_BRAND = URL_SERVER + "brand";
    /**
     * 新品上架
     */
    public static final String URL_NEWPRODUCT = URL_SERVER + "newproduct";
    /**
     * 热门单品
     */
    public static final String URL_HOTPRODUCT = URL_SERVER + "hotproduct";

    /**
     * 购物车
     */
    public static final String URL_CART = URL_SERVER + "cart";
    public static final int REQUEST_CODE_CART = 2;

    /**
     * 结算中心
     */
    public static final String URL_CHECKOUT = URL_SERVER + "checkout";
    public static final int REQUEST_CODE_CHECKOUT = 3;

    /**
     * 登录
     */
    public static final String URL_LOGIN = URL_SERVER + "login";
    public static final int REQUEST_CODE_LOGIN = 4;


    /**
     * 地址列表
     */
    public static final String URL_ADDRESS_LIST = URL_SERVER + "addresslist";
    public static final int REQUEST_CODE_ADDRESS_LIST = 5;

    /**
     * 登出
     */
    public static final String URL_LOGOUT = URL_SERVER + "logout";
    public static final int REQUEST_CODE_LOGOUT = 6;
    /**
     * 注册
     */
    public static final String URL_REGISTER = URL_SERVER + "register";
    public static final int REQUEST_CODE_REGISTER = 7;

    /**
     * 用户信息
     */
    public static final String URL_USERINFO = URL_SERVER + "userinfo";
    public static final int REQUEST_CODE_USERINFO = 8;

    /**
     * 修改地址/新增地址
     */
    public static final String URL_ADDRESSASAVE = URL_SERVER + "addresssave";
    public static final int REQUEST_CODE_ADDRESSASAVE = 9;

    /**
     * 搜索推荐
     */
    public static final String URL_SEARCH_LIST = URL_SERVER + "search/recommend";
    public static final int REQUEST_CODE_SEARCH_LIST = 10;

    /**
     *搜索商品列表
     */
    public static final String URL_SEARCH_LIST1 = URL_SERVER + "search";
    public static final int REQUEST_CODE_SEARCH_LIST1 = 11;

    public static final String URL_ORDERSAVE = URL_SERVER + "ordersumbit";
    public static final int REQUEST_CODE_ORDERSAVE = 41;


    public static final String URL_CATEGORY = URL_SERVER + "category";
    public static final int REQUEST_CODE_CATEGORY = 60;

    /**
     * 订单详情
     */
    public static final String URl_ORDERDETAIL = URL_SERVER + "orderdetail";
    public static final int  REQUEST_CODE_ORDERDETAIL = 18;


    /**
     * 取消订单
     */
    public static final String URL_ORDERCANCEL = URL_SERVER + "ordercancel";
    public static final int REQUEST_CODE_ORDERCANCEL = 19;

}
