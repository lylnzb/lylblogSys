package com.lylblog.project.common.bean;

import lombok.Data;

/**
 * 个人动态信息实体类
 * @Author: lyl
 * @Date: 2021/9/2 15:50
 */
@Data
public class DynamicBean extends ParaBean{

    private String id;               //	个人动态主键
    private String dynamicType;      //	动态类型
    private String wznm;             //	文章内码
    private String commentYhnm;      //	评论人用户内码
    private String commentName;      //	评论人名称
    private String articleTitle;     //	文章名称
    private String commentContent;   //	评论内容
    private String operYhnm;         //	操作人用户内码
    private String operTime;         //	操作时间

}
