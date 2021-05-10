package com.lylblog.project.system.sidebar.service.impl;

import com.lylblog.project.system.sidebar.mapper.SidebarMapper;
import com.lylblog.project.system.sidebar.service.SidebarService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: lyl
 * @Date: 2021/5/5 10:43
 */
@Service
public class SidebarServiceImpl implements SidebarService {

    @Resource
    private SidebarMapper sidebarMapper;

}
