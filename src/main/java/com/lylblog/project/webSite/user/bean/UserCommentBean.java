package com.lylblog.project.webSite.user.bean;

import com.lylblog.project.common.bean.ParaBean;
import lombok.Data;

/**
 * @Author: lyl
 * @Date: 2021/6/30 16:42
 */
@Data
public class UserCommentBean extends ParaBean {

    private String commentId;      //  评论id
    private String articleName;    //  文章名称（留言反馈）
    private String articleUrl;     //  文章网址（留言反馈网址）
    private String commentTime;    //  评论时间
    private String comments;       //  评论
    private String commentType;    //  评论类型
    private String commitName;     //  评论人
    private String replyName;      //  回复人
    private String wznm;           //  文章内码
    private String yhnm;           //  用户内码
    private String icon;           //  用户头像

}
