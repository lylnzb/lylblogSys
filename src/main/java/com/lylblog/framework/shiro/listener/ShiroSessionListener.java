package com.lylblog.framework.shiro.listener;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

/**
 * 配置session监听器
 *
 * @author zhuyongsheng
 * @date 2019/8/12
 */
public class ShiroSessionListener implements SessionListener {

    /**
     * 回话创建触发
     *
     * @author zhuyongsheng
     * @date 2019/8/12
     */
    @Override
    public void onStart(Session session) {

    }

    /**
     * 退出会话时触发
     *
     * @author zhuyongsheng
     * @date 2019/8/12
     */
    @Override
    public void onStop(Session session) {
        System.out.println("onStop====="+session);
    }

    /**
     * 会话过期时触发
     *
     * @author zhuyongsheng
     * @date 2019/8/12
     */
    @Override
    public void onExpiration(Session session) {
        System.out.println("onExpiration====="+session);
    }
}
