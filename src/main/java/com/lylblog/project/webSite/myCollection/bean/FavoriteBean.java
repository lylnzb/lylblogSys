package com.lylblog.project.webSite.myCollection.bean;

import lombok.Data;

import java.util.List;

/**
 * 用户收藏夹信息实体类
 * @Author: lyl
 * @Date: 2021/7/28 15:44
 */
@Data
public class FavoriteBean {

    private String id;                        //	收藏夹主键
    private String favoriteName;              //	收藏夹名称
    private String describe;                  //	描述
    private String createBy;                  //	创建人
    private String createTime;                //    创建时间

    private String count;                     //    收藏数量
    private List<CollectionBean> collLists;   //    已收藏的数据

}
