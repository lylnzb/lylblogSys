package com.lylblog.project.system.blogSet.bean;


import com.lylblog.project.common.bean.ParaBean;

/**
 * @Author: lyl
 * @Date: 2021/1/6 17:23
 */
public class BlogSetBean extends ParaBean {

    /**  */
    private Integer blogSetId;
    /** 博客标题 */
    private String basicsetTitle;
    /** 博客网站图标 */
    private String basicsetWebsiteIco;
    /** 博客描述 */
    private String basicsetDes;
    /** 全局允许评论 */
    private char basicsetGlobalAllowComment;
    /** 全局展示评论 */
    private char basicsetGlobalShowComment;
    /** 全局允许转载 */
    private char basicsetGlobalAllowReprint;
    /** 评论是否通知 */
    private char basicsetCommentNotice;
    /** 赞赏是否开启 */
    private char basicsetOpenAppreciate;
    /** 显示文章来源 */
    private char basicsetShowArticleSource;
    /** 评论自动审核 */
    private char basicsetCommentAutoReview;
    /** 是否添加版权声明 */
    private char basicsetAddCopyrightNotice;
    /** 版权声明信息 */
    private String basicsetCopyrightNoticeInfo;
    /** 底部网站版权信息 */
    private String basicsetWebNoticeInfo;
    /** 底部站点声明 */
    private String basicsetBottomsiteInfo;

    /** 博主姓名 */
    private String bloggersetBloggerName;
    /** 个性签名 */
    private String bloggersetBloggerDes;
    /** 博主头像 */
    private String bloggersetBloggerProfile;
    /** 博主QQ */
    private String bloggersetBloggerQqNumber;
    /** 博主微信二维码 */
    private String bloggersetBloggerWechatOrcode;
    /** 背景图片 */
    private String bloggersetBloggerBackroundImg;

    /** 每页显示文章条数 */
    private Integer blogsetPerpageShowNum;
    /** 最新文章显示条数 */
    private Integer blogsetLatestShowNum;
    /** 滚动推荐显示条数 */
    private Integer blogsetScrollRecommendedShowNum;
    /** 列表推荐显示条数 */
    private Integer blogsetRecommendedShowNum;
    /** 点击排行显示条数 */
    private Integer blogsetRankingShowNum;
    /** 书籍推荐显示条数 */
    private Integer blogsetSpecialRecdShowNum;
    /** 主文章无图使用默认图片 */
    private char blogsetNoPicUseDefault;
    /** 无封面图使用内容图 */
    private char blogsetNoCoverpicUseContentpic;
    /** 默认图片集合 */
    private String blogsetDefaultPic;
    /** 友情链接 */
    private String blogsetFriendLinks;

    /** 发送邮件账户 */
    private String eamilsetUsername;
    /** 邮箱授权码 */
    private String eamilsetPassword;
    /** smtp服务主机 */
    private String eamilsetHost;
    /** 服务协议 */
    private String eamilsetProtocol;
    /** 编码集 */
    private String eamilsetDefaultEncoding;
    /** 发送验证码邮箱内容模板 */
    private String eamilsetVerificationCode;

    public Integer getBlogSetId() {
        return blogSetId;
    }

    public void setBlogSetId(Integer blogSetId) {
        this.blogSetId = blogSetId;
    }

    public String getBasicsetTitle() {
        return basicsetTitle;
    }

    public void setBasicsetTitle(String basicsetTitle) {
        this.basicsetTitle = basicsetTitle;
    }

    public String getBasicsetWebsiteIco() {
        return basicsetWebsiteIco;
    }

    public void setBasicsetWebsiteIco(String basicsetWebsiteIco) {
        this.basicsetWebsiteIco = basicsetWebsiteIco;
    }

    public String getBasicsetDes() {
        return basicsetDes;
    }

    public void setBasicsetDes(String basicsetDes) {
        this.basicsetDes = basicsetDes;
    }

    public char getBasicsetGlobalAllowComment() {
        return basicsetGlobalAllowComment;
    }

    public void setBasicsetGlobalAllowComment(char basicsetGlobalAllowComment) {
        this.basicsetGlobalAllowComment = basicsetGlobalAllowComment;
    }

    public char getBasicsetGlobalShowComment() {
        return basicsetGlobalShowComment;
    }

