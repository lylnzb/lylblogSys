package com.lylblog.project.system.comment.controller;

import com.lylblog.project.system.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("/addOrUpdaComment")
    public String addOrUpdaComment(Model model){
        return BASEPATH + "/addOrUpdaComment";
    }
}
