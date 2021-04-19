package com.lylblog.project.system.link.mapper;

import com.lylblog.project.system.link.bean.LinkBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @Author: lyl
 * @Date: 2021/3/17 16:45
 */
@Mapper
public interface LinkMapper {

    /**
     * 查询所有友情链接信息
     * @param linkBean
     * @return
     */
    List<LinkBean> queryLinkInfo(LinkBean linkBean);

    /**
     * 查询所有友情链接信息总数
     * @param linkBean
     * @return
     */
    int queryLinkInfoCount(LinkBean linkBean);

    /**
     * 新增友情链接信息
     * @param linkBean
     * @return
     */
    int addLinkInfo(LinkBean linkBean);

    /**
     * 修改友情链接信息
     * @param linkBean
     * @return
     */
    int updaLikeInfo(LinkBean linkBean);

    /**
     * 删除友情链接信息
     * @param deleteIds
     * @return
     */
    int deleteLikeInfo(@Param("deleteIds") List<String> deleteIds);

    /**
     * 友情链接审核
     * @param linkBean
     * @return
     */
    int auditLink(LinkBean linkBean);
}
