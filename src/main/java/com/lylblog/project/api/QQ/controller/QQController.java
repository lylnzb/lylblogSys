package com.lylblog.project.api.QQ.controller;

import com.alibaba.fastjson.JSON;
import com.lylblog.common.api.oauth.OAuthProperties;
import com.lylblog.common.util.CodeUtil;
import com.lylblog.common.util.StringUtil;
import com.lylblog.common.util.shiro.ShiroUtils;
import com.lylblog.framework.server.WebSocketServer;
import com.lylblog.project.api.QQ.bean.QQBean;
import com.lylblog.project.api.QQ.service.QQService;
import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.login.bean.UserLoginBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: lyl
 * @Date: 2021/10/24 16:08
 */
@Controller
@RequestMapping("/api/qq")
public class QQController {

    @Autowired
    private QQService qqService;

    @Autowired
    private OAuthProperties oauth;

    //QQ登陆对外接口，只需将该接口放置html的a标签href中即可
    @GetMapping("/bindQQ")
    public void loginQQ(HttpServletResponse response) {
        try {
            response.sendRedirect(oauth.getQq().getCode_callback_uri() + //获取code码地址
                    "?client_id=" + oauth.getQq().getClient_id()//appid
                    + "&state=" + CodeUtil.getcode() + //这个说是防攻击的，就给个随机uuid吧
                    "&redirect_uri=" + oauth.getQq().getRedirect_uri() +//这个很重要，这个是回调地址，即就收腾讯返回的code码
                    "&response_type=code");//授权模式，授权码模式
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/qqCallback")
    public void qqCallback(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        // 授权码
        String code = request.getParameter("code");
        // client端的状态值
        String state = request.getParameter("state");
        // 获取Access_Token URL地址
        String urlForAccessToken = qqService.getUrlForAccessToken(code);
        // 获取Access_Token
        String access_token = qqService.getAccessToken(urlForAccessToken);
        // 获取open_id
        String open_id = qqService.getOpenId(access_token);
        // 获取当前登录用户信息
        UserLoginBean user = ShiroUtils.getUserInfo();
        // 消息
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if(StringUtil.isEmpty(code)){
            resultMap.put("result", ResultObj.fail("未获取到authorizationCode"));
        }else if(!state.equals(request.getParameter("state"))) {
            resultMap.put("result", ResultObj.fail("状态值不匹配"));
        }else if(StringUtil.isEmpty(access_token)) {
            resultMap.put("result", ResultObj.fail("未获取到accessToken"));
        }else if(StringUtil.isEmpty(open_id)) {
            resultMap.put("result", ResultObj.fail("未获取到openId"));
        }else {
            String userInfo = qqService.getUserInfo(open_id,access_token);
            QQBean qq = JSON.parseObject(userInfo, QQBean.class);
            resultMap.put("result", qqService.qqLogin(user, qq, open_id));
        }
        try{
            String type = "";
            if(user == null) {
                type = "login";  //第三方用户账号登录
            }else {
                type = "bind";   //绑定第三方用户账号
            }
            resultMap.put("type", type);
            // 发送QQ是否登录成功信息
            WebSocketServer.sendInfo(JSON.toJSONString(resultMap) ,ShiroUtils.getSessionId());
        }catch (Exception e){
            e.printStackTrace();
        }
        request.getRequestDispatcher("/error/jump").forward(request, response);
    }

}
