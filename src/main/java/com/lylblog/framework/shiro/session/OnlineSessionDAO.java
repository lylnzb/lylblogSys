package com.lylblog.framework.shiro.session;

import com.lylblog.framework.shiro.manager.AsyncManager;
import com.lylblog.framework.shiro.manager.factory.AsyncFactory;
import com.lylblog.project.system.online.bean.OnlineSession;
import com.lylblog.project.system.online.service.UserOnlineService;
import org.apache.shiro.session.Session;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: lyl
 * @Date: 2021/2/4 15:01
 */
public class OnlineSessionDAO extends RedisSessionDAO {

    /**
     * 同步session到数据库的周期 单位为毫秒（默认1分钟）
     */
    private int dbSyncPeriod = 1;

    @Autowired
    private UserOnlineService onlineService;

    @Autowired
    private RedisSessionDAO redisSessionDAO;

    @Autowired
    private RedisManager redisManager;

    private String keyPrefix = "shiro_redis_session:";

    /**
     * 上次同步数据库的时间戳
     */
    private static final String LAST_SYNC_DB_TIMESTAMP = OnlineSessionDAO.class.getName() + "LAST_SYNC_DB_TIMESTAMP";

    @Autowired
    private OnlineSessionFactory onlineSessionFactory;

    public OnlineSessionDAO()
    {
        super();
    }

    public OnlineSessionDAO(long expireTime)
    {
        super();
    }

    /**
     * 根据会话ID获取会话
     *
     * @param sessionId 会话ID
     * @return ShiroSession
     */
//    @Override
//    protected Session doReadSession(Serializable sessionId)
//    {
//        UserOnlineBean userOnline = onlineService.selectOnlineById(String.valueOf(sessionId));
//        if (userOnline == null)
//        {
//            Session s = (Session) SerializeUtils.deserialize(redisManager.get(getByteKey(sessionId)));
//            return s;
//        }
//        return onlineSessionFactory.createSession(userOnline);
//    }

    private byte[] getByteKey(Serializable sessionId) {
        String preKey = this.keyPrefix + sessionId;
        return preKey.getBytes();
    }

    /**
     * 更新会话；如更新会话最后访问时间/停止会话/设置超时时间/设置移除属性等会调用
     */
    public void syncToDb(OnlineSession onlineSession)
    {
        Date lastSyncTimestamp = (Date) onlineSession.getAttribute(LAST_SYNC_DB_TIMESTAMP);
        if (lastSyncTimestamp != null)
        {
            boolean needSync = true;
            long deltaTime = onlineSession.getLastAccessTime().getTime() - lastSyncTimestamp.getTime();
            if (deltaTime < dbSyncPeriod * 60 * 1000)
            {
                // 时间差不足 无需同步
                needSync = false;
            }
            boolean isGuest = onlineSession.getUserId() == null || "".equals(onlineSession.getUserId());

            // session 数据变更了 同步
            if (isGuest == false && onlineSession.isAttributeChanged())
            {
                needSync = true;
            }

            if (needSync == false)
            {
                return;
            }
        }
        onlineSession.setAttribute(LAST_SYNC_DB_TIMESTAMP, onlineSession.getLastAccessTime());
        // 更新完后 重置标识
        if (onlineSession.isAttributeChanged())
        {
            onlineSession.resetAttributeChanged();
        }
        AsyncManager.me().execute(AsyncFactory.syncSessionToDb(onlineSession));
    }

    /**
     * 当会话过期/停止（如用户退出时）属性等会调用
     */
    @Override
    public void delete(Session session)
    {
        OnlineSession onlineSession = (OnlineSession) session;
        if (null == onlineSession)
        {
            return;
        }
        onlineSession.setStatus(OnlineSession.OnlineStatus.off_line);
        onlineService.deleteOnlineById(String.valueOf(onlineSession.getId()));
    }
}
