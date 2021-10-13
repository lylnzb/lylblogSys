package com.lylblog.project.webSite.comment.service;

import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.system.comment.bean.CommentBean;
import com.lylblog.project.webSite.comment.bean.WebGreatBean;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: lyl
 * @Date: 2021/4/19 16:52
 */
public interface WebCommentService {

    /**
     * 评论发布
     * @param commentBean
     * @return
     */
    ResultObj addComment(CommentBean commentBean, MultipartFile file);

    /**
     * 网站评论列表信息查询
     * @param commentBean
     * @return
     */
    ResultObj commentList(CommentBean commentBean);

    /**
     * 通过文章内码查询总评论数量
     * @param wznm
     * @return
     */
    int totalCommentCountByWznm(String wznm);

    /**
     * 文章点赞或取消赞
     * @param webGreatBean
     * @return
     */
    ResultObj addGreatInfo(WebGreatBean webGreatBean, String isGiveLike);
}
