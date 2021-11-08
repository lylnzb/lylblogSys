package com.lylblog.project.api.weibo.controller;

import com.alibaba.fastjson.JSON;
import com.lylblog.common.api.oauth.OAuthProperties;
import com.lylblog.common.util.CodeUtil;
import com.lylblog.common.util.StringUtil;
import com.lylblog.common.util.shiro.ShiroUtils;
import com.lylblog.framework.server.WebSocketServer;
import com.lylblog.project.api.weibo.bean.AccessTokenBean;
import com.lylblog.project.api.weibo.bean.WeiboBean;
import com.lylblog.project.api.weibo.service.WeiboService;
import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.login.bean.UserLoginBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.annotation.Repeat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: lyl
 * @Date: 2021/11/4 14:29
 */
@Controller
@RequestMapping("/api/weibo")
public class WeiboController {

    @Autowired
    private OAuthProperties oauth;

    @Autowired
    private WeiboService weiboService;

    @GetMapping("/bindWeibo")
    public void bindWeibo(HttpServletResponse response) {
        try {
            response.sendRedirect(oauth.getWeibo().getCode_callback_uri() + //获取code码地址
                    "?client_id=" + oauth.getWeibo().getClient_id() +//appid
                    "&redirect_uri=" + oauth.getWeibo().getRedirect_uri() +//这个很重要，这个是回调地址，即就收腾讯返回的code码
                    "&response_type=code");//授权模式，授权码模式
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/weiboCallback")
    public void weiboCallback(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        // 获取当前登录用户信息
        UserLoginBean user = ShiroUtils.getUserInfo();
        // 授权码
        String code = request.getParameter("code");
        // 消息
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if(StringUtil.isEmpty(code)){
            resultMap.put("result", ResultObj.fail("未获取到authorizationCode"));
        }else {
            AccessTokenBean access_token = weiboService.getAccessToken(code);
            if(access_token == null) {
                resultMap.put("result", ResultObj.fail("未获取到accessToken"));
            }else {
                WeiboBean weibo = weiboService.getUserInfo(access_token);
                resultMap.put("result", weiboService.weiboLogin(user, weibo, access_token.getUid()));
            }
        }
        try{
            String type = "";
            if(user == null) {
                type = "login";  //第三方用户账号登录
            }else {
                type = "bind";   //绑定第三方用户账号
            }
            resultMap.put("type", type);
            // 发送微博是否登录成功信息
            WebSocketServer.sendInfo(JSON.toJSONString(resultMap) , ShiroUtils.getSessionId());
        }catch (Exception e){
            e.printStackTrace();
        }
        request.getRequestDispatcher("/error/jump").forward(request, response);
    }

    @RequestMapping("/cancelWeiboCallback")
    public void cancelWeiboCallback(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("type", "");
        resultMap.put("result", ResultObj.fail("用户已取消授权"));
        try{
            // 发送信息：用户取消授权
            WebSocketServer.sendInfo(JSON.toJSONString(resultMap) , ShiroUtils.getSessionId());
        }catch (Exception e){
            e.printStackTrace();
        }
        request.getRequestDispatcher("/error/jump").forward(request, response);
    }

}
