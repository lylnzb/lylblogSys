package com.lylblog.project.system.link.bean;

import com.lylblog.common.util.validation.RegularExpressUtil;
import com.lylblog.project.common.bean.ParaBean;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 友情链接实体benn
 * @Author: lyl
 * @Date: 2021/3/18 10:21
 */
@Data
public class LinkBean extends ParaBean {

    private String id;
    @NotBlank(message = "标题不能为空！")
    private String title;         //	网站标题
    @NotBlank(message = "网站地址不能为空！")
    @Pattern(regexp = RegularExpressUtil.V_URL,message = "网站地址格式不正确！")
    private String url;	          //    网站地址
    private String sortOrder;	  //    排序号
    private String target;        //	是否开启浏览器新窗口
    private String intro;         //	网站简况
    @NotBlank(message = "网站类型不能为空！")
    private String type;          //	类型
    private String status;        //	是否显示(1=显示，0=屏蔽)
    private String submitPerson;  // 	提交人
    private String submitTime;    //	提交时间
    private String auditPerson;   //	审核人
    private String auditStatus;   //	审核状态
    private String auditTime;     //	审核时间
    private String auditReason;   //	审核原因

    private String typeName;
    private String targetName;
    private String statusName;
    private String submitName;
    private String auditName;
}
