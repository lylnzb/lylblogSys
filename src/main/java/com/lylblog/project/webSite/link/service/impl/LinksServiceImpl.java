package com.lylblog.project.webSite.link.service.impl;

import com.lylblog.common.util.EntityToArrayUtil;
import com.lylblog.common.util.shiro.ShiroUtils;
import com.lylblog.framework.config.LylBlogConfig;
import com.lylblog.project.common.bean.MusicBean;
import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.common.mapper.CommonMapper;
import com.lylblog.project.login.bean.UserLoginBean;
import com.lylblog.project.system.admin.mapper.AdminMapper;
import com.lylblog.project.system.dict.bean.DictDataBean;
import com.lylblog.project.system.link.mapper.LinkMapper;
import com.lylblog.project.webSite.link.bean.LinkBean;
import com.lylblog.project.webSite.link.mapper.LinksMapper;
import com.lylblog.project.webSite.link.service.LinksService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lyl
 * @Date: 2021/2/20 14:12
 */
@Service
public class LinksServiceImpl implements LinksService {

    @Resource
    private LinksMapper linksMapper;

    @Resource
    private CommonMapper commonMapper;

    /**
     * 查询友情链接信息
     * @return
     */
    public Map<String, Object> queryLinksInfo(){

        List<DictDataBean> dictDataList = commonMapper.queryCodeValue("sys_link_type");

        Map<String, Object> linkMap = new LinkedHashMap<String, Object>();
        for(DictDataBean dict : dictDataList) {
            List<LinkBean> linkList = linksMapper.queryLinksInfo(dict.getDictValue());
            linkMap.put(dict.getDictLabel(), linkList);
        }
        return linkMap;
    }
}
