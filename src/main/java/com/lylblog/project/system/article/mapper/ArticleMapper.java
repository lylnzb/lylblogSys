package com.lylblog.project.system.article.mapper;

import com.lylblog.project.system.article.bean.ArticleBean;
import com.lylblog.project.system.article.bean.LabelBean;
import com.lylblog.project.system.article.bean.LabelSelectBean;
import com.lylblog.project.webSite.blog.bean.WebArticleBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文章管理
 */
@Mapper
public interface ArticleMapper {

    /**
     * 查询后台文章列表信息
     * @param articleBean
     * @return
     */
    List<ArticleBean> queryArticleInfo(ArticleBean articleBean);

    /**
     * 查询后台文章列表信息
     * @param articleBean
     * @return
     */
    int queryArticleInfoCount(ArticleBean articleBean);

    /**
     * 查询网站前端博客列表信息
     * @param articleBean
     * @return
     */
    List<WebArticleBean> queryBlogInfo(ArticleBean articleBean);

    /**
     * 查询网站前端博客列表信息总数
     * @param articleBean
     * @return
     */
    int queryBlogInfoCount(ArticleBean articleBean);

    /**
     * 新增文章信息
     * @param articleBean
     * @return
     */
    int addArticleInfo(ArticleBean articleBean);

    /**
     * 修改文章信息
     * @param articleBean
     * @return
     */
    int updateArticleInfo(ArticleBean articleBean);

    /**
     * 删除文章信息
     * @param deleteIds
     * @return
     */
    int deleteArticleInfo(@Param("deleteIds") List<String> deleteIds);

    /**
     * 查询标签信息
     * @param label
     * @return
     */
    List<LabelBean> queryLabelInfo(LabelBean label);

    /**
     * 多选框标签回显
     * @param labelIds
     * @return
     */
    List<LabelSelectBean> queryLabelSelect(@Param("labelIds") String labelIds);

    /**
     * 查询标签信息总数
     * @param label
     * @return
     */
    int queryLabelInfoCount(LabelBean label);

    /**
     * 新增标签
     * @param label
     * @return
     */
    int addLabelInfo(LabelBean label);

    /**
     * 修改标签
     * @param label
     * @return
     */
    int updateLabelInfo(LabelBean label);

    /**
     * 删除标签
     * @param deleteIds
     * @return
     */
    int deleteLabelInfo(@Param("deleteIds") List<String> deleteIds);

    /**
     * 查询当前文章的上一篇文章
     * @param articleId
     * @param columnId
     * @return
     */
    WebArticleBean getOnArticle(@Param("articleId") String articleId, @Param("columnId") String columnId);

    /**
     * 查询当前文章的下一篇文章
     * @param articleId
     * @param columnId
     * @return
     */
    WebArticleBean getUnderArticle(@Param("articleId") String articleId, @Param("columnId") String columnId);

    /**
     * 查询文章的评论数量
     * @param wznm
     * @return
     */
    int getCommentCountByWznm(@Param("wznm") String wznm);

    /**
     * 修改文章的评论数量
     * @param postNum
     * @param wznm
     * @return
     */
    int updateCommentCountByWznm(@Param("postNum") int postNum, @Param("wznm") String wznm);
}
