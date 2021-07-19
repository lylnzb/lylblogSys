package com.lylblog.project.webSite.user.bean;

import lombok.Data;

/**
 * @Author: lyl
 * @Date: 2021/7/6 16:47
 */
@Data
public class UserLinkBean {

    private String linkId;      //  友链编号
    private String linkName;    //  友链名称
    private String linkUrl;     //  友链网址
    private String linkStatus;  //  审核状态
    private String feedbackMsg; //  反馈信息
    private String intro;       //  备注

}
