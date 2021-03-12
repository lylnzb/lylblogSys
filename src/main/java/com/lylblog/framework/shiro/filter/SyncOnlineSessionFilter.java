package com.lylblog.framework.shiro.filter;

import com.lylblog.framework.shiro.session.OnlineSessionDAO;
import com.lylblog.project.system.online.bean.OnlineSession;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @Author: lyl
 * @Date: 2021/2/4 14:55
 */
public class SyncOnlineSessionFilter extends PathMatchingFilter {

    @Autowired
    private OnlineSessionDAO onlineSessionDAO;

    /**
     * 同步会话数据到DB 一次请求最多同步一次 防止过多处理 需要放到Shiro过滤器之前
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception
    {
        OnlineSession session = (OnlineSession) request.getAttribute("online_session");
        // 如果session stop了 也不同步
        // session停止时间，如果stopTimestamp不为null，则代表已停止
        if (session != null
                && session.getUserId() != null
                && session.getStopTimestamp() == null
                && session.getStatus() == OnlineSession.OnlineStatus.on_line)
        {
            onlineSessionDAO.syncToDb(session);
        }
        return true;
    }
}
