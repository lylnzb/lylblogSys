package com.lylblog.project.webSite.blog.controller;

import com.lylblog.common.util.DateUtil;
import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.system.article.bean.ArticleBean;
import com.lylblog.project.system.article.service.ArticleService;
import com.lylblog.project.webSite.blog.service.BlogService;
import com.lylblog.project.webSite.comment.service.WebCommentService;
import com.lylblog.project.webSite.myCollection.service.CollectionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: lyl
 * @Date: 2020/11/14 15:54
 */
@Controller
@RequestMapping("/blog")
public class BlogController {

    @Resource
    private ArticleService articleService;

    @Resource
    private BlogService blogService;

    @Resource
    private WebCommentService webCommentService;

    @Resource
    public CollectionService collectionService;

    @RequestMapping("/blogList")
    public String blogList(Model model, String columnId, String labelId){
        //获取栏目名称
        String columnName = blogService.getColumnName(columnId);
        model.addAttribute("columnId", columnId);
        model.addAttribute("columnName", ((columnId != null && !"".equals(columnId))?columnName : ""));

        //获取标签名称
        String labelName = blogService.getLabelName(labelId);
        model.addAttribute("labelId", labelId);
        model.addAttribute("labelName", ((labelId != null && !"".equals(labelId))?labelName : ""));
        return "/blog/blogList";
    }

    @RequestMapping("/detail/{wznm}")
    public String previewArtilce(Model model, @PathVariable String wznm) throws Exception{
        ArticleBean articleBean = new ArticleBean();
        articleBean.setWznm(wznm);
        ResultObj resultObj = articleService.queryArticleInfo(articleBean);
        model.addAttribute("type","preview");
        model.addAttribute("article", resultObj.getData().get(0));

        ArticleBean article = (ArticleBean)resultObj.getData().get(0);
        article.setJudgeLongTime(DateUtil.format(article.getCreateTime()));

        //当前文章的上一篇文章
        model.addAttribute("upblogcontent", blogService.getOnArticle(article.getArticleId(),article.getColumnId()));
        //当前文章的下一篇文章
        model.addAttribute("downblogcontent", blogService.getUnderArticle(article.getArticleId(),article.getColumnId()));
        //查询总评论数量
        model.addAttribute("totalCommentCount", webCommentService.totalCommentCountByWznm(article.getWznm()));
        //主评论总数
        model.addAttribute("totalMainCommentCount", webCommentService.totalCommentCountByWznm(article.getWznm()));
        //文章收藏总数
        model.addAttribute("totalCollectionCount", collectionService.getCollectNumBywznm(article.getWznm()));
        //判断该用户是否已收藏文章(0.未收藏  1.已收藏)
        model.addAttribute("isCollection", collectionService.isCollectionByYhnm(article.getWznm()));

        return "/blog/details";
    }

    /**
     * 查询网站文章信息
     * @param article
     * @return
     */
    @RequestMapping("/queryBlogInfo")
    @ResponseBody
    public ResultObj queryBlogInfo(@RequestBody ArticleBean article){
        try{
            return blogService.queryBlogInfo(article);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultObj.fail("系统异常");
    }
}
