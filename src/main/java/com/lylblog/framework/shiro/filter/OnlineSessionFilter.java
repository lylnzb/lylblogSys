package com.lylblog.framework.shiro.filter;

import com.lylblog.common.util.shiro.ShiroUtils;
import com.lylblog.framework.shiro.session.OnlineSessionDAO;
import com.lylblog.project.login.bean.UserLoginBean;
import com.lylblog.project.system.online.bean.OnlineSession;
import com.lylblog.project.system.online.service.UserOnlineService;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @Author: lyl
 * @Date: 2021/2/4 14:59
 */
public class OnlineSessionFilter extends AccessControlFilter {
    /**
     * 强制退出后重定向的地址
     */
    private String loginUrl = "/";

    @Autowired
    private OnlineSessionDAO onlineSessionDAO;

    @Autowired
    private UserOnlineService userOnlineService;

    /**
     * 表示是否允许访问；mappedValue就是[urls]配置中拦截器参数部分，如果允许访问返回true，否则false；
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception
    {
        Subject subject = getSubject(request, response);
        if (subject == null || subject.getSession() == null)
        {
            return true;
        }
        System.out.println(subject.getSession().getId());
        Session session = onlineSessionDAO.readSession(subject.getSession().getId());
        if (session != null && session instanceof OnlineSession)
        {
            OnlineSession onlineSession = (OnlineSession) session;
            request.setAttribute("online_session", onlineSession);
            // 把user对象设置进去
            boolean isGuest = onlineSession.getUserId() == null || "".equals(onlineSession.getUserId());
            if (isGuest == true)
            {
                UserLoginBean user = ShiroUtils.getUserInfo();
                if (user != null)
                {
                    onlineSession.setUserId(user.getUserId());
                    onlineSession.setNickName((null == user.getNickname() || "".equals(user.getNickname()))?user.getEmail():user.getNickname());
                    onlineSession.markAttributeChanged();
                }
            }

            if (onlineSession.getStatus() == OnlineSession.OnlineStatus.off_line)
            {
                return false;
            }
        }
        return true;
    }

    /**
     * 表示当访问拒绝时是否已经处理了；如果返回true表示需要继续处理；如果返回false表示该拦截器实例已经处理了，将直接返回即可。
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception
    {
        Subject subject = getSubject(request, response);
        if (subject != null) {
            userOnlineService.deleteOnlineById(String.valueOf(subject.getSession().getId()));
            subject.logout();
        }
        saveRequestAndRedirectToLogin(request, response);
        return true;
    }

    // 跳转到登录页
    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException
    {
        WebUtils.issueRedirect(request, response, loginUrl);
    }
}
