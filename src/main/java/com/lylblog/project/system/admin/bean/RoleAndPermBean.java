package com.lylblog.project.system.admin.bean;

/**
 * 角色权限关系实体类
 */
public class RoleAndPermBean {
    private String id;
    private String roleid;
    private String permid;
    private String valid;
    private String createperson;
    private String createtime;
    private String modifyperson;
    private String modifytime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getPermid() {
        return permid;
    }

    public void setPermid(String permid) {
        this.permid = permid;
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
