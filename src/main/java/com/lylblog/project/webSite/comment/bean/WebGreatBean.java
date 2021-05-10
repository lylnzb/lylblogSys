package com.lylblog.project.webSite.comment.bean;

import lombok.Data;

/**
 * 点赞记录表实体类
 * @Author: lyl
 * @Date: 2021/5/5 14:34
 */
@Data
public class WebGreatBean {

    private String greatId;     //	点赞记录表主键
    private String typeId;      //	对应的作品或评论的id
    private String type;        //	点赞类型
    private String yhnm;        //	用户内码
    private String status;      //	点赞状态

    private String greatTime;   // 点赞或取消赞的时间
}
