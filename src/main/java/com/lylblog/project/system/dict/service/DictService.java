package com.lylblog.project.system.dict.service;

import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.system.dict.bean.DictDataBean;
import com.lylblog.project.system.dict.bean.DictTypeBean;

public interface DictService {

    /**
     * 查询字典类型信息
     * @param dictTypeBean
     * @return
     */
    ResultObj queryDictTypeInfo(DictTypeBean dictTypeBean);

    /**
     * 新增或编辑字典类型信息
     * @param dictTypeBean
     * @return
     */
    ResultObj addOrEditDictTypeInfo(DictTypeBean dictTypeBean, String type);

    /**
     * 删除字典类型信息
     * @param dictTypeBean
     * @return
     */
    ResultObj deleteDictTypeInfo(DictTypeBean dictTypeBean);

    /**
     * 通过条件查询字典数据信息
     * @param dictDataBean
     * @return
     */
    ResultObj queryDictDataInfo(DictDataBean dictDataBean);

    /**
     * 新增或编辑字典数据信息
     * @param dictDataBean
     * @param type
     * @return
     */
    ResultObj addOrEditDictDataInfo(DictDataBean dictDataBean, String type);

    /**
     * 删除字典数据信息
     * @param dictDataBean
     * @return
     */
    ResultObj deleteDictDataInfo(DictDataBean dictDataBean);
}
