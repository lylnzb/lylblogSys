package com.lylblog.project.webSite.index.service.impl;

import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.system.banner.bean.BannerBean;
import com.lylblog.project.system.blogSet.bean.BlogSetBean;
import com.lylblog.project.system.blogSet.mapper.BlogSetMapper;
import com.lylblog.project.webSite.index.bean.ArticleListBean;
import com.lylblog.project.webSite.index.bean.CardBean;
import com.lylblog.project.webSite.index.bean.TabBean;
import com.lylblog.project.webSite.index.mapper.IndexMapper;
import com.lylblog.project.webSite.index.service.IndexService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: lyl
 * @Date: 2020/11/14 14:59
 */
@Service
public class IndexServiceImpl implements IndexService {

    @Resource
    private IndexMapper indexMapper;

    @Resource
    private BlogSetMapper blogSetMapper;

    /**
     * 展示网站首页轮播图信息
     * @return
     */
    public ResultObj showBannerInfo(){
        String page = "5";
        List<BlogSetBean> blogSetList = blogSetMapper.viewBlogSetInfo();
        if(!blogSetList.isEmpty()){
            page = blogSetList.get(0).getBlogsetScrollRecommendedShowNum().toString();
        }

        List<BannerBean> bannerList = indexMapper.showBannerInfo(page);
        return ResultObj.ok(bannerList);
    }

    /**
     * 展示网站首页卡片内容信息
     * @param articleType
     * @return
     */
    public ResultObj showCardInfo(String articleType){
        String page = "6";
        List<BlogSetBean> blogSetList = blogSetMapper.viewBlogSetInfo();
        if(!blogSetList.isEmpty()){
            page = blogSetList.get(0).getBlogsetLatestShowNum().toString();
        }

        //获取选项卡信息
        List<TabBean> tabList = indexMapper.getTabInfo();
        if(tabList.isEmpty()){//如果找不打，则返回错误信息
            return ResultObj.fail();
        }

        for(TabBean tab : tabList){
            List<CardBean> cardList = indexMapper.showCardInfo(tab.getTabType(), page);
            tab.setCardList(cardList);
        }
        return ResultObj.ok(tabList);
    }

    /**
     * 展示网站首页最新文章列表信息
     * @return
     */
    public ResultObj showArticleInfo(){
        String page = "5";
        List<BlogSetBean> blogSetList = blogSetMapper.viewBlogSetInfo();
        if(!blogSetList.isEmpty()){
            page = blogSetList.get(0).getBlogsetLatestShowNum().toString();
        }
        List<ArticleListBean> articleList = indexMapper.showArticleInfo(page);
        return ResultObj.ok(articleList);
    }
}
