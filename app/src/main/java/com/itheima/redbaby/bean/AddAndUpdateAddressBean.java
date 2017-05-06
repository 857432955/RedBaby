package com.itheima.redbaby.bean;

import org.senydevpkg.net.resp.IResponse;

import java.util.List;

/**
 * Created by lenovo on 2016/12/9.
 */
public class AddAndUpdateAddressBean implements IResponse {

    public String response;

    /**
     * id : 134
     * addressArea : 洪山区
     * addressDetail : 文华路文华学院
     * city : 武汉市
     * isDefault : 0
     * name : 张瑞丽
     * phoneNumber : 18986104910
     * province : 湖北
     * zipCode : 1008611
     */

    public List<AddressList> mAddressLists;

    public static class AddressList {
        public int id;
        public String addressArea;
        public String addressDetail;
        public String city;
        public int isDefault;
        public String name;
        public String phoneNumber;
        public String province;
        public String zipCode;
    }
}
