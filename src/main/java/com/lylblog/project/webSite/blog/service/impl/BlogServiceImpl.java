package com.lylblog.project.webSite.blog.service.impl;

import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.system.article.bean.ArticleBean;
import com.lylblog.project.system.article.mapper.ArticleMapper;
import com.lylblog.project.webSite.blog.bean.WebArticleBean;
import com.lylblog.project.webSite.blog.service.BlogService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.Max;
import java.util.List;

/**
 * @Author: lyl
 * @Date: 2021/2/8 15:09
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Resource
    private ArticleMapper articleMapper;

    /**
     * 查询当前文章的上一篇文章
     * @param articleId
     * @param columnId
     * @return
     */
    public WebArticleBean getOnArticle(String articleId, String columnId){
        return articleMapper.getOnArticle(articleId, columnId);
    }

    /**
     * 查询当前文章的下一篇文章
     * @param articleId
     * @param columnId
     * @return
     */
    public WebArticleBean getUnderArticle(String articleId, String columnId){
        return articleMapper.getUnderArticle(articleId, columnId);
    }

    /**
     * 查询网站文章信息
     * @param article
     * @return
     */
    public ResultObj queryBlogInfo(ArticleBean article){
        int count = articleMapper.queryBlogInfoCount(article);
        if(count > 0){
            List<WebArticleBean> articleList = articleMapper.queryBlogInfo(article);
            return ResultObj.ok(count, articleList);
        }
        return ResultObj.fail("没有查询到文章信息");
    }

    /**
     * 获取栏目名称
     * @param columnId
     * @return
     */
    public String getColumnName(String columnId){
        return articleMapper.getColumnName(columnId);
    }

    /**
     * 获取标签名称
     * @param labelId
     * @return
     */
    public String getLabelName(String labelId){
        return articleMapper.getLabelName(labelId);
    }
}
