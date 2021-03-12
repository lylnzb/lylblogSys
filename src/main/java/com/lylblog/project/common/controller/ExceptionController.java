package com.lylblog.project.common.controller;

import com.lylblog.common.util.http.CookieUtil;
import com.lylblog.project.common.bean.ResultObj;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.session.UnknownSessionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ExceptionController {

    /**
     * 捕捉shiro的权限不足异常
     * */
    @ExceptionHandler(AuthorizationException.class)
    @ResponseBody
    public ResultObj handleAuthorizationException(HttpServletRequest request) {
        System.out.println(request.getMethod());
        return ResultObj.fail(1,"无该权限的操作权，请联系博主！");
    }

    @ExceptionHandler(UnknownSessionException.class)
    public void handleUnknownSessionException(HttpServletResponse response) {
        CookieUtil.set(response, "sid", null, 0);
    }

    /**
     * 捕捉其他所有异常
     * */
//    @ExceptionHandler(Exception.class)
//    public String globalException(HttpServletRequest request, Throwable ex) {
//        if (ex instanceof NoHandlerFoundException) {
//            return "error/404";
//        }
//        else{
//            return null;
//        }
//    }
}
