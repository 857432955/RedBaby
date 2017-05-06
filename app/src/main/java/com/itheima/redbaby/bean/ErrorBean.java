package com.itheima.redbaby.bean;

import org.senydevpkg.net.resp.IResponse;

/**
 * Created by lenovo on 2016/12/9.
 */
public class ErrorBean implements IResponse {


    /**
     * error : 请求参数错误或缺失
     * error_code : 1534
     * response : error
     */

    public String error;
    public String error_code;
    public String response;
}
