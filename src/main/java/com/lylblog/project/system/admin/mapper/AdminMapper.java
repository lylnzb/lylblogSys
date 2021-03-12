package com.lylblog.project.system.admin.mapper;

import com.lylblog.project.common.bean.ZTreeBean;
import com.lylblog.project.system.admin.bean.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: lyl
 * @Date: 2021/2/8 10:47
 */
@Mapper
public interface AdminMapper {
    /**
     * 查询所有用户信息
     * @param userBean
     * @return
     */
    List<UserBean> queryUserList(UserBean userBean);

    /**
     * 查询所有用户信息总数
     * @param userBean
     * @return
     */
    int queryUserListCount(UserBean userBean);

    /**
     * 新增用户信息
     * @param userBean
     * @return
     */
    int addUserInfo(UserBean userBean);

    /**
     * 停用账号
     * @param userIds
     * @return
     */
    int disableUserInfo(List<String> userIds);

    /**
     * 密码重置
     * @param emails
     * @return
     */
    int resetPassword(Map<String, Object> emails);

    /**
     * 修改用户信息
     * @param userBean
     * @return
     */
    int updateUserInfo(UserBean userBean);

    /**
     * 新增用户头像信息数据
     * @param userIcon
     * @return
     */
    int insertUserIcon(UserIconBean userIcon);

    /**
     * 指定用户是否存在头像
     * @param yhnm
     * @return
     */
    int isUserIcon(@Param("yhnm") String yhnm);

    /**
     * 修改头像状态
     * @param yhnm
     * @param isActivity
     * @return
     */
    int updateUserIconStatus(@Param("yhnm") String yhnm,@Param("isActivity") String isActivity);

    /**
     * 查询所有角色信息
     * @param roleBean
     * @return
     */
    List<RoleBean> queryRoleList(RoleBean roleBean);

    /**
     * 查询所有角色信息总数
     * @param roleBean
     * @return
     */
    int queryRoleListCount(RoleBean roleBean);

    /**
     * 新增角色信息
     * @param roleBean
     * @return
     */
    int addRoleInfo(RoleBean roleBean);

    /**
     * 修改角色信息
     * @param roleBean
     * @return
     */
    int updateRoleInfo(RoleBean roleBean);

    /**
     * 删除角色信息
     * @param deleteIds
     * @return
     */
    int deleteRoleInfo(@Param("deleteIds") List<String> deleteIds);

    /**
     * 查询权限树
     * @param permissionBean
     * @return
     */
    List<ZTreeBean> queryPermInfoToTree(PermissionBean permissionBean);

    /**
     * 通过角色id查询所有权限id，用于数据回显
     * @param roleId
     * @return
     * @throws Exception
     */
    List<String> queryPermsByRoleId(@Param("roleId") String roleId) throws Exception;

    /**
     * 通过条件查询权限详情
     * @param permissionBean
     * @return
     */
    List<PermissionBean> qeuryPermInfoByConditions(PermissionBean permissionBean);

    /**
     * 新增权限信息
     * @param permissionBean
     * @return
     */
    int addPermInfo(PermissionBean permissionBean);

    /**
     * 修改权限信息
     * @param permissionBean
     * @return
     */
    int updatePermInfo(PermissionBean permissionBean);

    /**
     * 取消或者恢复权限功能
     * @param permIds
     * @param valid
     * @return
     */
    int cancelOrRestorePermInfo(@Param("permIds") List<Map<String,Object>> permIds, @Param("valid") String valid);

    /**
     * 新增角色权限关系数据
     * @param roleAndPermBean
     * @return
     */
    int addRoleAuthorizationRelations(RoleAndPermBean roleAndPermBean);

    /**
     * 删除角色权限关系数据
     * @param deleteList
     * @return
     */
    int deleteRoleAuthorizationRelations(@Param("roleId")String roleId,@Param("deleteList") List<String> deleteList);

    /**
     * 根据权限id查询所有权限信息
     * @param roleId
     * @return
     */
    List<RoleAndPermBean> queryPermByRole(@Param("roleId")String roleId);

    /**
     * 查询菜单数据结构
     * @param yhnm
     * @return
     */
    List<MenuBean> queryMenuInfo(@Param("yhnm")String yhnm);
}
