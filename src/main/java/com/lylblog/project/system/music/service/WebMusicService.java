package com.lylblog.project.system.music.service;

import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.system.music.bean.WebMusicBean;

import java.util.List;

/**
 * @Author: lyl
 * @Date: 2020/11/15 13:27
 */
public interface WebMusicService {

    /**
     * 查询所有音乐信息
     * @param webMusicBean
     * @return
     */
    ResultObj queryMusicInfo(WebMusicBean webMusicBean);

    /**
     * 添加或编辑网站音乐信息
     * @param webMusicBean
     * @return
     */
    ResultObj addOrUpdateMusicInfo(WebMusicBean webMusicBean, String type);

    /**
     * 删除音乐信息
     * @param deleteIds
     * @return
     */
    ResultObj deleteMusicInfo( List<String> deleteIds);

    /**
     * 上传音乐文件
     * @param webMusicBean
     * @return
     */
    ResultObj uploadMusicFile(WebMusicBean webMusicBean);

    /**
     * 获取文件路径
     * @param musicId
     * @param fileType
     * @return
     */
    String getFileUrl(String musicId, String fileType);

    /**
     * 获取文件名称
     * @param musicId
     * @param fileType
     * @return
     */
    String getFileName(String musicId, String fileType);
}
