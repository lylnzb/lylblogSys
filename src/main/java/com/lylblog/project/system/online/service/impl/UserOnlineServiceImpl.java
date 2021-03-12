package com.lylblog.project.system.online.service.impl;

import com.lylblog.common.util.DateUtil;
import com.lylblog.common.util.StringUtil;
import com.lylblog.project.system.online.bean.UserOnlineBean;
import com.lylblog.project.system.online.mapper.UserOnlineMapper;
import com.lylblog.project.system.online.service.UserOnlineService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: lyl
 * @Date: 2021/2/4 20:27
 */
@Service
public class UserOnlineServiceImpl implements UserOnlineService {

    @Resource
    private UserOnlineMapper userOnlineMapper;

    /**
     * 通过会话序号查询信息
     *
     * @param sessionId 会话ID
     * @return 在线用户信息
     */
    @Override
    public UserOnlineBean selectOnlineById(String sessionId)
    {
        return userOnlineMapper.selectOnlineById(sessionId);
    }

    /**
     * 通过会话序号删除信息
     *
     * @param sessionId 会话ID
     * @return 在线用户信息
     */
    @Override
    public void deleteOnlineById(String sessionId)
    {
        UserOnlineBean userOnline = selectOnlineById(sessionId);
        if (StringUtil.isNotNull(userOnline))
        {
            userOnlineMapper.deleteOnlineById(sessionId);
        }
    }

    /**
     * 通过会话序号删除信息
     *
     * @param sessions 会话ID集合
     * @return 在线用户信息
     */
    @Override
    public void batchDeleteOnline(List<String> sessions)
    {
        for (String sessionId : sessions)
        {
            UserOnlineBean userOnline = selectOnlineById(sessionId);
            if (StringUtil.isNotNull(userOnline))
            {
                userOnlineMapper.deleteOnlineById(sessionId);
            }
        }
    }

    /**
     * 保存会话信息
     *
     * @param online 会话信息
     */
    @Override
    public void saveOnline(UserOnlineBean online)
    {
        userOnlineMapper.saveOnline(online);
    }

    /**
     * 查询会话集合
     *
     */
    @Override
    public List<UserOnlineBean> selectUserOnlineList(UserOnlineBean userOnline)
    {
        return userOnlineMapper.selectUserOnlineList(userOnline);
    }

    /**
     * 强退用户
     *
     * @param sessionId 会话ID
     */
    @Override
    public void forceLogout(String sessionId)
    {

    }

    /**
     * 查询会话集合
     *
     */
    @Override
    public List<UserOnlineBean> selectOnlineByExpired(Date expiredDate)
    {
        String lastAccessTime = DateUtil.datetimeToStr(expiredDate, "yyyy-MM-dd hh:mm:ss");
        return userOnlineMapper.selectOnlineByExpired(lastAccessTime);
    }
}
