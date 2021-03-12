package com.lylblog.project.system.blogSet.service.impl;

import com.lylblog.common.util.shiro.ShiroUtils;
import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.login.bean.UserLoginBean;
import com.lylblog.project.system.blogSet.bean.BlogSetBean;
import com.lylblog.project.system.blogSet.mapper.BlogSetMapper;
import com.lylblog.project.system.blogSet.service.BlogSetService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: lyl
 * @Date: 2021/2/8 11:24
 */
@Service
public class BlogSetServiceImpl implements BlogSetService {

    @Resource
    private BlogSetMapper blogSetMapper;

    /**
     * 查看博客设置
     * @return
     */
    public ResultObj viewBlogSetInfo(){
        List<BlogSetBean> blogSetList = blogSetMapper.viewBlogSetInfo();
        return ResultObj.ok(blogSetList.size(), blogSetList);
    }

    /**
     * 配置博客设置
     * @return
     */
    public ResultObj configurationBlogSetInfo(BlogSetBean blogSet){
        //获取当前用户信息
        UserLoginBean userBean = ShiroUtils.getUserInfo();

        List<BlogSetBean> blogSetList = blogSetMapper.viewBlogSetInfo();
        if(blogSetList.isEmpty()){
            blogSet.setCreateBy(userBean.getYhnm());//创建人id
            int count = blogSetMapper.addBlogSetInfo(blogSet);
            if(count == 0){
                return ResultObj.fail("配置失败");
            }else{
                return ResultObj.ok("配置成功");
            }
        }else{
            blogSet.setUpdateBy(userBean.getYhnm());//修改人id
            int count = blogSetMapper.updateBlogSetInfo(blogSet);
            if(count == 0){
                return ResultObj.fail("配置失败");
            }else{
                return ResultObj.ok("配置成功");
            }
        }
    }
}
