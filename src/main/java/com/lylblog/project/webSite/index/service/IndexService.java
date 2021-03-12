package com.lylblog.project.webSite.index.service;

import com.lylblog.project.common.bean.ResultObj;

/**
 * @Author: lyl
 * @Date: 2020/11/14 14:58
 */
public interface IndexService {

    /**
     * 展示网站首页轮播图信息
     * @return
     */
    ResultObj showBannerInfo();

    /**
     * 展示网站首页卡片内容信息
     * @param articleType
     * @return
     */
    ResultObj showCardInfo(String articleType);

    /**
     * 展示网站首页最新文章列表信息
     * @return
     */
    ResultObj showArticleInfo();
}
