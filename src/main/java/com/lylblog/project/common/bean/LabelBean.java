package com.lylblog.project.common.bean;

import lombok.Data;

/**
 * @Author: lyl
 * @Date: 2021/5/10 13:37
 */
@Data
public class LabelBean {
    private String labelId;     // '标签编号'
    private String labelName;   // '标签名称'
    private String columnId;    // '所属栏目编号'
    private String articleNum;  // '所属文章数量'
}
