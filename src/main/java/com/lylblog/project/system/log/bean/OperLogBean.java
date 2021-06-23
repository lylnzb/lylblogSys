package com.lylblog.project.system.log.bean;

import com.lylblog.project.common.bean.ParaBean;
import lombok.Data;

/**
 * @Author: lyl
 * @Date: 2021/6/15 16:15
 */
@Data
public class OperLogBean extends ParaBean {

    private String operId;    //	日志主键
    private String title;     //	模块标题
    private String action;    //	功能请求
    private String method;    //	方法名称
    private String channel;   //	来源渠道
    private String loginName; //	登录账号
    private String operUrl;   //	请求URL
    private String operIp;    //	主机地址
    private String operParam; //	请求参数
    private String status;    //	操作状态 0正常 1异常
    private String errorMsg;  //	错误消息
    private String operTime;  //	操作时间
    private String yhnm;      //	用户内码

}
