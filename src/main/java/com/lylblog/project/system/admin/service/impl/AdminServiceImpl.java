package com.lylblog.project.system.admin.service.impl;

import com.lylblog.common.util.*;
import com.lylblog.common.util.shiro.ShiroUtils;
import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.common.bean.ZTreeBean;
import com.lylblog.project.login.mapper.LoginMapper;
import com.lylblog.project.system.admin.bean.*;
import com.lylblog.project.login.bean.*;
import com.lylblog.project.system.admin.mapper.AdminMapper;
import com.lylblog.project.system.admin.service.AdminService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lyl
 * @Date: 2021/2/8 10:46
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminMapper adminMapper;

    @Resource
    private LoginMapper loginMapper;

    /**
     * 查询所有用户信息
     * @param userBean
     * @return
     */
    @Override
    public ResultObj queryUserList(UserBean userBean) throws Exception{
        List<UserBean> userList = adminMapper.queryUserList(userBean);
        int count = adminMapper.queryUserListCount(userBean);
        return ResultObj.ok(count,userList);
    }

    /**
     * 新增或修改用户信息
     * @param userBean
     * @return
     */
    public ResultObj addOrEditUserInfo(UserBean userBean, String type) throws Exception{
        int result = 0;
        if("add".equals(type)){
            //判断用户是否已存在
            UserLoginBean user = loginMapper.findUserByEmail(userBean.getEmail());
            if(user != null){
                return ResultObj.fail(2, "该用户已注册");//该用户已注册
            }
            if("y0n613Q8aMU9qQ0c".equals(userBean.getRoleId())){
                return ResultObj.fail(3, "不能新增角色为'博主'的用户");
            }

            //密码加密
            Map<String, String> map = EncryptionUtil.MD5Pwd(userBean.getEmail(),userBean.getPassword());
            if(null == userBean.getYhnm() || "".equals(userBean.getYhnm())){
                userBean.setYhnm(IdUtil.getUUID());
            }
            userBean.setPassword(map.get("password"));//密码
            userBean.setSalt(map.get("salt"));//盐
            result = adminMapper.addUserInfo(userBean);
        }else{
            userBean.setPassword("");//密码不允许修改
            result = adminMapper.updateUserInfo(userBean);
        }

        if(result > 0){
            if("update".equals(type)){
                //删除角色与用户关联表数据
                loginMapper.deleteUserAndRoleRelevant(userBean.getYhnm());
            }
            UserLoginBean user = new UserLoginBean();
            user.setYhnm(userBean.getYhnm());//用户唯一内码
            user.setRoleId(userBean.getRoleId());
            //新增角色与用户关联表数据
            loginMapper.addUserAndRoleRelevant(user);
            return ResultObj.ok();
        }else{
            return ResultObj.fail();
        }
    }

    /**
     * 停用账号
     * @param userIds
     * @return
     */
    public ResultObj disableUserInfo(List<String> userIds){
        //删除用户与角色关联
        //loginMapper.deleteUserAndRoleRelevant(userId);

        int flag = adminMapper.disableUserInfo(userIds);
        if(flag > 0){
            return ResultObj.ok();
        }else{
            return ResultObj.fail();
        }
    }

    /**
     * 上传头像
     * @param userIcon
     * @return
     */
    @Transactional
    public ResultObj uploadIcon(UserIconBean userIcon){
        int falg = adminMapper.isUserIcon(userIcon.getYhnm());
        if(falg > 0){
            int count = adminMapper.updateUserIconStatus(userIcon.getYhnm(),"0");
            if(count == 0){
                return ResultObj.fail(1, "头像上传失败");
            }
        }
        String yhnm = IdUtil.getUUID();
        if(null == userIcon.getYhnm() || "".equals(userIcon.getYhnm())){
            userIcon.setYhnm(yhnm);
        }
        int count = adminMapper.insertUserIcon(userIcon);
        if(count >= 1){
            return ResultObj.ok(new StringBuffer(yhnm + "_"));
        }else{
            return ResultObj.fail();
        }
    }

    /**
     * 密码重置
     * @param emails
     * @return
     */
    public ResultObj resetPassword(List<String> emails){
        int count = 0;
        for(String email : emails){
            Map<String, Object> userMap = new HashMap<String, Object>();
            //密码加密
            Map<String, String> map = EncryptionUtil.MD5Pwd(email, "12345678");
            userMap.put("password", map.get("password"));//密码
            userMap.put("salt", map.get("salt"));//盐
            userMap.put("email", email);//邮箱

            count += adminMapper.resetPassword(userMap);
        }

        if(count > 0){
            return ResultObj.ok("重置成功");
        }
        return ResultObj.fail("重置失败");
    }

    /**
     * 查询所有角色信息
     * @param roleBean
     * @return
     * @throws Exception
     */
    @Override
    public ResultObj queryRoleList(RoleBean roleBean) throws Exception {
        List<RoleBean> roleList = adminMapper.queryRoleList(roleBean);
        int count = adminMapper.queryRoleListCount(roleBean);
        for(RoleBean role : roleList){
            role.setTreeList(adminMapper.queryPermsByRoleId(role.getRoleid()));
        }
        return ResultObj.ok(count,roleList);
    }

    /**
     * 新增角色信息
     * @param roleBean
     * @return
     * @throws Exception
     */
    @Override
    public ResultObj addRoleInfo(RoleBean roleBean, String type) throws Exception{
        //获取当前用户信息
        UserLoginBean userBean = ShiroUtils.getUserInfo();
        int count = 0;
        if("add".equals(type)){//新增
            roleBean.setCreateperson(userBean.getYhnm());//创建人id
            roleBean.setCreatetime(DateUtil.getCurrentDatetime());
            count = adminMapper.addRoleInfo(roleBean);
        }else if("update".equals(type)){//修改
            roleBean.setModifyperson(userBean.getYhnm());//修改人id
            roleBean.setModifytime(DateUtil.getCurrentDatetime());//修改时间
            count = adminMapper.updateRoleInfo(roleBean);
        }
        if(count == 0){
            if("add".equals(type)){
                return ResultObj.fail("角色新增失败");
            }else if("update".equals(type)){
                return ResultObj.fail("角色修改失败");
            }
        }else{
            //根据权限id查询所有权限信息
            List<RoleAndPermBean> roleAndPermBeans = adminMapper.queryPermByRole(roleBean.getRoleid());
            for(String permId : roleBean.getTreeList()){
                boolean falg = true;
                for(RoleAndPermBean roleAndPerm : roleAndPermBeans){
                    //如果存在该权限，则跳出本次循环
                    if(permId.equals(roleAndPerm.getPermid())){
                        falg = false;
                        break;
                    }
                }
                if(falg){
                    RoleAndPermBean roleAndPermBean = new RoleAndPermBean();
                    roleAndPermBean.setId(IdUtil.getUUID());//主键
                    roleAndPermBean.setRoleid(roleBean.getRoleid());//角色id
                    roleAndPermBean.setPermid(permId);//权限id
                    roleAndPermBean.setCreateperson(userBean.getYhnm());//创建人id
                    roleAndPermBean.setCreatetime(DateUtil.getCurrentDatetime());//创建时间
                    adminMapper.addRoleAuthorizationRelations(roleAndPermBean);
                }
            }

            /**********************删除未选中的角色权限关系 start***************************/
            List<String> deleteList = new ArrayList<String>();
            for(RoleAndPermBean roleAndPerm : roleAndPermBeans){
                deleteList.add(roleAndPerm.getPermid());
            }
            deleteList.removeAll(roleBean.getTreeList());
            if(!deleteList.isEmpty()){
                adminMapper.deleteRoleAuthorizationRelations(roleBean.getRoleid(),deleteList);
            }
            /**********************删除未选中的角色权限关系 end*****************************/
            if("add".equals(type)){
                return ResultObj.ok("角色新增成功");
            }else if("update".equals(type)){
                return ResultObj.ok("角色修改成功");
            }
        }
        return ResultObj.fail("小老弟，你type传错值了。");
    }

    /**
     * 删除角色信息
     * @param deleteIds
     * @return
     */
    public ResultObj deleteRoleInfo(List<String> deleteIds){
        int count = adminMapper.deleteRoleInfo(deleteIds);
        if(count > 0){
            return ResultObj.ok("角色删除成功");
        }else{
            return ResultObj.fail("角色删除失败");
        }
    }

    /**
     * 查询所有权限信息
     * @param permissionBean
     * @return
     * @throws Exception
     */
    @Override
    public ResultObj queryPermInfoToTree(PermissionBean permissionBean) throws Exception{
        List<ZTreeBean> data = adminMapper.queryPermInfoToTree(permissionBean);
        List<ZTreeBean> zTreeList = ZTreeUtil.queryCatList(data);
        return ResultObj.ok(0 ,zTreeList);
    }

    /**
     * 通过条件查询权限详情
     * @param permissionBean
     * @return
     */
    @Override
    public ResultObj qeuryPermInfoByConditions(PermissionBean permissionBean) throws Exception{
        List<PermissionBean> data = adminMapper.qeuryPermInfoByConditions(permissionBean);
        return ResultObj.ok(data.size() ,data);
    }

    /**
     * 新增权限信息
     * @param permissionBean
     * @return
     * @throws Exception
     */
    @Override
    public ResultObj addPermInfo(PermissionBean permissionBean,String type) throws Exception{
        //获取当前用户信息
        UserLoginBean userBean = ShiroUtils.getUserInfo();

        //权限类型为按钮,并且父级权限ID为空，则给出提示
        if("2".equals(permissionBean.getPermType())
                && (null == permissionBean.getParentId() || "".equals(null == permissionBean.getParentId()))){
            return ResultObj.fail("请选择上级菜单！");
        }
        //权限类型为菜单,并且父级权限ID为空，则给出提示
        if("1".equals(permissionBean.getPermType())
                && (null == permissionBean.getParentId() || "".equals(null == permissionBean.getParentId()))){
            return ResultObj.fail("请选择上级目录！");
        }
        //权限类型为目录,或者父级权限ID为空,默认父级权限ID为0
        if("0".equals(permissionBean.getPermType())
                && (null == permissionBean.getParentId() || "".equals(null == permissionBean.getParentId()))){
            permissionBean.setParentId("1");//1代表第一级
            permissionBean.setPermUrl("*");
        }
        if("add".equals(type)){
            permissionBean.setCreateperson(userBean.getYhnm());//创建人id
            permissionBean.setCreatetime(DateUtil.getCurrentDatetime());//创建时间
            int count = adminMapper.addPermInfo(permissionBean);
            if(count == 0){
                return ResultObj.fail("权限新增失败");
            }else{
                return ResultObj.ok("权限新增成功");
            }
        }else if("update".equals(type)){
            permissionBean.setModifyperson(userBean.getYhnm());//创建人用户内码
            permissionBean.setModifytime(DateUtil.getCurrentDatetime());//创建时间
            int count = adminMapper.updatePermInfo(permissionBean);
            if(count == 0){
                return ResultObj.fail("权限修改失败");
            }else{
                return ResultObj.ok("权限修改成功");
            }
        }else{
            return ResultObj.fail("请选择类型！");
        }
    }

    /**
     * 取消或者恢复权限功能
     * @param permIds
     * @param valid
     * @return
     * @throws Exception
     */
    public ResultObj cancelOrRestorePermInfo(List<Map<String,Object>> permIds, String valid) throws Exception{
        int count = adminMapper.cancelOrRestorePermInfo(permIds, valid);
        if(count == 0){
            return ResultObj.fail();
        }else{
            return ResultObj.ok();
        }
    }

    /**
     * 查询菜单数据结构
     * @return
     */
    public ResultObj queryMenuInfo() throws Exception{
        //获取当前用户信息
        UserLoginBean userBean = ShiroUtils.getUserInfo();
        List<MenuBean> menuList = adminMapper.queryMenuInfo(userBean.getYhnm());
        return ResultObj.ok(menuList.size(), menuList);
    }
}
