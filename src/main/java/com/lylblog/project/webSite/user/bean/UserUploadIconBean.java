package com.lylblog.project.webSite.user.bean;

import lombok.Data;

/**
 * @Author: lyl
 * @Date: 2021/7/8 9:18
 */
@Data
public class UserUploadIconBean {

    private String yhnm;      //  用户唯一内码
    private String base64;    //  base64编码
    private String imgName;   //  图片名称

}
