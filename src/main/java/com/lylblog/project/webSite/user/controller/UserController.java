package com.lylblog.project.webSite.user.controller;

import com.lylblog.common.util.EncryptionUtil;
import com.lylblog.common.util.RSAUtil;
import com.lylblog.common.util.StringUtil;
import com.lylblog.common.util.file.FileUploadUtil;
import com.lylblog.common.util.redis.RedisUtil;
import com.lylblog.common.util.shiro.ShiroUtils;
import com.lylblog.common.util.validation.ValidatorUtil;
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
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private RedisUtil redisUtil;

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
        model.addAttribute("isUserAuths", isUserAuths >= 1?true:false);
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

    @RequestMapping(value = "/validationPwd", method = RequestMethod.POST)
    @ResponseBody
    public ResultObj validationPwd(@RequestBody HashMap param) throws Exception {
        //获取 redis缓存中的 privateKey
        String privateKey = (String) redisUtil.get(ShiroUtils.getSessionId() + "_privateKey");
        if(StringUtil.isEmpty(privateKey)) {
            return ResultObj.fail("公钥已过期，请重新获取！");
        }
        String oldPwd = RSAUtil.decodeSecret(privateKey, param.get("oldPwd").toString());
        return userService.validationPwd(oldPwd);
    }

    @RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
    @ResponseBody
    public ResultObj updatePwd(@RequestBody HashMap param) throws Exception {
        //获取 redis缓存中的 privateKey
        String privateKey = (String) redisUtil.get(ShiroUtils.getSessionId() + "_privateKey");
        if(StringUtil.isEmpty(privateKey)) {
            return ResultObj.fail("公钥已过期，请重新获取！");
        }
        String oldPwd = RSAUtil.decodeSecret(privateKey, param.get("oldPwd").toString());
        String newPwd = RSAUtil.decodeSecret(privateKey, param.get("newPwd").toString());
        return userService.updatePwd(oldPwd, newPwd);
    }

    /**
     * 设置密码
     * @param param
     * @return
     */
    @RequestMapping(value = "/setPwd", method = RequestMethod.POST)
    @ResponseBody
    public ResultObj setPwd(@RequestBody HashMap param) throws Exception {
        //获取 redis缓存中的 privateKey
        String privateKey = (String) redisUtil.get(ShiroUtils.getSessionId() + "_privateKey");
        if(StringUtil.isEmpty(privateKey)) {
            return ResultObj.fail("公钥已过期，请重新获取！");
        }
        String pwd = RSAUtil.decodeSecret(privateKey, param.get("pwd").toString());
        return userService.setPwd(pwd);
    }

    /**
     * 验证邮箱是否已注册
     * @param newEmail
     * @return
     */
    @RequestMapping(value = "/validationEmail", method = RequestMethod.POST)
    @ResponseBody
    public ResultObj validationEmail(String newEmail){
        return userService.validationEmail(newEmail);
    }

    /**
     * 绑定新邮箱
     * @param param
     * @return
     */
    @RequestMapping(value = "/bindEmail", method = RequestMethod.POST)
    @ResponseBody
    public ResultObj bindEmail(@RequestBody HashMap param) {
        String newEmail = param.get("newEmail").toString();
        String vCode = param.get("vCode").toString();
        String code = (String) redisUtil.get(newEmail + "_smslogin");   //从redis取出验证码
        if(null != code && null != vCode){
            if(!code.equals(vCode)){
                return ResultObj.fail(3,"验证码输入不正确");
            }
        }else{
            return ResultObj.fail(3,"该验证码已失效");
        }
        return userService.bindEmail(newEmail);
    }

    /**
     * 查询当前登录用户的登录记录数据
     * @param userLoginRecord
     * @return
     */
    @PostMapping("/queryLoginRecord")
    @ResponseBody
    public ResultObj queryLoginRecord(@RequestBody UserLoginRecordBean userLoginRecord){
        return userService.queryLoginRecord(userLoginRecord);
    }

    /**
     * 我的评论列表
     * @param comment
     * @return
     */
    @PostMapping("/queryMyCommentsByYhnm")
    @ResponseBody
    public ResultObj queryMyCommentsByYhnm(@RequestBody UserCommentBean comment){
        return userService.queryMyCommentsByYhnm(comment);
    }

    /**
     * 我的友链申请列表
     * @param linkStatus
     * @return
     */
    @PostMapping("/queryMyLinks")
    @ResponseBody
    public ResultObj queryMyLinks(String linkStatus){
        return userService.queryMyLinks(linkStatus);
    }

    /**
     * 根据编号查询友链信息
     * @param linkId
     * @return
     */
    @PostMapping("/queryMyLinksById")
    @ResponseBody
    public ResultObj queryMyLinksById(String linkId){
        return userService.queryMyLinksById(linkId);
    }

    /**
     * 删除我的评论
     * @param commentId
     * @return
     */
    @PostMapping("/delMyComment")
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
    @PostMapping("/queryPersonalData")
    @ResponseBody
    public ResultObj queryPersonalData(){
        return userService.queryPersonalData();
    }

    /**
     * 查询个人动态信息
     * @return
     */
    @PostMapping("/queryDynamicInfo")
    @ResponseBody
    public ResultObj queryDynamicInfo(@RequestBody DynamicBean dynamic){
        return userService.queryDynamicInfo(dynamic);
    }

    /**
     * 查询已绑定第三方账号
     * @return
     */
    @PostMapping("/queryUserAuthsInfoByYhnm")
    @ResponseBody
    public ResultObj queryUserAuthsInfoByYhnm() {
        return userService.queryUserAuthsInfoByYhnm();
    }

    /**
     * 账号注销
     * @return
     */
    @PostMapping("/accountCancel")
    @ResponseBody
    public ResultObj accountCancel() {
        return userService.accountCancel();
    }

    /**
     * 解绑第三方账号
     * @param openId
     * @return
     */
    @PostMapping("/unbundUserAuths")
    @ResponseBody
    public ResultObj unbundUserAuths(String openId) {
        return userService.unbundUserAuths(openId);
    }

    /**
     * 是否已绑定邮箱
     * @return
     */
    @PostMapping("/isbindingEmail")
    @ResponseBody
    public int isbindingEmail() {
        return userService.isbindingEmail();
    }
}
