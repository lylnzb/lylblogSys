package com.lylblog.project.webSite.index.bean;

import lombok.Data;

/**
 * @Author: lyl
 * @Date: 2021/1/15 11:20
 */
@Data
public class ArticleListBean {

    private String wznm;             //  文章内码
    private String articleImg;       //  文章图片
    private String articleTitle;     //  文章标题
    private String articleUrl;       //  文章链接
    private String articleDec;       //  文章描述
    private String releasePeople;    //  发布人
    private String releaseTime;      //  发布时间
    private String hits;             //	 点击数
    private String postNum;          //	 评论数
    private String articleForm;      //  文章来源
}
