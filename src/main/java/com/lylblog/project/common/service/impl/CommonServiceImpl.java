package com.lylblog.project.common.service.impl;

import com.lylblog.common.util.EntityToArrayUtil;
import com.lylblog.project.common.bean.LabelBean;
import com.lylblog.project.common.bean.MenuBean;
import com.lylblog.project.common.bean.MusicBean;
import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.common.mapper.CommonMapper;
import com.lylblog.project.common.service.CommonService;
import com.lylblog.project.system.blogSet.bean.BlogSetBean;
import com.lylblog.project.system.dict.bean.DictDataBean;
import org.springframework.stereotype.Service;
import com.lylblog.framework.config.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommonServiceImpl implements CommonService {

    @Resource
    private CommonMapper commonMapper;

    /**
     * 根据编码类别查询字典
     * @param dictType
     * @return
     */
    public ResultObj queryCodeValue(String dictType){
        List<DictDataBean> dictDataList = commonMapper.queryCodeValue(dictType);
        return ResultObj.ok(dictDataList);
    }

    /**
     * 查询音乐列表
     * @return
     */
    public Object[][] queryMusicList(){
        //查询音乐歌单数据
        List<DictDataBean> dictDataList = commonMapper.queryCodeValue("web_music_gedan");

        Map<Integer, Object> musicMap = new HashMap<Integer, Object>();
        Integer i = 1;
        for(DictDataBean dict : dictDataList) {
            List<MusicBean> musicList = commonMapper.queryMusicList(dict.getDictValue());
            for(MusicBean music : musicList){
                music.setImg(LylBlogConfig.getBasePath() + "musicfile/" + music.getImg());
                music.setSrc(LylBlogConfig.getBasePath() + "musicfile/" + music.getSrc());
            }
            musicMap.put(i, musicList);
            i++;
        }
        return EntityToArrayUtil.toArray(musicMap);
    }

    /**
     * 导航栏初始化
     * @return
     */
    public ResultObj queryMeunInfo(){
        List<MenuBean> menuList = commonMapper.queryMeunInfo("2",null);
        for(MenuBean menu : menuList){
            if("1".equals(menu.getIsDefault())){
                menu.setChildList(commonMapper.queryMeunInfo("1", menu.getId()));
            }
        }
        return ResultObj.ok(menuList.size(), menuList);
    }

    /**
     * 根据栏目编号获取标签信息
     * @param columnId
     * @return
     */
    public ResultObj getLabelList(String columnId){
        List<LabelBean> labelList = commonMapper.getLabelList(columnId);
        return ResultObj.ok(labelList.size(), labelList);
    }

    /**
     * 获取博客配置信息
     * @return
     */
    public BlogSetBean getBlogConfiguration(){
        return commonMapper.getBlogConfiguration();
    }
}
