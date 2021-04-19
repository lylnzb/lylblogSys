package com.lylblog.project.system.webColumn.controller;

import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.system.webColumn.bean.WebColumnBean;
import com.lylblog.project.system.webColumn.service.WebColumnService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: lyl
 * @Date: 2020/11/8 15:00
 */
@Controller
@RequestMapping("/admin/webColumn")
public class WebColumnController {

    @Resource
    private WebColumnService webColumnService;

    /**
     * 添加或编辑网站栏目信息
     * @param webColumnBean
     * @return
     */
    @RequiresPermissions("system:WebColumn:add")
    @RequestMapping("/addOrUpdateWebColumnInfo")
    @ResponseBody
    public ResultObj addOrUpdateWebColumnInfo(@RequestBody WebColumnBean webColumnBean, String type){
        try {
            return webColumnService.addOrUpdateWebColumnInfo(webColumnBean, type);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultObj.fail();
    }

    /**
     * 查询网站栏目信息
     * @return
     */
    @RequiresPermissions("system:WebColumn:list")
    @RequestMapping("/queryWebColumnInfo")
    @ResponseBody
    public ResultObj queryWebColumnInfo(){
        try {
            return webColumnService.queryWebColumnInfo();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultObj.fail();
    }

    /**
     * 查询允许发布文章的专栏信息
     * @return
     */
    @RequestMapping("/queryWebColumnByAllow")
    @ResponseBody
    public ResultObj queryWebColumnByAllow(){
        try {
            return webColumnService.queryWebColumnByAllow();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultObj.fail();
    }

    /**
     * 根据id查询网站栏目信息
     * @param columnId
     * @return
     */
    @RequestMapping("/queryWebColumnInfoById")
    @ResponseBody
    public ResultObj queryWebColumnInfoById(String columnId){
        try {
            return webColumnService.queryWebColumnInfoById(columnId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultObj.fail();
    }

    /**
     * 删除网站栏目信息
     * @param deleteIds
     * @return
     */
    @RequiresPermissions("system:WebColumn:delete")
    @RequestMapping("/deleteWebColumnInfo")
    @ResponseBody
    public ResultObj deleteWebColumnInfo(@RequestBody List<String> deleteIds){
        try {
            return webColumnService.deleteWebColumnInfo(deleteIds);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultObj.fail();
    }

    /**
     * 查询所有父栏目信息
     * @return
     */
    @RequestMapping("/queryParentColumn")
    @ResponseBody
    public ResultObj queryParentColumn(){
        try {
            return webColumnService.queryParentColumn();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultObj.fail();
    }
}
