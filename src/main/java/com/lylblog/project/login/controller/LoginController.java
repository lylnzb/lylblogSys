package com.lylblog.project.login.controller;

import com.lylblog.common.api.QQ.properties.OAuthProperties;
import com.lylblog.common.util.CodeUtil;
import com.lylblog.common.util.redis.RedisUtil;
import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.login.bean.UserLoginBean;
import com.lylblog.project.login.service.LoginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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
    public ResultObj registerUser(@RequestBody UserLoginBean userBean) throws Exception{
        String code = (String) redisUtil.get(userBean.getEmail() + "_smslogin");   //从redis取出验证码
        String possword = userBean.getPassword();

        if(null != code && null != userBean.getvCode()){
            if(code.equals(userBean.getvCode())){
                int i = loginService.registerUser(userBean);
                if(i == 0){
                    return ResultObj.fail(1,"该邮箱已被注册！");
                }else if(i == 1){
                    userBean.setPassword(possword);
                    loginService.login(userBean);
                    return ResultObj.ok();
                }else if(i == 2){
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
     * @param user
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResultObj login(@RequestBody UserLoginBean user) {
        return loginService.login(user);
    }

    /**
     * 验证邮箱是否已注册
     * @param newEmail
     * @return
     */
    @RequestMapping(value = "/validationEmail", method = RequestMethod.POST)
    @ResponseBody
    public ResultObj validationEmail(String newEmail){
        return loginService.validationEmail(newEmail);
    }

    @RequestMapping(value = "/user/validationPwd", method = RequestMethod.POST)
    @ResponseBody
    public ResultObj validationPwd(String oldPwd){
        return loginService.validationPwd(oldPwd);
    }

    @RequestMapping(value = "/user/updatePwd", method = RequestMethod.POST)
    @ResponseBody
    public ResultObj updatePwd(String oldPwd, String newPwd){
        return loginService.updatePwd(oldPwd, newPwd);
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

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ResponseBody
    public void test() throws Exception{
        String filePath = this.getClass().getResource("/static/admin/img/icon.png").getPath();
        File file = new File(filePath);
        if (file.exists()) {
            InputStream inputStream = new FileInputStream(file);
            MultipartFile multipartFile = new MockMultipartFile(file.getName(), inputStream);
            System.out.println(multipartFile);
        }
        System.out.println(file);
    }
}
