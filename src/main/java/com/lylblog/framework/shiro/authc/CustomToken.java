package com.lylblog.framework.shiro.authc;

import com.lylblog.common.support.LoginTypeConstant;
import com.lylblog.project.login.mapper.LoginMapper;
import lombok.Data;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 重写UsernamePasswordToken权限认证
 * @Author: lyl
 * @Date: 2021/10/27 13:32
 */
@Data
public class CustomToken extends UsernamePasswordToken {

    private String loginType;

    public CustomToken() {
        super();
    }

    /**
     * 免密登录
     * @param username
     */
    public CustomToken(String username) {
        super(username, "", false, null);
        this.loginType = LoginTypeConstant.NOPASSWORD;
    }

    /**
     * 账号密码登录
     * @param username
     * @param pwd
     */
    public CustomToken(String username, String pwd) {
        super(username, pwd, false, null);
        this.loginType = LoginTypeConstant.PASSWORD;
    }

    public CustomToken(String username, String password, String loginType, boolean rememberMe, String host) {
        super(username, password, rememberMe,  host);
        this.loginType = loginType;
    }
}
