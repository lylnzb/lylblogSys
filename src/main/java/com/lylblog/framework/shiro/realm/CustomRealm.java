package com.lylblog.framework.shiro.realm;

import com.lylblog.common.support.CommonConstant;
import com.lylblog.common.support.LoginTypeConstant;
import com.lylblog.common.util.MessageUtil;
import com.lylblog.common.util.StringUtil;
import com.lylblog.common.util.redis.RedisUtil;
import com.lylblog.common.util.shiro.ShiroUtils;
import com.lylblog.framework.shiro.authc.CustomToken;
import com.lylblog.project.login.bean.UserAuthsBean;
import com.lylblog.project.login.bean.UserLoginBean;
import com.lylblog.project.login.service.LoginService;
import com.lylblog.project.system.admin.bean.PermissionBean;
import com.lylblog.project.system.admin.bean.RoleBean;
import com.lylblog.project.system.log.service.LogService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 描述：
 *
 * @author caojing
 * @create 2019-01-27-13:57
 */
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private LoginService loginServer;

    @Autowired
    private LogService logService;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获取当前用户信息
        UserLoginBean user = ShiroUtils.getUserInfo();
        //获取角色信息
        user.setRoles(loginServer.queryRoles(user.getYhnm()));
        //获取权限信息
        user.setPerms(loginServer.queryPerms(user.getYhnm()));
        for(RoleBean role : user.getRoles()){
            info.addRole(role.getRolename());
        }
        for(PermissionBean perm : user.getPerms()){
            info.addStringPermission(perm.getPermission());
        }
        return info;
    }

    /**
     * 获取即将需要认证的信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("-------身份认证方法--------");
        CustomToken tk = (CustomToken) authenticationToken;
        String loginType = tk.getLoginType();
        String userName = (String) tk.getPrincipal();
        String userPwd = new String((char[]) tk.getCredentials());
        if(loginType.equals(LoginTypeConstant.PASSWORD)) {//如果是账号密码登录，则需要验证密码
            //根据用户名从数据库获取用户信息
            UserLoginBean user = loginServer.findUserByUsername(userName);
            if (user == null) {
                UserLoginBean userLoginBean = new UserLoginBean();
                userLoginBean.setNickname(userName);
                logService.insertLoginLogInfo(userLoginBean, CommonConstant.LOGIN_FAIL, MessageUtil.message("user.not.exists"));
                throw new AccountException("找不到该用户信息");
            }
            user.setAppType("0");

            AtomicInteger errorNum = new AtomicInteger(0);//初始化错误登录次数
            String value = (String) redisUtil.get("login:error:" + userName);//获取错误登录的次数
            if (StringUtil.isNotBlank(value)) {
                errorNum = new AtomicInteger(Integer.parseInt(value));
            }
            if (errorNum.get() >= 5) {  //如果用户错误登录次数超过5次
                throw new ExcessiveAttemptsException(); //抛出账号锁定异常类
            }

            String md5Pwd = new SimpleHash("MD5", userPwd,
                    ByteSource.Util.bytes(user.getEmail() + ((user.getSalt() == null)?"":user.getSalt())), 2).toHex();
            if (!md5Pwd.equals(user.getPassword())) {
                //存储错误次数到redis中
                redisUtil.setEx("login:error:" + userName, errorNum.incrementAndGet() + "", 1800, TimeUnit.SECONDS);
                logService.insertLoginLogInfo(user, CommonConstant.LOGIN_FAIL, MessageUtil.message("user.password.not.match"));
                throw new AccountException("密码不正确");
            }else {
                redisUtil.delete("login:error:" + userName);//移除缓存中用户的错误登录次数
            }
            logService.insertLoginLogInfo(user, CommonConstant.LOGIN_SUCCESS, MessageUtil.message("user.login.success"));
            return new SimpleAuthenticationInfo(user, user.getPassword(),
                    ByteSource.Util.bytes(userName + user.getSalt()), getName());
        }else if(loginType.equals(LoginTypeConstant.NOPASSWORD)) {//如果是免密登录，则无需验证密码
            //根据第三方唯一ID查询第三方登录信息
            UserAuthsBean userAuths = loginServer.getUserAuthsByOpenId(userName);
            //根据用户内码查询用户信息
            UserLoginBean user = loginServer.findUserByUsername(userAuths.getYhnm());
            user.setAppType(userAuths.getAppType());
            logService.insertLoginLogInfo(user, CommonConstant.LOGIN_SUCCESS, MessageUtil.message("user.login.success"));
            return new SimpleAuthenticationInfo(user, "","");
        }
        return null;
    }
}