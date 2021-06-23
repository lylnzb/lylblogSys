package com.lylblog.project.system.log.service;

import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.login.bean.UserLoginBean;
import com.lylblog.project.system.log.bean.LoginLogBean;
import com.lylblog.project.system.log.bean.OperLogBean;

import java.util.List;

/**
 * @Author: lyl
 * @Date: 2021/6/13 11:04
 */
public interface LogService {
    /**
     * 新增用户登录日志记录
     * @param user 用户信息
     * @param status 操作状态
     * @param msg 操作消息
     */
    void insertLoginLogInfo(UserLoginBean user, String status, String msg);

    /**
     * 新增系统操作日志记录
     * @param operLog
     * @return
     */
    void insertOperLogInfo(OperLogBean operLog);

    /**
     * 查询用户登录日志记录
     * @param loginLog
     * @return
     */
    ResultObj queryLoginLogInfo(LoginLogBean loginLog);

    /**
     * 查询系统操作日志记录
     * @param operLog
     * @return
     */
    ResultObj queryOperLogInfo(OperLogBean operLog);
}
