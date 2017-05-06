package com.itheima.redbaby.bean;

import org.senydevpkg.net.resp.IResponse;

import java.util.List;

/**
 * @author 王帅峰
 * @time 2016/12/7  14:04
 * @des 首页Bean
 */
public class HomeBean implements IResponse {

    /**
     * homeTopic : [{"id":130,"pic":"/images/home/topic1.jpg","title":"活动"},{"id":131,"pic":"/images/home/topic2.jpg","title":"活动"},{"id":133,"pic":"/images/home/topic4.jpg","title":"活动"},{"id":136,"pic":"/images/home/topic7.jpg","title":"活动"},{"id":139,"pic":"/images/home/topic11.png","title":"活动"},{"id":140,"pic":"/images/home/topic14.jpg","title":"活动"}]
     * response : home
     */

    public String response;
    /**
     * id : 130
     * pic : /images/home/topic1.jpg
     * title : 活动
     */

    public List<HomeTopicBean> homeTopic;

    public static class HomeTopicBean {
        public int id;
        public String pic;
        public String title;
    }
}
