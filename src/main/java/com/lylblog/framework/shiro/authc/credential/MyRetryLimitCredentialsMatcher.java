package com.lylblog.framework.shiro.authc.credential;

import com.lylblog.common.support.LoginTypeConstant;
import com.lylblog.common.util.StringUtil;
import com.lylblog.common.util.redis.RedisUtil;
import com.lylblog.framework.shiro.authc.CustomToken;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: lyl
 * @Date: 2021/10/27 13:47
 */
@Configuration
public class MyRetryLimitCredentialsMatcher extends HashedCredentialsMatcher {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
        CustomToken tk = (CustomToken) authcToken;
        String fromType = tk.getLoginType();//登录类型
        if(fromType.equals(LoginTypeConstant.NOPASSWORD)){
            return true;
        }
        boolean matches = super.doCredentialsMatch(authcToken, info);
        return matches;
    }
}
