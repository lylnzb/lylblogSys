package com.lylblog.project.common.mapper;

import com.lylblog.project.common.bean.*;
import com.lylblog.project.system.blogSet.bean.BlogSetBean;
import com.lylblog.project.system.dict.bean.DictDataBean;
import com.lylblog.project.webSite.index.bean.ArticleListBean;
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
     * 根据编码类别和字典值查询字典
     * @param dictType
     * @return
     */
    DictDataBean queryCodeValueByCode(@Param("dictType") String dictType, @Param("values") String values);

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
     * 获取站长推荐文章信息
     * @return
     */
    List<ArticleListBean> getBlogRecommended(@Param("page") String page);

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

    /**
     * 查询网站统计数据
     * @return
     */
    WebSiteTjBean queryWebSiteTjInfo();
}
