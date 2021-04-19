package com.lylblog.project.system.link.service.impl;

import com.lylblog.common.util.shiro.ShiroUtils;
import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.login.bean.UserLoginBean;
import com.lylblog.project.system.admin.mapper.AdminMapper;
import com.lylblog.project.system.link.bean.LinkBean;
import com.lylblog.project.system.link.mapper.LinkMapper;
import com.lylblog.project.system.link.service.LinkService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: lyl
 * @Date: 2021/3/17 16:44
 */
@Service
public class LinkServiceImpl implements LinkService {

    @Resource
    private LinkMapper linkMapper;

    @Resource
    private AdminMapper adminMapper;

    /**
     * 查询所有友情链接信息
     * @param linkBean
     * @return
     */
    public ResultObj queryLinkInfo(LinkBean linkBean){
        int count = linkMapper.queryLinkInfoCount(linkBean);
        if(count > 0){
            List<LinkBean> linkList = linkMapper.queryLinkInfo(linkBean);
            return ResultObj.ok(count,linkList);
        }
        return ResultObj.fail("查询不到数据");
    }

    /**
     * 查询所有友情链接信息
     * @param linkBean
     * @return
     */
    public ResultObj queryLinkShInfo(LinkBean linkBean){
        //获取当前用户信息
        UserLoginBean userBean = ShiroUtils.getUserInfo();
        linkBean.setAuditPerson(userBean.getYhnm());
        int count = linkMapper.queryLinkInfoCount(linkBean);
        if(count > 0){
            List<LinkBean> linkList = linkMapper.queryLinkInfo(linkBean);
            return ResultObj.ok(count,linkList);
        }
        return ResultObj.fail("暂无审核数据");
    }

    /**
     * 新增或修改友情链接信息
     * @param linkBean
     * @return
     */
    public ResultObj addOrUpdaLinkInfo(LinkBean linkBean, String type){
        //获取当前用户信息
        UserLoginBean userBean = ShiroUtils.getUserInfo();
        //新增
        if("add".equals(type)){
            String yhnm = adminMapper.getBloggerToYhnm();
            if(null == yhnm || "".equals(yhnm)){
                return ResultObj.fail("未查询到博主信息，请联系博主");
            }
            linkBean.setSubmitPerson(userBean.getYhnm());//创建人id
            linkBean.setAuditPerson(yhnm);//审核人（博主）
            linkBean.setAuditStatus("0");//审核状态（未审核）
            int count = linkMapper.addLinkInfo(linkBean);
            if(count == 0){
                return ResultObj.fail("新增失败");
            }else{
                return ResultObj.ok("新增成功");
            }
        }else if("update".equals(type)){//修改
            int count = linkMapper.updaLikeInfo(linkBean);
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
     * 删除友情链接信息
     * @param deleteIds
     * @return
     */
    public ResultObj deleteLikeInfo(List<String> deleteIds){
        int count = linkMapper.deleteLikeInfo(deleteIds);
        if(count > 0){
            return ResultObj.ok("删除成功");
        }else{
            return ResultObj.ok("删除失败");
        }
    }

    /**
     * 友情链接审核
     * @param linkBean
     * @return
     */
    public ResultObj auditLink(LinkBean linkBean){
        int count = linkMapper.auditLink(linkBean);
        if(count > 0){
            return ResultObj.ok("操作成功");
        }else{
            return ResultObj.ok("操作失败");
        }
    }
}
