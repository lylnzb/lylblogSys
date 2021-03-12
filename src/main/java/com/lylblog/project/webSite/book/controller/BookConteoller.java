package com.lylblog.project.webSite.book.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: lyl
 * @Date: 2020/11/14 18:40
 */
@Controller
@RequestMapping("/book")
public class BookConteoller {

    @RequestMapping("/bookList")
    public String blogList(){
        return "/book/bookList";
    }
}
