package com.lylblog.project.webSite.myCollection.comtroller;

import com.lylblog.common.util.shiro.ShiroUtils;
import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.login.bean.UserLoginBean;
import com.lylblog.project.webSite.myCollection.bean.CollectionBean;
import com.lylblog.project.webSite.myCollection.bean.FavoriteBean;
import com.lylblog.project.webSite.myCollection.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: lyl
 * @Date: 2021/7/28 15:42
 */
@Controller
@RequestMapping("/myCollection")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    /**
     * 新增收藏夹信息
     * @param favorite
     * @return
     */
    @RequestMapping("/addFavoriteInfo")
    @ResponseBody
    public ResultObj addFavoriteInfo(@RequestBody FavoriteBean favorite) {
        return collectionService.addFavoriteInfo(favorite);
    }

    /**
     * 修改收藏夹信息
     * @param favorite
     * @param type 类型（1.修改名称  2.修改收藏夹描述）
     * @return
     */
    @RequestMapping("/updateFavoriteInfo")
    @ResponseBody
    public ResultObj updateFavoriteInfo(@RequestBody FavoriteBean favorite, String type){
        return collectionService.updateFavoriteInfo(favorite, type);
    }

    /**
     * 删除收藏夹信息
     * @param id 删除收藏夹id
     * @param type 类型（1、移入其他收藏夹  2、与当前文件夹一起删除）
     * @param targetId 目标文件夹id
     * @return
     */
    @RequestMapping("/deleteFavoriteInfo")
    @ResponseBody
    public ResultObj deleteFavoriteInfo(String id, String type, String targetId){
        return collectionService.deleteFavoriteInfo(id, type, targetId);
    }

    /**
     * 新增用户收藏数据
     * @param collection
     * @return
     */
    @RequestMapping("/addCollectionData")
    @ResponseBody
    public ResultObj addCollectionData(@RequestBody CollectionBean collection) {
        return collectionService.addCollectionData(collection);
    }

    /**
     * 根据收藏id删除收藏数据
     * @param collectionId
     * @return
     */
    @RequestMapping("/deleteCollectionDataByCollectId")
    @ResponseBody
    public ResultObj deleteCollectionDataByCollectId(String collectionId) {
        return collectionService.deleteCollectionDataByCollectId(collectionId);
    }

    /**
     * 根据用户内码查询收藏夹信息
     * @return
     */
    @RequestMapping("/queryFavoriteInfo")
    @ResponseBody
    public ResultObj queryFavoriteInfo(){
        return collectionService.queryFavoriteInfo();
    }

    /**
     * 展示指定收藏夹下的收藏数据
     * @param id
     * @return
     */
    @RequestMapping("/showCollectionData")
    @ResponseBody
    public ResultObj showCollectionData(String id){
        return collectionService.showCollectionData(id);
    }
}
