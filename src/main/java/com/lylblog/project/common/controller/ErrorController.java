package com.lylblog.project.common.controller;

import com.lylblog.project.common.bean.ResultObj;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: lyl
 * @Date: 2021/10/30 17:58
 */
@Controller
@RequestMapping("/error")
public class ErrorController {

    @GetMapping("/jump")
    public String jump(Model model, HttpServletRequest request) {
        String type = (String) request.getAttribute("type");
        ResultObj result = (ResultObj)request.getAttribute("resultObj");
        return "/error/jump";
    }

}
