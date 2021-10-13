package com.lylblog.project.webSite.blog.service.impl;

import com.lylblog.common.util.DateUtil;
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
     * 查询文章详细信息
     * @param wznm
     * @return
     */
    public ArticleBean getArticleInfoByWznm(String wznm){
        ArticleBean article = articleMapper.getArticleInfoByWznm(wznm);
        article.setLabel(articleMapper.queryLabelSelect(article.getArticleLabel()));
        return article;
    }

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
        if(null != article.getBlogType() && "life".equals(article.getBlogType())) {
            article.setColumnId("112");
        }
        int count = articleMapper.queryBlogInfoCount(article);
        if(count > 0){
            List<WebArticleBean> articleList = articleMapper.queryBlogInfo(article);
            for(WebArticleBean webArticle : articleList) {
                try {
                    webArticle.setReleaseTime(DateUtil.format(webArticle.getReleaseTime()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
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

    /**
     * 文章浏览量加一
     * @param wznm
     * @return
     */
    public int updateHitsByWznm(String wznm) {
        return articleMapper.updateHitsByWznm(wznm);
    }
}
