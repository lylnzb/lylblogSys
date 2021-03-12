package com.lylblog.project.webSite.message.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: lyl
 * @Date: 2020/11/14 20:45
 */
@Controller
@RequestMapping("/message")
public class MessageController {

    @RequestMapping("/messageFeedback")
    public String blogList(){
        return "/message/message";
    }
}
