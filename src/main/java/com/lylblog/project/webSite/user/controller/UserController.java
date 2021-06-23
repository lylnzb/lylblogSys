package com.lylblog.project.webSite.user.controller;

import com.lylblog.common.util.StringUtil;
import com.lylblog.common.util.shiro.ShiroUtils;
import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.login.bean.UserLoginBean;
import com.lylblog.project.webSite.user.bean.UserLoginRecordBean;
import com.lylblog.project.webSite.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping("/userCenter")
    public String blogList(Model model){
        //获取当前用户信息
        UserLoginBean user = ShiroUtils.getUserInfo();

        int isbindingEmail = userService.isbindingEmail();
        int isSetupPwd = userService.isSetupPwd();
        //是否已绑定邮箱
        model.addAttribute("isbindingEmail", isbindingEmail == 1?true:false);
        //是否已设置密码
        model.addAttribute("isSetupPwd", isSetupPwd == 1?true:false);

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
        try {
            return userService.queryLoginRecord(userLoginRecord);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return ResultObj.fail("系统异常");
    }
}
