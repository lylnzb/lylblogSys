package com.lylblog.project.webSite.myCollection.service;

import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.webSite.myCollection.bean.CollectionBean;
import com.lylblog.project.webSite.myCollection.bean.FavoriteBean;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: lyl
 * @Date: 2021/7/28 15:40
 */
public interface CollectionService {

    /**
     * 新增收藏夹信息
     * @param favorite
     * @return
     */
    ResultObj addFavoriteInfo(FavoriteBean favorite);

    /**
     * 修改收藏夹信息
     * @param favorite
     * @param type
     * @return
     */
    ResultObj updateFavoriteInfo(FavoriteBean favorite, String type);

    /**
     * 删除收藏夹信息
     * @param id 删除收藏夹id
     * @param type 类型（1、移入其他收藏夹  2、与当前文件夹一起删除）
     * @param targetId 目标文件夹id
     * @return
     */
    ResultObj deleteFavoriteInfo(String id, String type, String targetId);

    /**
     * 新增用户收藏数据
     * @param collection
     * @return
     */
    ResultObj addCollectionData(CollectionBean collection);

    /**
     * 根据收藏id删除收藏数据
     * @param collectionId
     * @return
     */
    ResultObj deleteCollectionDataByCollectId(String collectionId);

    /**
     * 根据用户内码查询收藏夹信息
     * @return
     */
    ResultObj queryFavoriteInfo();

    /**
     * 展示指定收藏夹下的收藏数据
     * @param id
     * @return
     */
    ResultObj showCollectionData(String id);

    /**
     * 通过文章内码查询总收藏数量
     * @param wznm
     * @return
     */
    int getCollectNumBywznm(String wznm);

    /**
     * 通过用户内码和文章内码判断当前用户是否已收藏
     * @param wznm
     * @return
     */
    int isCollectionByYhnm(String wznm);
}
