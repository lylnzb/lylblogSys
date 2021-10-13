package com.lylblog.project.login.service;

import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.login.bean.UserLoginBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface LoginService {

    /**
     * 用户登录
     * @param userBean
     * @return
     */
    ResultObj login(UserLoginBean userBean);

    /**
     * 用户注册
     * @return
     */
    int registerUser(UserLoginBean userBean) throws IOException;

    /**
     * 通过邮箱查询用户是否存在
     * @param email
     * @return
     */
    UserLoginBean findUserByEmail(String email);

    /**
     * 修改密码
     * @param oldPwd
     * @param newPwd
     * @return
     */
    ResultObj updatePwd(String oldPwd, String newPwd);

    /**
     * 验证邮箱是否已注册
     * @param newEmail
     * @return
     */
    ResultObj validationEmail(String newEmail);

    /**
     * 验证密码正确性
     * @param oldPwd
     * @return
     */
    ResultObj validationPwd(String oldPwd);
}
