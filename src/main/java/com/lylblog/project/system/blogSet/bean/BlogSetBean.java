package com.lylblog.project.system.blogSet.bean;


import com.lylblog.project.common.bean.ParaBean;
import lombok.Data;

/**
 * @Author: lyl
 * @Date: 2021/1/6 17:23
 */
@Data
public class BlogSetBean extends ParaBean {

    /**  */
    private Integer blogSetId;
    /** 博客标题 */
    private String basicsetTitle;
    /** 博客网站图标 */
    private String basicsetWebsiteIco;
    /** 博客描述 */
    private String basicsetDes;
    /** 网站上线时间 */
    private String basicsetWebSiteOnlineTime;
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

}
