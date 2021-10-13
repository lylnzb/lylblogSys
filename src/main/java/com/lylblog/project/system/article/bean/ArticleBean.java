package com.lylblog.project.system.article.bean;

import com.lylblog.project.common.bean.ParaBean;
import lombok.Data;

import java.util.List;

/**
 * @Author: lyl
 * @Date: 2020/12/2 19:38
 */
@Data
public class ArticleBean extends ParaBean {

    private String articleId;        //	 文章编号
    private String wznm;             //  文章内码
    private String columnId;         //	 所属栏目编号
    private String articleTitle;     //	 文章标题
    private String abstracts;        //	 文章摘要
    private String content;          //  文章内容
    private String copyFrom;         //	 文章来源
    private String fromWay;          //	 文章来源方式（1、原创  2、转载）
    private String fromUrl;          //	 文章来源网址
    private String articleLabel;     //	 文章标签
    private String hits;             //	 点击数
    private String postNum;          //	 评论数
    private String onTop;            //	 是否置顶
    private String iselite;          //  是否推荐
    private String openIntroduce;    //	 开启简介
    private String allowComment;     //	 允许评论
    private String allowReprinted;   //	 允许转载
    private String thumb;            //	 浓缩图路径
    private String articleStatus;    //	 文章状态（草稿、审核、正常）

    private String file;             //  文件
    private String columnName;       //  所属专栏
    private String articleStatusName;//  文章状态名称
    private String fromWayName;      //  文章来源方式名称

    private String judgeLongTime;    //  判断时间据现在多久

    private String labelId;          //  标签id

    private String blogType;         //  博客类型

    private List<LabelSelectBean> label;
}
