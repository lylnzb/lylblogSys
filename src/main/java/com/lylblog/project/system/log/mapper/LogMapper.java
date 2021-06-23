package com.lylblog.project.system.log.mapper;

import com.lylblog.project.system.log.bean.LoginLogBean;
import com.lylblog.project.system.log.bean.OperLogBean;
import org.apache.ibatis.annotations.Mapper;

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
     * 新增系统操作日志记录
     * @param operLog
     * @return
     */
    int insertOperLogInfo(OperLogBean operLog);

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
}
