package com.lylblog.project.webSite.blog.controller;

import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.system.article.bean.ArticleBean;
import com.lylblog.project.system.article.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @Author: lyl
 * @Date: 2020/11/14 15:54
 */
@Controller
@RequestMapping("/blog")
public class BlogController {

    @Resource
    private ArticleService articleService;

    @RequestMapping("/blogList")
    public String blogList(){
        return "/blog/blogList";
    }

    @RequestMapping("/detail/{wznm}")
    public String previewArtilce(Model model, @PathVariable String wznm){
        ArticleBean articleBean = new ArticleBean();
        articleBean.setWznm(wznm);
        ResultObj resultObj = articleService.queryArticleInfo(articleBean);
        model.addAttribute("type","preview");
        System.out.println(resultObj.getData().get(0).toString());
        model.addAttribute("article", resultObj.getData().get(0));
        return "/blog/details";
    }
}
