package com.lylblog.project.system.banner.service.impl;

import com.lylblog.common.util.shiro.ShiroUtils;
import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.login.bean.UserLoginBean;
import com.lylblog.project.system.banner.bean.BannerBean;
import com.lylblog.project.system.banner.mapper.BannerMapper;
import com.lylblog.project.system.banner.service.BannerService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: lyl
 * @Date: 2021/1/20 19:30
 */
@Service
public class BannerServiceImpl implements BannerService {

    @Resource
    private BannerMapper bannerMapper;

    /**
     * 查询所有轮播图信息
     * @param bannerBean
     * @return
     */
    public ResultObj queryBannerInfo(BannerBean bannerBean){
        int count = bannerMapper.queryBannerInfoCount(bannerBean);
        if(count > 0){
            List<BannerBean> webMusicList = bannerMapper.queryBannerInfo(bannerBean);
            return ResultObj.ok(count, webMusicList);
        }
        return ResultObj.ok();
    }

    /**
     * 新增轮播图信息
     * @param bannerBean
     * @return
     */
    public ResultObj addOrUpdaBannerInfo(BannerBean bannerBean, String type){
        //获取当前用户信息
        UserLoginBean userBean = ShiroUtils.getUserInfo();
        //新增
        if("add".equals(type)){
            bannerBean.setCreateBy(userBean.getYhnm());//创建人id
            int count = bannerMapper.addBannerInfo(bannerBean);
            if(count == 0){
                return ResultObj.fail("新增失败");
            }else{
                return ResultObj.ok("新增成功");
            }
        }else if("update".equals(type)){//修改
            bannerBean.setUpdateBy(userBean.getYhnm());//修改人id
            int count = bannerMapper.updateBannerInfo(bannerBean);
            if(count == 0){
                return ResultObj.fail("修改失败");
            }else{
                return ResultObj.ok("修改成功");
            }
        }else{
            return ResultObj.fail("请选择类型！");
        }
    }

    /**
     * 删除轮播图信息
     * @param deleteIds
     * @return
     */
    public ResultObj deleteBannerInfo(@RequestBody List<String> deleteIds){
        int count = bannerMapper.deleteBannerInfo(deleteIds);
        if(count > 0){
            return ResultObj.ok("删除成功");
        }else{
            return ResultObj.ok("删除失败");
        }
    }
}
