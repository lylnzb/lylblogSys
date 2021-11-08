package com.lylblog.project.login.service;

import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.login.bean.AccessTokenBean;
import com.lylblog.project.login.bean.UserAuthsBean;
import com.lylblog.project.login.bean.UserLoginBean;
import com.lylblog.project.system.admin.bean.PermissionBean;
import com.lylblog.project.system.admin.bean.RoleBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

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
     * 通过用户名查询用户信息
     * @param username
     * @return
     */
    UserLoginBean findUserByUsername(String username);

    /**
     * 查询用户所属的角色信息
     * @param yhnm
     * @return
     */
    List<RoleBean> queryRoles(String yhnm);

    /**
     * 查询用户所属的权限信息
     * @param yhnm
     * @return
     */
    List<PermissionBean> queryPerms(String yhnm);

    /**
     * 获取accessToken
     * @param type
     * @return
     */
    AccessTokenBean getAccessToken(String type);

    /**
     * 新增accessToken值
     * @param accessToken
     * @return
     */
    int addAccessToken(AccessTokenBean accessToken);

    /**
     * 修改accessToken值
     * @param accessToken
     * @return
     */
    int updateAccessToken(AccessTokenBean accessToken);

    /**
     * 通过第三方登录唯一标识查询用户信息
     * @param openId
     * @return
     */
    UserAuthsBean getUserAuthsByOpenId(String openId);

    /**
     * 新增第三方用户信息
     * @param userAuths
     * @return
     */
    int addUserAuths(UserAuthsBean userAuths);
}
