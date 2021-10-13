package com.lylblog.project.webSite.index.controller;

import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.webSite.index.service.IndexService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/")
public class IndexController {

    @Resource
    private IndexService indexService;

    @RequestMapping("/index")
    public String index(){
        return "/index";
    }

    @RequestMapping("/about/aboutMe")
    public String aboutMe(){
        return "/about/aboutMe";
    }

    @RequestMapping("/blogTouGao")
    public String blogTouGao(){
        return "/about/blogContribute";
    }

    /**
     * 展示网站首页轮播图信息
     * @return
     */
    @RequestMapping("/showBannerInfo")
    @ResponseBody
    public ResultObj showBannerInfo(){
        try{
            return indexService.showBannerInfo();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultObj.fail();
    }

    /**
     * 展示网站首页卡片内容信息
     * @param articleType
     * @return
     */
    @RequestMapping("/showCardInfo")
    @ResponseBody
    public ResultObj showCardInfo(String articleType){
        try{
            return  indexService.showCardInfo(articleType);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultObj.fail();
    }

    /**
     * 展示网站首页最新文章列表信息
     * @return
     */
    @RequestMapping("/showArticleInfo")
    @ResponseBody
    public ResultObj showArticleInfo(){
        try{
            return indexService.showArticleInfo();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultObj.fail();
    }
}
