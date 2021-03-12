package com.lylblog.project.system.admin.bean;

import com.lylblog.project.common.bean.ParaBean;

import java.io.Serializable;
import java.util.List;

/**
 * 用户信息实体类
 */
public class UserBean extends ParaBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private int rk;

    private String yhnm;//用户唯一内码
    private String email;//用户邮箱
    private String sex;//性别
    private String password;//用户密码
    private String salt;//盐
    private String signature;//个性签名
    private String level;//用户级别
    private String regtime;//注册时间
    private String vCode;//验证码
    private String iconUrl;//头像路径

    private String userId;//用户id
    private String roleId;//角色id
    private String rolename;//角色名称
    private String createperson;//创建人
    private String createtime;//创建时间

    private String valid;//有效标志[1:有效,0:无效]

    public int getRk() {
        return rk;
    }

    public void setRk(int rk) {
        this.rk = rk;
    }

    public String getvCode() { return vCode; }

    public void setvCode(String vCode) {
        this.vCode = vCode;
    }

    public String getYhnm() {
        return yhnm;
    }

    public void setYhnm(String yhnm) {
        this.yhnm = yhnm;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) { this.salt = salt; }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getRegtime() {
        return regtime;
    }

    public void setRegtime(String regtime) {
        this.regtime = regtime;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
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

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public static boolean isAdmin(String userId) {
        return userId != null && "41545wBXlg7sjt2f".equals(userId);
    }
}
