package com.lylblog.project.system.webColumn.mapper;

import com.lylblog.project.system.webColumn.bean.WebColumnBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: lyl
 * @Date: 2020/11/8 14:55
 */
@Mapper
public interface WebColumnMapper {

    /**
     * 添加网站栏目信息
     * @param webColumnBean
     * @return
     */
    int addWebColumnInfo(WebColumnBean webColumnBean);

    /**
     * 编辑网站栏目信息
     * @param webColumnBean
     * @return
     */
    int updateWebColumnInfo(WebColumnBean webColumnBean);

    /**
     * 查询网站栏目信息
     * @return
     */
    List<WebColumnBean> queryWebColumnInfo();

    /**
     * 查询允许发布文章的专栏信息
     * @return
     */
    List<WebColumnBean> queryWebColumnByAllow();

    /**
     * 根据id查询网站栏目信息
     * @param columnId
     * @return
     */
    WebColumnBean queryWebColumnInfoById(@Param("columnId") String columnId);

    /**
     * 删除网站栏目信息
     * @param deleteIds
     * @return
     */
    int deleteWebColumnInfo(@Param("deleteIds") List<String> deleteIds);

    /**
     * 查询所有父栏目信息
     * @return
     */
    List<WebColumnBean> queryParentColumn();
}
