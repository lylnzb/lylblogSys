package com.lylblog.project.common.bean;

import java.util.List;

/**
 * @Author: lyl
 * @Date: 2020/12/30 12:57
 */
public class MenuBean {

    private String id;
    private String menuName;          //  导航栏名称
    private String menuUrl;           //  导航栏网址
    private String icon;              //  导航栏图标
    private String isDefault;         //  级别（1.父栏目 2.子栏目）

    private List<MenuBean> childList; //  子栏目集合

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public List<MenuBean> getChildList() {
        return childList;
    }

    public void setChildList(List<MenuBean> childList) {
        this.childList = childList;
    }
}
