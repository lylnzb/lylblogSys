package com.lylblog.project.system.link.controller;

import com.lylblog.common.util.shiro.ShiroUtils;
import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.login.bean.UserLoginBean;
import com.lylblog.project.system.link.bean.LinkBean;
import com.lylblog.project.system.link.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author: lyl
 * @Date: 2021/3/17 16:46
 */
@Controller
@RequestMapping("/admin/link")
public class LinkController {

    private static String BASEPATH = "/admin/links";

    @Autowired
    private LinkService linkService;

    @RequestMapping("/linkData")
    public String linkData(){
        return BASEPATH + "/linkData";
    }

    @RequestMapping("/addOrUpdaLink")
    public String addOrUpdaLink(){
        return BASEPATH + "/addOrUpdaLink";
    }

    @RequestMapping("/linkShData")
    public String linkShData(){
        return BASEPATH + "/linkShData";
    }

    @RequestMapping("/auditLink")
    public String addOrUpdaLinkSh(){
        return BASEPATH + "/auditLink";
    }

    /**
     * 查询所有友情链接信息
     * @param linkBean
     * @return
     */
    @RequestMapping("queryLinkInfo")
    @ResponseBody
    public ResultObj queryLinkInfo(@RequestBody LinkBean linkBean){
        try{
            return linkService.queryLinkInfo(linkBean);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultObj.fail("查询不到数据");
    }

    /**
     * 查询友情链接审核信息
     * @param linkBean
     * @return
     */
    @RequestMapping("queryLinkShInfo")
    @ResponseBody
    public ResultObj queryLinkShInfo(@RequestBody LinkBean linkBean){
        try{
            return linkService.queryLinkShInfo(linkBean);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultObj.fail("暂无审核数据");
    }

    /**
     * 新增或修改友情链接信息
     * @param linkBean
     * @return
     */
    @RequestMapping("addOrUpdaLinkInfo")
    @ResponseBody
    public ResultObj addOrUpdaLinkInfo(@Validated @RequestBody LinkBean linkBean, String type){
        try{
            return linkService.addOrUpdaLinkInfo(linkBean, type);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultObj.fail("系统异常");
    }


    /**
     * 删除友情链接信息
     * @param deleteIds
     * @return
     */
    @RequestMapping("deleteLikeInfo")
    @ResponseBody
    public ResultObj deleteLikeInfo(@RequestBody List<String> deleteIds){
        try{
            return linkService.deleteLikeInfo(deleteIds);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultObj.fail("系统异常");
    }

    /**
     * 友情链接审核
     * @param linkBean
     * @return
     */
    @RequestMapping("auditLinkData")
    @ResponseBody
    public ResultObj auditLink(@RequestBody LinkBean linkBean){
        try{
            return linkService.auditLink(linkBean);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultObj.fail("系统异常");
    }
}
