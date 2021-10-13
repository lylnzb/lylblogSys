package com.lylblog.project.common.bean;

import lombok.Data;

/**
 * @Author: lyl
 * @Date: 2021/9/15 10:24
 */
@Data
public class WebSiteTjBean {

    private String webName;              //  网站名称
    private String blogNum;              //  博客文章数量
    private String commentNum;           //  评论数量
    private String messageNum;           //  留言数量
    private String browseNum;            //  总浏览量
    private String toDaybrowseNum;       //  今日访问量
    private String nowVisitorsFrom;      //  当前访客来源

}
