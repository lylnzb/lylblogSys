package com.lylblog.project.webSite.user.service.impl;

import com.lylblog.common.util.shiro.ShiroUtils;
import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.login.bean.UserLoginBean;
import com.lylblog.project.webSite.user.bean.UserLoginRecordBean;
import com.lylblog.project.webSite.user.mapper.UserMapper;
import com.lylblog.project.webSite.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: lyl
 * @Date: 2021/6/7 11:34
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 查询当前登录用户的登录记录数据
     * @param userLoginRecord
     * @return
     */
    public ResultObj queryLoginRecord(UserLoginRecordBean userLoginRecord){
        //获取当前用户信息
        UserLoginBean userBean = ShiroUtils.getUserInfo();
        userLoginRecord.setYhnm(userBean.getYhnm());
        int count = userMapper.queryLoginRecordCount(userLoginRecord);
        if(count > 0){
            List<UserLoginRecordBean> userLoginRecordList = userMapper.queryLoginRecord(userLoginRecord);
            return ResultObj.ok(count, userLoginRecordList);
        }
        return ResultObj.fail("没有查询到用户登录记录信息");
    }

    /**
     * 是否已绑定邮箱
     * @return
     */
    public int isbindingEmail() {
        //获取当前用户信息
        UserLoginBean user = ShiroUtils.getUserInfo();
        int falg = userMapper.isbindingEmail(user.getYhnm());//是否已绑定邮箱
        return falg;
    }

    /**
     * 是否已设置密码
     * @return
     */
    public int isSetupPwd() {
        //获取当前用户信息
        UserLoginBean user = ShiroUtils.getUserInfo();
        int falg = userMapper.isSetupPwd(user.getYhnm());//是否已设置密码
        return falg;
    }
}
