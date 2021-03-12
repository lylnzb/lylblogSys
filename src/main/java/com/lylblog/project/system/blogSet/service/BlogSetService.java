package com.lylblog.project.system.blogSet.service;

import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.system.blogSet.bean.BlogSetBean;

/**
 * @Author: lyl
 * @Date: 2021/2/8 11:23
 */
public interface BlogSetService {
    /**
     * 查看博客设置
     * @return
     */
    ResultObj viewBlogSetInfo();

    /**
     * 配置博客设置
     * @return
     */
    ResultObj configurationBlogSetInfo(BlogSetBean blogSet);
}
