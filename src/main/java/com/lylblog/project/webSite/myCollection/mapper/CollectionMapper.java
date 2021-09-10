package com.lylblog.project.webSite.myCollection.mapper;

import com.lylblog.project.webSite.myCollection.bean.CollectionBean;
import com.lylblog.project.webSite.myCollection.bean.CollectionRecordBean;
import com.lylblog.project.webSite.myCollection.bean.FavoriteBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: lyl
 * @Date: 2021/7/28 15:39
 */
@Mapper
public interface CollectionMapper {

    /**
     * 新增收藏夹信息
     * @param favorite
     * @return
     */
    int addFavoriteInfo(FavoriteBean favorite);

    /**
     * 修改收藏夹信息
     * @param favorite
     * @return
     */
    int updateFavoriteInfo(FavoriteBean favorite);

    /**
     * 删除收藏夹信息
     * @param id
     * @return
     */
    int deleteFavoriteInfo(String id);

    /**
     * 新增用户收藏数据
     * @param collection
     * @return
     */
    int addCollectionData(CollectionBean collection);

    /**
     * 修改用户收藏数据
     * @param collection
     * @return
     */
    int updateCollectionData(CollectionBean collection);

    /**
     * 移入收藏数据到其他收藏夹
     * @param id
     * @param targetId
     * @return
     */
    int moveCollectionDataToFavorite(String id, String targetId);

    /**
     * 通过收藏夹ID删除删除所有收藏数据
     * @param id
     * @return
     */
    int deleteCollectionDataByFavoriteId(String id);

    /**
     * 根据收藏id删除收藏数据
     * @param collectionId
     * @return
     */
    int deleteCollectionDataByCollectId(String collectionId);

    /**
     * 根据用户内码查询收藏夹信息
     * @param yhnm
     * @return
     */
    List<FavoriteBean> queryFavoriteInfo(@Param("yhnm") String yhnm);

    /**
     * 根据收藏夹ID查询指定收藏夹信息
     * @param id
     * @param yhnm
     * @return
     */
    FavoriteBean queryFavoriteInfoById(@Param("id") String id, @Param("yhnm") String yhnm);

    /**
     * 根据收藏夹ID查询指定收藏夹下的收藏数据
     * @param id
     * @return
     */
    List<CollectionBean> queryCollectionData(@Param("id") String id);

    /**
     * 通过文章内码查询总收藏数量
     * @param wznm
     * @return
     */
    int getCollectNumBywznm(@Param("wznm") String wznm);

    /**
     * 通过用户内码和文章内码判断当前用户是否已收藏
     * @param yhnm
     * @param wznm
     * @return
     */
    int isCollectionByYhnm(@Param("yhnm") String yhnm, @Param("wznm") String wznm);
}
