package com.lylblog.project.webSite.index.mapper;

import com.lylblog.project.system.banner.bean.BannerBean;
import com.lylblog.project.webSite.index.bean.ArticleListBean;
import com.lylblog.project.webSite.index.bean.CardBean;
import com.lylblog.project.webSite.index.bean.TabBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @Author: lyl
 * @Date: 2020/11/14 14:55
 */
@Mapper
public interface IndexMapper {

    /**
     * 展示网站首页轮播图信息
     * @return
     */
    List<BannerBean> showBannerInfo(@Param("page") String page);

    /**
     * 获取选项卡信息
     * @return
     */
    List<TabBean> getTabInfo();

    /**
     * 展示网站首页卡片内容信息
     * @param articleType
     * @return
     */
    List<CardBean> showCardInfo(@Param("articleType") String articleType, @Param("page") String page);

    /**
     * 展示网站首页最新文章列表信息
     * @return
     */
    List<ArticleListBean> showArticleInfo(@Param("page") String page);
}
