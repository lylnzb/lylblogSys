package com.lylblog.project.webSite.myCollection.service.impl;

import com.lylblog.common.util.shiro.ShiroUtils;
import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.login.bean.UserLoginBean;
import com.lylblog.project.system.article.bean.ArticleBean;
import com.lylblog.project.system.article.mapper.ArticleMapper;
import com.lylblog.project.webSite.myCollection.bean.CollectionBean;
import com.lylblog.project.webSite.myCollection.bean.FavoriteBean;
import com.lylblog.project.webSite.myCollection.mapper.CollectionMapper;
import com.lylblog.project.webSite.myCollection.service.CollectionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: lyl
 * @Date: 2021/7/28 15:41
 */
@Service
public class CollectionServiceImpl implements CollectionService {

    @Resource
    private CollectionMapper collectionMapper;

    @Resource
    private ArticleMapper articleMapper;

    /**
     * 新增收藏夹信息
     * @param favorite
     * @return
     */
    public ResultObj addFavoriteInfo(FavoriteBean favorite) {
        if(null == favorite.getFavoriteName() || "".equals(favorite.getFavoriteName())) {
            return ResultObj.fail("收藏夹名称不能为空");
        }
        UserLoginBean user = ShiroUtils.getUserInfo();
        favorite.setCreateBy(user.getYhnm());
        int count = collectionMapper.addFavoriteInfo(favorite);
        if(count > 0){
            return ResultObj.ok("新增成功");
        }
        return ResultObj.fail("新增失败");
    }

    /**
     * 修改收藏夹信息
     * @param favorite
     * @param type 类型（1.修改名称  2.修改收藏夹描述）
     * @return
     */
    public ResultObj updateFavoriteInfo(FavoriteBean favorite, String type){
        if(null != type && "1".equals(type) && (null == favorite.getFavoriteName() || "".equals(favorite.getFavoriteName()))) {
            return ResultObj.fail("收藏夹名称不能为空!");
        }
        if(null != type && "2".equals(type) && (null == favorite.getDescribe() || "".equals(favorite.getDescribe()))) {
            return ResultObj.fail("收藏夹描述不能为空!");
        }
        if(null != type && "1".equals(type)) {
            favorite.setDescribe("");
        }
        if(null != type && "2".equals(type)) {
            favorite.setFavoriteName("");
        }
        UserLoginBean user = ShiroUtils.getUserInfo();
        favorite.setCreateBy(user.getYhnm());
        int count = collectionMapper.updateFavoriteInfo(favorite);
        if(count > 0){
            return ResultObj.ok("修改成功");
        }
        return ResultObj.fail("修改失败");
    }

    /**
     * 删除收藏夹信息
     * @param id 删除收藏夹id
     * @param type 类型（1、移入其他收藏夹  2、与当前文件夹一起删除）
     * @param targetId 目标文件夹id
     * @return
     */
    public ResultObj deleteFavoriteInfo(String id, String type, String targetId){
        if(null == id || "".equals(id)) {
            return ResultObj.fail("收藏夹不能为空！");
        }
        if("1".equals(type) && (null == targetId || "".equals(targetId))) {
            return ResultObj.fail("请选择要移动的文件内容！");
        }
        if("1".equals(type) && id.equals(targetId)) {
            return ResultObj.fail("文件已经在此文件夹中了！");
        }

        int count = collectionMapper.deleteFavoriteInfo(id);
        if(count > 0) {
            if(null != type && "1".equals(type)) {
                collectionMapper.moveCollectionDataToFavorite(id, targetId);
            }else if("2".equals(type)) {
                collectionMapper.deleteCollectionDataByFavoriteId(id);
            }
            return ResultObj.ok("删除成功");
        }
        return ResultObj.fail("删除失败");
    }

    /**
     * 新增用户收藏数据
     * @param collection
     * @return
     */
    public ResultObj addCollectionData(CollectionBean collection) {
        UserLoginBean user = ShiroUtils.getUserInfo();
        if(user == null){
            return ResultObj.fail("找不到该用户信息！");
        }
        ArticleBean article = new ArticleBean();
        article.setWznm(collection.getWznm());
        int num = articleMapper.queryArticleInfoCount(article);
        if(num == 0){
            return ResultObj.fail("找不到该文章信息！");
        }
        if(collection.getFavoriteId() == null || "".equals(collection.getFavoriteId())){
            return ResultObj.fail("请选择收藏夹！");
        }

        collection.setCreateBy(user.getYhnm());
        int isColl = collectionMapper.isCollectionByYhnm(user.getYhnm(), collection.getWznm());
        int count = 0;
        if(isColl > 0){
            count = collectionMapper.updateCollectionData(collection);
        }else {
            count = collectionMapper.addCollectionData(collection);
        }
        if(count > 0){
            return ResultObj.ok("收藏成功");
        }else {
            return ResultObj.fail("收藏失败");
        }

    }

    /**
     * 根据收藏id删除收藏数据
     * @param collectionId
     * @return
     */
    public ResultObj deleteCollectionDataByCollectId(String collectionId) {
        int count = collectionMapper.deleteCollectionDataByCollectId(collectionId);
        if(count > 0) {
            return ResultObj.ok("取消收藏成功！");
        }
        return ResultObj.fail("取消收藏失败！");
    }

    /**
     * 根据用户内码查询收藏夹信息
     * @return
     */
    public ResultObj queryFavoriteInfo(){
        UserLoginBean user = ShiroUtils.getUserInfo();
        List<FavoriteBean> favoriteList = collectionMapper.queryFavoriteInfo(user.getYhnm());
        return ResultObj.ok(favoriteList.size(), favoriteList);
    }

    /**
     * 展示指定收藏夹下的收藏数据
     * @param id
     * @return
     */
    public ResultObj showCollectionData(String id){
        UserLoginBean user = ShiroUtils.getUserInfo();
        FavoriteBean favorite = collectionMapper.queryFavoriteInfoById(id, user.getYhnm());
        favorite.setCollLists(collectionMapper.queryCollectionData(id));
        if(null != favorite) {
            return ResultObj.ok(favorite);
        }
        return ResultObj.fail("未查询到该收藏夹数据");
    }

    /**
     * 通过文章内码查询总收藏数量
     * @param wznm
     * @return
     */
    public int getCollectNumBywznm(String wznm) {
        return collectionMapper.getCollectNumBywznm(wznm);
    }

    /**
     * 通过用户内码和文章内码判断当前用户是否已收藏
     * @param wznm
     * @return
     */
    public int isCollectionByYhnm(String wznm){
        UserLoginBean user = ShiroUtils.getUserInfo();
        if(user == null) {
            return 0;
        }
        return collectionMapper.isCollectionByYhnm(user.getYhnm(), wznm);
    }
}
