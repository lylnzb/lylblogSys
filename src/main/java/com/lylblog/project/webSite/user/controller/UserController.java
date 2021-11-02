package com.lylblog.project.webSite.user.controller;

import com.lylblog.common.util.StringUtil;
import com.lylblog.common.util.file.FileUploadUtil;
import com.lylblog.common.util.shiro.ShiroUtils;
import com.lylblog.project.common.bean.DynamicBean;
import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.login.bean.UserAuthsBean;
import com.lylblog.project.login.bean.UserLoginBean;
import com.lylblog.project.login.service.LoginService;
import com.lylblog.project.system.admin.bean.UserIconBean;
import com.lylblog.project.system.admin.service.AdminService;
import com.lylblog.project.webSite.user.bean.*;
import com.lylblog.project.webSite.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @Author: lyl
 * @Date: 2021/6/7 11:39
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private AdminService adminService;

    @RequestMapping("/userCenter")
    public String blogList(Model model){
        //获取当前用户信息
        UserLoginBean user = ShiroUtils.getUserInfo();
        //获取角色信息
        user.setRoles(loginService.queryRoles(user.getYhnm()));
        int isbindingEmail = userService.isbindingEmail();
        int isSetupPwd = userService.isSetupPwd();
        int isUserAuths = userService.isUserAuths();
        //是否已绑定邮箱
        model.addAttribute("isbindingEmail", isbindingEmail == 1?true:false);
        //是否已设置密码
        model.addAttribute("isSetupPwd", isSetupPwd == 1?true:false);
        //是否绑定第三方账号
        model.addAttribute("isUserAuths", isUserAuths == 1?true:false);
        //用户昵称
        model.addAttribute("nickName", user.getNickname());
        //性别
        model.addAttribute("sex", "0".equals(user.getSex())?"男":"女");
        //用户角色
        model.addAttribute("roleName", user.getRoles().get(0).getRolename());

        int num = isbindingEmail + isSetupPwd + isUserAuths;
        if(num == 3) {
            model.addAttribute("num", 100);
        }else if(num == 2) {
            model.addAttribute("num", 80);
        }else if(num == 1) {
            model.addAttribute("num", 45);
        }else if(num == 0) {
            model.addAttribute("num", 0);
        }

        model.addAttribute("email", StringUtil.idMask(user.getEmail(), 3, 4));
        return "/user/userCenter";
    }

    /**
     * 查询当前登录用户的登录记录数据
     * @param userLoginRecord
     * @return
     */
    @RequestMapping("/queryLoginRecord")
    @ResponseBody
    public ResultObj queryLoginRecord(@RequestBody UserLoginRecordBean userLoginRecord){
        return userService.queryLoginRecord(userLoginRecord);
    }

    /**
     * 我的评论列表
     * @param comment
     * @return
     */
    @RequestMapping("/queryMyCommentsByYhnm")
    @ResponseBody
    public ResultObj queryMyCommentsByYhnm(@RequestBody UserCommentBean comment){
        return userService.queryMyCommentsByYhnm(comment);
    }

    /**
     * 我的友链申请列表
     * @param linkStatus
     * @return
     */
    @RequestMapping("/queryMyLinks")
    @ResponseBody
    public ResultObj queryMyLinks(String linkStatus){
        return userService.queryMyLinks(linkStatus);
    }

    /**
     * 根据编号查询友链信息
     * @param linkId
     * @return
     */
    @RequestMapping("/queryMyLinksById")
    @ResponseBody
    public ResultObj queryMyLinksById(String linkId){
        return userService.queryMyLinksById(linkId);
    }

    /**
     * 删除我的评论
     * @param commentId
     * @return
     */
    @RequestMapping("/delMyComment")
    @ResponseBody
    public ResultObj delMyComment(String commentId){
        return userService.delMyComment(commentId);
    }

    /**
     * 个人中心头像上传
     * @param userUploadIcon
     * @return
     */
    @RequestMapping("/uploadIcon")
    @ResponseBody
    public ResultObj uploadIcon(@RequestBody UserUploadIconBean userUploadIcon) throws IOException {
        //获取当前用户信息
        UserLoginBean user = ShiroUtils.getUserInfo();
        String fileName = FileUploadUtil.uploadByBase64(userUploadIcon.getBase64(), userUploadIcon.getImgName());
        UserIconBean userIcon = new UserIconBean();
        userIcon.setYhnm(user.getYhnm());
        userIcon.setIconUrl(fileName);
        return adminService.uploadIcon(userIcon);
    }

    /**
     * 更新个人资料
     * @param userParam
     * @return
     */
    @RequestMapping(value = "/updatePersonalData", method = RequestMethod.POST)
    @ResponseBody
    public ResultObj updatePersonalData(@RequestBody UserParamBean userParam){
        return userService.updatePersonalData(userParam);
    }

    /**
     * 查询个人资料详情
     * @return
     */
    @RequestMapping("/queryPersonalData")
    @ResponseBody
    public ResultObj queryPersonalData(){
        return userService.queryPersonalData();
    }

    /**
     * 查询个人动态信息
     * @return
     */
    @RequestMapping("/queryDynamicInfo")
    @ResponseBody
    public ResultObj queryDynamicInfo(@RequestBody DynamicBean dynamic){
        return userService.queryDynamicInfo(dynamic);
    }

    /**
     * 查询已绑定第三方账号
     * @return
     */
    @RequestMapping("/queryUserAuthsInfoByYhnm")
    @ResponseBody
    public ResultObj queryUserAuthsInfoByYhnm() {
        return userService.queryUserAuthsInfoByYhnm();
    }

    /**
     * 账号注销
     * @return
     */
    @RequestMapping("/accountCancel")
    @ResponseBody
    public ResultObj accountCancel() {
        return userService.accountCancel();
    }

    /**
     * 解绑第三方账号
     * @param openId
     * @return
     */
    @RequestMapping("/unbundUserAuths")
    @ResponseBody
    public ResultObj unbundUserAuths(String openId) {
        return userService.unbundUserAuths(openId);
    }
}
