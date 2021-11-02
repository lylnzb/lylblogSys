package com.lylblog.project.webSite.user.service;

import com.lylblog.project.common.bean.DynamicBean;
import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.login.bean.UserAuthsBean;
import com.lylblog.project.webSite.user.bean.UserCommentBean;
import com.lylblog.project.webSite.user.bean.UserLinkBean;
import com.lylblog.project.webSite.user.bean.UserLoginRecordBean;
import com.lylblog.project.webSite.user.bean.UserParamBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 是否绑定第三方账号
     * @return
     */
    int isUserAuths();

    /**
     * 我的评论列表
     * @param comment
     * @return
     */
    ResultObj queryMyCommentsByYhnm(UserCommentBean comment);

    /**
     * 我的友链申请列表
     * @param linkStatus
     * @return
     */
    ResultObj queryMyLinks(String linkStatus);

    /**
     * 根据编号查询友链信息
     * @param linkId
     * @return
     */
    ResultObj queryMyLinksById(String linkId);

    /**
     * 删除我的评论
     * @param commentId
     * @return
     */
    ResultObj delMyComment(String commentId);

    /**
     * 更新个人资料
     * @param userParam
     * @return
     */
    ResultObj updatePersonalData(UserParamBean userParam);

    /**
     * 查询个人资料详情
     * @return
     */
    ResultObj queryPersonalData();

    /**
     * 查询个人动态信息
     * @return
     */
    ResultObj queryDynamicInfo(DynamicBean dynamic);

    /**
     * 账号注销
     * @return
     */
    ResultObj accountCancel();

    /**
     * 查询已绑定第三方账号
     * @return
     */
    ResultObj queryUserAuthsInfoByYhnm();

    /**
     * 解绑第三方账号
     * @param openId
     * @return
     */
    ResultObj unbundUserAuths(String openId);
}
