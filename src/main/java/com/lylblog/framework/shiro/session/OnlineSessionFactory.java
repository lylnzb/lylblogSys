package com.lylblog.framework.shiro.session;

import com.lylblog.common.util.IpUtil;
import com.lylblog.common.util.StringUtil;
import com.lylblog.common.util.http.ServletUtil;
import com.lylblog.project.system.online.bean.OnlineSession;
import com.lylblog.project.system.online.bean.UserOnlineBean;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.web.session.mgt.WebSessionContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: lyl
 * @Date: 2021/2/4 15:04
 */
@Component
public class OnlineSessionFactory implements SessionFactory {
    public Session createSession(UserOnlineBean userOnline)
    {
        OnlineSession onlineSession = userOnline.getSession();
        if (StringUtil.isNotNull(onlineSession) && onlineSession.getId() == null)
        {
            onlineSession.setId(userOnline.getSessionId());
        }
        return userOnline.getSession();
    }

    @Override
    public Session createSession(SessionContext initData)
    {
        OnlineSession session = new OnlineSession();
        if (initData != null && initData instanceof WebSessionContext)
        {
            WebSessionContext sessionContext = (WebSessionContext) initData;
            HttpServletRequest request = (HttpServletRequest) sessionContext.getServletRequest();
            if (request != null)
            {
                UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtil.getRequest().getHeader("User-Agent"));
                // 获取客户端操作系统
                String os = userAgent.getOperatingSystem().getName();
                // 获取客户端浏览器
                String browser = userAgent.getBrowser().getName();
                session.setHost(IpUtil.getIpAddr(request));
                session.setBrowser(browser);
                session.setOnlineSystem(os);
            }
        }
        return session;
    }
}
