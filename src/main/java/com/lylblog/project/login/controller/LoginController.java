package com.lylblog.project.login.controller;

import com.lylblog.common.api.properties.QQ.OAuthProperties;
import com.lylblog.common.util.CodeUtil;
import com.lylblog.common.util.redis.RedisUtil;
import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.login.bean.UserLoginBean;
import com.lylblog.project.login.service.LoginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/")
public class LoginController {

    @Resource
    private LoginService loginService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private OAuthProperties oauth;

    /**
     * 用户注册
     * @return
     */
    @RequestMapping("/registerUser")
    @ResponseBody
    public ResultObj registerUser(@RequestBody UserLoginBean userBean, HttpServletRequest request){
        String code = (String) redisUtil.get(userBean.getEmail() + "_smslogin");   //从redis取出验证码

        if(null != code && null != userBean.getvCode()){
            if(code.equals(userBean.getvCode())){
                int i = loginService.registerUser(userBean);
                if(i == 0){
                    return ResultObj.fail(1,"该邮箱已被注册！");
                }else if(i == 1){
                    return ResultObj.ok();
                }
                else if(i == 2){
                    return ResultObj.fail(2,"注册失败");
                }
            }else{
                return ResultObj.fail(3,"验证码输入不正确");
            }
        }else{
            return ResultObj.fail(3,"该验证码已失效");
        }
        return ResultObj.ok();
    }

    /**
     * 用户登录
     * @param userBean
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResultObj login(@RequestBody UserLoginBean userBean) {
        if(userBean.getEmail() == null || "".equals(userBean.getEmail())){
            return ResultObj.fail(2,"电子邮箱不能为空");
        }

        if(userBean.getPassword() == null || "".equals(userBean.getPassword())){
            return ResultObj.fail(2,"密码不能为空");
        }

        // 从SecurityUtils里边创建一个 subject
        Subject subject = SecurityUtils.getSubject();
        // 在认证提交前准备 token（令牌）
        UsernamePasswordToken token = new UsernamePasswordToken(userBean.getEmail(), userBean.getPassword());
        // 执行认证登陆
        try {
            subject.login(token);
        } catch (UnknownAccountException uae) {
            return ResultObj.fail(1,"未知账户");
        } catch (IncorrectCredentialsException ice) {
            return ResultObj.fail(1,"密码不正确");
        } catch (LockedAccountException lae) {
            return ResultObj.fail(1,"账户已锁定");
        } catch (ExcessiveAttemptsException eae) {
            return ResultObj.fail(1,"用户名或密码错误次数过多");
        } catch (AuthenticationException ae) {
            return ResultObj.fail(1,"用户名或密码不正确");
        }
        if (subject.isAuthenticated()) {

            return ResultObj.ok("登录成功");
        } else {
            token.clear();
            return ResultObj.fail(1,"登录失败");
        }
    }

    //QQ登陆对外接口，只需将该接口放置html的a标签href中即可
    @GetMapping("/login/qq")
    public void loginQQ(HttpServletResponse response) {
        try {
            response.sendRedirect(oauth.getQq().getCode_callback_uri() + //获取code码地址
                    "?client_id=" + oauth.getQq().getClient_id()//appid
                    + "&state=" + CodeUtil.getcode() + //这个说是防攻击的，就给个随机uuid吧
                    "&redirect_uri=" + oauth.getQq().getRedirect_uri() +//这个很重要，这个是回调地址，即就收腾讯返回的code码
                    "&response_type=code");//授权模式，授权码模式
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 用户退出登录
     * @return
     */
    @RequestMapping(value = "/loginOut", method = RequestMethod.POST)
    @ResponseBody
    public ResultObj loginOut() {
        // 从SecurityUtils里边创建一个 subject
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return ResultObj.ok();
    }
}
