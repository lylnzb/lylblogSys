package com.lylblog.project.common.service;

import com.lylblog.project.common.bean.LabelBean;
import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.system.blogSet.bean.BlogSetBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
     * 根据栏目编号获取标签信息
     * @param columnId
     * @return
     */
    ResultObj getLabelList(String columnId);

    /**
     * 获取博客配置信息
     * @return
     */
    BlogSetBean getBlogConfiguration();
}
