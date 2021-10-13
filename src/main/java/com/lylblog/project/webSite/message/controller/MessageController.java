package com.lylblog.project.webSite.message.controller;

import com.alibaba.fastjson.JSON;
import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.system.comment.bean.CommentBean;
import com.lylblog.project.webSite.message.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
     * @param file
     * @param paramData
     * @return
     */
    @RequestMapping("/releaseMessage")
    @ResponseBody
    public ResultObj addMessage(@RequestParam(value = "file", required = false) MultipartFile file,
                                @RequestParam(value = "paramData", required = false) String paramData
    ){
        try{
            CommentBean comment = JSON.parseObject(paramData, CommentBean.class);
            return messageService.addMessage(comment, file);
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
