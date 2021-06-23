package com.lylblog.project.system.banner.controller;

import com.lylblog.framework.Aspectj.annotation.Log;
import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.system.banner.bean.BannerBean;
import com.lylblog.project.system.banner.service.BannerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: lyl
 * @Date: 2021/1/20 19:33
 */
@Controller
@RequestMapping("/admin/banner")
public class BannerController {

    @Resource
    private BannerService bannerService;

    private static String BASEPATH = "/admin/banner";

    @RequestMapping("/bannerData")
    public String articleList(Model model){
        return BASEPATH + "/bannerList";
    }

    @RequestMapping("/addOrUpdaBanner")
    public String addOrUpdaBanner(Model model){
        return BASEPATH + "/addOrUpdaBanner";
    }

    /**
     * 查询所有轮播图信息
     * @param bannerBean
     * @return
     */
    @RequestMapping("/queryBannerInfo")
    @ResponseBody
    public ResultObj queryBannerInfo(@RequestBody BannerBean bannerBean){
        return bannerService.queryBannerInfo(bannerBean);
    }

    /**
     * 新增轮播图信息
     * @param bannerBean
     * @return
     */
    @Log(moduleName = "轮播图管理", functionName = "轮播图管理-新增（编辑）")
    @RequestMapping("/addOrUpdaBannerInfo")
    @ResponseBody
    public ResultObj addOrUpdaBannerInfo(@RequestBody BannerBean bannerBean, String type){
        return bannerService.addOrUpdaBannerInfo(bannerBean, type);
    }

    /**
     * 删除轮播图信息
     * @param deleteIds
     * @return
     */
    @Log(moduleName = "轮播图管理", functionName = "轮播图管理-删除")
    @RequestMapping("/deleteBannerInfo")
    @ResponseBody
    public ResultObj deleteBannerInfo(@RequestBody List<String> deleteIds){
        return bannerService.deleteBannerInfo(deleteIds);
    }
}
