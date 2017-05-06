package com.itheima.redbaby.bean;

import org.senydevpkg.net.resp.IResponse;

/**
 * Created by xiongmc on 2016/6/8.
 */
public class LoginResponse implements IResponse {

    /**
     * response :  login
     * userInfo : {" userid ":154636}
     */

    public String response;

    /**
     *  userid  : 154636
     */

    public UserInfoBean userInfo;

    /**
     * error : 请求参数错误或缺失
     * error_code : 1534
     */

    public String error;
    public String error_code;

    public static class UserInfoBean {
        public String userid;
    }


}
