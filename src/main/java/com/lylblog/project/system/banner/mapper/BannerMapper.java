package com.lylblog.project.system.banner.mapper;

import com.lylblog.project.system.banner.bean.BannerBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 轮播图管理
 * @Author: lyl
 * @Date: 2021/1/20 19:28
 */
@Mapper
public interface BannerMapper {

    /**
     * 查询所有轮播图信息
     * @param bannerBean
     * @return
     */
    List<BannerBean> queryBannerInfo(BannerBean bannerBean);

    /**
     * 查询所有轮播图信息总数
     * @param bannerBean
     * @return
     */
    int queryBannerInfoCount(BannerBean bannerBean);

    /**
     * 新增轮播图信息
     * @param bannerBean
     * @return
     */
    int addBannerInfo(BannerBean bannerBean);

    /**
     * 修改轮播图信息
     * @param bannerBean
     * @return
     */
    int updateBannerInfo(BannerBean bannerBean);

    /**
     * 删除轮播图信息
     * @param deleteIds
     * @return
     */
    int deleteBannerInfo(@Param("deleteIds") List<String> deleteIds);
}
