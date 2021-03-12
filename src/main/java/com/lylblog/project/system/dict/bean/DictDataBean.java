package com.lylblog.project.system.dict.bean;

import com.lylblog.project.common.bean.BaseEntity;

/**
 * 字典数据表 sys_dict_data
 * 
 * @author ruoyi
 */
public class DictDataBean extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 字典编码 */
    private Long dictCode;

    /** 字典排序 */
    private Long dictSort;

    /** 字典标签 */
    private String dictLabel;

    /** 字典键值 */
    private String dictValue;

    /** 字典类型 */
    private String dictType;

    /** 是否默认（Y是 N否） */
    private String isDefault;

    /** 状态（0正常 1停用） */
    private String valId;

    public Long getDictCode()
    {
        return dictCode;
    }

    public void setDictCode(Long dictCode)
    {
        this.dictCode = dictCode;
    }

    public Long getDictSort()
    {
        return dictSort;
    }

    public void setDictSort(Long dictSort)
    {
        this.dictSort = dictSort;
    }

    public String getDictLabel()
    {
        return dictLabel;
    }

    public void setDictLabel(String dictLabel)
    {
        this.dictLabel = dictLabel;
    }

    public String getDictValue()
    {
        return dictValue;
    }

    public void setDictValue(String dictValue)
    {
        this.dictValue = dictValue;
    }

    public String getDictType()
    {
        return dictType;
    }

    public void setDictType(String dictType)
    {
        this.dictType = dictType;
    }

    public String getIsDefault()
    {
        return isDefault;
    }

    public void setIsDefault(String isDefault)
    {
        this.isDefault = isDefault;
    }

    public String getValId() {
        return valId;
    }

    public void setValId(String valId) {
        this.valId = valId;
    }
}
