package com.lylblog.project.api.weibo.service;

import com.lylblog.project.api.weibo.bean.AccessTokenBean;
import com.lylblog.project.api.weibo.bean.WeiboBean;
import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.login.bean.UserLoginBean;

import java.io.IOException;

/**
 * @Author: lyl
 * @Date: 2021/11/4 14:23
 */
public interface WeiboService {

    /**
     * 获取accessToken
     * @param code
     * @return
     */
    AccessTokenBean getAccessToken(String code);

    /**
     * 获取用户信息
     * @param accessToken
     * @return
     */
    WeiboBean getUserInfo(AccessTokenBean accessToken);

    /**
     * 第三方微博登录
     * @param user
     * @param weibo
     * @param uid
     * @return
     */
    ResultObj weiboLogin(UserLoginBean user, WeiboBean weibo, String uid) throws IOException;
}
