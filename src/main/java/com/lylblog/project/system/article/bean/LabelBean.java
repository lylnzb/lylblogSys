package com.lylblog.project.system.article.bean;

import com.lylblog.project.common.bean.ParaBean;
import lombok.Data;

/**
 * @Author: lyl
 * @Date: 2020/12/13 14:02
 */
@Data
public class LabelBean extends ParaBean {
    private String labelId;     // '标签编号'
    private String labelName;   // '标签名称'
    private String columnId;    // '所属栏目编号'
    private String labelRemarks;// '标签寄语'
    private String description; // '标签描述'
    private String valid;       // '是否可用[1:可用,0:不可用]'

    private String columnName;  // '栏目名称'
    private String isAvailable; // '是否可用'
}
