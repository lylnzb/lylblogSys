package com.lylblog.framework.shiro.authc.credential;

import com.lylblog.common.support.LoginTypeConstant;
import com.lylblog.framework.shiro.authc.CustomToken;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: lyl
 * @Date: 2021/10/27 13:47
 */
@Configuration
public class MyRetryLimitCredentialsMatcher extends HashedCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
        CustomToken tk = (CustomToken) authcToken;
        if(tk.getLoginType().equals(LoginTypeConstant.NOPASSWORD)){
            return true;
        }
        boolean matches = super.doCredentialsMatch(authcToken, info);
        return matches;
    }
}
