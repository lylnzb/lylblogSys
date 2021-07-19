package com.lylblog.project.common.service.impl;

import com.lylblog.common.util.EntityToArrayUtil;
import com.lylblog.project.common.bean.*;
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

    /**
     * 获取所有省份
     * @return
     */
    public ResultObj getProvince(){
        List<AreaBean> provinceList = commonMapper.getProvince();
        return ResultObj.ok(provinceList.size(), provinceList);
    }

    /**
     * 通过省份行政区划编码获取城市
     * @param code
     * @return
     */
    public ResultObj getCityByProvinceCode(String code){
        List<AreaBean> cityList = commonMapper.getCityByProvinceCode(code);
        return ResultObj.ok(cityList.size(), cityList);
    }

    /**
     * 通过城市行政区划编码获取地区
     * @param code
     * @return
     */
    public ResultObj getAreaByCityCode(String code){
        List<AreaBean> areaList = commonMapper.getAreaByCityCode(code);
        return ResultObj.ok(areaList.size(), areaList);
    }
}
