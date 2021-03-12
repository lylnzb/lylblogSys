package com.lylblog.project.webSite.index.bean;

import java.util.List;

/**
 * @Author: lyl
 * @Date: 2021/1/10 18:27
 */
public class TabBean {
    private String tabName;            //选项卡名称
    private String tabType;            //选项卡类型
    private List<CardBean> cardList;   //选项卡内容集合

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public String getTabType() {
        return tabType;
    }

    public void setTabType(String tabType) {
        this.tabType = tabType;
    }

    public List<CardBean> getCardList() {
        return cardList;
    }

    public void setCardList(List<CardBean> cardList) {
        this.cardList = cardList;
    }
}
