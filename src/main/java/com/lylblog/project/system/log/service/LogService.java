package com.lylblog.project.system.log.service;

import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.login.bean.UserLoginBean;
import com.lylblog.project.system.log.bean.LogDetailBean;
import com.lylblog.project.system.log.bean.LoginLogBean;
import com.lylblog.project.system.log.bean.OperLogBean;
import org.apache.ibatis.annotations.Param;

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
     * 删除用户登录日志记录
     * @param deleteIds
     * @return
     */
    ResultObj deleteLoginLogInfo(List<String> deleteIds);

    /**
     * 新增系统操作日志记录
     * @param operLog
     * @return
     */
    void insertOperLogInfo(OperLogBean operLog);

    /**
     * 删除系统操作日志记录
     * @param deleteIds
     * @return
     */
    ResultObj deleteOperLogInfo(List<String> deleteIds);

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

    /**
     * 查询操作日志详情信息
     * @param logId
     * @return
     */
    ResultObj queryOperLogDetailInfo(String logId);
}
