package com.lylblog.project.system.sidebar.controller;

import com.lylblog.project.system.sidebar.service.SidebarService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @Author: lyl
 * @Date: 2021/5/5 10:45
 */
@Controller
@RequestMapping("/admin/sidebar")
public class SidebarController {

    private static String BASEPATH = "/admin/sidebar";

    @Resource
    private SidebarService sidebarService;

    @RequestMapping("/sidebarData")
    public String musicData(){
        return BASEPATH + "/sidebarData";
    }

    @RequestMapping("/addOrUpdaSidebar")
    public String addOrUpdaMusic(){
        return BASEPATH + "/addOrUpdaSidebar";
    }
}