    public void setBasicsetGlobalShowComment(char basicsetGlobalShowComment) {
        this.basicsetGlobalShowComment = basicsetGlobalShowComment;
    }

    public char getBasicsetGlobalAllowReprint() {
        return basicsetGlobalAllowReprint;
    }

    public void setBasicsetGlobalAllowReprint(char basicsetGlobalAllowReprint) {
        this.basicsetGlobalAllowReprint = basicsetGlobalAllowReprint;
    }

    public char getBasicsetCommentNotice() {
        return basicsetCommentNotice;
    }

    public void setBasicsetCommentNotice(char basicsetCommentNotice) {
        this.basicsetCommentNotice = basicsetCommentNotice;
    }

    public char getBasicsetOpenAppreciate() {
        return basicsetOpenAppreciate;
    }

    public void setBasicsetOpenAppreciate(char basicsetOpenAppreciate) {
        this.basicsetOpenAppreciate = basicsetOpenAppreciate;
    }

    public char getBasicsetShowArticleSource() {
        return basicsetShowArticleSource;
    }

    public void setBasicsetShowArticleSource(char basicsetShowArticleSource) {
        this.basicsetShowArticleSource = basicsetShowArticleSource;
    }

    public char getBasicsetCommentAutoReview() {
        return basicsetCommentAutoReview;
    }

    public void setBasicsetCommentAutoReview(char basicsetCommentAutoReview) {
        this.basicsetCommentAutoReview = basicsetCommentAutoReview;
    }

    public char getBasicsetAddCopyrightNotice() {
        return basicsetAddCopyrightNotice;
    }

    public void setBasicsetAddCopyrightNotice(char basicsetAddCopyrightNotice) {
        this.basicsetAddCopyrightNotice = basicsetAddCopyrightNotice;
    }

    public String getBasicsetCopyrightNoticeInfo() {
        return basicsetCopyrightNoticeInfo;
    }

    public void setBasicsetCopyrightNoticeInfo(String basicsetCopyrightNoticeInfo) {
        this.basicsetCopyrightNoticeInfo = basicsetCopyrightNoticeInfo;
    }

    public String getBasicsetWebNoticeInfo() {
        return basicsetWebNoticeInfo;
    }

    public void setBasicsetWebNoticeInfo(String basicsetWebNoticeInfo) {
        this.basicsetWebNoticeInfo = basicsetWebNoticeInfo;
    }

    public String getBasicsetBottomsiteInfo() {
        return basicsetBottomsiteInfo;
    }

    public void setBasicsetBottomsiteInfo(String basicsetBottomsiteInfo) {
        this.basicsetBottomsiteInfo = basicsetBottomsiteInfo;
    }

    public String getBloggersetBloggerName() {
        return bloggersetBloggerName;
    }

    public void setBloggersetBloggerName(String bloggersetBloggerName) {
        this.bloggersetBloggerName = bloggersetBloggerName;
    }

    public String getBloggersetBloggerDes() {
        return bloggersetBloggerDes;
    }

    public void setBloggersetBloggerDes(String bloggersetBloggerDes) {
        this.bloggersetBloggerDes = bloggersetBloggerDes;
    }

    public String getBloggersetBloggerProfile() {
        return bloggersetBloggerProfile;
    }

    public void setBloggersetBloggerProfile(String bloggersetBloggerProfile) {
        this.bloggersetBloggerProfile = bloggersetBloggerProfile;
    }

    public String getBloggersetBloggerQqNumber() {
        return bloggersetBloggerQqNumber;
    }

    public void setBloggersetBloggerQqNumber(String bloggersetBloggerQqNumber) {
        this.bloggersetBloggerQqNumber = bloggersetBloggerQqNumber;
    }

    public String getBloggersetBloggerWechatOrcode() {
        return bloggersetBloggerWechatOrcode;
    }

    public void setBloggersetBloggerWechatOrcode(String bloggersetBloggerWechatOrcode) {
        this.bloggersetBloggerWechatOrcode = bloggersetBloggerWechatOrcode;
    }

    public String getBloggersetBloggerBackroundImg() {
        return bloggersetBloggerBackroundImg;
    }

    public void setBloggersetBloggerBackroundImg(String bloggersetBloggerBackroundImg) {
        this.bloggersetBloggerBackroundImg = bloggersetBloggerBackroundImg;
    }

