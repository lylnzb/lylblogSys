package com.lylblog.project.system.webColumn.service;

import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.system.webColumn.bean.WebColumnBean;

import java.util.List;

/**
 * @Author: lyl
 * @Date: 2020/11/8 14:58
 */
public interface WebColumnService {

    /**
     * 添加或编辑网站栏目信息
     * @param webColumnBean
     * @return
     */
    ResultObj addOrUpdateWebColumnInfo(WebColumnBean webColumnBean, String type);

    /**
     * 查询网站栏目信息
     * @return
     */
    ResultObj queryWebColumnInfo();

    /**
     * 查询允许发布文章的专栏信息
     * @return
     */
    ResultObj queryWebColumnByAllow();

    /**
     * 根据id查询网站栏目信息
     * @param columnId
     * @return
     */
    ResultObj queryWebColumnInfoById(String columnId);

    /**
     * 删除网站栏目信息
     * @param deleteIds
     * @return
     */
    ResultObj deleteWebColumnInfo(List<String> deleteIds);

    /**
     * 查询所有父栏目信息
     * @return
     */
    ResultObj queryParentColumn();
}
