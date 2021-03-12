package com.lylblog.project.system.banner.bean;

import com.lylblog.project.common.bean.ParaBean;

/**
 * @Author: lyl
 * @Date: 2021/1/22 20:16
 */
public class BannerBean extends ParaBean {

    private String bannerId;        //轮播图id
    private String bannerTitle;     //轮播图标题
    private String bannerImg;       //轮播图
    private String bannerUrl;       //轮播图url地址
    private String valid;           //有效标志[1:有效,0:无效]

    public String getBannerId() {
        return bannerId;
    }

    public void setBannerId(String bannerId) {
        this.bannerId = bannerId;
    }

    public String getBannerTitle() {
        return bannerTitle;
    }

    public void setBannerTitle(String bannerTitle) {
        this.bannerTitle = bannerTitle;
    }

    public String getBannerImg() {
        return bannerImg;
    }

    public void setBannerImg(String bannerImg) {
        this.bannerImg = bannerImg;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }
}
