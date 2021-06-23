package com.lylblog.project.system.admin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lylblog.common.util.shiro.ShiroUtils;
import com.lylblog.common.util.file.FileUploadUtil;
import com.lylblog.framework.Aspectj.annotation.Log;
import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.login.bean.UserLoginBean;
import com.lylblog.project.login.service.LoginService;
import com.lylblog.project.system.admin.bean.PermissionBean;
import com.lylblog.project.system.admin.bean.RoleBean;
import com.lylblog.project.system.admin.bean.UserBean;
import com.lylblog.project.system.admin.bean.UserIconBean;
import com.lylblog.project.system.admin.service.AdminService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Author: lyl
 * @Date: 2021/2/8 10:56
 */
@Controller
@RequestMapping("/admin")
public class AdminController {


    @Resource
    private AdminService adminService;

    @Resource
    private LoginService loginService;

    private static String USERBASEPATH = "admin/user";
    private static String ROLEBASEPATH = "admin/role";
    private static String PERMBASEPATH = "admin/permission";

    @RequestMapping("/user/userList")
    public String userList(Model model){
        return USERBASEPATH + "/userList";
    }

    @RequestMapping("/user/addOrUpdaUser")
    public String addOrUpdaUser(Model model){
        return USERBASEPATH + "/addOrUpdaUser";
    }

    @RequestMapping("/role/roleList")
    public String roleList(Model model){
        return ROLEBASEPATH + "/roleList";
    }

    @RequestMapping("/role/addOrUpdaRole")
    public String addOrUpdaRole(Model model){
        return ROLEBASEPATH + "/addRole";
    }

    @RequestMapping("/perm/permissionList")
    public String permissionList(Model model){
        return PERMBASEPATH + "/permissionList";
    }

    @RequestMapping("/perm/addOrUpdaPermission")
    public String addOrUpdaPermission(Model model){
        return PERMBASEPATH + "/addPermission";
    }

    @RequestMapping("/index")
    public String index(Model model){
        //获取当前用户信息
        UserLoginBean user = loginService.findUserByEmail(ShiroUtils.getUserInfo().getEmail());
        user.setIconUrl("/profile/" + user.getIconUrl());
        model.addAttribute("userInfo", user);
        model.addAttribute("sessionId", ShiroUtils.getSessionId());
        return "admin/index";
    }

    /**
     * 查询所有用户信息
     * @param userBean
     * @return
     */
    @RequiresPermissions("system:user:list")
    @RequestMapping("/queryUserList")
    @ResponseBody
    public ResultObj queryUserList(@RequestBody UserBean userBean) throws Exception{
        return adminService.queryUserList(userBean);
    }

    /**
     * 新增或修改用户信息
     * @param userBean
     * @return
     */
    @Log(moduleName = "用户管理", functionName = "用户管理-新增（编辑）")
    @RequiresPermissions("system:user:add")
    @PostMapping("/addOrEditUserInfo")
    @ResponseBody
    public ResultObj addOrEditUserInfo(@RequestBody UserBean userBean, String type) throws Exception{
        return adminService.addOrEditUserInfo(userBean, type);
    }

    @Log(moduleName = "用户管理", functionName = "用户管理-账号停用")
    @RequiresPermissions("system:user:disable")
    @PostMapping("/disableUserInfo")
    @ResponseBody
    public ResultObj disableUserInfo(@RequestBody List<String> userIds){
        return adminService.disableUserInfo(userIds);
    }

    @Log(moduleName = "用户管理", functionName = "用户管理-头像上传")
    @PostMapping("/uploadIcon")
    @ResponseBody
    public ResultObj uploadIcon(UserIconBean userIcon, @RequestParam(value = "file",required = false) MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            String avatar = FileUploadUtil.upload(file);
            userIcon.setIconUrl(avatar);
            return adminService.uploadIcon(userIcon);
        }
        return ResultObj.fail();
    }

    /**
     * 密码重置
     * @param emails
     * @return
     */
    @Log(moduleName = "用户管理", functionName = "用户管理-密码重置")
    @RequiresPermissions("system:user:reset")
    @PostMapping("/resetPassword")
    @ResponseBody
    public ResultObj resetPassword(@RequestBody List<String> emails){
        return adminService.resetPassword(emails);
    }

    /**
     * 查询所有角色信息
     * @param roleBean
     * @return
     */
    @RequiresPermissions("system:role:list")
    @RequestMapping("/queryRoleList")
    @ResponseBody
    public ResultObj queryRoleList(@RequestBody RoleBean roleBean) throws Exception {
        return adminService.queryRoleList(roleBean);
    }

    /**
     * 新增角色信息
     * @param role
     * @return
     */
    @Log(moduleName = "角色管理", functionName = "角色管理-新增（编辑）")
    @RequiresPermissions("system:role:add")
    @RequestMapping("/addRoleInfo")
    @ResponseBody
    public ResultObj addRoleInfo(@RequestBody Map<String, Object> role, String type) throws Exception {
        RoleBean roleBean = JSONObject.parseObject(JSON.toJSONString(role),RoleBean.class);
        return adminService.addRoleInfo(roleBean, type);
    }

    /**
     * 删除角色信息
     * @param deleteIds
     * @return
     */
    @Log(moduleName = "角色管理", functionName = "角色管理-删除")
    @RequiresPermissions("system:role:delete")
    @RequestMapping("/deleteRoleInfo")
    @ResponseBody
    public ResultObj deleteRoleInfo(@RequestBody List<String> deleteIds){
        return adminService.deleteRoleInfo(deleteIds);
    }

    /**
     * 查询所有权限信息
     * @return
     */
    @RequestMapping("/queryPermInfoToTree")
    @ResponseBody
    public ResultObj queryPermInfoToTree() throws Exception {
        return adminService.queryPermInfoToTree(null);
    }

    /**
     * 通过条件查询权限详情
     * @param permissionBean
     * @return
     */
    @RequestMapping("/qeuryPermInfoByConditions")
    @ResponseBody
    public ResultObj qeuryPermInfoByConditions(@RequestBody PermissionBean permissionBean) throws Exception {
        return adminService.qeuryPermInfoByConditions(permissionBean);
    }

    /**
     * 新增权限信息
     * @param permissionBean
     * @return
     */
    @Log(moduleName = "权限维护", functionName = "权限维护-新增（编辑）")
    @RequiresPermissions("system:perm:add")
    @RequestMapping("/addPermInfo")
    @ResponseBody
    public ResultObj addPermInfo(@RequestBody PermissionBean permissionBean,String type) throws Exception {
        return adminService.addPermInfo(permissionBean, type);
    }

    /**
     * 取消或者恢复权限功能
     * @param permIds
     * @param valid
     * @return
     */
    @Log(moduleName = "权限维护", functionName = "权限维护-取消或者恢复权限")
    @RequiresPermissions("system:perm:cancelOrReturn")
    @RequestMapping("/cancelOrRestorePermInfo")
    @ResponseBody
    public ResultObj cancelOrRestorePermInfo(@RequestBody List<Map<String,Object>> permIds, String valid) throws Exception {
        return adminService.cancelOrRestorePermInfo(permIds, valid);
    }

    /**
     * 查询菜单数据结构
     * @return
     */
    @RequestMapping("/queryMenuInfo")
    @ResponseBody
    public ResultObj queryMenuInfo() throws Exception {
        return adminService.queryMenuInfo();
    }
}
