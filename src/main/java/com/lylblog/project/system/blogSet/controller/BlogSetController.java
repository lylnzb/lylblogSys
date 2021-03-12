package com.lylblog.project.system.blogSet.controller;

/**
 * @Author: lyl
 * @Date: 2021/2/18 10:44
 */

import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.system.blogSet.bean.BlogSetBean;
import com.lylblog.project.system.blogSet.service.BlogSetService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 博客设置控制层
 * @Author: lyl
 * @Date: 2021/1/3 10:32
 */
@Controller
@RequestMapping("/blogSet")
public class BlogSetController {

    @Resource
    private BlogSetService blogSetService;

    @RequestMapping("/blogSetData")
    public String blogSetData(){
        return "/admin/blogSet/blogData";
    }

    /**
     * 查看博客设置
     * @return
     */
    @RequestMapping("/viewBlogSetInfo")
    @ResponseBody
    public ResultObj viewBlogSetInfo(){
        try{
            return blogSetService.viewBlogSetInfo();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultObj.fail();
    }

    /**
     * 配置博客设置
     * @return
     */
    @RequestMapping("/configurationBlogSetInfo")
    @ResponseBody
    public ResultObj configurationBlogSetInfo(@RequestBody BlogSetBean blogSet){
        try{
            return blogSetService.configurationBlogSetInfo(blogSet);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultObj.fail();
    }
}

