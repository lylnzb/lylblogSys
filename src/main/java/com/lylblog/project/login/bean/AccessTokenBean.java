package com.lylblog.project.login.bean;

import lombok.Data;

/**
 * @Author: lyl
 * @Date: 2021/10/25 22:17
 */
@Data
public class AccessTokenBean {

    private String id;                //	自增主键
    private String accessToken;       //	用户唯一内码
    private String tokenType;         //	类型
    private String expiresIn;         //    token有效期
    private String createDate;        //	获取时间

}
