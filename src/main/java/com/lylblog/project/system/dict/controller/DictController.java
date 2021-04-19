package com.lylblog.project.system.dict.controller;

import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.system.dict.bean.DictDataBean;
import com.lylblog.project.system.dict.bean.DictTypeBean;
import com.lylblog.project.system.dict.service.DictService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/admin/dict")
public class DictController {

    @Resource
    private DictService dictService;

    private static String BASEPATH = "admin/dict";

    @RequestMapping("/queryDictTypeInfo")
    @ResponseBody
    public ResultObj queryDictTypeInfo(@RequestBody DictTypeBean dictTypeBean){
        try{
            return dictService.queryDictTypeInfo(dictTypeBean);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/addOrEditDictTypeInfo")
    @ResponseBody
    public ResultObj addOrEditDictTypeInfo(@RequestBody DictTypeBean dictTypeBean,String type){
        try{
            return dictService.addOrEditDictTypeInfo(dictTypeBean, type);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/deleteDictTypeInfo")
    @ResponseBody
    public ResultObj deleteDictTypeInfo(@RequestBody DictTypeBean dictTypeBean){
        try{
            return dictService.deleteDictTypeInfo(dictTypeBean);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/addOrEditDictDataInfo")
    @ResponseBody
    public ResultObj addOrEditDictDataInfo(@RequestBody DictDataBean dictDataBean, String type){
        try{
            return dictService.addOrEditDictDataInfo(dictDataBean, type);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/queryDictDataInfo")
    @ResponseBody
    public ResultObj queryDictDataInfo(@RequestBody DictDataBean dictDataBean){
        try{
            return dictService.queryDictDataInfo(dictDataBean);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/deleteDictDataInfo")
    @ResponseBody
    public ResultObj deleteDictDataInfo(@RequestBody DictDataBean dictDataBean){
        try{
            return dictService.deleteDictDataInfo(dictDataBean);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/dictData")
    public String dictData(Model model){
        return BASEPATH + "/dictData";
    }

    @RequestMapping("/saveOrEditDictData")
    public String saveOrEditDictData(Model model){
        return BASEPATH + "/saveOrEditDictData";
    }

    @RequestMapping("/saveOrEditDictType")
    public String saveOrEditDictType(Model model){
        return BASEPATH + "/saveOrEditDictType";
    }
}
