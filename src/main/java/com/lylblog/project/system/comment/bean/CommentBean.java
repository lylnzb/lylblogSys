package com.lylblog.project.system.comment.bean;

import com.lylblog.project.common.bean.ParaBean;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: lyl
 * @Date: 2021/4/19 16:48
 */
@Data
public class CommentBean extends ParaBean {

    private String commentId;         //	评论id
    private String replyId;           //	回复id
    private String wznm;              //	文章内码
    @NotBlank(message="评论内容不能为空")
    private String commentContent;    //	评论内容
    private String commentType;       //	评论类型
    private String praiseNum;         //	点赞数
    private String valid;             //	有效标志[1:有效,0:无效]
    private String submitPerson;      //	提交人
    private String submitTime;        //	提交时间
    private String auditPerson;       //	审核人
    private String auditStatus;       //	审核状态
    private String auditTime;         //	审核时间
    private String auditReason;       //	审核原因

    private String yhnm;
}
