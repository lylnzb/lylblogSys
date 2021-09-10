package com.lylblog.project.common.service;

import com.lylblog.project.common.bean.AreaBean;
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

    /**
     * 获取所有省份
     * @return
     */
    ResultObj getProvince();

    /**
     * 通过省份行政区划编码获取城市
     * @param code
     * @return
     */
    ResultObj getCityByProvinceCode(String code);

    /**
     * 通过城市行政区划编码获取地区
     * @param code
     * @return
     */
    ResultObj getAreaByCityCode(String code);

    /**
     * 生成个人动态信息数据
     * @param obj
     * @param type
     */
    void aspectDynamicInfo(Object obj, int type);
}
