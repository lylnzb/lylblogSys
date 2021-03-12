package com.lylblog.project.common.mapper;

import com.lylblog.project.common.bean.MenuBean;
import com.lylblog.project.common.bean.MusicBean;
import com.lylblog.project.system.blogSet.bean.BlogSetBean;
import com.lylblog.project.system.dict.bean.DictDataBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommonMapper {

    /**
     * 根据编码类别查询字典
     * @param dictType
     * @return
     */
    List<DictDataBean> queryCodeValue(@Param("dictType") String dictType);

    /**
     * 查询音乐列表
     * @return
     */
    List<MusicBean> queryMusicList(@Param("gedan") String gedan);

    /**
     * 导航栏初始化
     * @return
     */
    List<MenuBean> queryMeunInfo(@Param("isDefault") String isDefault, @Param("columnId") String columnId);

    /**
     * 获取博客配置信息
     * @return
     */
    BlogSetBean getBlogConfiguration();
}
