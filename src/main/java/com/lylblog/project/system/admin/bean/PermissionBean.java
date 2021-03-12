package com.lylblog.project.system.admin.bean;

import java.io.Serializable;

/**
 * 用户权限信息实体类
 */
public class PermissionBean implements Serializable {
    private String permId;//权限id
    private String iconType;//图标上传类型
    private String iconUrl;//图标路径或图标代码
    private String permname;//权限名称
    private String permdesc;//权限描述
    private String permission;//授权控制
    private String parentId;//父级权限ID
    private String parentName;//父级权限名称
    private String permType;//权限类型[0：目录，1：菜单，2：按钮]
    private String permUrl;//模块加载路径
    private String permOrder;//菜单顺序位置
    private String valid;//有效标志[1:有效,0:无效]
    private String createperson;//创建人
    private String createtime;//创建时间
    private String modifyperson;//修改人
    private String modifytime;//修改时间

    public String getPermId() {
        return permId;
    }

    public void setPermId(String permId) {
        this.permId = permId;
    }

    public String getIconType() {
        return iconType;
    }

    public void setIconType(String iconType) {
        this.iconType = iconType;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getPermname() {
        return permname;
    }

    public void setPermname(String permname) {
        this.permname = permname;
    }

    public String getPermdesc() {
        return permdesc;
    }

    public void setPermdesc(String permdesc) {
        this.permdesc = permdesc;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getPermType() {
        return permType;
    }

    public void setPermType(String permType) {
        this.permType = permType;
    }

    public String getPermUrl() {
        return permUrl;
    }

    public void setPermUrl(String permUrl) {
        this.permUrl = permUrl;
    }

    public String getPermOrder() {
        return permOrder;
    }

    public void setPermOrder(String permOrder) {
        this.permOrder = permOrder;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public String getCreateperson() {
        return createperson;
    }

    public void setCreateperson(String createperson) {
        this.createperson = createperson;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getModifyperson() {
        return modifyperson;
    }

    public void setModifyperson(String modifyperson) {
        this.modifyperson = modifyperson;
    }

    public String getModifytime() {
        return modifytime;
    }

    public void setModifytime(String modifytime) {
        this.modifytime = modifytime;
    }
}
