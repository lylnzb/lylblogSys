package com.lylblog.project.webSite.message.service.impl;

import com.lylblog.project.webSite.message.mapper.MessageMapper;
import com.lylblog.project.webSite.message.service.MessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: lyl
 * @Date: 2021/2/8 15:18
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    private MessageMapper messageMapper;


}
