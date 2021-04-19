package com.lylblog.project.webSite.link.service;

import com.lylblog.project.common.bean.ResultObj;

import java.util.Map;

/**
 * @Author: lyl
 * @Date: 2021/2/20 14:12
 */
public interface LinksService {

    /**
     * 查询友情链接信息
     * @return
     */
    Map<String, Object> queryLinksInfo();
}
