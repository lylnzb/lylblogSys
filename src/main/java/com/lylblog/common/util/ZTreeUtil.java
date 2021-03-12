package com.lylblog.common.util;

import com.lylblog.project.common.bean.ZTreeBean;

import java.util.ArrayList;
import java.util.List;

public class ZTreeUtil {
    /**
     * 获取所有分类
     * @return
     */
    public static List<ZTreeBean> queryCatList(List<ZTreeBean> data){
        //定义新的list
        List<ZTreeBean> zTreeList = new ArrayList<ZTreeBean>();
        //先找到所有的一级分类
        for(ZTreeBean zTree : data){
            // 一级菜单的parentId是0
            if(zTree.getParentId() == 0){
                String permName = "";
                if("0".equals(zTree.getPermType())){
                    permName = "目录";
                }else if("1".equals(zTree.getPermType())){
                    permName = "菜单";
                }else if("2".equals(zTree.getPermType())){
                    permName = "按钮";
                }
                zTree.setPermName(zTree.getName());
                zTree.setName(zTree.getName() + " [" + permName + "]");
                zTreeList.add(zTree);
            }
        }
        // 为一级菜单设置子菜单，getChild是递归调用的
        for(ZTreeBean zTree : zTreeList){
            zTree.setChildren(getChilde(zTree.getId(), data));
        }
        return zTreeList;
    }

    /**
     * 递归查找子菜单
     *
     * @param id 当前菜单id
     * @param rootList 要查找的列表
     * @return
     */
    private static List<ZTreeBean> getChilde(Integer id, List<ZTreeBean> rootList){
        //子菜单
        List<ZTreeBean> childList = new ArrayList<>();
        for(ZTreeBean zTree : rootList){
            // 遍历所有节点，将父菜单id与传过来的id比较
            if(zTree.getParentId()== id){
                String permName = "";
                if("0".equals(zTree.getPermType())){
                    permName = "目录";
                }else if("1".equals(zTree.getPermType())){
                    permName = "菜单";
                }else if("2".equals(zTree.getPermType())){
                    permName = "按钮";
                }
                zTree.setPermName(zTree.getName());
                zTree.setName(zTree.getName() + " [" + permName + "]");
                childList.add(zTree);
            }
        }
        // 把子菜单的子菜单再循环一遍
        for(ZTreeBean zTree : childList){
            zTree.setChildren(getChilde(zTree.getId(), rootList));
        }
        // 递归退出条件
        if (childList.size() == 0){
            return null;
        }
        return childList;
    }
}
