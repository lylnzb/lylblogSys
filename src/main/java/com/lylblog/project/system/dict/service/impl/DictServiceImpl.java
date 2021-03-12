package com.lylblog.project.system.dict.service.impl;

import com.lylblog.common.util.shiro.ShiroUtils;
import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.login.bean.UserLoginBean;
import com.lylblog.project.system.dict.bean.DictDataBean;
import com.lylblog.project.system.dict.bean.DictTypeBean;
import com.lylblog.project.system.dict.mapper.DictMapper;
import com.lylblog.project.system.dict.service.DictService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DictServiceImpl implements DictService {
    @Resource
    private DictMapper dictMapper;

    /**
     * 查询字典类型信息
     * @param dictTypeBean
     * @return
     */
    public ResultObj queryDictTypeInfo(DictTypeBean dictTypeBean){
        int count = dictMapper.queryDictTypeInfoCount(dictTypeBean);
        List<DictTypeBean> dictTypeList = null;
        if(count >= 1){
            dictTypeList = dictMapper.queryDictTypeInfo(dictTypeBean);
        }

        return ResultObj.ok(count, dictTypeList);
    }

    /**
     * 新增或修改字典类型信息
     * @param dictTypeBean
     * @return
     */
    public ResultObj addOrEditDictTypeInfo(DictTypeBean dictTypeBean, String type){
        //获取当前用户信息
        UserLoginBean userBean = ShiroUtils.getUserInfo();

        if("add".equals(type)){
            dictTypeBean.setCreateBy(userBean.getYhnm());//创建人id
            int count = dictMapper.addDictTypeInfo(dictTypeBean);
            if(count == 0){
                return ResultObj.fail("新增失败");
            }else{
                return ResultObj.ok("新增成功");
            }
        }else if("update".equals(type)){
            dictTypeBean.setUpdateBy(userBean.getYhnm());//修改人id
            int count = dictMapper.editDictTypeInfo(dictTypeBean);
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
     * 删除字典类型信息
     * @param dictTypeBean
     * @return
     */
    public ResultObj deleteDictTypeInfo(DictTypeBean dictTypeBean){
        int count = dictMapper.deleteDictTypeInfo(dictTypeBean);
        if(count >= 1){
            DictDataBean dictDataBean = new DictDataBean();
            dictDataBean.setDictType(dictTypeBean.getDictType());
            //联动删除字典数据信息
            dictMapper.deleteDictDataInfo(dictDataBean);
            return ResultObj.ok("删除成功");
        }else{
            return ResultObj.ok("删除失败");
        }
    }

    /**
     * 通过条件查询字典数据信息
     * @param dictDataBean
     * @return
     */
    public ResultObj queryDictDataInfo(DictDataBean dictDataBean){
        List<DictDataBean> dictDataList = dictMapper.queryDictDataInfo(dictDataBean);
        return ResultObj.ok(dictDataList.size(), dictDataList);
    }

    /**
     * 新增或编辑字典数据信息
     * @param dictDataBean
     * @param type
     * @return
     */
    public ResultObj addOrEditDictDataInfo(DictDataBean dictDataBean, String type){
        //获取当前用户信息
        UserLoginBean userBean = ShiroUtils.getUserInfo();

        //如果系统默认新增或修改的字典为‘是’，则修改其他数据的值为‘否’
        if("Y".equals(dictDataBean.getIsDefault())){
            dictMapper.updateIsDefault(dictDataBean.getDictType(), dictDataBean.getDictValue());
        }

        //新增
        if("add".equals(type)){
            dictDataBean.setCreateBy(userBean.getYhnm());//创建人id
            int count = dictMapper.addDictDataInfo(dictDataBean);
            if(count == 0){
                return ResultObj.fail("新增失败");
            }else{
                return ResultObj.ok("新增成功");
            }
        }else if("update".equals(type)){//修改
            dictDataBean.setUpdateBy(userBean.getYhnm());//修改人id
            int count = dictMapper.editDictDataInfo(dictDataBean);
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
     * 删除字典数据信息
     * @param dictDataBean
     * @return
     */
    public ResultObj deleteDictDataInfo(DictDataBean dictDataBean){
        int count = dictMapper.deleteDictDataInfo(dictDataBean);
        if(count >= 1){
            return ResultObj.ok("删除成功");
        }else{
            return ResultObj.ok("删除失败");
        }
    }
}
