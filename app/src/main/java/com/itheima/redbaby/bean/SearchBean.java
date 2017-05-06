package com.itheima.redbaby.bean;

import org.senydevpkg.net.resp.IResponse;

import java.util.List;

/**
 * Created by ZhangRuxing on 2016-12-07.
 */

public class SearchBean implements IResponse {
    /**
     * response : searchrecommend
     * searchKeywords : ["帽子","时尚女裙","时尚秋装","韩版外套","情女装","女鞋","韩版棉袄","韩版秋装","女士外套"]
     */

    public String response;
    public String[] searchKeywords;
}
