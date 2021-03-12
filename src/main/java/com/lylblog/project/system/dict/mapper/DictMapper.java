package com.lylblog.project.system.dict.mapper;

import com.lylblog.project.system.dict.bean.DictDataBean;
import com.lylblog.project.system.dict.bean.DictTypeBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DictMapper {

    /**
     * 查询字典类型信息
     * @param dictTypeBean
     * @return
     */
    List<DictTypeBean> queryDictTypeInfo(DictTypeBean dictTypeBean);

    /**
     * 查询字典类型信息总数
     * @param dictTypeBean
     * @return
     */
    int queryDictTypeInfoCount(DictTypeBean dictTypeBean);

    /**
     * 新增字典类型信息
     * @param dictTypeBean
     * @return
     */
    int addDictTypeInfo(DictTypeBean dictTypeBean);

    /**
     * 编辑字典类型信息
     * @param dictTypeBean
     * @return
     */
    int editDictTypeInfo(DictTypeBean dictTypeBean);

    /**
     * 删除字典类型信息
     * @param dictTypeBean
     * @return
     */
    int deleteDictTypeInfo(DictTypeBean dictTypeBean);

    /**
     * 通过条件查询字典数据信息
     * @param dictDataBean
     * @return
     */
    List<DictDataBean> queryDictDataInfo(DictDataBean dictDataBean);

    /**
     * 新增字典数据信息
     * @param dictDataBean
     * @return
     */
    int addDictDataInfo(DictDataBean dictDataBean);

    /**
     * 编辑字典数据信息
     * @param dictDataBean
     * @return
     */
    int editDictDataInfo(DictDataBean dictDataBean);

    /**
     * 删除字典数据信息
     * @param dictDataBean
     * @return
     */
    int deleteDictDataInfo(DictDataBean dictDataBean);

    /**
     * 修改字典数据默认值为‘否’
     * @param dictType
     * @param dictValue
     * @return
     */
    int updateIsDefault(@Param("dictType") String dictType, @Param("dictValue") String dictValue);
}
