package com.lylblog.project.webSite.link.controller;

import com.lylblog.project.webSite.link.service.LinkService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @Author: lyl
 * @Date: 2021/2/20 14:15
 */
@Controller
@RequestMapping("/link")
public class LinkController {

    @Resource
    private LinkService linkService;

    @RequestMapping("/friendLink")
    public String index(){
        return "/link/friendLink";
    }
}
