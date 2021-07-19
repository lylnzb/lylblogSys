package com.lylblog.project.system.log.bean;

import lombok.Data;

/**
 * 日志详情实体类
 * @Author: lyl
 * @Date: 2021/6/25 13:38
 */
@Data
public class LogDetailBean {
    private String operModule;       //   操作模块
    private String loginInfo;        //   登录信息
    private String requestUrl;       //   请求地址
    private String operMethod;       //   操作方法
    private String requestParam;     //   请求参数
    private String status;           //   状态
    private String errorMsg;         //   错误信息
}
