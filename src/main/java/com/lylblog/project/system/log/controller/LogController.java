package com.lylblog.project.system.log.controller;

import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.system.log.bean.LogDetailBean;
import com.lylblog.project.system.log.bean.LoginLogBean;
import com.lylblog.project.system.log.bean.OperLogBean;
import com.lylblog.project.system.log.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author: lyl
 * @Date: 2021/6/13 11:06
 */
@Controller
@RequestMapping("/admin")
public class LogController {

    private static String BASEPATH = "/admin/log";

    @Autowired
    private LogService logService;

    @RequestMapping("/loginLog/loginLogData")
    public String loginLogData(){
        return BASEPATH + "/loginLogData";
    }

    @RequestMapping("/operLog/operLogData")
    public String operLogData(){
        return BASEPATH + "/operLogData";
    }

    @RequestMapping("/operLog/viewOperLog")
    public String viewOperLog(){
        return BASEPATH + "/viewOperLog";
    }

    /**
     * 查询用户登录日志记录
     * @param loginLog
     * @return
     */
    @RequestMapping("/loginLog/queryLoginLogInfo")
    @ResponseBody
    public ResultObj queryLoginLogInfo(@RequestBody LoginLogBean loginLog){
        try {
            return logService.queryLoginLogInfo(loginLog);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return ResultObj.fail("查询不到登录日志记录");
    }

    /**
     * 删除用户登录日志记录
     * @param deleteIds
     * @return
     */
    @RequestMapping("/loginLog/deleteLoginLogInfo")
    @ResponseBody
    public ResultObj deleteLoginLogInfo(@RequestBody List<String> deleteIds) {
        return logService.deleteLoginLogInfo(deleteIds);
    }

    /**
     * 查询用户操作日志记录
     * @param operLog
     * @return
     */
    @RequestMapping("/operLog/queryOperLogInfo")
    @ResponseBody
    public ResultObj queryOperLogInfo(@RequestBody OperLogBean operLog){
        try {
            return logService.queryOperLogInfo(operLog);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return ResultObj.fail("查询不到操作日志记录");
    }

    /**
     * 删除系统操作日志记录
     * @param deleteIds
     * @return
     */
    @RequestMapping("/operLog/deleteOperLogInfo")
    @ResponseBody
    public ResultObj deleteOperLogInfo(@RequestBody List<String> deleteIds) {
        return logService.deleteOperLogInfo(deleteIds);
    }

    /**
     * 查询操作日志详情信息
     * @param logId
     * @return
     */
    @RequestMapping("/operLog/queryOperLogDetailInfo")
    @ResponseBody
    public ResultObj queryOperLogDetailInfo(String logId) {
        return logService.queryOperLogDetailInfo(logId);
    }
}
