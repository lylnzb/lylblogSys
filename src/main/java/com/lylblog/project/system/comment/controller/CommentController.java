package com.lylblog.project.system.comment.controller;

import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.system.comment.bean.CommentBean;
import com.lylblog.project.system.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author: lyl
 * @Date: 2021/4/19 16:53
 */
@Controller("commentController")
@RequestMapping("/admin/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    private static String BASEPATH = "/admin/comment";

    @RequestMapping("/commentData")
    public String commentData(Model model){
        return BASEPATH + "/commentData";
    }

    @RequestMapping("/messageData")
    public String messageData(Model model){
        return BASEPATH + "/messageData";
    }

    @RequestMapping("/viewComment")
    public String viewComment(Model model){
        return BASEPATH + "/viewComment";
    }

    /**
     * 后台系统评论管理信息查询
     * @param commentBean
     * @return
     */
    @RequestMapping("queryCommentInfo")
    @ResponseBody
    public ResultObj queryCommentInfo(@RequestBody CommentBean commentBean){
        try {
            return commentService.queryCommentInfo(commentBean);
        } catch (Exception e){
            e.printStackTrace();
        }
        return ResultObj.fail("未查询到数据！");
    }

    @RequestMapping("/deleteCommentInfo")
    @ResponseBody
    public ResultObj deleteCommentInfo(@RequestBody List<String> deleteIds){
        try{
            return commentService.deleteCommentInfo(deleteIds);
        }catch(Exception e){
            e.printStackTrace();
        }
        return ResultObj.fail("系统异常！");
    }
}
