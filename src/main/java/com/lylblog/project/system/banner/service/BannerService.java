package com.lylblog.project.system.banner.service;

import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.system.banner.bean.BannerBean;

import java.util.List;

/**
 * @Author: lyl
 * @Date: 2021/1/20 19:30
 */
public interface BannerService {
    /**
     * 查询所有轮播图信息
     * @param bannerBean
     * @return
     */
    ResultObj queryBannerInfo(BannerBean bannerBean);

    /**
     * 新增轮播图信息
     * @param bannerBean
     * @return
     */
    ResultObj addOrUpdaBannerInfo(BannerBean bannerBean, String type);

    /**
     * 删除轮播图信息
     * @param deleteIds
     * @return
     */
    ResultObj deleteBannerInfo(List<String> deleteIds);
}
