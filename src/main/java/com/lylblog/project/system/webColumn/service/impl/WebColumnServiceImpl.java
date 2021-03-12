package com.lylblog.project.system.webColumn.service.impl;

import com.lylblog.common.util.shiro.ShiroUtils;
import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.login.bean.UserLoginBean;
import com.lylblog.project.system.webColumn.bean.WebColumnBean;
import com.lylblog.project.system.webColumn.mapper.WebColumnMapper;
import com.lylblog.project.system.webColumn.service.WebColumnService;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: lyl
 * @Date: 2020/11/8 14:58
 */
@Service
public class WebColumnServiceImpl implements WebColumnService {

    @Resource
    private WebColumnMapper webColumnMapper;

    @Autowired
    private RedisSessionDAO redisSessionDAO;

    /**
     * 添加或编辑网站栏目信息
     * @param webColumnBean
     * @return
     */
    public ResultObj addOrUpdateWebColumnInfo(WebColumnBean webColumnBean, String type){
        //获取当前用户信息
        UserLoginBean userBean = ShiroUtils.getUserInfo();
        //新增
        if("add".equals(type)){
            webColumnBean.setCreateBy(userBean.getYhnm());//创建人id
            int count = webColumnMapper.addWebColumnInfo(webColumnBean);
            if(count == 0){
                return ResultObj.fail("新增失败");
            }else{
                return ResultObj.ok("新增成功");
            }
        }else if("update".equals(type)){//修改
            webColumnBean.setUpdateBy(userBean.getYhnm());//修改人id
            int count = webColumnMapper.updateWebColumnInfo(webColumnBean);
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
     * 查询网站栏目信息
     * @return
     */
    public ResultObj queryWebColumnInfo(){
        List<WebColumnBean> webColumnBeanList = webColumnMapper.queryWebColumnInfo();
        return ResultObj.ok(webColumnBeanList.size(), webColumnBeanList);
    }

    /**
     * 查询允许发布文章的专栏信息
     * @return
     */
    public ResultObj queryWebColumnByAllow(){
        List<WebColumnBean> webColumnBeanList = webColumnMapper.queryWebColumnByAllow();
        return ResultObj.ok(webColumnBeanList.size(), webColumnBeanList);
    }

    /**
     * 根据id查询网站栏目信息
     * @param columnId
     * @return
     */
    public ResultObj queryWebColumnInfoById(String columnId){
        return ResultObj.ok(webColumnMapper.queryWebColumnInfoById(columnId));
    }

    /**
     * 删除网站栏目信息
     * @param deleteIds
     * @return
     */
    public ResultObj deleteWebColumnInfo(List<String> deleteIds){
        int count = webColumnMapper.deleteWebColumnInfo(deleteIds);
        if(count > 0){
            return ResultObj.ok("删除成功");
        }
        return ResultObj.fail("删除失败");
    }

    /**
     * 查询所有父栏目信息
     * @return
     */
    public ResultObj queryParentColumn(){
        List<WebColumnBean> webColumnBeanList = webColumnMapper.queryParentColumn();
        return ResultObj.ok(webColumnBeanList.size(), webColumnBeanList);
    }
}
