package com.lylblog.project.system.dict.bean;

import com.lylblog.project.common.bean.BaseEntity;
import lombok.Data;

/**
 * 字典数据表 sys_dict_data
 * 
 * @author ruoyi
 */
@Data
public class DictDataBean extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 字典编码 */
    private Long dictCode;

    /** 字典排序 */
    private Long dictSort;

    /** 字典样式 */
    private String dictStyle;

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

}
