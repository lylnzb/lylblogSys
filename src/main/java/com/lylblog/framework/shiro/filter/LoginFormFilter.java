package com.lylblog.framework.shiro.filter;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @Author: lyl
 * @Date: 2021/4/25 11:37
 */

public class LoginFormFilter  extends FormAuthenticationFilter {

    /**
          * 在访问controller前判断是否登录，返回json，不进行重定向。
          * @param request
          * @param response
          * @return true-继续往下执行，false-该filter过滤器已经处理，不继续执行其他过滤器
          * @throws Exception
          */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest)request;

        //判断是否为ajax请求，默认不是  ,判断ajax 的方法有很多，需要根据项目的具体情况来判断，我的判断方法如下
        boolean isAjaxRequest = false;
        if(!StringUtils.isBlank(req.getHeader("Accept" ))&& req.getHeader("Accept").equals("application/json, text/javascript, */*; q=0.01")){
            isAjaxRequest = true;
        }
        if (isAjaxRequest) {
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json");
            //httpServletResponse.getWriter().write(JSONObject.toJSON("登录认证失效，请重新登录!").toString());
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            httpServletResponse.setHeader("isLogin", "false");//告诉ajax 网站未登录
            return false;
        } else {
            saveRequestAndRedirectToLogin(request, response);
        }
        return true;
    }
}
