package com.lylblog.project.system.article.service;

import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.system.article.bean.ArticleBean;
import com.lylblog.project.system.article.bean.LabelBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleService {

    /**
     * 查询文章列表信息
     * @param articleBean
     * @return
     */
    ResultObj queryArticleInfo(ArticleBean articleBean);

    /**
     * 新增或修改文章信息
     * @param articleBean
     * @return
     */
    ResultObj addOrUpdaArticleInfo(ArticleBean articleBean, String type);

    /**
     * 删除文章信息
     * @param deleteIds
     * @return
     */
    ResultObj deleteArticleInfo(@Param("deleteIds") List<String> deleteIds);

    /**
     * 查询标签信息
     * @param label
     * @return
     */
    ResultObj queryLabelInfo(LabelBean label);

    /**
     * 新增标签
     * @param label
     * @return
     */
    ResultObj addOrUpdaLabelInfo(LabelBean label, String type);

    /**
     * 删除标签
     * @param deleteIds
     * @return
     */
    ResultObj deleteLabelInfo(List<String> deleteIds);
}
