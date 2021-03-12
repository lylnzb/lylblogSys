package com.lylblog.project.common.controller;

import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.common.service.CommonService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
}