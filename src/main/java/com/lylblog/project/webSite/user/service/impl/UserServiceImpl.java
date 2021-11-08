package com.lylblog.project.webSite.user.service.impl;

import com.lylblog.common.util.EncryptionUtil;
import com.lylblog.common.util.StringUtil;
import com.lylblog.common.util.shiro.ShiroUtils;
import com.lylblog.common.util.validation.ValidatorUtil;
import com.lylblog.project.common.bean.AreaBean;
import com.lylblog.project.common.bean.DynamicBean;
import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.common.mapper.CommonMapper;
import com.lylblog.project.login.bean.UserAuthsBean;
import com.lylblog.project.login.bean.UserLoginBean;
import com.lylblog.project.system.comment.mapper.CommentMapper;
import com.lylblog.project.webSite.user.bean.UserCommentBean;
import com.lylblog.project.webSite.user.bean.UserLinkBean;
import com.lylblog.project.webSite.user.bean.UserLoginRecordBean;
import com.lylblog.project.webSite.user.bean.UserParamBean;
import com.lylblog.project.webSite.user.mapper.UserMapper;
import com.lylblog.project.webSite.user.service.UserService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author: lyl
 * @Date: 2021/6/7 11:34
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private CommonMapper commonMapper;

    /**
     * 查询当前登录用户的登录记录数据
     * @param userLoginRecord
     * @return
     */
    public ResultObj queryLoginRecord(UserLoginRecordBean userLoginRecord){
        //获取当前用户信息
        UserLoginBean userBean = ShiroUtils.getUserInfo();
        userLoginRecord.setYhnm(userBean.getYhnm());
        int count = userMapper.queryLoginRecordCount(userLoginRecord);
        if(count > 0){
            List<UserLoginRecordBean> userLoginRecordList = userMapper.queryLoginRecord(userLoginRecord);
            return ResultObj.ok(count, userLoginRecordList);
        }
        return ResultObj.fail("没有查询到用户登录记录信息");
    }

    /**
     * 是否已绑定邮箱
     * @return
     */
    public int isbindingEmail() {
        //获取当前用户信息
        UserLoginBean user = ShiroUtils.getUserInfo();
        int falg = userMapper.isbindingEmail(user.getYhnm());//是否已绑定邮箱
        return falg;
    }

    /**
     * 是否已设置密码
     * @return
     */
    public int isSetupPwd() {
        //获取当前用户信息
        UserLoginBean user = ShiroUtils.getUserInfo();
        int falg = userMapper.isSetupPwd(user.getYhnm());//是否已设置密码
        return falg;
    }

    /**
     * 是否绑定第三方账号
     * @return
     */
    public int isUserAuths() {
        //获取当前用户信息
        UserLoginBean user = ShiroUtils.getUserInfo();
        int falg = userMapper.isUserAuths(user.getYhnm());//是否绑定第三方账号
        return (falg >= 1?1:0);
    }

    /**
     * 修改密码
     * @param oldPwd
     * @param newPwd
     * @return
     */
    public ResultObj updatePwd(String oldPwd, String newPwd){
        UserLoginBean user = ShiroUtils.getUserInfo();
        //旧密码加密
        String oldMd5Pwd = new SimpleHash("MD5", oldPwd,
                ByteSource.Util.bytes(user.getEmail() + ((user.getSalt() == null)?"":user.getSalt())), 2).toHex();
        if(!oldMd5Pwd.equals(user.getPassword())){
            return ResultObj.fail("旧密码与当前账号密码不一致！");
        } else if(!ValidatorUtil.isPassword(newPwd)) {
            return ResultObj.fail("请输入8~20位字母和数字组合的密码！");
        }
        //新密码加密
        Map<String, String> map = EncryptionUtil.MD5Pwd(user.getEmail(),newPwd);
        int count = userMapper.updatePwd(map.get("password"), map.get("salt"), user.getEmail());
        if(count > 0){
            return ResultObj.ok("密码修改成功，请重新登录！");
        }
        return ResultObj.fail("修改密码失败！");
    }

    /**
     * 设置密码
     * @param pwd
     * @return
     */
    public ResultObj setPwd(String pwd) {
        UserLoginBean user = ShiroUtils.getUserInfo();
        if(!ValidatorUtil.isPassword(pwd)) {
            return ResultObj.fail("请输入8~20位字母和数字组合的密码！");
        }
        //新密码加密
        Map<String, String> map = EncryptionUtil.MD5Pwd(user.getEmail(),pwd);
        int count = userMapper.updatePwd(map.get("password"), map.get("salt"), user.getEmail());
        if(count > 0){
            return ResultObj.ok("密码设置成功！");
        }
        return ResultObj.fail("修改设置失败！");
    }

    /**
     * 验证密码正确性
     * @param oldPwd
     * @return
     */
    public ResultObj validationPwd(String oldPwd){
        UserLoginBean user = ShiroUtils.getUserInfo();
        //旧密码加密
        String oldMd5Pwd = new SimpleHash("MD5", oldPwd,
                ByteSource.Util.bytes(user.getEmail() + ((user.getSalt() == null)?"":user.getSalt())), 2).toHex();

        if(!oldMd5Pwd.equals(user.getPassword())){
            return ResultObj.fail("旧密码与当前账号密码不一致！");
        }
        return ResultObj.ok();
    }

    /**
     * 验证邮箱是否已注册
     * @param newEmail
     * @return
     */
    public ResultObj validationEmail(String newEmail){
        int num = userMapper.validationEmail(newEmail);
        if(num > 0){
            return ResultObj.fail("该邮箱已注册");
        }
        return ResultObj.ok();
    }

    /**
     * 绑定新邮箱
     * @param newEmail
     * @return
     */
    public ResultObj bindEmail(String newEmail) {
        UserLoginBean user = ShiroUtils.getUserInfo();
        int num = userMapper.validationEmail(newEmail);
        if(num > 0){
            return ResultObj.fail("该邮箱已注册");
        }
        int count = userMapper.bindEmail(newEmail, user.getYhnm());
        if(count > 0) {
            return ResultObj.ok("绑定成功！");
        }
        return ResultObj.fail("绑定失败！");
    }

    /**
     * 我的评论列表
     * @param comment
     * @return
     */
    public ResultObj queryMyCommentsByYhnm(UserCommentBean comment){
        //获取当前用户信息
        UserLoginBean user = ShiroUtils.getUserInfo();
        comment.setYhnm(user.getYhnm());
        int count = userMapper.queryMyCommentsByYhnmCount(comment);
        if(count > 0){
            List<UserCommentBean> commentList = userMapper.queryMyCommentsByYhnm(comment);
            return ResultObj.ok(count, commentList);
        }
        return ResultObj.fail("您还没有评论！");
    }

    /**
     * 我的友链申请列表
     * @param linkStatus
     * @return
     */
    public ResultObj queryMyLinks(String linkStatus){
        //获取当前用户信息
        UserLoginBean user = ShiroUtils.getUserInfo();
        List<UserLinkBean> linkList = userMapper.queryMyLinks(linkStatus, user.getYhnm());
        if(!linkList.isEmpty()){
            return ResultObj.ok(linkList.size(), linkList);
        }
        return ResultObj.fail("您还没有申请过友链");
    }

    /**
     * 根据编号查询友链信息
     * @param linkId
     * @return
     */
    public ResultObj queryMyLinksById(String linkId){
        if(null == linkId || "".equals(linkId)){
            return ResultObj.fail("编号不能为空");
        }
        UserLinkBean userLink = userMapper.queryMyLinksById(linkId);
        if(userLink != null){
            return ResultObj.ok(userLink);
        }
        return ResultObj.fail("查询不到数据");
    }

    /**
     * 删除我的评论
     * @param commentId
     * @return
     */
    public ResultObj delMyComment(String commentId){
        int count = userMapper.delMyMainComments(commentId);
        if(count > 0) {
            userMapper.delMyChildComments(commentId);
            return ResultObj.ok("删除成功");
        }
        return ResultObj.fail("删除失败");
    }

    /**
     * 更新个人资料
     * @param userParam
     * @return
     */
    public ResultObj updatePersonalData(UserParamBean userParam){
        UserLoginBean user = ShiroUtils.getUserInfo();
        userParam.setYhnm(user.getYhnm());
        int count = userMapper.updatePersonalData(userParam);
        if(count > 0){
            return ResultObj.ok("编辑资料成功");
        }else {
            return ResultObj.fail("编辑资料失败");
        }
    }


    /**
     * 查询个人资料详情
     * @return
     */
    public ResultObj queryPersonalData() {
        UserLoginBean user = ShiroUtils.getUserInfo();
        UserParamBean userParam = userMapper.queryPersonalData(user.getYhnm());
        if(null != userParam){
            if(null != userParam.getArea() && !"".equals(userParam.getArea())) {
                String[] areaArr = userParam.getArea().split(",");
                String area = "";
                for(int i = 0;i < areaArr.length;i++){
                    String regionCode = commonMapper.queryAreas(areaArr[i]);
                    if(null != regionCode && !"".equals(regionCode)){
                        if(i != 0){
                            area += " / ";
                        }
                        area += regionCode;
                    }
                    if(i == 0) {
                        userParam.setProvinceCode(areaArr[i]);
                    }else if(i == 1) {
                        userParam.setCityCode(areaArr[i]);
                    }else if(i == 2) {
                        userParam.setAreaCode(areaArr[i]);
                    }
                }
                userParam.setArea(area);
            }
            return ResultObj.ok(userParam);
        }else {
            return ResultObj.ok("查询不到个人资料信息");
        }
    }

    /**
     * 查询个人动态信息
     * @return
     */
    public ResultObj queryDynamicInfo(DynamicBean dynamic){
        UserLoginBean user = ShiroUtils.getUserInfo();
        dynamic.setOperYhnm(user.getYhnm());
        int count = userMapper.queryDynamicInfoCount(dynamic);
        if(count > 0){
            List<DynamicBean> list = userMapper.queryDynamicInfo(dynamic);
            return ResultObj.ok(count, list);
        }
        return ResultObj.fail("暂无个人动态信息");
    }

    /**
     * 账号注销
     * @return
     */
    public ResultObj accountCancel() {
        UserLoginBean user = ShiroUtils.getUserInfo();
        int num = 0;
        try {

        }catch (Exception e) {
            e.printStackTrace();
        }
        return ResultObj.ok();
    }

    /**
     * 查询已绑定第三方账号
     * @return
     */
    public ResultObj queryUserAuthsInfoByYhnm() {
        UserLoginBean user = ShiroUtils.getUserInfo();
        List<UserAuthsBean> authsList = userMapper.queryUserAuthsInfoByYhnm(user.getYhnm());
        return ResultObj.ok(authsList.size(), authsList);
    }

    /**
     * 解绑第三方账号
     * @param openId
     * @return
     */
    public ResultObj unbundUserAuths(String openId) {
        UserLoginBean user = ShiroUtils.getUserInfo();

        if(user == null) {
            return ResultObj.fail("你这个杀千刀的东西！");
        }
        List<UserAuthsBean> authsList = userMapper.queryUserAuthsInfoByYhnm(user.getYhnm());
        int i1 = userMapper.isbindingEmail(user.getYhnm());
        if(i1 == 0 && (!authsList.isEmpty() && authsList.size() == 1)) {
            return ResultObj.fail("这是您最后的登录方式，如果解绑了您就无法登录这个帐号，请先绑定邮箱或绑定其它社交登录方式后再尝试解绑");
        }
        int i2 = userMapper.isSetupPwd(user.getYhnm());
        if(i2 == 0 && (!authsList.isEmpty() && authsList.size() == 1)) {
            return ResultObj.fail("您还没有设置密码，请先设置密码吧！");
        }
        int count = userMapper.unbundUserAuths(openId, user.getYhnm());
        if(count > 0) {
            return ResultObj.ok("解绑成功!");
        }
        return ResultObj.fail("解绑失败!");
    }
}
