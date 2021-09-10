package com.lylblog.project.webSite.myCollection.bean;

import lombok.Data;

/**
 * 用户收藏信息实体类
 * @Author: lyl
 * @Date: 2021/7/28 15:46
 */
@Data
public class CollectionBean {

    private String collectionId;     //	收藏主键
    private String favoriteId;       //	收藏夹id
    private String wznm;             //	文章内码
    private String createBy;         //	创建人
    private String createTime;       //	创建时间

    private String articleTitle;     // 文章名称
}
