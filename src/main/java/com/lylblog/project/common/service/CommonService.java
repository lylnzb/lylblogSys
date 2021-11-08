package com.lylblog.project.common.service;

import com.lylblog.project.common.bean.AreaBean;
import com.lylblog.project.common.bean.LabelBean;
import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.common.bean.WebSiteTjBean;
import com.lylblog.project.system.blogSet.bean.BlogSetBean;
import com.lylblog.project.system.dict.bean.DictDataBean;
import com.lylblog.project.system.log.bean.BrowseLogBean;
import com.lylblog.project.webSite.index.bean.ArticleListBean;
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
     * 根据编码类别和字典值查询字典
     * @param dictType
     * @return
     */
    ResultObj queryCodeValueByCode(String dictType, String values);

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
     * 获取站长推荐文章信息
     * @return
     */
    ResultObj getBlogRecommended();

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

    /**
     * 新增博客浏览日志记录
     * @param browseLog
     * @return
     */
    ResultObj insertBlogBrowseLogInfo(BrowseLogBean browseLog);

    /**
     * 获取RSA算法公钥
     * @return
     */
    String getPublicKey() throws Exception;
}
