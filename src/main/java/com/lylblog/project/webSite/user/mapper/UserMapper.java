package com.lylblog.project.webSite.user.mapper;

import com.lylblog.project.common.bean.DynamicBean;
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


}
