package com.lylblog.project.webSite.comment.bean;

import com.lylblog.project.common.bean.ParaBean;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @Author: lyl
 * @Date: 2021/4/19 16:48
 */
@Data
public class WebCommentBean extends ParaBean {
    private String id;           //  评论id
    private String iconUrl;      //  评论用户头像
    private String content;      //  用户评论内容
    private String praiseNum;    //  点赞数
    private String time;         //  评论时间
    private String commitName;   //  评论人
    private String replyName;    //  回复人

    private boolean isGiveLike;        //    是否已点赞

    private List<WebCommentBean> childCommentList;
}
