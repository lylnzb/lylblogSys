package com.lylblog.project.login.bean;

import lombok.Data;

/**
 * @Author: lyl
 * @Date: 2021/10/25 22:19
 */
@Data
public class UserAuthsBean {

    private String id;         //	自增主键
    private String yhnm;       //	用户唯一内码
    private String appType;    //	登录方式
    private String appUserId;  //	第三方登录唯一ID
    private String bindFlag;   //	标识是否绑定(1绑定，2解绑)
    private String createDate; //	绑定时间
    private String appNickname;//   昵称

}