    public Integer getBlogsetPerpageShowNum() {
        return blogsetPerpageShowNum;
    }

    public void setBlogsetPerpageShowNum(Integer blogsetPerpageShowNum) {
        this.blogsetPerpageShowNum = blogsetPerpageShowNum;
    }

    public Integer getBlogsetLatestShowNum() {
        return blogsetLatestShowNum;
    }

    public void setBlogsetLatestShowNum(Integer blogsetLatestShowNum) {
        this.blogsetLatestShowNum = blogsetLatestShowNum;
    }

    public Integer getBlogsetScrollRecommendedShowNum() {
        return blogsetScrollRecommendedShowNum;
    }

    public void setBlogsetScrollRecommendedShowNum(Integer blogsetScrollRecommendedShowNum) {
        this.blogsetScrollRecommendedShowNum = blogsetScrollRecommendedShowNum;
    }

    public Integer getBlogsetRecommendedShowNum() {
        return blogsetRecommendedShowNum;
    }

    public void setBlogsetRecommendedShowNum(Integer blogsetRecommendedShowNum) {
        this.blogsetRecommendedShowNum = blogsetRecommendedShowNum;
    }

    public Integer getBlogsetRankingShowNum() {
        return blogsetRankingShowNum;
    }

    public void setBlogsetRankingShowNum(Integer blogsetRankingShowNum) {
        this.blogsetRankingShowNum = blogsetRankingShowNum;
    }

    public Integer getBlogsetSpecialRecdShowNum() {
        return blogsetSpecialRecdShowNum;
    }

    public void setBlogsetSpecialRecdShowNum(Integer blogsetSpecialRecdShowNum) {
        this.blogsetSpecialRecdShowNum = blogsetSpecialRecdShowNum;
    }

    public char getBlogsetNoPicUseDefault() {
        return blogsetNoPicUseDefault;
    }

    public void setBlogsetNoPicUseDefault(char blogsetNoPicUseDefault) {
        this.blogsetNoPicUseDefault = blogsetNoPicUseDefault;
    }

    public char getBlogsetNoCoverpicUseContentpic() {
        return blogsetNoCoverpicUseContentpic;
    }

    public void setBlogsetNoCoverpicUseContentpic(char blogsetNoCoverpicUseContentpic) {
        this.blogsetNoCoverpicUseContentpic = blogsetNoCoverpicUseContentpic;
    }

    public String getBlogsetDefaultPic() {
        return blogsetDefaultPic;
    }

    public void setBlogsetDefaultPic(String blogsetDefaultPic) {
        this.blogsetDefaultPic = blogsetDefaultPic;
    }

    public String getBlogsetFriendLinks() {
        return blogsetFriendLinks;
    }

    public void setBlogsetFriendLinks(String blogsetFriendLinks) {
        this.blogsetFriendLinks = blogsetFriendLinks;
    }

    public String getEamilsetUsername() {
        return eamilsetUsername;
    }

    public void setEamilsetUsername(String eamilsetUsername) {
        this.eamilsetUsername = eamilsetUsername;
    }

    public String getEamilsetPassword() {
        return eamilsetPassword;
    }

    public void setEamilsetPassword(String eamilsetPassword) {
        this.eamilsetPassword = eamilsetPassword;
    }

    public String getEamilsetHost() {
        return eamilsetHost;
    }

    public void setEamilsetHost(String eamilsetHost) {
        this.eamilsetHost = eamilsetHost;
    }

    public String getEamilsetProtocol() {
        return eamilsetProtocol;
    }

    public void setEamilsetProtocol(String eamilsetProtocol) {
        this.eamilsetProtocol = eamilsetProtocol;
    }

    public String getEamilsetDefaultEncoding() {
        return eamilsetDefaultEncoding;
    }

    public void setEamilsetDefaultEncoding(String eamilsetDefaultEncoding) {
        this.eamilsetDefaultEncoding = eamilsetDefaultEncoding;
    }

    public String getEamilsetVerificationCode() {
        return eamilsetVerificationCode;
    }

    public void setEamilsetVerificationCode(String eamilsetVerificationCode) {
        this.eamilsetVerificationCode = eamilsetVerificationCode;
    }
}
