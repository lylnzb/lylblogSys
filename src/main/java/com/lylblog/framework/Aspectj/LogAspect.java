package com.lylblog.framework.Aspectj;

import com.alibaba.fastjson.JSONObject;
import com.lylblog.common.util.StringUtil;
import com.lylblog.common.util.http.ServletUtil;
import com.lylblog.common.util.shiro.ShiroUtils;
import com.lylblog.framework.Aspectj.annotation.Log;
import com.lylblog.project.login.bean.UserLoginBean;
import com.lylblog.project.system.log.bean.OperLogBean;
import com.lylblog.project.system.log.service.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Map;

/**
 * @Author: lyl
 * @Date: 2021/6/13 13:08
 */
@Aspect
@Component
public class LogAspect {

    @Autowired
    private LogService logService;

    // 配置切入点
    @Pointcut("@annotation(com.lylblog.framework.Aspectj.annotation.Log)")
    public void logPointCut() {}

    /**
     * 前置通知 用于拦截操作
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()")
    public void doBefore(JoinPoint joinPoint)
    {
        handleLog(joinPoint, null);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfter(JoinPoint joinPoint, Exception e)
    {
        handleLog(joinPoint, e);
    }

    public void handleLog(JoinPoint joinPoint, Exception e){
        try {
            Signature signature = joinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();

            //获得注解
            Log log = method.getAnnotation(Log.class);

            //获取用户信息
            UserLoginBean user = ShiroUtils.getUserInfo();

            // *========数据库日志=========*//
            OperLogBean operLog = new OperLogBean();
            operLog.setStatus("1");//成功
            operLog.setYhnm(user.getYhnm());//用户内码
            operLog.setOperIp(ShiroUtils.getIp());//操作IP
            operLog.setOperUrl(ServletUtil.getRequest().getRequestURI());//请求URL路径
            operLog.setLoginName(user.getEmail());//登录邮箱
            if (e != null)
            {
                operLog.setStatus("2");//失败
                operLog.setErrorMsg(StringUtil.substring(e.getMessage(), 0, 2000));
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setMethod(className + "." + methodName + "()");

            operLog.setAction(log.functionName());// 功能名称
            operLog.setTitle(log.moduleName());// 设置模块名称
            operLog.setChannel("web");//来源

            Map<String, String[]> map = ServletUtil.getRequest().getParameterMap();
            String params = JSONObject.toJSONString(map);
            operLog.setOperParam(StringUtil.substring(params, 0, 255));

            logService.insertOperLogInfo(operLog);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
