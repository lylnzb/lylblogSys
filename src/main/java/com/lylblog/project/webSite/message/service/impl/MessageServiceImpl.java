package com.lylblog.project.webSite.message.service.impl;

import com.lylblog.common.util.file.FileUploadUtil;
import com.lylblog.common.util.shiro.ShiroUtils;
import com.lylblog.framework.config.LylBlogConfig;
import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.common.service.CommonService;
import com.lylblog.project.login.bean.UserLoginBean;
import com.lylblog.project.system.comment.bean.CommentBean;
import com.lylblog.project.system.comment.mapper.CommentMapper;
import com.lylblog.project.webSite.comment.bean.WebCommentBean;
import com.lylblog.project.webSite.message.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: lyl
 * @Date: 2021/2/8 15:18
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    private CommentMapper commentMapper;

    @Autowired
    private CommonService commonService;

    /**
     * 留言发布
     * @param commentBean
     * @return
     */
    public ResultObj addMessage(CommentBean commentBean, MultipartFile file){
        if(null == commentBean.getCommentContent() || "".equals(commentBean.getCommentContent())) {
            return ResultObj.fail("留言内容不能为空");
        }

        //获取当前用户信息
        UserLoginBean userBean = ShiroUtils.getUserInfo();
        commentBean.setSubmitPerson(userBean.getYhnm());//提交人用户编码
        commentBean.setCommentType("2");//评论类型（1.文章评论、2.留言反馈）

        if(null != file){
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            try {
                String fileurl = FileUploadUtil.upload(LylBlogConfig.getArticlefile(), file, "commentImg/" + commentBean.getWznm(), suffix);
                commentBean.setImgPath(fileurl);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        int count = commentMapper.addComment(commentBean);
        if(count > 0){
            try {
                commonService.aspectDynamicInfo(commentBean, (null == commentBean.getReplyId() || "".equals(commentBean.getReplyId())?5:6));
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
     * 留言反馈列表
     * @param commentBean
     * @return
     */
    public ResultObj messageList(CommentBean commentBean){
        UserLoginBean userBean = ShiroUtils.getUserInfo();
        if(userBean != null){
            commentBean.setYhnm(userBean.getYhnm());
        }else{
            commentBean.setYhnm("");
        }

        commentBean.setCommentType("2");//评论类型（1.文章评论、2.留言反馈）
        int count = commentMapper.mainCommentListCount(commentBean);
        if(count > 0){
            List<WebCommentBean> webCommentList = commentMapper.mainCommentList(commentBean);
            for(WebCommentBean webComment : webCommentList){
                // 创建一个空集合
                List<WebCommentBean> fatherChildren = new ArrayList<WebCommentBean>();
                // 递归处理子级的回复，即回复内有回复
                findChildren(webComment, fatherChildren);
                // 将递归处理后的集合放回父级的孩子中
                webComment.setChildCommentList(fatherChildren);
            }
            return ResultObj.ok(count, webCommentList);
        }
        return ResultObj.fail();
    }

    /**
     * 遍历子集
     * @param parent
     * @param fatherChildren
     */
    public void findChildren(WebCommentBean parent, List<WebCommentBean> fatherChildren) {
        CommentBean commentBean = new CommentBean();
        commentBean.setReplyId(parent.getId());
        commentBean.setCommentType("2");//评论类型（1.文章评论、2.留言反馈）
        // 找出直接子级
        List<WebCommentBean> comments = commentMapper.secondaryCommentList(commentBean);
        // 遍历直接子级的子级
        for (WebCommentBean comment : comments) {
            // 回复人赋值
            comment.setReplyName(parent.getCommitName());
            // 已经到了最底层的嵌套关系，将该回复放入新建立的集合
            fatherChildren.add(comment);
            // 直接递归
            findChildren(comment, fatherChildren);

            // 容易忽略的地方：将相对底层的子级放入新建立的集合之后
            // 则表示解除了嵌套关系，对应的其父级的子级应该设为空
            comment.setChildCommentList(new ArrayList<>());
        }
    }

    /**
     * 查询留言反馈总评论数量
     * @return
     */
    public int totalCommentCount(){
        return commentMapper.totalCommentCount();
    }
}
