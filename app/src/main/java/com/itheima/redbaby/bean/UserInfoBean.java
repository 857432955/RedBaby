package com.itheima.redbaby.bean;

import org.senydevpkg.net.resp.IResponse;

/**
 * Created by sdc on 2016/12/8.
 * 账户中心的javaBean
 */

public class UserInfoBean implements IResponse{


    /**
     * response : userInfo
     * userInfo : {"bonus":0,"favoritesCount":17,"level":"普通会员","orderCount":132,"userid":"20428"}
     */

    public String response;
    /**
     * bonus : 0
     * favoritesCount : 17
     * level : 普通会员
     * orderCount : 132
     * userid : 20428
     */

    public UserInfo userInfo;

    public static class UserInfo{
        public int bonus;
        public int favoritesCount;
        public String level;
        public int orderCount;
        public String userid;
    }
}
