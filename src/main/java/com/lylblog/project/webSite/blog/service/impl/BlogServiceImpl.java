package com.lylblog.project.webSite.blog.service.impl;

import com.lylblog.project.webSite.blog.mapper.BlogMapper;
import com.lylblog.project.webSite.blog.service.BlogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: lyl
 * @Date: 2021/2/8 15:09
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Resource
    private BlogMapper blogMapper;


}
