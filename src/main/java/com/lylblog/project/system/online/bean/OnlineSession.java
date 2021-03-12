package com.lylblog.project.system.online.bean;

import org.apache.shiro.session.mgt.SimpleSession;

/**
 * 在线用户会话属性
 * @Author: lyl
 * @Date: 2021/2/4 13:58
 */
public class OnlineSession extends SimpleSession {

    private static final long serialVersionUID = 1L;

    private String nickName;       //	用户昵称
    private String loginAddress;   //	登录地址
    private String browser;        //	浏览器
    private String onlineSystem;   //	在线系统
    private String sessionStatus;  //	会话状态
    private String loginTime;      //	登录时间

    private String userId;

    /** 在线状态 */
    private OnlineStatus status = OnlineStatus.on_line;

    /** 属性是否改变 优化session数据同步 */
    private transient boolean attributeChanged = false;

    public OnlineStatus getStatus()
    {
        return status;
    }

    public void setStatus(OnlineStatus status)
    {
        this.status = status;
    }

    public void markAttributeChanged()
    {
        this.attributeChanged = true;
    }

    public void resetAttributeChanged()
    {
        this.attributeChanged = false;
    }

    public boolean isAttributeChanged()
    {
        return attributeChanged;
    }

    @Override
    public void setAttribute(Object key, Object value)
    {
        super.setAttribute(key, value);
    }

    @Override
    public Object removeAttribute(Object key)
    {
        return super.removeAttribute(key);
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getLoginAddress() {
        return loginAddress;
    }

    public void setLoginAddress(String loginAddress) {
        this.loginAddress = loginAddress;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getOnlineSystem() {
        return onlineSystem;
    }

    public void setOnlineSystem(String onlineSystem) {
        this.onlineSystem = onlineSystem;
    }

    public String getSessionStatus() {
        return sessionStatus;
    }

    public void setSessionStatus(String sessionStatus) {
        this.sessionStatus = sessionStatus;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public void setAttributeChanged(boolean attributeChanged) {
        this.attributeChanged = attributeChanged;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public static enum OnlineStatus {
        /** 用户状态 */
        on_line("在线"), off_line("离线");
        private final String info;

        private OnlineStatus(String info)
        {
            this.info = info;
        }

        public String getInfo()
        {
            return info;
        }
    }

}
