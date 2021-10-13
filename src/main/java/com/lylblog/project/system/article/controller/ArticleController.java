package com.lylblog.project.system.article.controller;

import com.lylblog.framework.Aspectj.annotation.Log;
import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.system.article.bean.ArticleBean;
import com.lylblog.project.system.article.bean.LabelBean;
import com.lylblog.project.system.article.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/admin/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    private static String BASEPATH = "/admin/article";

    @RequestMapping("/articleList")
    public String articleList(Model model){
        return BASEPATH + "/articleList";
    }

    @RequestMapping("/releaseArticle")
    public String releaseArticle(Model model){
        return BASEPATH + "/releaseArticle";
    }

    @RequestMapping("/labelList")
    public String labelList(Model model){
        return BASEPATH + "/labelList";
    }

    @RequestMapping("/saveOrEditLabelData")
    public String saveOrEditLabelData(Model model){
        return BASEPATH + "/saveOrEditLabel";
    }

    @RequestMapping("/previewArticle/{wznm}")
    public String previewArtilce(Model model, @PathVariable String wznm){
        ArticleBean articleBean = new ArticleBean();
        articleBean.setWznm(wznm);
        ResultObj resultObj = articleService.queryArticleInfo(articleBean);
        model.addAttribute("type","preview");
        System.out.println(resultObj.getData().get(0).toString());
        model.addAttribute("article", resultObj.getData().get(0));
        return "/blog/details";
    }

    /**
     * 查询文章列表信息
     * @param articleBean
     * @return
     */
    @RequestMapping("/queryArticleInfo")
    @ResponseBody
    public ResultObj queryArticleInfo(@RequestBody ArticleBean articleBean){
        return articleService.queryArticleInfo(articleBean);
    }

    /**
     * 新增或修改文章信息
     * @param articleBean
     * @return
     */
    @Log(moduleName = "文章管理", functionName = "文章管理-新增（编辑）")
    @RequestMapping("/addOrUpdaArticleInfo")
    @ResponseBody
    public ResultObj addOrUpdaArticleInfo(@RequestBody ArticleBean articleBean, String type){
        return articleService.addOrUpdaArticleInfo(articleBean, type);
    }

    /**
     * 删除文章信息
     * @param deleteIds
     * @return
     */
    @Log(moduleName = "文章管理", functionName = "文章管理-删除")
    @RequestMapping("/deleteArticleInfo")
    @ResponseBody
    public ResultObj deleteArticleInfo(@RequestBody List<String> deleteIds){
        return articleService.deleteArticleInfo(deleteIds);
    }

    /**
     * 查询标签信息
     * @param label
     * @return
     */
    @RequestMapping("/queryLabelInfo")
    @ResponseBody
    public ResultObj queryLabelInfo(@RequestBody LabelBean label){
        return articleService.queryLabelInfo(label);
    }

    /**
     * 新增或修改标签
     * @param label
     * @return
     */
    @Log(moduleName = "标签管理", functionName = "标签管理-新增（编辑）")
    @RequestMapping("/addOrUpdaLabelInfo")
    @ResponseBody
    public ResultObj addOrUpdaLabelInfo(@RequestBody LabelBean label, String type){
        return articleService.addOrUpdaLabelInfo(label, type);
    }

    /**
     * 删除标签
     * @param deleteIds
     * @return
     */
    @Log(moduleName = "标签管理", functionName = "标签管理-删除")
    @RequestMapping("/deleteLabelInfo")
    @ResponseBody
    public ResultObj deleteLabelInfo(@RequestBody List<String> deleteIds){
        return articleService.deleteLabelInfo(deleteIds);
    }

    /**
     * 设置文章是否置顶
     * @param wznm
     * @param value
     * @return
     */
    @Log(moduleName = "文章管理", functionName = "标签管理-设置文章是否置顶")
    @RequestMapping("/updateArticleToOnTop")
    @ResponseBody
    public ResultObj updateArticleToOnTop(String wznm, String value){
        return articleService.updateArticleToOnTop(wznm, value);
    }

    /**
     * 设置文章是否推荐
     * @param wznm
     * @param value
     * @return
     */
    @Log(moduleName = "文章管理", functionName = "标签管理-设置文章是否推荐")
    @RequestMapping("/updateArticleToIselite")
    @ResponseBody
    public ResultObj updateArticleToIselite(String wznm, String value) {
        return articleService.updateArticleToIselite(wznm, value);
    }
}
