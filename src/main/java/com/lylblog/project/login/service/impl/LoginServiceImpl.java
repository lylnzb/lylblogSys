package com.lylblog.project.login.service.impl;

import com.lylblog.common.util.DateUtil;
import com.lylblog.common.util.EncryptionUtil;
import com.lylblog.common.util.IdUtil;
import com.lylblog.common.util.file.FileUploadUtil;
import com.lylblog.common.util.shiro.ShiroUtils;
import com.lylblog.common.util.validation.ValidatorUtil;
import com.lylblog.framework.shiro.authc.CustomToken;
import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.login.bean.AccessTokenBean;
import com.lylblog.project.login.bean.UserAuthsBean;
import com.lylblog.project.login.bean.UserLoginBean;
import com.lylblog.project.login.mapper.LoginMapper;
import com.lylblog.project.login.service.LoginService;
import com.lylblog.project.system.admin.bean.PermissionBean;
import com.lylblog.project.system.admin.bean.RoleBean;
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
import java.util.List;
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
        // 重写的UsernamePasswordToken类
        CustomToken token = new CustomToken(userBean.getEmail(), userBean.getPassword());
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
            return ResultObj.fail(1,"用户名或密码错误次数过多，请30分钟后重试！");
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

        if(null != userBean.getAppType() && "0".equals(userBean.getAppType())) {//账号密码注册
            UserLoginBean user = loginMapper.findUserByUsername(userBean.getEmail());
            if(user != null){
                return 0;//该用户已注册
            }
            Map<String, String> map = EncryptionUtil.MD5Pwd(userBean.getEmail(),userBean.getPassword());
            userBean.setYhnm(IdUtil.getUUID());
            userBean.setPassword(map.get("password"));
            userBean.setSalt(map.get("salt"));
        }
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
                if(null != userBean.getAppType() && "0".equals(userBean.getAppType())) {//账号密码注册
                    String filePath = this.getClass().getResource("/static/admin/img/icon.png").getPath();
                    File file = new File(filePath);
                    InputStream inputStream = new FileInputStream(file);
                    MultipartFile multipartFile = new MockMultipartFile(file.getName(), inputStream);
                    String avatar = FileUploadUtil.upload(multipartFile);

                    UserIconBean userIcon = new UserIconBean();
                    userIcon.setYhnm(userBean.getYhnm());   //  用户内码
                    userIcon.setIconUrl(avatar);            //  头像url路径
                    adminService.uploadIcon(userIcon);
                }else {
                    MultipartFile file = FileUploadUtil.createFileItem(userBean.getIconUrl(), "test");
                    String avatar = FileUploadUtil.upload(file);
                    UserIconBean userIcon = new UserIconBean();
                    userIcon.setYhnm(userBean.getYhnm());
                    userIcon.setIconUrl(avatar);
                    adminService.uploadIcon(userIcon);
                }
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
     * 通过用户名查询用户信息
     * @param username
     * @return
     */
    public UserLoginBean findUserByUsername(String username) {
        return loginMapper.findUserByUsername(username);
    }

    /**
     * 查询用户所属的角色信息
     * @param yhnm
     * @return
     */
    public List<RoleBean> queryRoles(String yhnm) {
        return loginMapper.queryRoles(yhnm);
    }

    /**
     * 查询用户所属的权限信息
     * @param yhnm
     * @return
     */
    public List<PermissionBean> queryPerms(String yhnm) {
        return loginMapper.queryPerms(yhnm);
    }

    /**
     * 获取accessToken
     * @param type
     * @return
     */
    public AccessTokenBean getAccessToken(String type) {
        return loginMapper.getAccessToken(type);
    }

    /**
     * 新增accessToken值
     * @param accessToken
     * @return
     */
    public int addAccessToken(AccessTokenBean accessToken) {
        return loginMapper.addAccessToken(accessToken);
    }

    /**
     * 修改accessToken值
     * @param accessToken
     * @return
     */
    public int updateAccessToken(AccessTokenBean accessToken) {
        return loginMapper.updateAccessToken(accessToken);
    }

    /**
     * 通过第三方登录唯一标识查询用户信息
     * @param openId
     * @return
     */
    public UserAuthsBean getUserAuthsByOpenId(String openId) {
        return loginMapper.getUserAuthsByOpenId(openId);
    }

    /**
     * 新增第三方用户信息
     * @param userAuths
     * @return
     */
    public int addUserAuths(UserAuthsBean userAuths) {
        return loginMapper.addUserAuths(userAuths);
    }
}
