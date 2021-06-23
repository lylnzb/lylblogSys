package com.lylblog.project.webSite.user.mapper;

import com.lylblog.project.webSite.user.bean.UserLoginRecordBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: lyl
 * @Date: 2021/6/7 11:35
 */
@Mapper
public interface UserMapper {

    /**
     * 查询当前登录用户的登录记录数据
     * @param userLoginRecord
     * @return
     */
    List<UserLoginRecordBean> queryLoginRecord(UserLoginRecordBean userLoginRecord);

    /**
     * 查询当前登录用户的登录记录数据总数
     * @param userLoginRecord
     * @return
     */
    int queryLoginRecordCount(UserLoginRecordBean userLoginRecord);

    /**
     * 是否已绑定邮箱
     * @param yhnm
     * @return
     */
    int isbindingEmail(@Param("yhnm") String yhnm);

    /**
     * 是否已设置密码
     * @param yhnm
     * @return
     */
    int isSetupPwd(@Param("yhnm") String yhnm);
}
