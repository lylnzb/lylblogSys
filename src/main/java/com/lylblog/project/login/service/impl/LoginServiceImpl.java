package com.lylblog.project.login.service.impl;

import com.lylblog.common.support.CommonConstant;
import com.lylblog.common.util.DateUtil;
import com.lylblog.common.util.EncryptionUtil;
import com.lylblog.common.util.IdUtil;
import com.lylblog.common.util.MessageUtil;
import com.lylblog.project.login.bean.UserLoginBean;
import com.lylblog.project.login.mapper.LoginMapper;
import com.lylblog.project.login.service.LoginService;
import com.lylblog.project.system.log.mapper.LogMapper;
import com.lylblog.project.system.log.service.LogService;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private LoginMapper loginMapper;

    /**
     * 用户注册
     * @return
     */
    public int registerUser(UserLoginBean userBean){
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
            userBean.setYhnm(userBean.getYhnm());//用户id
            userBean.setRoleId("w4lI24P684Gcp6Yx");//默认角色：普通用户
            loginMapper.addUserAndRoleRelevant(userBean);
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
}
