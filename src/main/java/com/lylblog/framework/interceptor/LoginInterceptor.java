package com.lylblog.framework.interceptor;

import com.lylblog.common.util.shiro.ShiroUtils;
import com.lylblog.project.common.mapper.CommonMapper;
import com.lylblog.project.login.bean.UserLoginBean;
import com.lylblog.project.login.mapper.LoginMapper;
import com.lylblog.project.system.blogSet.bean.BlogSetBean;
import com.lylblog.framework.config.*;
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

    @Resource
    private LoginMapper loginMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        boolean flag = false;
        try{
            flag = ShiroUtils.isAuthenticated();
            if(flag){
                UserLoginBean user = loginMapper.findUserByEmail(ShiroUtils.getUserInfo().getEmail());
                request.setAttribute("icon", "/profile/" + user.getIconUrl());//
            }
            request.setAttribute("isAuthenticated", flag);//判断用户是否登录
            request.setAttribute("basePath", LylBlogConfig.getBasePath());//项目基础路径

            /*获取博客配置信息*/
            BlogSetBean blogSet = commonMapper.getBlogConfiguration();
            request.setAttribute("blogSet", blogSet);
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
