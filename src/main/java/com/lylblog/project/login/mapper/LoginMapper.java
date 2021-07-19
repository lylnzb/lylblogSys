package com.lylblog.project.login.mapper;

import com.lylblog.project.login.bean.UserLoginBean;
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
     * 通过邮箱查询用户是否存在
     * @param email
     * @return
     */
    UserLoginBean findUserByEmail(String email);

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
     * @param email
     * @return
     */
    List<RoleBean> queryRoles(String email);

    /**
     * 查询用户所属的权限信息
     * @param email
     * @return
     */
    List<PermissionBean> queryPerms(String email);

    /**
     * 修改密码
     * @param newPwd
     * @param salt
     * @param email
     * @return
     */
    int updatePwd(@Param("newPwd") String newPwd, @Param("salt") String salt, @Param("email") String email);

    /**
     * 验证邮箱是否已注册
     * @param newEmail
     * @return
     */
    int validationEmail(@Param("newEmail") String newEmail);
}
