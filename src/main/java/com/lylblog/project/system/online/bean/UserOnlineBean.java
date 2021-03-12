package com.lylblog.project.system.online.bean;


import com.lylblog.project.common.bean.ParaBean;
import com.lylblog.project.system.online.bean.OnlineSession.OnlineStatus;

/**
 * @Author: lyl
 * @Date: 2021/2/4 15:06
 */
public class UserOnlineBean extends ParaBean {
    private static final long serialVersionUID = 1L;

    private String sessionId;      //	回话ID
    private String nickName;       //	用户昵称
    private String onlineIp;       //	在线主机IP
    private String loginAddress;   //	登录地址
    private String browser;        //	浏览器
    private String onlineSystem;   //	在线系统
    private String loginTime;      //	登录时间
    private String lastAccessTime; //	最后访问时间


    /** 在线状态 */
    private OnlineStatus sessionStatus = OnlineStatus.on_line;

    /** 备份的当前用户会话 */
    private OnlineSession session;

    public String getSessionId()
    {
        return sessionId;
    }

    public void setSessionId(String sessionId)
    {
        this.sessionId = sessionId;
    }

    public String getBrowser()
    {
        return browser;
    }

    public void setBrowser(String browser)
    {
        this.browser = browser;
    }

    public OnlineSession getSession()
    {
        return session;
    }

    public void setSession(OnlineSession session)
    {
        this.session = session;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getOnlineIp() {
        return onlineIp;
    }

    public void setOnlineIp(String onlineIp) {
        this.onlineIp = onlineIp;
    }

    public String getLoginAddress() {
        return loginAddress;
    }

    public void setLoginAddress(String loginAddress) {
        this.loginAddress = loginAddress;
    }

    public String getOnlineSystem() {
        return onlineSystem;
    }

    public void setOnlineSystem(String onlineSystem) {
        this.onlineSystem = onlineSystem;
    }

    public OnlineStatus getSessionStatus() {
        return sessionStatus;
    }

    public void setSessionStatus(OnlineStatus sessionStatus) {
        this.sessionStatus = sessionStatus;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(String lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }
}
