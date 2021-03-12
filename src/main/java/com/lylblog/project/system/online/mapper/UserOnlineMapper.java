package com.lylblog.project.system.online.mapper;

import com.lylblog.project.system.online.bean.UserOnlineBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: lyl
 * @Date: 2021/2/4 19:40
 */
@Mapper
public interface UserOnlineMapper {
    /**
     * 通过会话序号查询信息
     *
     * @param sessionId 会话ID
     * @return 在线用户信息
     */
    UserOnlineBean selectOnlineById(String sessionId);

    /**
     * 通过会话序号删除信息
     *
     * @param sessionId 会话ID
     * @return 在线用户信息
     */
    int deleteOnlineById(String sessionId);

    /**
     * 保存会话信息
     *
     * @param online 会话信息
     * @return 结果
     */
    int saveOnline(UserOnlineBean online);

    /**
     * 查询会话集合
     *
     * @param userOnline 会话参数
     * @return 会话集合
     */
    List<UserOnlineBean> selectUserOnlineList(UserOnlineBean userOnline);

    /**
     * 查询过期会话集合
     *
     * @param lastAccessTime 过期时间
     * @return 会话集合
     */
    List<UserOnlineBean> selectOnlineByExpired(@Param("lastAccessTime") String lastAccessTime);
}
