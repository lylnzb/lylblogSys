package com.lylblog.project.webSite.message.controller;

import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.system.comment.bean.CommentBean;
import com.lylblog.project.webSite.message.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: lyl
 * @Date: 2020/11/14 20:45
 */
@Controller
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @RequestMapping("/messageFeedback")
    public String blogList(Model model){
        //查询总评论数量
        model.addAttribute("totalCommentCount", messageService.totalCommentCount());
        return "/message/message";
    }

    /**
     * 留言发布
     * @param commentBean
     * @return
     */
    @RequestMapping("/releaseMessage")
    @ResponseBody
    public ResultObj addMessage(@Validated @RequestBody CommentBean commentBean){
        try{
            return messageService.addMessage(commentBean);
        }catch(Exception e){
            e.printStackTrace();
        }
        return ResultObj.fail("系统异常");
    }

    /**
     * 留言反馈列表
     * @param commentBean
     * @return
     */
    @RequestMapping("/messageList")
    @ResponseBody
    public ResultObj messageList(@RequestBody CommentBean commentBean){
        try{
            return messageService.messageList(commentBean);
        }catch(Exception e){
            e.printStackTrace();
        }
        return ResultObj.fail("系统异常");
    }
}
