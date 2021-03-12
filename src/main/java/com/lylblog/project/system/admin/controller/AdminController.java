package com.lylblog.project.system.admin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lylblog.common.util.shiro.ShiroUtils;
import com.lylblog.common.util.file.FileUploadUtil;
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
    //@RequiresPermissions("system:user:list")
    @RequestMapping("/queryUserList")
    @ResponseBody
    public ResultObj queryUserList(@RequestBody UserBean userBean){
        try {
            return adminService.queryUserList(userBean);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 新增或修改用户信息
     * @param userBean
     * @return
     */
    //@RequiresPermissions("system:user:add")
    @PostMapping("/addOrEditUserInfo")
    @ResponseBody
    public ResultObj addOrEditUserInfo(@RequestBody UserBean userBean, String type){
        try {
            return adminService.addOrEditUserInfo(userBean, type);
        } catch(Exception e){
            e.printStackTrace();
        }
        return ResultObj.fail();
    }

    @RequiresPermissions("system:user:disable")
    @PostMapping("/disableUserInfo")
    @ResponseBody
    public ResultObj disableUserInfo(@RequestBody List<String> userIds){
        try {
            return adminService.disableUserInfo(userIds);
        } catch(Exception e){
            e.printStackTrace();
        }
        return ResultObj.fail();
    }

    @PostMapping("/uploadIcon")
    @ResponseBody
    public ResultObj uploadIcon(UserIconBean userIcon, @RequestParam(value = "file",required = false) MultipartFile file) {
        try {
            if (!file.isEmpty()) {
                String avatar = FileUploadUtil.upload(file);
                userIcon.setIconUrl(avatar);
                return adminService.uploadIcon(userIcon);
            }
            return ResultObj.fail();
        } catch (Exception e) {
            return ResultObj.fail(e.getMessage());
        }
    }

    /**
     * 密码重置
     * @param emails
     * @return
     */
    @RequiresPermissions("system:user:reset")
    @PostMapping("/resetPassword")
    @ResponseBody
    public ResultObj resetPassword(@RequestBody List<String> emails){
        try {
            return adminService.resetPassword(emails);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询所有角色信息
     * @param roleBean
     * @return
     */
    //@RequiresPermissions("system:role:list")
    @RequestMapping("/queryRoleList")
    @ResponseBody
    public ResultObj queryRoleList(@RequestBody RoleBean roleBean){
        try {
            return adminService.queryRoleList(roleBean);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 新增角色信息
     * @param role
     * @return
     */
    //@RequiresPermissions("system:role:add")
    @RequestMapping("/addRoleInfo")
    @ResponseBody
    public ResultObj addRoleInfo(@RequestBody Map<String, Object> role, String type){
        try {
            RoleBean roleBean = JSONObject.parseObject(JSON.toJSONString(role),RoleBean.class);
            return adminService.addRoleInfo(roleBean, type);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除角色信息
     * @param deleteIds
     * @return
     */
    //@RequiresPermissions("system:role:delete")
    @RequestMapping("/deleteRoleInfo")
    @ResponseBody
    public ResultObj deleteRoleInfo(@RequestBody List<String> deleteIds){
        try {
            return adminService.deleteRoleInfo(deleteIds);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询所有权限信息
     * @return
     */
    @RequestMapping("/queryPermInfoToTree")
    @ResponseBody
    public ResultObj queryPermInfoToTree(){
        try {
            return adminService.queryPermInfoToTree(null);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过条件查询权限详情
     * @param permissionBean
     * @return
     */
    @RequestMapping("/qeuryPermInfoByConditions")
    @ResponseBody
    public ResultObj qeuryPermInfoByConditions(@RequestBody PermissionBean permissionBean){
        try {
            return adminService.qeuryPermInfoByConditions(permissionBean);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 新增权限信息
     * @param permissionBean
     * @return
     */
    @RequiresPermissions("system:perm:add")
    @RequestMapping("/addPermInfo")
    @ResponseBody
    public ResultObj addPermInfo(@RequestBody PermissionBean permissionBean,String type){
        try {
            return adminService.addPermInfo(permissionBean, type);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 取消或者恢复权限功能
     * @param permIds
     * @param valid
     * @return
     */
    @RequiresPermissions("system:perm:cancelOrReturn")
    @RequestMapping("/cancelOrRestorePermInfo")
    @ResponseBody
    public ResultObj cancelOrRestorePermInfo(@RequestBody List<Map<String,Object>> permIds, String valid){
        try {
            return adminService.cancelOrRestorePermInfo(permIds, valid);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询菜单数据结构
     * @return
     */
    @RequestMapping("/queryMenuInfo")
    @ResponseBody
    public ResultObj queryMenuInfo(){
        try {
            return adminService.queryMenuInfo();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/userCenter")
    public String userCenter(Model model){
        return "/user/userCenter";
    }
}
