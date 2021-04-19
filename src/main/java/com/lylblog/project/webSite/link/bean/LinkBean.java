package com.lylblog.project.webSite.link.bean;

import com.lylblog.project.common.bean.ParaBean;
import lombok.Data;

/**
 * 友情链接实体benn
 * @Author: lyl
 * @Date: 2021/3/18 10:21
 */
@Data
public class LinkBean {

    private String title;         //	网站标题
    private String url;	          //    网站地址
    private String sortOrder;	  //    排序号
    private String target;        //	是否开启浏览器新窗口

}
