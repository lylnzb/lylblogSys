package com.lylblog.project.api.weibo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lylblog.common.api.oauth.OAuthProperties;
import com.lylblog.common.util.DateUtil;
import com.lylblog.common.util.IdUtil;
import com.lylblog.framework.shiro.authc.CustomToken;
import com.lylblog.project.api.weibo.bean.AccessTokenBean;
import com.lylblog.project.api.weibo.bean.WeiboBean;
import com.lylblog.project.api.weibo.service.*;
import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.login.bean.UserAuthsBean;
import com.lylblog.project.login.bean.UserLoginBean;
import com.lylblog.project.login.service.LoginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: lyl
 * @Date: 2021/11/4 14:28
 */
@Service
public class WeiboServiceImpl implements WeiboService {

    @Autowired
    private OAuthProperties oauth;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoginService loginService;

    /**
     * 获取accessToken
     * @param code
     * @return
     */
    public AccessTokenBean getAccessToken(String code) {
        String grant_type = "authorization_code";
        String client_id = oauth.getWeibo().getClient_id();
        String client_secret = oauth.getWeibo().getClient_secret();
        String redirect_uri = oauth.getWeibo().getRedirect_uri();
        String url = String.format(oauth.getWeibo().getAccess_token_callback_uri() +
                        "?grant_type=%s&client_id=%s&client_secret=%s&code=%s&redirect_uri=%s",
                grant_type, client_id, client_secret, code, redirect_uri);

        String firstCallbackInfo = restTemplate.postForObject(url, "", String.class);
        AccessTokenBean accessToken = JSON.parseObject(firstCallbackInfo, AccessTokenBean.class);
        return accessToken;
    }

    /**
     * 获取用户信息
     * @param accessToken
     * @return
     */
    public WeiboBean getUserInfo(AccessTokenBean accessToken) {
        String infoUrl = String.format(oauth.getWeibo().getUser_info_callback_uri() + "?access_token=%s&uid=%s", accessToken.getAccess_token(), accessToken.getUid());
        String userInfo = restTemplate.getForObject(infoUrl, String.class);
        WeiboBean weibo = JSON.parseObject(userInfo, WeiboBean.class);
        return weibo;
    }

    /**
     * 第三方微博登录
     * @param user
     * @param weibo
     * @param uid
     * @return
     * @throws IOException
     */
    public ResultObj weiboLogin(UserLoginBean user, WeiboBean weibo, String uid) throws IOException {
        UserAuthsBean userAuths = loginService.getUserAuthsByOpenId(uid);
        if(null != user) {
            if(null == userAuths) {
                UserAuthsBean userAuthsBean = new UserAuthsBean();
                userAuthsBean.setYhnm(user.getYhnm());                 //用户唯一内码
                userAuthsBean.setAppType("2");                         //登录方式
                userAuthsBean.setAppUserId(uid);                       //第三方登录唯一ID
                userAuthsBean.setBindFlag("1");                        //标识是否绑定(1绑定，2解绑)
                userAuthsBean.setAppNickname(weibo.getScreen_name());  //昵称
                loginService.addUserAuths(userAuthsBean);     //新增第三方登录信息
                return ResultObj.ok("绑定成功！");
            }else {
                return ResultObj.fail("微博 " + weibo.getScreen_name() + " 已被其他账号绑定，无法绑定！");
            }
        }else {
            UserLoginBean userLogin = new UserLoginBean();
            if(null == userAuths) {
                userLogin.setYhnm(IdUtil.getUUID());            //用户唯一内码
                userLogin.setNickname(weibo.getScreen_name());  //用户昵称
                userLogin.setSex((null != weibo.getGender() && "f".equals(weibo.getGender())?"1":"0"));//性别
                userLogin.setAppType("2");
                userLogin.setIconUrl(weibo.getAvatar_hd());
                userLogin.setRegtime(DateUtil.getCurrentDatetime()); //注册时间
                int i = loginService.registerUser(userLogin);        //用户注册
                if(i == 1) {
                    try {
                        UserAuthsBean userAuthsBean = new UserAuthsBean();
                        userAuthsBean.setYhnm(userLogin.getYhnm());            //用户唯一内码
                        userAuthsBean.setAppType(userLogin.getAppType());      //登录方式
                        userAuthsBean.setAppUserId(uid);                       //第三方登录唯一ID
                        userAuthsBean.setBindFlag("1");                        //标识是否绑定(1绑定，2解绑)
                        userAuthsBean.setAppNickname(weibo.getScreen_name());  //昵称
                        loginService.addUserAuths(userAuthsBean);              //新增第三方登录信息
                    }catch (Exception e) {
                        e.printStackTrace();
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//就是这一句了，加上之后，如果doDbStuff2()抛了异常
                    }
                }
            }else {
                userLogin.setYhnm(userAuths.getYhnm());
            }
            // 从SecurityUtils里边创建一个 subject
            Subject subject = SecurityUtils.getSubject();
            // 在认证提交前准备 token（令牌）
            CustomToken token = new CustomToken(uid);
            subject.login(token);
            if (subject.isAuthenticated()) {
                return ResultObj.ok("登录成功");
            } else {
                token.clear();
                return ResultObj.fail(1,"登录失败");
            }
        }
    }

}
