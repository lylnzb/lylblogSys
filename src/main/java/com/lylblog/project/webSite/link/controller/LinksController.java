package com.lylblog.project.webSite.link.controller;

import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.system.link.bean.LinkBean;
import com.lylblog.project.system.link.service.LinkService;
import com.lylblog.project.webSite.link.service.LinksService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author: lyl
 * @Date: 2021/2/20 14:15
 */
@Controller
@RequestMapping("/links")
public class LinksController {

    @Resource
    private LinksService linksService;

    @Resource
    private LinkService linkService;

    @RequestMapping("/friendLink")
    public String index(){
        return "/links/friendLink";
    }

    @RequestMapping(value="/queryLinksInfo")
    @ResponseBody
    public Map<String, Object> queryLinksInfo(){
        try {
            return linksService.queryLinksInfo();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 网站友情链接申请
     * @return
     */
    @RequestMapping(value="/applyLinks")
    @ResponseBody
    public ResultObj applyLinks(@RequestBody LinkBean linkBean){
        try {
            linkBean.setType("2");//网站类型
            return linkService.addOrUpdaLinkInfo(linkBean,"add");
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
