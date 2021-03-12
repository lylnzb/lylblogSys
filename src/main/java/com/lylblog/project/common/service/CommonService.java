package com.lylblog.project.common.service;

import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.system.blogSet.bean.BlogSetBean;

public interface CommonService {

    /**
     * 根据编码类别查询字典
     * @param dictType
     * @return
     */
    ResultObj queryCodeValue(String dictType);

    /**
     * 查询音乐列表
     * @return
     */
    Object[][] queryMusicList();

    /**
     * 导航栏初始化
     * @return
     */
    ResultObj queryMeunInfo();

    /**
     * 获取博客配置信息
     * @return
     */
    BlogSetBean getBlogConfiguration();
}
