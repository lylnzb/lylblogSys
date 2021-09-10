package com.lylblog.project.system.comment.mapper;

import com.lylblog.project.system.comment.bean.CommentBean;
import com.lylblog.project.webSite.comment.bean.WebGreatBean;
import com.lylblog.project.webSite.comment.bean.WebCommentBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: lyl
 * @Date: 2021/4/19 16:50
 */
@Mapper
public interface CommentMapper {

    /**
     * 评论发布
     * @param commentBean
     * @return
     */
    int addComment(CommentBean commentBean);

    /**
     * 网站主评论列表信息查询总数
     * @return
     */
    int mainCommentListCount(CommentBean commentBean);

    /**
     * 网站主评论列表信息查询
     * @param commentBean
     * @return
     */
    List<WebCommentBean> mainCommentList(CommentBean commentBean);

    /**
     * 网站评论回复列表信息查询
     * @param commentBean
     * @return
     */
    List<WebCommentBean> secondaryCommentList(CommentBean commentBean);

    /**
     * 通过回复ID查询评论数据
     * @param replyId
     * @return
     */
    CommentBean getCommentByReplyId(@Param("replyId") String replyId);

    /**
     * 通过文章内码查询总评论数量
     * @param wznm
     * @return
     */
    int totalCommentCountByWznm(@Param("wznm") String wznm);

    /**
     * 查询留言反馈总评论数量
     * @return
     */
    int totalCommentCount();

    /**
     * 新增点赞记录
     * @param greatBean
     * @return
     */
    int addGreatInfo(WebGreatBean greatBean);

    /**
     * 修改点赞记录
     * @param greatBean
     * @return
     */
    int updateGreatInfo(WebGreatBean greatBean);

    /**
     * 是否存在点赞记录
     * @param type
     * @param typeId
     * @param yhnm
     * @return
     */
    int isThereGreat(@Param("type") String type, @Param("typeId") String typeId, @Param("yhnm") String yhnm);

    /**
     * 查询文章的评论数量
     * @param commentId
     * @return
     */
    String getCommentCountByCommentId(@Param("commentId") String commentId);

    /**
     * 修改文章的评论数量
     * @param praiseNum
     * @param commentId
     * @return
     */
    int updateCommentCountByCommentId(@Param("praiseNum") int praiseNum, @Param("commentId") String commentId);

    /**
     * 后台系统评论管理信息查询
     * @param commentBean
     * @return
     */
    List<CommentBean> queryCommentInfo(CommentBean commentBean);

    /**
     * 后台系统评论管理信息总数查询
     * @param commentBean
     * @return
     */
    int queryCommentInfoCount(CommentBean commentBean);

    /**
     * 删除评论信息
     * @param deleteIds
     * @return
     */
    int deleteCommentInfo(@Param("deleteIds") List<String> deleteIds);
}
