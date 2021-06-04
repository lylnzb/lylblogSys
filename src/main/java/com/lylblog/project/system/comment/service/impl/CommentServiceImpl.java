package com.lylblog.project.system.comment.service.impl;

import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.system.comment.bean.CommentBean;
import com.lylblog.project.system.comment.mapper.CommentMapper;
import com.lylblog.project.system.comment.service.CommentService;
import com.lylblog.project.system.webColumn.bean.WebColumnBean;
import com.lylblog.project.webSite.comment.bean.WebCommentBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: lyl
 * @Date: 2021/4/19 16:52
 */
@Service("commentService")
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    /**
     * 后台系统评论管理信息查询
     * @param commentBean
     * @return
     */
    public ResultObj queryCommentInfo(CommentBean commentBean){
        int count = commentMapper.queryCommentInfoCount(commentBean);
        if(count > 0){
            List<CommentBean> commentList = commentMapper.queryCommentInfo(commentBean);
            return ResultObj.ok(count, commentList);
        }
        return ResultObj.fail("未查询到数据！");
    }

    /**
     * 删除评论信息
     * @param deleteIds
     * @return
     */
    public ResultObj deleteCommentInfo(List<String> deleteIds) {
        int num = commentMapper.deleteCommentInfo(deleteIds);
        if(num > 0){
            return ResultObj.ok("删除成功");
        }else {
            return ResultObj.fail("删除失败");
        }
    }
}
