package com.lylblog.project.webSite.message.service;

import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.system.comment.bean.CommentBean;
import com.lylblog.project.webSite.comment.bean.WebGreatBean;

/**
 * @Author: lyl
 * @Date: 2021/2/8 15:17
 */
public interface MessageService {

    /**
     * 留言发布
     * @param commentBean
     * @return
     */
    ResultObj addMessage(CommentBean commentBean);

    /**
     * 留言反馈列表信息查询
     * @param commentBean
     * @return
     */
    ResultObj messageList(CommentBean commentBean);

    /**
     * 查询留言反馈总评论数量
     * @return
     */
    int totalCommentCount();
}
