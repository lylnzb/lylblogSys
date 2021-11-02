package com.lylblog.project.api.QQ.service;

import com.lylblog.project.api.QQ.bean.QQBean;
import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.login.bean.UserLoginBean;

import java.io.IOException;
import java.text.ParseException;

/**
 * @Author: lyl
 * @Date: 2021/10/24 16:08
 */
public interface QQService {

    /**
     * 拼接获取accessToken的URL路径
     * @param code
     * @return
     */
    String getUrlForAccessToken(String code);

    /**
     *  获取accessToken
     * @param urlForAccessToken
     * @return
     */
    String getAccessToken(String urlForAccessToken) throws ParseException;

    /**
     * 根据accessToken获取openid
     * @param access_token
     * @return
     * @throws IOException
     */
    String getOpenId(String access_token) throws IOException;

    /**
     * 根据openid获取用户信息
     * @param openid
     * @param access_token
     * @return
     */
    String getUserInfo(String openid,String access_token);

    /**
     * 第三方QQ登录
     * @param user
     * @param qq
     * @param open_id
     * @return
     * @throws IOException
     */
    ResultObj qqLogin(UserLoginBean user, QQBean qq, String open_id) throws IOException;

}
