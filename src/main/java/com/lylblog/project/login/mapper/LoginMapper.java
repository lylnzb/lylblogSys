package com.lylblog.project.login.mapper;

import com.lylblog.project.login.bean.*;
import com.lylblog.project.system.admin.bean.PermissionBean;
import com.lylblog.project.system.admin.bean.RoleBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LoginMapper {
    /**
     * 用户注册
     * @return
     */
    int registerUser(UserLoginBean userBean);

    /**
     * 通过用户名查询用户信息
     * @param username
     * @return
     */
    UserLoginBean findUserByUsername(String username);

    /**
     * 新增角色与用户关联表数据
     * @param userBean
     * @return
     */
    int addUserAndRoleRelevant(UserLoginBean userBean);

    /**
     * 删除角色与用户关联表数据
     * @param yhnm
     * @return
     */
    int deleteUserAndRoleRelevant(@Param("yhnm") String yhnm);

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
     * 获取角色ID
     * @param roleKey
     * @return
     */
    String getRoleId(@Param("roleKey") String roleKey);

    /**
     * 获取accessToken
     * @param type
     * @return
     */
    AccessTokenBean getAccessToken(@Param("type") String type);

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
    UserAuthsBean getUserAuthsByOpenId(@Param("openId") String openId);

    /**
     * 新增第三方用户信息
     * @param userAuths
     * @return
     */
    int addUserAuths(UserAuthsBean userAuths);
}
