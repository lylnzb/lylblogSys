package com.lylblog.project.system.admin.service;

import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.system.admin.bean.PermissionBean;
import com.lylblog.project.system.admin.bean.RoleBean;
import com.lylblog.project.system.admin.bean.UserBean;
import com.lylblog.project.system.admin.bean.UserIconBean;

import java.util.List;
import java.util.Map;

/**
 * @Author: lyl
 * @Date: 2021/2/8 10:46
 */
public interface AdminService {
    /**
     * 查询所有用户信息
     * @param userBean
     * @return
     */
    ResultObj queryUserList(UserBean userBean) throws Exception;

    /**
     * 新增或修改用户信息
     * @param userBean
     * @return
     */
    ResultObj addOrEditUserInfo(UserBean userBean, String type) throws Exception;

    /**
     * 停用账号
     * @param userIds
     * @return
     */
    ResultObj disableUserInfo(List<String> userIds);

    /**
     * 上传头像
     * @param userIcon
     * @return
     */
    ResultObj uploadIcon(UserIconBean userIcon);

    /**
     * 密码重置
     * @param emails
     * @return
     */
    ResultObj resetPassword(List<String> emails);

    /**
     * 查询所有角色信息
     * @param roleBean
     * @return
     */
    ResultObj queryRoleList(RoleBean roleBean) throws Exception;

    /**
     * 新增或修改角色信息
     * @param roleBean
     * @return
     */
    ResultObj addRoleInfo(RoleBean roleBean, String type) throws Exception;

    /**
     * 删除角色信息
     * @param deleteIds
     * @return
     */
    ResultObj deleteRoleInfo(List<String> deleteIds);

    /**
     * 查询所有权限信息
     * @param permissionBean
     * @return
     */
    ResultObj queryPermInfoToTree(PermissionBean permissionBean) throws Exception;

    /**
     * 通过条件查询权限详情
     * @param permissionBean
     * @return
     */
    ResultObj qeuryPermInfoByConditions(PermissionBean permissionBean) throws Exception;

    /**
     * 新增权限信息
     * @param permissionBean
     * @return
     */
    ResultObj addPermInfo(PermissionBean permissionBean,String type) throws Exception;

    /**
     * 取消或者恢复权限功能
     * @param permIds
     * @param valid
     * @return
     * @throws Exception
     */
    ResultObj cancelOrRestorePermInfo(List<Map<String,Object>> permIds, String valid) throws Exception;

    /**
     * 查询菜单数据结构
     * @return
     */
    ResultObj queryMenuInfo() throws Exception;
}
