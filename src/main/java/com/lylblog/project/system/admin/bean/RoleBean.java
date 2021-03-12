package com.lylblog.project.system.admin.bean;

import com.lylblog.project.common.bean.ParaBean;

import java.io.Serializable;
import java.util.List;

/**
 * 用户角色信息实体类
 */
public class RoleBean extends ParaBean implements Serializable {
    private int rk;//序号

    private String yhnm;//用户唯一编码
    private String roleid;//角色id
    private String rolename;//角色名称
    private String rolekey;//权限字符
    private String roledesc;//角色描述
    private String valid;//有效标志[1:有效,0:无效]
    private String isDefault;//是否默认用户新增角色[1:是,2:否]
    private String createperson;//创建人ID
    private String founder;//创建人名称
    private String createtime;//创建时间
    private String modifyperson;//修改人ID
    private String modifier;//修改人名称
    private String modifytime;//修改时间

    private List<String> treeList;//权限树

    public int getRk() {
        return rk;
    }

    public void setRk(int rk) {
        this.rk = rk;
    }

    public String getYhnm() {
        return yhnm;
    }

    public void setYhnm(String yhnm) {
        this.yhnm = yhnm;
    }

    public List<String> getTreeList() {
        return treeList;
    }

    public void setTreeList(List<String> treeList) {
        this.treeList = treeList;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getRolekey() {
        return rolekey;
    }

    public void setRolekey(String rolekey) {
        this.rolekey = rolekey;
    }

    public String getRoledesc() {
        return roledesc;
    }

    public void setRoledesc(String roledesc) {
        this.roledesc = roledesc;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getCreateperson() {
        return createperson;
    }

    public void setCreateperson(String createperson) {
        this.createperson = createperson;
    }

    public String getFounder() {
        return founder;
    }

    public void setFounder(String founder) {
        this.founder = founder;
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

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getModifytime() {
        return modifytime;
    }

    public void setModifytime(String modifytime) {
        this.modifytime = modifytime;
    }
}
