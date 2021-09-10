package com.lylblog.project.common.mapper;

import com.lylblog.project.common.bean.*;
import com.lylblog.project.system.blogSet.bean.BlogSetBean;
import com.lylblog.project.system.dict.bean.DictDataBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommonMapper {

    /**
     * 根据编码类别查询字典
     * @param dictType
     * @return
     */
    List<DictDataBean> queryCodeValue(@Param("dictType") String dictType);

    /**
     * 查询音乐列表
     * @return
     */
    List<MusicBean> queryMusicList(@Param("gedan") String gedan);

    /**
     * 导航栏初始化
     * @return
     */
    List<MenuBean> queryMeunInfo(@Param("isDefault") String isDefault, @Param("columnId") String columnId);

    /**
     * 根据栏目编号获取标签信息
     * @param columnId
     * @return
     */
    List<LabelBean> getLabelList(@Param("columnId") String columnId);

    /**
     * 获取博客配置信息
     * @return
     */
    BlogSetBean getBlogConfiguration();

    /**
     * 查询行政区划名称
     * @param code
     * @return
     */
    String queryAreas(@Param("code") String code);

    /**
     * 获取所有省份
     * @return
     */
    List<AreaBean> getProvince();

    /**
     * 通过省份行政区划编码获取城市
     * @param code
     * @return
     */
    List<AreaBean> getCityByProvinceCode(@Param("code") String code);

    /**
     * 通过城市行政区划编码获取地区
     * @param code
     * @return
     */
    List<AreaBean> getAreaByCityCode(@Param("code") String code);

    /**
     * 新增个人动态数据
     * @param dynamic
     * @return
     */
    int insertDynamicInfo(DynamicBean dynamic);
}
