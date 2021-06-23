package com.lylblog.project.system.log.bean;

import com.lylblog.project.common.bean.ParaBean;
import lombok.Data;

/**
 * @Author: lyl
 * @Date: 2021/6/13 11:08
 */
@Data
public class LoginLogBean extends ParaBean {

    private String logId;            //	记录主键
    private String yhnm;             // 用户内码
    private String loginName;        //	登录名称
    private String onlineIp;         // 在线主机IP
    private String loginAddress;     //	登录地址
    private String browser;          //	浏览器
    private String loginSystem;      //	登录系统
    private String loginType;        //	登录状态（1：登录成功  2：登录失败）
    private String loginWay;         // 登录方式
    private String loginMsg;         //	操作消息
    private String loginTime;        //	登录时间

}
