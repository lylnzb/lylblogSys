package com.lylblog.project.webSite.book.service.impl;

import com.lylblog.project.webSite.book.mapper.BookMapper;
import com.lylblog.project.webSite.book.service.BookService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: lyl
 * @Date: 2021/2/8 15:14
 */
@Service
public class BookServiceImpl implements BookService {

    @Resource
    private BookMapper bookMapper;


}
