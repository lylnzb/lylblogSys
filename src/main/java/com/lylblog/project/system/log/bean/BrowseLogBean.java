package com.lylblog.project.system.log.bean;

import com.lylblog.project.common.bean.ParaBean;
import lombok.Data;

/**
 * @Author: lyl
 * @Date: 2021/9/15 15:24
 */
@Data
public class BrowseLogBean extends ParaBean {

    private String blogBroweId;           //	id
    private String clientPlatform;        //	客户端平台
    private String clientUserAgent;       //	客户端UA
    private String clientBrowsePlatform;  //	浏览器系统
    private String clientBrowseName;      //	浏览器名称
    private String clientBrowsVersion;    //	浏览器版本
    private String clientBrowseIp;        //	浏览器ip
    private String clientBrowseCity;      //	浏览器所在城市
    private String clientBrowseAppAndPc;  //	浏览器客户端类型
    private String clientBrowseUrl;       //	浏览地址
    private String createTime;            //	创建时间


}
