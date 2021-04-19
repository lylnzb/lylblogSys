package com.lylblog.project.webSite.link.mapper;

import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.webSite.link.bean.LinkBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.validation.constraints.Max;
import java.util.List;

/**
 * @Author: lyl
 * @Date: 2021/2/20 14:13
 */
@Mapper
public interface LinksMapper {

    /**
     * 查询友情链接信息
     * @param type
     * @return
     */
    List<LinkBean> queryLinksInfo(@Param("type") String type);
}
