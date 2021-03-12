package com.lylblog.project.system.dict.bean;

import com.lylblog.project.common.bean.BaseEntity;

/**
 * 字典类型对象 sys_dict_type
 * 
 * @author ruoyi
 */
public class DictTypeBean extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 字典主键 */
    private Long dictId;

    /** 字典名称 */
    private String dictName;

    /** 字典类型 */
    private String dictType;

    /** 状态（0正常 1停用） */
    private String valId;

    public Long getDictId()
    {
        return dictId;
    }

    public void setDictId(Long dictId)
    {
        this.dictId = dictId;
    }

    public String getDictName()
    {
        return dictName;
    }

    public void setDictName(String dictName)
    {
        this.dictName = dictName;
    }

    public String getDictType()
    {
        return dictType;
    }

    public void setDictType(String dictType)
    {
        this.dictType = dictType;
    }

    public String getValId() {
        return valId;
    }

    public void setValId(String valId) {
        this.valId = valId;
    }

}
