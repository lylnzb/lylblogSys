package com.lylblog.project.webSite.user.service;

import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.webSite.user.bean.UserLoginRecordBean;

/**
 * @Author: lyl
 * @Date: 2021/6/7 11:34
 */
public interface UserService {

    /**
     * 查询当前登录用户的登录记录数据
     * @param userLoginRecord
     * @return
     */
    ResultObj queryLoginRecord(UserLoginRecordBean userLoginRecord);

    /**
     * 是否已绑定邮箱
     * @return
     */
    int isbindingEmail();

    /**
     * 是否已设置密码
     * @return
     */
    int isSetupPwd();
}
