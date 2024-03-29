package com.lylblog.project.webSite.comment.service.impl;

import com.lylblog.common.exception.file.FileNameLengthLimitExceededException;
import com.lylblog.common.util.file.FileUploadUtil;
import com.lylblog.common.util.file.FileUtil;
import com.lylblog.common.util.shiro.ShiroUtils;
import com.lylblog.framework.config.LylBlogConfig;
import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.common.service.CommonService;
import com.lylblog.project.login.bean.UserLoginBean;
import com.lylblog.project.system.article.bean.ArticleBean;
import com.lylblog.project.system.article.mapper.ArticleMapper;
import com.lylblog.project.system.comment.bean.CommentBean;
import com.lylblog.project.system.comment.mapper.CommentMapper;
import com.lylblog.project.webSite.comment.bean.WebCommentBean;
import com.lylblog.project.webSite.comment.bean.WebGreatBean;
import com.lylblog.project.webSite.comment.service.WebCommentService;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: lyl
 * @Date: 2021/4/19 16:52
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WebCommentServiceImpl implements WebCommentService {

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private ArticleMapper articleMapper;

    @Autowired
    private CommonService commonService;

    /**
     * 评论发布
     * @param commentBean
     * @return
     */
    public ResultObj addComment(CommentBean commentBean, MultipartFile file){
        if(null == commentBean.getCommentContent() || "".equals(commentBean.getCommentContent())) {
            return ResultObj.fail("评论内容不能为空");
        }

        //获取当前用户信息
        UserLoginBean userBean = ShiroUtils.getUserInfo();
        commentBean.setSubmitPerson(userBean.getYhnm());//提交人用户编码
        commentBean.setCommentType("1");//评论类型（1.文章评论、2.留言反馈）

        if(null != file){
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            try {
                String fileurl = FileUploadUtil.upload(LylBlogConfig.getArticlefile(), file, "commentImg/" + commentBean.getWznm(), suffix);
                commentBean.setImgPath(fileurl);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        int num = articleMapper.getCommentCountByWznm(commentBean.getWznm());
        articleMapper.updateCommentCountByWznm(num + 1 , commentBean.getWznm());

        int count = commentMapper.addComment(commentBean);
        if(count > 0){
            try {
                commonService.aspectDynamicInfo(commentBean, (null == commentBean.getReplyId() || "".equals(commentBean.getReplyId())?1:2));
            }catch (Exception e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
            return ResultObj.ok("评论成功");
        }else {
            return ResultObj.fail("评论失败");
        }
    }

    /**
     * 网站文章评论列表
     * @param commentBean
     * @return
     */
    public ResultObj commentList(CommentBean commentBean){
        UserLoginBean userBean = ShiroUtils.getUserInfo();
        if(userBean != null){
            commentBean.setYhnm(userBean.getYhnm());
        }else{
            commentBean.setYhnm("");
        }

        commentBean.setCommentType("1");
        int count = commentMapper.mainCommentListCount(commentBean);
        if(count > 0){
            List<WebCommentBean> webCommentList = commentMapper.mainCommentList(commentBean);
            for(WebCommentBean webComment : webCommentList){
                // 创建一个空集合
                List<WebCommentBean> fatherChildren = new ArrayList<WebCommentBean>();
                // 递归处理子级的回复，即回复内有回复
                findChildren(commentBean.getWznm(), webComment, fatherChildren);
                // 将递归处理后的集合放回父级的孩子中
                webComment.setChildCommentList(fatherChildren);
            }
            return ResultObj.ok(count, webCommentList);
        }
        return ResultObj.fail();
    }

    public void findChildren(String wznm, WebCommentBean parent, List<WebCommentBean> fatherChildren) {
        CommentBean commentBean = new CommentBean();
        commentBean.setReplyId(parent.getId());
        commentBean.setWznm(wznm);
        commentBean.setCommentType("1");
        // 找出直接子级
        List<WebCommentBean> comments = commentMapper.secondaryCommentList(commentBean);
        // 遍历直接子级的子级
        for (WebCommentBean comment : comments) {
            // 回复人赋值
            comment.setReplyName(parent.getCommitName());
            // 已经到了最底层的嵌套关系，将该回复放入新建立的集合
            fatherChildren.add(comment);
            // 直接递归
            findChildren(wznm, comment, fatherChildren);

            // 容易忽略的地方：将相对底层的子级放入新建立的集合之后
            // 则表示解除了嵌套关系，对应的其父级的子级应该设为空
            comment.setChildCommentList(new ArrayList<WebCommentBean>());
        }
    }

    /**
     * 通过文章内码查询总评论数量
     * @param wznm
     * @return
     */
    public int totalCommentCountByWznm(String wznm){
        return commentMapper.totalCommentCountByWznm(wznm);
    }

    /**
     * 文章点赞或取消赞
     * @param webGreatBean
     * @param isGiveLike
     * @return
     */
    public ResultObj addGreatInfo(WebGreatBean webGreatBean, String isGiveLike){
        //获取当前用户信息
        UserLoginBean userBean = ShiroUtils.getUserInfo();
        webGreatBean.setYhnm(userBean.getYhnm());

        String count = commentMapper.getCommentCountByCommentId(webGreatBean.getTypeId());
        if("true".equals(isGiveLike)){
            commentMapper.updateCommentCountByCommentId(Integer.valueOf(count) + 1 , webGreatBean.getTypeId());
            webGreatBean.setStatus("1");
        }else {
            commentMapper.updateCommentCountByCommentId(Integer.valueOf(count) - 1 , webGreatBean.getTypeId());
            webGreatBean.setStatus("0");
        }

        int isThere = commentMapper.isThereGreat(webGreatBean.getType(), webGreatBean.getTypeId(), webGreatBean.getYhnm());
        int num = 0;
        int type;

        CommentBean comment = commentMapper.getCommentByReplyId(webGreatBean.getTypeId());
        if(isThere > 0){
            if("1".equals(comment.getCommentType())){
                type = 4;
            }else {
                type = 8;
            }
            num = commentMapper.updateGreatInfo(webGreatBean);
        }else {
            if("1".equals(comment.getCommentType())){
                type = 3;
            }else {
                type = 7;
            }
            num = commentMapper.addGreatInfo(webGreatBean);
        }
        if(num == 1){
            try {
                commonService.aspectDynamicInfo(webGreatBean, type);
            }catch (Exception e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
            return ResultObj.ok("点赞成功");
        }else{
            return ResultObj.fail("点赞失败");
        }
    }
}
