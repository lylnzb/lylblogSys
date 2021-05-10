package com.lylblog.project.webSite.comment.controller;

import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.system.comment.bean.CommentBean;
import com.lylblog.project.webSite.comment.bean.WebGreatBean;
import com.lylblog.project.webSite.comment.service.WebCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: lyl
 * @Date: 2021/4/19 16:53
 */
@Controller
@RequestMapping("/comment")
public class WebCommentController {

    @Autowired
    private WebCommentService webCommentService;
    /**
     * 评论发布
     * @param commentBean
     * @return
     */
    @RequestMapping("/releaseComment")
    @ResponseBody
    public ResultObj addComment(@Validated @RequestBody CommentBean commentBean){
        try{
            return webCommentService.addComment(commentBean);
        }catch(Exception e){
            e.printStackTrace();
        }
        return ResultObj.fail("系统异常");
    }

    /**
     * 网站文章评论列表
     * @param commentBean
     * @return
     */
    @RequestMapping("/commentList")
    @ResponseBody
    public ResultObj commentList(@RequestBody CommentBean commentBean){
        try{
            return webCommentService.commentList(commentBean);
        }catch(Exception e){
            e.printStackTrace();
        }
        return ResultObj.fail("系统异常");
    }

    /**
     * 文章点赞或取消赞
     * @param webGreatBean
     * @param isGiveLike
     * @return
     */
    @RequestMapping("/addGreatInfo")
    @ResponseBody
    public ResultObj addGreatInfo(@RequestBody WebGreatBean webGreatBean, String isGiveLike){
        try{
            return webCommentService.addGreatInfo(webGreatBean, isGiveLike);
        }catch(Exception e){
            e.printStackTrace();
        }
        return ResultObj.fail("系统异常");
    }
}
