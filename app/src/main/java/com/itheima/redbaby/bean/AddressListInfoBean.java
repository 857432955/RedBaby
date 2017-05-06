package com.itheima.redbaby.bean;

import org.senydevpkg.net.resp.IResponse;

import java.util.List;

/**
 * Created by cxw on 2016/12/9.
 */
public class AddressListInfoBean {

        public String addressDetail;
        public String name;
        public String phoneNumber;
        public int position;

        @Override
        public String toString() {
                return "AddressListInfoBean{" +
                        "addressDetail='" + addressDetail + '\'' +
                        ", name='" + name + '\'' +
                        ", phoneNumber='" + phoneNumber + '\'' +
                        ", position=" + position +
                        '}';
        }
}
