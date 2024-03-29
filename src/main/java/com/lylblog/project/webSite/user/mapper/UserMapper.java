package com.lylblog.project.webSite.user.mapper;

import com.lylblog.project.common.bean.DynamicBean;
import com.lylblog.project.login.bean.UserAuthsBean;
import com.lylblog.project.webSite.user.bean.UserCommentBean;
import com.lylblog.project.webSite.user.bean.UserLinkBean;
import com.lylblog.project.webSite.user.bean.UserLoginRecordBean;
import com.lylblog.project.webSite.user.bean.UserParamBean;
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

    /**
     * 是否绑定第三方账号
     * @param yhnm
     * @return
     */
    int isUserAuths(@Param("yhnm") String yhnm);

    /**
     * 修改密码
     * @param newPwd
     * @param salt
     * @param email
     * @return
     */
    int updatePwd(@Param("newPwd") String newPwd, @Param("salt") String salt, @Param("email") String email);

    /**
     * 验证邮箱是否已注册
     * @param newEmail
     * @return
     */
    int validationEmail(@Param("newEmail") String newEmail);

    /**
     * 绑定新邮箱
     * @param newEmail
     * @param yhnm
     * @return
     */
    int bindEmail(@Param("newEmail") String newEmail, @Param("yhnm") String yhnm);

    /**
     * 我的评论列表
     * @param comment
     * @return
     */
    List<UserCommentBean> queryMyCommentsByYhnm(UserCommentBean comment);

    /**
     * 我的评论列表总数
     * @param comment
     * @return
     */
    int queryMyCommentsByYhnmCount(UserCommentBean comment);

    /**
     * 我的友链申请列表
     * @param linkStatus
     * @param yhnm
     * @return
     */
    List<UserLinkBean> queryMyLinks(@Param("linkStatus") String linkStatus, @Param("yhnm") String yhnm);

    /**
     * 根据编号查询友链信息
     * @param linkId
     * @return
     */
    UserLinkBean queryMyLinksById(@Param("linkId") String linkId);

    /**
     * 删除我的主体评论
     * @param commentId
     * @return
     */
    int delMyMainComments(@Param("commentId") String commentId);

    /**
     * 删除我的子评论
     * @param replyId
     * @return
     */
    int delMyChildComments(@Param("replyId") String replyId);

    /**
     * 更新个人资料
     * @param userParam
     * @return
     */
    int updatePersonalData(UserParamBean userParam);

    /**
     * 查询个人资料详情
     * @param yhnm
     * @return
     */
    UserParamBean queryPersonalData(@Param("yhnm") String yhnm);

    /**
     * 查询个人动态信息总数
     * @param dynamic
     * @return
     */
    int queryDynamicInfoCount(DynamicBean dynamic);

    /**
     * 查询个人动态信息
     * @param dynamic
     * @return
     */
    List<DynamicBean> queryDynamicInfo(DynamicBean dynamic);

    /**
     * 查询已绑定第三方账号
     * @param yhnm
     * @return
     */
    List<UserAuthsBean> queryUserAuthsInfoByYhnm(@Param("yhnm") String yhnm);

    /**
     * 解绑第三方账号
     * @param openId
     * @param yhnm
     * @return
     */
    int unbundUserAuths(@Param("openId") String openId, @Param("yhnm") String yhnm);
}
