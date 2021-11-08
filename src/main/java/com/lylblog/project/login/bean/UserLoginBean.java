package com.lylblog.project.login.bean;

import com.lylblog.project.system.admin.bean.PermissionBean;
import com.lylblog.project.system.admin.bean.RoleBean;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.Serializable;
import java.util.List;

/**
 * 用户信息实体类
 */
@Data
public class UserLoginBean implements Serializable {
    private static final long serialVersionUID = 1L;

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
    private String nickname;//昵称
    private String rolename;//角色名称
    private String createperson;//创建人
    private String createtime;//创建时间

    private String valid;//有效标志[1:有效,0:无效]

    private String appType;   //	登录方式
    private String appUserId; //	第三方登录唯一ID

    private List<RoleBean> roles;
    private List<PermissionBean> perms;

    public static boolean isAdmin(String userId) {
        return userId != null && "41545wBXlg7sjt2f".equals(userId);
    }

    public String getvCode() {
        return vCode;
    }

    public void setvCode(String vCode) {
        this.vCode = vCode;
    }
}
