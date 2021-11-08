package com.lylblog.project.api.QQ.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lylblog.common.api.oauth.OAuthProperties;
import com.lylblog.common.util.DateUtil;
import com.lylblog.common.util.IdUtil;
import com.lylblog.common.util.StringUtil;
import com.lylblog.framework.shiro.authc.CustomToken;
import com.lylblog.project.api.QQ.bean.QQBean;
import com.lylblog.project.api.QQ.service.QQService;
import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.login.bean.*;
import com.lylblog.project.login.service.LoginService;
import com.lylblog.project.system.admin.service.AdminService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: lyl
 * @Date: 2021/10/24 16:08
 */
@Service
public class QQServiceImpl implements QQService {

    @Autowired
    private OAuthProperties oauth;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoginService loginService;

    /**
     * 第三方QQ登录
     * @param user
     * @param qq
     * @param open_id
     * @return
     * @throws IOException
     */
    @Transactional
    public ResultObj qqLogin(UserLoginBean user, QQBean qq, String open_id) throws IOException {

        UserAuthsBean userAuths = loginService.getUserAuthsByOpenId(open_id);
        if(null != user) {
            if(null == userAuths) {
                UserAuthsBean userAuthsBean = new UserAuthsBean();
                userAuthsBean.setYhnm(user.getYhnm());            //用户唯一内码
                userAuthsBean.setAppType("1");                    //登录方式
                userAuthsBean.setAppUserId(open_id);              //第三方登录唯一ID
                userAuthsBean.setBindFlag("1");                   //标识是否绑定(1绑定，2解绑)
                userAuthsBean.setAppNickname(qq.getNickname());   //昵称
                loginService.addUserAuths(userAuthsBean);         //新增第三方登录信息
                return ResultObj.ok("绑定成功！");
            }else {
                return ResultObj.fail("QQ " + qq.getNickname() + " 已被其他账号绑定，无法绑定！");
            }
        }else {
            UserLoginBean userLogin = new UserLoginBean();
            if(null == userAuths) {
                userLogin.setYhnm(IdUtil.getUUID());      //用户唯一内码
                userLogin.setNickname(qq.getNickname());  //用户昵称
                userLogin.setSex((null != qq.getGender() && "女".equals(qq.getGender())?"1":"0"));//性别
                userLogin.setAppType("1");
                userLogin.setIconUrl(qq.getFigureurl_qq_2());
                userLogin.setRegtime(DateUtil.getCurrentDatetime()); //注册时间
                int i = loginService.registerUser(userLogin);      //用户注册
                if(i == 1) {
                    try {
                        UserAuthsBean userAuthsBean = new UserAuthsBean();
                        userAuthsBean.setYhnm(userLogin.getYhnm());        //用户唯一内码
                        userAuthsBean.setAppType(userLogin.getAppType());  //登录方式
                        userAuthsBean.setAppUserId(open_id);               //第三方登录唯一ID
                        userAuthsBean.setBindFlag("1");                    //标识是否绑定(1绑定，2解绑)
                        userAuthsBean.setAppNickname(qq.getNickname());    //昵称
                        loginService.addUserAuths(userAuthsBean);          //新增第三方登录信息
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
            CustomToken token = new CustomToken(open_id);
            subject.login(token);
            if (subject.isAuthenticated()) {
                return ResultObj.ok("登录成功");
            } else {
                token.clear();
                return ResultObj.fail(1,"登录失败");
            }
        }
    }

    /**
     * 拼接获取accessToken的URL路径
     * @param code
     * @return
     */
    public String getUrlForAccessToken(String code) {
        if(StringUtil.isEmpty(code)) {
            return null;
        }
        String grant_type = "authorization_code";
        String client_id = oauth.getQq().getClient_id();
        String client_secret = oauth.getQq().getClient_secret();
        String redirect_uri = oauth.getQq().getRedirect_uri();
        String url = String.format(oauth.getQq().getAccess_token_callback_uri() +
                        "?grant_type=%s&client_id=%s&client_secret=%s&code=%s&redirect_uri=%s",
                grant_type, client_id, client_secret, code, redirect_uri);
        return url;
    }

    /**
     *  获取accessToken
     * @param urlForAccessToken
     * @return
     */
    public String getAccessToken(String urlForAccessToken) throws ParseException {
        if(StringUtil.isEmpty(urlForAccessToken)) {
            return null;
        }
        AccessTokenBean accessTokenBean = loginService.getAccessToken("qq");

        String firstCallbackInfo = restTemplate.getForObject(urlForAccessToken, String.class);
        String[] params = firstCallbackInfo.split("&");
        String access_token = null;
        String expires_in = null;
        for (String param : params) {
            String[] keyvalue = param.split("=");
            if (keyvalue[0].equals("access_token")) {
                access_token = keyvalue[1];
            }else if(keyvalue[0].equals("expires_in")) {
                expires_in = keyvalue[1];
            }
        }
        //更新失效Token
        if(!StringUtil.isEmpty(access_token)) {
            AccessTokenBean accessToken = new AccessTokenBean();
            accessToken.setAccessToken(access_token);
            accessToken.setTokenType("qq");
            accessToken.setExpiresIn(expires_in);
            if(null != accessTokenBean) {
                loginService.updateAccessToken(accessToken);
            }else {
                loginService.addAccessToken(accessToken);
            }
        }
        return access_token;
    }

    /**
     * 根据accessToken获取openid
     * @param access_token
     * @return
     * @throws IOException
     */
    public String getOpenId(String access_token) throws IOException {
        if(StringUtil.isEmpty(access_token)) {
            return null;
        }
        String url = String.format(oauth.getQq().getOpenid_callback_uri() + "?access_token=%s", access_token);
        //第二次模拟客户端发出请求后得到的是带openid的返回数据,格式如下
        //callback( {"client_id":"YOUR_APPID","openid":"YOUR_OPENID"} );
        String secondCallbackInfo = restTemplate.getForObject(url, String.class);

        //正则表达式处理
        String regex = "\\{.*\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(secondCallbackInfo);
        if (!matcher.find()) {
            throw new RuntimeException("异常的回调值: " + secondCallbackInfo);
        }

        //调用jackson
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap hashMap = objectMapper.readValue(matcher.group(0), HashMap.class);
        return ((String) hashMap.get("openid"));
    }

    /**
     * 根据openid获取用户信息
     */
    public String getUserInfo(String openid,String access_token) {
        String infoUrl = String.format(oauth.getQq().getUser_info_callback_uri() + "?access_token=%s&oauth_consumer_key=%s&openid=%s", access_token, oauth.getQq().getClient_id(), openid);
        String userInfo = restTemplate.getForObject(infoUrl, String.class);
        return userInfo;
    }

}
