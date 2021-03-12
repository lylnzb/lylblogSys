package com.lylblog.project.system.article.bean;

import com.lylblog.project.common.bean.ParaBean;

import java.util.List;

/**
 * @Author: lyl
 * @Date: 2020/12/2 19:38
 */
public class ArticleBean extends ParaBean {

    private String articleId;       //	文章编号
    private String wznm;            //  文章内码
    private String columnId;        //	所属栏目编号
    private String articleTitle;    //	文章标题
    private String abstracts;       //	文章摘要
    private String content;         //	文章内容
    private String copyFrom;        //	文章来源
    private String fromWay;         //	文章来源方式（1、原创  2、转载）
    private String fromUrl;         //	文章来源网址
    private String articleLabel;    //	文章标签
    private String hits;            //	点击数
    private String postNum;         //	评论数
    private String onTop;           //	是否置顶
    private String iselite;         //	是否推荐
    private String openIntroduce;   //	开启简介
    private String allowComment;    //	允许评论
    private String allowReprinted;  //	允许转载
    private String thumb;           //	浓缩图路径
    private String articleStatus;   //	文章状态（草稿、审核、正常）

    private String file;             //  文件
    private String columnName;       //  所属专栏
    private String articleStatusName;//  文章状态名称
    private String fromWayName;      //  文章来源方式名称

    private List<LabelSelectBean> label;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getWznm() {
        return wznm;
    }

    public void setWznm(String wznm) {
        this.wznm = wznm;
    }

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getAbstracts() {
        return abstracts;
    }

    public void setAbstracts(String abstracts) {
        this.abstracts = abstracts;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCopyFrom() {
        return copyFrom;
    }

    public void setCopyFrom(String copyFrom) {
        this.copyFrom = copyFrom;
    }

    public String getFromWay() {
        return fromWay;
    }

    public void setFromWay(String fromWay) {
        this.fromWay = fromWay;
    }

    public String getFromUrl() {
        return fromUrl;
    }

    public void setFromUrl(String fromUrl) {
        this.fromUrl = fromUrl;
    }

    public String getArticleLabel() {
        return articleLabel;
    }

    public void setArticleLabel(String articleLabel) {
        this.articleLabel = articleLabel;
    }

    public String getHits() {
        return hits;
    }

    public void setHits(String hits) {
        this.hits = hits;
    }

    public String getPostNum() {
        return postNum;
    }

    public void setPostNum(String postNum) {
        this.postNum = postNum;
    }

    public String getOnTop() {
        return onTop;
    }

    public void setOnTop(String onTop) {
        this.onTop = onTop;
    }

    public String getIselite() {
        return iselite;
    }

    public void setIselite(String iselite) {
        this.iselite = iselite;
    }

    public String getOpenIntroduce() {
        return openIntroduce;
    }

    public void setOpenIntroduce(String openIntroduce) {
        this.openIntroduce = openIntroduce;
    }

    public String getAllowComment() {
        return allowComment;
    }

    public void setAllowComment(String allowComment) {
        this.allowComment = allowComment;
    }

    public String getAllowReprinted() {
        return allowReprinted;
    }

    public void setAllowReprinted(String allowReprinted) {
        this.allowReprinted = allowReprinted;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getArticleStatus() {
        return articleStatus;
    }

    public void setArticleStatus(String articleStatus) {
        this.articleStatus = articleStatus;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getArticleStatusName() {
        return articleStatusName;
    }

    public void setArticleStatusName(String articleStatusName) {
        this.articleStatusName = articleStatusName;
    }

    public String getFromWayName() {
        return fromWayName;
    }

    public void setFromWayName(String fromWayName) {
        this.fromWayName = fromWayName;
    }

    public List<LabelSelectBean> getLabel() {
        return label;
    }

    public void setLabel(List<LabelSelectBean> label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "{" +
                "'articleId':'" + articleId + '\'' +
                ", 'wznm':'" + wznm + '\'' +
                ", 'columnId':'" + columnId + '\'' +
                ", 'articleTitle':'" + articleTitle + '\'' +
                ", 'abstracts':'" + abstracts + '\'' +
                ", 'content':'" + content + '\'' +
                ", 'copyFrom':'" + copyFrom + '\'' +
                ", 'fromWay':'" + fromWay + '\'' +
                ", 'fromUrl':'" + fromUrl + '\'' +
                ", 'articleLabel':'" + articleLabel + '\'' +
                ", 'hits':'" + hits + '\'' +
                ", 'postNum':'" + postNum + '\'' +
                ", 'onTop':'" + onTop + '\'' +
                ", 'iselite':'" + iselite + '\'' +
                ", 'openIntroduce':'" + openIntroduce + '\'' +
                ", 'allowComment':'" + allowComment + '\'' +
                ", 'allowReprinted':'" + allowReprinted + '\'' +
                ", 'thumb':'" + thumb + '\'' +
                ", 'articleStatus':'" + articleStatus + '\'' +
                ", 'file':'" + file + '\'' +
                ", 'columnName':'" + columnName + '\'' +
                ", 'articleStatusName':'" + articleStatusName + '\'' +
                ", 'fromWayName':'" + fromWayName + '\'' +
                ", 'label':" + label +
                '}';
    }
}
