package com.lylblog.project.system.log.service.impl;

import com.lylblog.common.support.CommonConstant;
import com.lylblog.common.util.AddressUtil;
import com.lylblog.common.util.http.ServletUtil;
import com.lylblog.common.util.shiro.ShiroUtils;
import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.login.bean.UserLoginBean;
import com.lylblog.project.system.log.bean.LoginLogBean;
import com.lylblog.project.system.log.bean.OperLogBean;
import com.lylblog.project.system.log.mapper.LogMapper;
import com.lylblog.project.system.log.service.LogService;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: lyl
 * @Date: 2021/6/13 11:04
 */
@Service
public class LogSrviceImpl implements LogService {

    @Resource
    private LogMapper logMapper;

    /**
     * 新增用户登录日志记录
     * @param user 用户信息
     * @param status 操作状态
     * @param msg 操作消息
     */
    public void insertLoginLogInfo(UserLoginBean user, String status, String msg){
        LoginLogBean loginLog = new LoginLogBean();
        loginLog.setYhnm(user.getYhnm());          // 用户内码
        loginLog.setLoginName(user.getNickname()); // 用户昵称
        loginLog.setOnlineIp(ShiroUtils.getIp());  // 登录IP

        UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtil.getRequest().getHeader("User-Agent"));
        // 获取客户端操作系统
        String os = userAgent.getOperatingSystem().getName();
        loginLog.setLoginSystem(os);
        // 获取客户端浏览器
        String browser = userAgent.getBrowser().getName();
        loginLog.setBrowser(browser);

        loginLog.setLoginAddress(AddressUtil.getRealAddressByIP(loginLog.getOnlineIp()));  //登录地址

        if(CommonConstant.LOGIN_SUCCESS.equals(status)){
            loginLog.setLoginType("1");//登录成功
        }else if(CommonConstant.LOGIN_FAIL.equals(status)){
            loginLog.setLoginType("2");//登录失败
        }

        loginLog.setLoginMsg(msg);  //操作消息
        loginLog.setLoginWay("0");  //登录方式

        logMapper.insertLoginLogInfo(loginLog);
    }

    /**
     * 新增系统操作日志记录
     * @param operLog
     * @return
     */
    public void insertOperLogInfo(OperLogBean operLog){
        logMapper.insertOperLogInfo(operLog);
    }

    /**
     * 查询用户登录日志记录
     * @param loginLog
     * @return
     */
    public ResultObj queryLoginLogInfo(LoginLogBean loginLog){
        int count = logMapper.queryLoginLogInfoCount(loginLog);
        if(count > 0){
            List<LoginLogBean> loginLogList = logMapper.queryLoginLogInfo(loginLog);
            return ResultObj.ok(count, loginLogList);
        }
        return  ResultObj.fail("查询不到登录日志记录");
    }

    /**
     * 查询系统操作日志记录
     * @param operLog
     * @return
     */
    public ResultObj queryOperLogInfo(OperLogBean operLog) {
        int count = logMapper.queryOperLogInfoCount(operLog);
        if(count > 0){
            List<OperLogBean> loginLogList = logMapper.queryOperLogInfo(operLog);
            return ResultObj.ok(count, loginLogList);
        }
        return  ResultObj.fail("查询不到操作日志记录");
    }
}
