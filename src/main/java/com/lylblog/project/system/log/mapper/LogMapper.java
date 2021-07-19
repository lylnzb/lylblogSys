package com.lylblog.project.system.log.mapper;

import com.lylblog.project.system.log.bean.LogDetailBean;
import com.lylblog.project.system.log.bean.LoginLogBean;
import com.lylblog.project.system.log.bean.OperLogBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: lyl
 * @Date: 2021/6/13 11:05
 */
@Mapper
public interface LogMapper {

    /**
     * 新增用户登录日志记录
     * @param loginLog
     * @return
     */
    int insertLoginLogInfo(LoginLogBean loginLog);

    /**
     * 删除用户登录日志记录
     * @param deleteIds
     * @return
     */
    int deleteLoginLogInfo(@Param("deleteIds") List<String> deleteIds);

    /**
     * 新增系统操作日志记录
     * @param operLog
     * @return
     */
    int insertOperLogInfo(OperLogBean operLog);

    /**
     * 删除系统操作日志记录
     * @param deleteIds
     * @return
     */
    int deleteOperLogInfo(@Param("deleteIds") List<String> deleteIds);

    /**
     * 查询用户登录日志记录
     * @param loginLog
     * @return
     */
    List<LoginLogBean> queryLoginLogInfo(LoginLogBean loginLog);

    /**
     * 查询用户登录日志记录总数
     * @param loginLog
     * @return
     */
    int queryLoginLogInfoCount(LoginLogBean loginLog);

    /**
     * 查询系统操作日志记录
     * @param operLog
     * @return
     */
    List<OperLogBean> queryOperLogInfo(OperLogBean operLog);

    /**
     * 查询系统操作日志记录总数
     * @param operLog
     * @return
     */
    int queryOperLogInfoCount(OperLogBean operLog);

    /**
     * 查询操作日志详情信息
     * @param logId
     * @return
     */
    LogDetailBean queryOperLogDetailInfo(@Param("logId") String logId);
}
