package com.lylblog.project.login.service.impl;

import com.lylblog.common.util.DateUtil;
import com.lylblog.common.util.EncryptionUtil;
import com.lylblog.common.util.IdUtil;
import com.lylblog.common.util.file.FileUploadUtil;
import com.lylblog.common.util.shiro.ShiroUtils;
import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.login.bean.UserLoginBean;
import com.lylblog.project.login.mapper.LoginMapper;
import com.lylblog.project.login.service.LoginService;
import com.lylblog.project.system.admin.bean.UserIconBean;
import com.lylblog.project.system.admin.service.AdminService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private LoginMapper loginMapper;

    @Autowired
    private AdminService adminService;

    /**
     * 用户登录
     * @param userBean
     * @return
     */
    public ResultObj login(UserLoginBean userBean) {
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
    /**
     * 用户注册
     * @return
     */
    public int registerUser(UserLoginBean userBean) {
        UserLoginBean user = loginMapper.findUserByEmail(userBean.getEmail());
        if(user != null){
            return 0;//该用户已注册
        }
        Map<String, String> map = EncryptionUtil.MD5Pwd(userBean.getEmail(),userBean.getPassword());
        userBean.setYhnm(IdUtil.getUUID());
        userBean.setPassword(map.get("password"));
        userBean.setSalt(map.get("salt"));
        userBean.setRegtime(DateUtil.dateTimeToStr(new Date()));
        int result = loginMapper.registerUser(userBean);
        if(result > 0){
            /*****************角色赋予*********************/
            userBean.setYhnm(userBean.getYhnm());//用户id
            userBean.setRoleId(loginMapper.getRoleId("common"));//默认角色：普通用户
            loginMapper.addUserAndRoleRelevant(userBean);
            /*****************角色赋予*********************/

            /*****************默认头像赋予******************/
            try {
                String filePath = this.getClass().getResource("/static/admin/img/icon.png").getPath();
                File file = new File(filePath);
                InputStream inputStream = new FileInputStream(file);
                MultipartFile multipartFile = new MockMultipartFile(file.getName(), inputStream);
                String avatar = FileUploadUtil.upload(multipartFile);

                UserIconBean userIcon = new UserIconBean();
                userIcon.setYhnm(userBean.getYhnm());   //  用户内码
                userIcon.setIconUrl(avatar);            //  头像url路径
                adminService.uploadIcon(userIcon);
            }catch (Exception e) {
                e.printStackTrace();
            }
            /*****************默认头像赋予******************/

            return 1;
        }else{
            return 2;
        }
    }

    /**
     * 通过邮箱查询用户是否存在
     * @param email
     * @return
     */
    public UserLoginBean findUserByEmail(String email){
        UserLoginBean user = loginMapper.findUserByEmail(email);
        if(user != null){
            user.setRoles(loginMapper.queryRoles(email));
            user.setPerms(loginMapper.queryPerms(email));
        }
        return user;
    }

    /**
     * 修改密码
     * @param oldPwd
     * @param newPwd
     * @return
     */
    public ResultObj updatePwd(String oldPwd, String newPwd){
        UserLoginBean user = ShiroUtils.getUserInfo();

        //旧密码加密
        String oldMd5Pwd = new SimpleHash("MD5", oldPwd,
                ByteSource.Util.bytes(user.getEmail() + ((user.getSalt() == null)?"":user.getSalt())), 2).toHex();

        if(!oldMd5Pwd.equals(user.getPassword())){
            return ResultObj.fail("旧密码与当前账号密码不一致！");
        }

        //新密码加密
        Map<String, String> map = EncryptionUtil.MD5Pwd(user.getEmail(),newPwd);

        int count = loginMapper.updatePwd(map.get("password"), map.get("salt"), user.getEmail());
        if(count > 0){
            return ResultObj.ok("密码修改成功，请重新登录！");
        }
        return ResultObj.fail("修改密码失败！");
    }

    /**
     * 验证邮箱是否已注册
     * @param newEmail
     * @return
     */
    public ResultObj validationEmail(String newEmail){
        int num = loginMapper.validationEmail(newEmail);
        if(num > 0){
            return ResultObj.fail("该邮箱已注册");
        }
        return ResultObj.ok();
    }

    /**
     * 验证密码正确性
     * @param oldPwd
     * @return
     */
    public ResultObj validationPwd(String oldPwd){
        UserLoginBean user = ShiroUtils.getUserInfo();
        //旧密码加密
        String oldMd5Pwd = new SimpleHash("MD5", oldPwd,
                ByteSource.Util.bytes(user.getEmail() + ((user.getSalt() == null)?"":user.getSalt())), 2).toHex();

        if(!oldMd5Pwd.equals(user.getPassword())){
            return ResultObj.fail("旧密码与当前账号密码不一致！");
        }
        return ResultObj.ok();
    }
}
