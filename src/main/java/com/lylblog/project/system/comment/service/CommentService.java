package com.lylblog.project.system.comment.service;

import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.system.comment.bean.CommentBean;
import com.lylblog.project.system.webColumn.bean.WebColumnBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: lyl
 * @Date: 2021/4/19 16:52
 */
public interface CommentService {

    /**
     * 后台系统评论管理信息查询
     * @param commentBean
     * @return
     */
    ResultObj queryCommentInfo(CommentBean commentBean);

    /**
     * 删除评论信息
     * @param deleteIds
     * @return
     */
    ResultObj deleteCommentInfo(List<String> deleteIds);
}
