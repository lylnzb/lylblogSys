package com.lylblog.project.webSite.link.service.impl;

import com.lylblog.project.webSite.link.mapper.LinkMapper;
import com.lylblog.project.webSite.link.service.LinkService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: lyl
 * @Date: 2021/2/20 14:12
 */
@Service
public class LinkServiceImpl implements LinkService {

    @Resource
    private LinkMapper linkMapper;


}
