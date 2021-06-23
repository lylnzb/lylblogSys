package com.lylblog.framework.shiro.realm;

import com.lylblog.common.support.CommonConstant;
import com.lylblog.common.util.MessageUtil;
import com.lylblog.common.util.shiro.ShiroUtils;
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

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获取当前用户信息

        UserLoginBean user = loginServer.findUserByEmail(ShiroUtils.getUserInfo().getEmail());
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
        String userName = (String) authenticationToken.getPrincipal();
        String userPwd = new String((char[]) authenticationToken.getCredentials());
        //根据用户名从数据库获取密码
        UserLoginBean user = loginServer.findUserByEmail(userName);
        if (user == null) {
            UserLoginBean userLoginBean = new UserLoginBean();
            userLoginBean.setNickname(userName);
            logService.insertLoginLogInfo(userLoginBean, CommonConstant.LOGIN_FAIL, MessageUtil.message("user.not.exists"));
            throw new AccountException("找不到该用户信息");
        }
        String md5Pwd = new SimpleHash("MD5", userPwd,
                ByteSource.Util.bytes(user.getEmail() + ((user.getSalt() == null)?"":user.getSalt())), 2).toHex();
        if (!md5Pwd.equals(user.getPassword())) {
            logService.insertLoginLogInfo(user, CommonConstant.LOGIN_FAIL, MessageUtil.message("user.password.not.match"));
            throw new AccountException("密码不正确");
        }
        logService.insertLoginLogInfo(user, CommonConstant.LOGIN_SUCCESS, MessageUtil.message("user.login.success"));
        return new SimpleAuthenticationInfo(user, user.getPassword(),
                ByteSource.Util.bytes(userName + user.getSalt()), getName());
    }
}