package com.lylblog.project.webSite.user.bean;

import com.lylblog.project.common.bean.ParaBean;
import lombok.Data;

/**
 * @Author: lyl
 * @Date: 2021/6/15 9:49
 */
@Data
public class UserLoginRecordBean extends ParaBean {

    private String loginWay;
    private String address;
    private String loginSystem;
    private String loginTime;
    private String yhnm;

}
