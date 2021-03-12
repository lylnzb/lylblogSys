package com.lylblog.project.common.bean;

import com.lylblog.common.util.CodeUtil;

import java.io.Serializable;

/**
 * 邮箱发送实体类
 */
public class ToEmailBean implements Serializable {
    /**
     * 邮件接收方，可多人
     */
    private String tos;

    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 邮件内容
     */
    private String content;

    public String getTos() {
        return tos;
    }

    public void setTos(String tos) {
        this.tos = tos;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return "您的验证码为："+ CodeUtil.getFour()+",若非本人操作，请忽略此邮件。";
    }
}
