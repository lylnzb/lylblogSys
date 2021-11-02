package com.lylblog.framework.interceptor;

import com.lylblog.common.util.shiro.ShiroUtils;
import com.lylblog.project.common.bean.WebSiteTjBean;
import com.lylblog.project.common.mapper.CommonMapper;
import com.lylblog.project.login.bean.UserLoginBean;
import com.lylblog.project.login.mapper.LoginMapper;
import com.lylblog.project.login.service.LoginService;
import com.lylblog.project.system.blogSet.bean.BlogSetBean;
import com.lylblog.framework.config.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: lyl
 * @Date: 2020/11/14 17:18
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private CommonMapper commonMapper;

    @Autowired
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        boolean flag = false;
        try{
            flag = ShiroUtils.isAuthenticated();
            if(flag){
                UserLoginBean user = loginService.findUserByUsername(ShiroUtils.getUserInfo().getYhnm());
                request.setAttribute("icon", "/profile/" + user.getIconUrl());//
            }
            request.setAttribute("isAuthenticated", flag);//判断用户是否登录
            request.setAttribute("basePath", LylBlogConfig.getBasePath());//项目基础路径
            request.setAttribute("sessionId", ShiroUtils.getSessionId());

            /*获取博客配置信息*/
            BlogSetBean blogSet = commonMapper.getBlogConfiguration();
            request.setAttribute("blogSet", blogSet);

            /*获取网站统计数据*/
            WebSiteTjBean webSiteTj = commonMapper.queryWebSiteTjInfo();
            request.setAttribute("webSiteTj", webSiteTj);

        }catch (Exception e){
            System.out.println("我要忽略你。。。");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
