package com.lylblog.project.api.QQ.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lylblog.common.api.QQ.properties.OAuthProperties;
import com.lylblog.common.util.CodeUtil;
import com.lylblog.common.util.IdUtil;
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
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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

    @RequestMapping("/qqCallback")
    @ResponseBody
    public ResultObj qqCallback(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, ServletException {
        String code = request.getParameter("code");
        if(StringUtil.isEmpty(code)){
            return ResultObj.fail("未获取到authorizationCode");
        }
        String state = request.getParameter("state");
        if(!state.equals(request.getParameter("state"))) {
            return ResultObj.fail("状态值不匹配");
        }

        String urlForAccessToken = qqService.getUrlForAccessToken(code);
        String access_token = qqService.getAccessToken(urlForAccessToken);
        if(StringUtil.isEmpty(access_token)) {
            return ResultObj.fail("未获取到accessToken");
        }

        String open_id = qqService.getOpenId(access_token);
        if(StringUtil.isEmpty(access_token)) {
            return ResultObj.fail("未获取到openId");
        }
        String userInfo = qqService.getUserInfo(open_id,access_token);
        QQBean qq = JSON.parseObject(userInfo, QQBean.class);

        //获取当前登录用户信息
        UserLoginBean user = ShiroUtils.getUserInfo();
        ResultObj resultObj = qqService.qqLogin(user, qq, open_id);
        String type = "";
        if(user == null) {
            type = "login";  //第三方用户账号登录
        }else {
            type = "bind";   //绑定第三方用户账号
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("type", type);
        resultMap.put("result", resultObj);
        try{
            WebSocketServer.sendInfo(JSON.toJSONString(resultMap) ,ShiroUtils.getSessionId());
        }catch (Exception e){
            e.printStackTrace();
        }
        request.setAttribute("resultObj", resultObj);
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        request.getRequestDispatcher("/error/jump").forward(request, response);
        return null;
    }

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
}
