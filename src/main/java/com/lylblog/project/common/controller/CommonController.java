package com.lylblog.project.common.controller;

import com.lylblog.project.common.bean.AreaBean;
import com.lylblog.project.common.bean.LabelBean;
import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.common.service.CommonService;
import io.swagger.models.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/common")
public class CommonController {

    @Resource
    private CommonService commonService;

    /**
     * 根据编码类别查询字典
     * @param dictType
     * @return
     */
    @RequestMapping(value="/queryCodeValue",method= RequestMethod.POST)
    @ResponseBody
    public ResultObj queryCodeValue(String dictType){
        try {
            return commonService.queryCodeValue(dictType);
        } catch (Exception e){
            e.printStackTrace();
        }
        return ResultObj.fail();
    }

    /**
     * 查询音乐列表
     * @return
     */
    @RequestMapping(value="/queryMusicList")
    @ResponseBody
    public Object[][] queryMusicList(){
        try {
            return commonService.queryMusicList();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 导航栏初始化
     * @return
     */
    @RequestMapping(value="/queryMeunInfo")
    @ResponseBody
    public ResultObj queryMeunInfo(){
        try {
            return commonService.queryMeunInfo();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据栏目编号获取标签信息
     * @param columnId
     * @return
     */
    @RequestMapping(value="/getLabelList")
    @ResponseBody
    public ResultObj getLabelList(String columnId){
        try {
            return commonService.getLabelList(columnId);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value="/headerRefresh",method=RequestMethod.GET)
    public String headerRefresh(Model model) {
        return "include/header :: myHeader";
    }

    /**
     * 获取所有省份
     * @return
     */
    @RequestMapping(value="/getProvince")
    @ResponseBody
    public ResultObj getProvince(){
        return commonService.getProvince();
    }

    /**
     * 通过省份行政区划编码获取城市
     * @param code
     * @return
     */
    @RequestMapping(value="/getCityByProvinceCode")
    @ResponseBody
    public ResultObj getCityByProvinceCode(String code){
        return commonService.getCityByProvinceCode(code);
    }

    /**
     * 通过城市行政区划编码获取地区
     * @param code
     * @return
     */
    @RequestMapping(value="/getAreaByCityCode")
    @ResponseBody
    public ResultObj getAreaByCityCode(String code){
        return commonService.getAreaByCityCode(code);
    }
}
