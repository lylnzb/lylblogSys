package com.lylblog.project.system.article.service.impl;

import com.lylblog.common.util.IdUtil;
import com.lylblog.common.util.shiro.ShiroUtils;
import com.lylblog.common.util.file.FileUtil;
import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.login.bean.UserLoginBean;
import com.lylblog.project.system.article.bean.ArticleBean;
import com.lylblog.project.system.article.bean.LabelBean;
import com.lylblog.project.system.article.mapper.ArticleMapper;
import com.lylblog.project.system.article.service.ArticleService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import com.lylblog.framework.config.*;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    /**
     * 查询文章列表信息
     * @param articleBean
     * @return
     */
    public ResultObj queryArticleInfo(ArticleBean articleBean){
        int count = articleMapper.queryArticleInfoCount(articleBean);
        if(count > 0){
            List<ArticleBean> articleList = articleMapper.queryArticleInfo(articleBean);
            for(ArticleBean article : articleList){
                article.setLabel(articleMapper.queryLabelSelect(article.getArticleLabel()));
            }
            return ResultObj.ok(count, articleList);
        }
        return ResultObj.ok();
    }

    /**
     * 新增或修改文章信息
     * @param articleBean
     * @return
     */
    public ResultObj addOrUpdaArticleInfo(ArticleBean articleBean, String type){
        //获取当前用户信息
        UserLoginBean userBean = ShiroUtils.getUserInfo();

        //文章内码
        String wznm = (null != articleBean.getWznm() && !"".equals(articleBean.getWznm()))?articleBean.getWznm(): IdUtil.getUUID();
        //文章标题码
        String articleName = IdUtil.getUUID();
        //文章绝对路径
        String filePath = LylBlogConfig.getArticlefile();
        //文章保存目录
        String fileDirectory = "";
        if(null != articleBean.getFile() && !"".equals(articleBean.getFile())){
            fileDirectory = "/blogImg/" + wznm + "/" + articleName + "." + FileUtil.checkImageBase64Format(articleBean.getFile());
            //图片保存到本地
            boolean flag = FileUtil.GenerateImage(articleBean.getFile(), filePath + fileDirectory);
            if(!flag){
                return ResultObj.fail("文件上传失败！");
            }else{
                articleBean.setWznm(wznm);
                //如果保存成功，则将文件目录保存到‘浓缩图路径’字段中
                articleBean.setThumb(fileDirectory);
            }
        }
        //新增
        if("add".equals(type)){
            articleBean.setCreateBy(userBean.getYhnm());//创建人id
            int count = articleMapper.addArticleInfo(articleBean);
            if(count == 0){
                if(null != fileDirectory && !"".equals(fileDirectory)){
                    FileUtil.deleteFile(filePath + fileDirectory);//删除文件
                }
                return ResultObj.fail("新增失败");
            }else{
                return ResultObj.ok("新增成功");
            }
        }else if("update".equals(type)){//修改
            articleBean.setUpdateBy(userBean.getYhnm());//修改人id
            int count = articleMapper.updateArticleInfo(articleBean);
            if(count == 0){
                if(null != fileDirectory && !"".equals(fileDirectory)){
                    FileUtil.deleteFile(filePath + fileDirectory);//删除文件
                }
                return ResultObj.fail("修改失败");
            }else{
                return ResultObj.ok("修改成功");
            }
        }else{
            return ResultObj.fail("请选择类型！");
        }
    }

    /**
     * 删除文章信息
     * @param deleteIds
     * @return
     */
    public ResultObj deleteArticleInfo(@Param("deleteIds") List<String> deleteIds){
        int count = articleMapper.deleteArticleInfo(deleteIds);
        if(count > 0){
            return ResultObj.ok("删除成功!");
        }else {
            return ResultObj.fail("删除失败！");
        }
    }

    /**
     * 查询标签信息
     * @param label
     * @return
     */
    public ResultObj queryLabelInfo(LabelBean label){
        List<LabelBean> labelList = articleMapper.queryLabelInfo(label);
        int labelCount = articleMapper.queryLabelInfoCount(label);
        return ResultObj.ok(labelCount, labelList);
    }

    /**
     * 新增或修改标签
     * @param label
     * @return
     */
    public ResultObj addOrUpdaLabelInfo(LabelBean label, String type){
        //获取当前用户信息
        UserLoginBean userBean = ShiroUtils.getUserInfo();
        //新增
        if("add".equals(type)){
            label.setCreateBy(userBean.getYhnm());//创建人id
            int count = articleMapper.addLabelInfo(label);
            if(count == 0){
                return ResultObj.fail("新增失败");
            }else{
                return ResultObj.ok("新增成功");
            }
        }else if("update".equals(type)){//修改
            label.setUpdateBy(userBean.getYhnm());//修改人id
            int count = articleMapper.updateLabelInfo(label);
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
     * 删除标签
     * @param deleteIds
     * @return
     */
    public ResultObj deleteLabelInfo(List<String> deleteIds){
        int count = articleMapper.deleteLabelInfo(deleteIds);
        if(count > 0){
            return ResultObj.ok("删除成功!");
        }else {
            return ResultObj.fail("删除失败！");
        }
    }

    /**
     * 设置文章是否置顶
     * @param wznm
     * @param value
     * @return
     */
    public ResultObj updateArticleToOnTop(String wznm, String value){
        int count = articleMapper.updateArticleToOnTop(wznm, value);
        if(count > 0){
            return ResultObj.ok("修改成功");
        }
        return ResultObj.fail("修改失败");
    }

    /**
     * 设置文章是否推荐
     * @param wznm
     * @param value
     * @return
     */
    public ResultObj updateArticleToIselite(String wznm, String value) {
        int count = articleMapper.updateArticleToIselite(wznm, value);
        if(count > 0){
            return ResultObj.ok("修改成功");
        }
        return ResultObj.fail("修改失败");
    }

}
