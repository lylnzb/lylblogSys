package com.lylblog.project.system.article.bean;

/**
 * @Author: lyl
 * @Date: 2020/12/27 22:25
 */
public class LabelSelectBean {
    private String name;
    private String value;
    private boolean selected;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
