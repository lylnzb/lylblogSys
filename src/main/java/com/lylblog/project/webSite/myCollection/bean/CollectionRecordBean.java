package com.lylblog.project.webSite.myCollection.bean;

import lombok.Data;

/**
 * 用户收藏记录信息实体类
 * @Author: lyl
 * @Date: 2021/7/28 17:09
 */
@Data
public class CollectionRecordBean {

    private String recordId;         //	收藏记录主键
    private String wznm;             //	文章内码
    private String yhnm;             //	用户内码
    private String status;           //	收藏状态
    private String recordTime;       //	创建时间

}
