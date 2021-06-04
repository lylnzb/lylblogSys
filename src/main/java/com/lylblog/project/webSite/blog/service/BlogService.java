package com.lylblog.project.webSite.blog.service;

import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.system.article.bean.ArticleBean;
import com.lylblog.project.webSite.blog.bean.WebArticleBean;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: lyl
 * @Date: 2021/2/8 15:09
 */
public interface BlogService {

    /**
     * 查询当前文章的上一篇文章
     * @param articleId
     * @param columnId
     * @return
     */
    WebArticleBean getOnArticle(String articleId, String columnId);

    /**
     * 查询当前文章的下一篇文章
     * @param articleId
     * @param columnId
     * @return
     */
    WebArticleBean getUnderArticle(String articleId, String columnId);

    /**
     * 查询网站文章信息
     * @param article
     * @return
     */
    ResultObj queryBlogInfo(ArticleBean article);

    /**
     * 获取栏目名称
     * @param columnId
     * @return
     */
    String getColumnName(String columnId);

    /**
     * 获取标签名称
     * @param labelId
     * @return
     */
    String getLabelName(String labelId);
}
