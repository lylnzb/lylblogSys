package com.lylblog.project.system.music.service.impl;

import com.lylblog.common.util.shiro.ShiroUtils;
import com.lylblog.common.util.file.FileUtil;
import com.lylblog.framework.config.LylBlogConfig;
import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.login.bean.UserLoginBean;
import com.lylblog.project.system.music.bean.WebMusicBean;
import com.lylblog.project.system.music.mapper.WebMusicMapper;
import com.lylblog.project.system.music.service.WebMusicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: lyl
 * @Date: 2020/11/15 13:27
 */
@Service
public class WebMusicServiceImpl implements WebMusicService {

    @Resource
    private WebMusicMapper webMusicMapper;

    /**
     * 查询所有音乐信息
     * @param webMusicBean
     * @return
     */
    public ResultObj queryMusicInfo(WebMusicBean webMusicBean){
        List<WebMusicBean> webMusicList = webMusicMapper.queryMusicInfo(webMusicBean);
        int count = webMusicMapper.queryMusicInfoCount(webMusicBean);
        return ResultObj.ok(count, webMusicList);
    }

    /**
     * 添加或编辑网站音乐信息
     * @param webMusicBean
     * @return
     */
    public ResultObj addOrUpdateMusicInfo(WebMusicBean webMusicBean, String type){
        //获取当前用户信息
        UserLoginBean userBean = ShiroUtils.getUserInfo();
        //新增
        if("add".equals(type)){
            webMusicBean.setCreateBy(userBean.getYhnm());//创建人id
            int count = webMusicMapper.addMusicInfo(webMusicBean);
            if(count == 0){
                return ResultObj.fail("新增失败");
            }else{
                return ResultObj.ok("新增成功");
            }
        }else if("update".equals(type)){//修改
            webMusicBean.setUpdateBy(userBean.getYhnm());//修改人id
            int count = webMusicMapper.updateMusicInfo(webMusicBean);
            if(count == 0){
                return ResultObj.fail("修改失败");
            }else{
                return ResultObj.ok("修改成功");
            }
        }else{
            return ResultObj.fail("请选择类型！");
        }
    }

    /**
     * 删除音乐信息
     * @param deleteIds
     * @return
     */
    public ResultObj deleteMusicInfo(List<String> deleteIds){
        int count = webMusicMapper.deleteMusicInfo(deleteIds);
        if(count > 0){
            for(String deleteId : deleteIds){
                String fileUrl = LylBlogConfig.getMusicfile() + deleteId;
                boolean flag = FileUtil.deleteDirectory(fileUrl);//删除文件夹下所有文件
                if(flag){//删除文件成功
                    //删除文件数据
                    webMusicMapper.deleteMusicFile(deleteId);
                }
            }
            return ResultObj.ok("删除成功");
        }else{
            return ResultObj.ok("删除失败");
        }
    }

    /**
     * 上传音乐文件
     * @param webMusicBean
     * @return
     */
    public ResultObj uploadMusicFile(WebMusicBean webMusicBean){
        //获取当前用户信息
        UserLoginBean userBean = ShiroUtils.getUserInfo();

        int flag = webMusicMapper.isMusicFile(webMusicBean.getMusicId(),webMusicBean.getFileType());
        if(flag > 0){
            int count = webMusicMapper.updateMusicFileStatus(webMusicBean.getMusicId(),webMusicBean.getFileType(),"0");
            if(count == 0){
                return ResultObj.fail(1, "上传失败");
            }
        }
        if("lyrics".equals(webMusicBean.getFileType())){
            // 读取歌词文件
            webMusicBean.setLyrics(FileUtil.getLyrics(LylBlogConfig.getMusicfile() + webMusicBean.getFileUrl()));
        }else if("audio".equals(webMusicBean.getFileType())){
            // 获取歌曲时长
            webMusicBean.setLength(FileUtil.getMP3ToDuration(LylBlogConfig.getMusicfile() + webMusicBean.getFileUrl()));
        }
        webMusicBean.setCreateBy(userBean.getYhnm());
        int count = webMusicMapper.addMusicFileInfo(webMusicBean);
        if(count >= 1){
            return ResultObj.ok(webMusicBean);
        }else{
            return ResultObj.fail();
        }
    }

    /**
     * 获取文件路径
     * @param musicId
     * @param fileType
     * @return
     */
    public String getFileUrl(String musicId, String fileType){
        return webMusicMapper.getFileUrl(musicId, fileType);
    }

    /**
     * 获取文件名称
     * @param musicId
     * @param fileType
     * @return
     */
    public String getFileName(String musicId, String fileType){
        return webMusicMapper.getFileName(musicId, fileType);
    }
}
