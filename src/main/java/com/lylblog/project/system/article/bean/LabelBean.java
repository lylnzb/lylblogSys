package com.lylblog.project.system.article.bean;

import com.lylblog.project.common.bean.ParaBean;

/**
 * @Author: lyl
 * @Date: 2020/12/13 14:02
 */
public class LabelBean extends ParaBean {
    private String labelId;     // '标签编号'
    private String labelName;   // '标签名称'
    private String columnId;    // '所属栏目编号'
    private String labelRemarks;// '标签寄语'
    private String description; // '标签描述'
    private String valid;       // '是否可用[1:可用,0:不可用]'

    private String columnName;  // '栏目名称'
    private String isAvailable; // '是否可用'

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getLabelRemarks() {
        return labelRemarks;
    }

    public void setLabelRemarks(String labelRemarks) {
        this.labelRemarks = labelRemarks;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(String isAvailable) {
        this.isAvailable = isAvailable;
    }
}
