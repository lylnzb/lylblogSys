package com.lylblog.project.webSite.user.bean;

import lombok.Data;

/**
 * @Author: lyl
 * @Date: 2021/7/16 9:24
 */
@Data
public class UserParamBean {

    private String yhnm;        //	用户唯一内码
    private String sex;         //	性别
    private String nickName;    //	用户昵称
    private String realName;    //	真实姓名
    private String area;        //	所在地区
    private String provinceCode;//  省份行政区划代码
    private String cityCode;    //  城市行政区划代码
    private String areaCode;    //  区县行政区划代码
    private String birthday;    //	出生日期
    private String workTime;    //	开始工作日期
    private String professional;//	职业
    private String company;     //	公司
    private String signature;   //	个性签名

}
