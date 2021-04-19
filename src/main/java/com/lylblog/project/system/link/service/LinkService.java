package com.lylblog.project.system.link.service;

import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.system.link.bean.LinkBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: lyl
 * @Date: 2021/3/17 16:44
 */
public interface LinkService {

    /**
     * 查询所有友情链接信息
     * @param linkBean
     * @return
     */
    ResultObj queryLinkInfo(LinkBean linkBean);

    /**
     * 查询友情链接审核信息
     * @param linkBean
     * @return
     */
    ResultObj queryLinkShInfo(LinkBean linkBean);

    /**
     * 新增或修改友情链接信息
     * @param linkBean
     * @return
     */
    ResultObj addOrUpdaLinkInfo(LinkBean linkBean, String type);


    /**
     * 删除友情链接信息
     * @param deleteIds
     * @return
     */
    ResultObj deleteLikeInfo(List<String> deleteIds);

    /**
     * 友情链接审核
     * @param linkBean
     * @return
     */
    ResultObj auditLink(LinkBean linkBean);
}
