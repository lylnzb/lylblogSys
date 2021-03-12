package com.lylblog.project.system.music.mapper;

import com.lylblog.project.system.music.bean.WebMusicBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: lyl
 * @Date: 2020/11/15 13:26
 */
@Mapper
public interface WebMusicMapper {

    /**
     * 查询所有音乐信息
     * @param webMusicBean
     * @return
     */
    List<WebMusicBean> queryMusicInfo(WebMusicBean webMusicBean);

    /**
     * 查询所有音乐信息总数
     * @param webMusicBean
     * @return
     */
    int queryMusicInfoCount(WebMusicBean webMusicBean);

    /**
     * 新增音乐信息
     * @param webMusicBean
     * @return
     */
    int addMusicInfo(WebMusicBean webMusicBean);

    /**
     * 新增音乐附件信息
     * @param webMusicBean
     * @return
     */
    int addMusicFileInfo(WebMusicBean webMusicBean);

    /**
     * 修改音乐信息
     * @param webMusicBean
     * @return
     */
    int updateMusicInfo(WebMusicBean webMusicBean);

    /**
     * 修改音乐附件信息
     * @param webMusicBean
     * @return
     */
    int updateMusicFileInfo(WebMusicBean webMusicBean);

    /**
     * 删除音乐信息
     * @param deleteIds
     * @return
     */
    int deleteMusicInfo(@Param("deleteIds") List<String> deleteIds);

    /**
     * 删除音乐文件信息
     * @param musicId
     * @return
     */
    int deleteMusicFile(@Param("musicId") String musicId);

    /**
     * 是否存在音乐文件数据
     * @param musicId
     * @return
     */
    int isMusicFile(@Param("musicId") String musicId, @Param("fileType") String fileType);

    /**
     * 修改音乐文件数据状态
     * @param musicId
     * @param fileType
     * @param valid
     * @return
     */
    int updateMusicFileStatus(@Param("musicId") String musicId, @Param("fileType") String fileType, @Param("valid") String valid);

    /**
     * 获取文件路径
     * @param musicId
     * @param fileType
     * @return
     */
    String getFileUrl(@Param("musicId") String musicId, @Param("fileType") String fileType);

    /**
     * 获取文件名称
     * @param musicId
     * @param fileType
     * @return
     */
    String getFileName(@Param("musicId") String musicId, @Param("fileType") String fileType);
}
