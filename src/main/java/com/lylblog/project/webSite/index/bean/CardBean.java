package com.lylblog.project.webSite.index.bean;

/**
 * 首页卡片展示实体类
 * @Author: lyl
 * @Date: 2021/1/10 17:56
 */
public class CardBean {
    private String wznm;             //  文章内码
    private String articleImg;       //  文章图片
    private String articleTitle;     //  文章标题
    private String articleUrl;       //  文章链接
    private String articleDec;       //  文章描述
    private String releasePeople;    //  发布人
    private String releaseTime;      //  发布时间

    public String getWznm() {
        return wznm;
    }

    public void setWznm(String wznm) {
        this.wznm = wznm;
    }

    public String getArticleImg() {
        return articleImg;
    }

    public void setArticleImg(String articleImg) {
        this.articleImg = articleImg;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public String getArticleDec() {
        return articleDec;
    }

    public void setArticleDec(String articleDec) {
        this.articleDec = articleDec;
    }

    public String getReleasePeople() {
        return releasePeople;
    }

    public void setReleasePeople(String releasePeople) {
        this.releasePeople = releasePeople;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }
}
