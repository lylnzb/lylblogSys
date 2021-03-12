package com.lylblog.project.login.service;

import com.lylblog.project.login.bean.UserLoginBean;

public interface LoginService {

    /**
     * 用户注册
     * @return
     */
    int registerUser(UserLoginBean userBean);

    /**
     * 通过邮箱查询用户是否存在
     * @param email
     * @return
     */
    UserLoginBean findUserByEmail(String email);
}
