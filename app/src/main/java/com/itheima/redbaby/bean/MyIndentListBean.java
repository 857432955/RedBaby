package com.itheima.redbaby.bean;

import org.senydevpkg.net.resp.IResponse;

import java.util.List;

/**
 * .::::.
 * .::::::::.
 * :::::::::::
 * ..:::::::::::'
 * '::::::::::::'
 * .::::::::::
 * '::::::::::::::..
 * ..::::::::::::.
 * ``::::::::::::::::
 * ::::``:::::::::'        .:::.
 * ::::'   ':::::'       .::::::::.
 * .::::'      ::::     .:::::::'::::.
 * .:::'       :::::  .:::::::::' ':::::.
 * .::'        :::::.:::::::::'      ':::::.
 * .::'         ::::::::::::::'         ``::::.
 * ...:::           ::::::::::::'              ``::.
 * ```` ':.          ':::::::::'                  ::::..
 * '.:::::'                    ':'````..
 * Created by lx on 2016/12/10.
 */
public class MyIndentListBean implements IResponse {

    /**
     * response : orderList
     * orderList : [{"orderId":"260092","status":"未处理","time":"1439528260115","price":"208","flag":"1"},{"orderId":"171835","status":"未处理","time":"1439529171852","price":"\u201c208\u201d","flag":"1"},{"orderId":" 879495 ","status":"未处理","time":" 1449037879509 ","price":"\u201c450\u201d","flag":"1"}]
     */

    public String response;
    /**
     * orderId : 260092
     * status : 未处理
     * time : 1439528260115
     * price : 208
     * flag : 1
     */

    public List<OrderListBean> orderList;

    public static class OrderListBean {
        public String orderId;
        public String status;
        public String time;
        public String price;
        public String flag;
    }
}
