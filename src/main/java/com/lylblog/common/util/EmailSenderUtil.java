package com.lylblog.common.util;

import com.lylblog.project.system.blogSet.bean.BlogSetBean;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @Author: lyl
 * @Date: 2021/1/21 15:11
 */
@Component
public class EmailSenderUtil {

    //配置邮箱
    public JavaMailSenderImpl getJavaMailSenderImpl(BlogSetBean blogSet){

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();//直接生产一个实例
        //smtp服务主机  qq邮箱则为smtp.qq.com,163邮箱则为smtp.163.com
        mailSender.setHost(blogSet.getEamilsetHost());
        //发送者邮箱
        mailSender.setUsername(blogSet.getEamilsetUsername());
        //授权码
        mailSender.setPassword(blogSet.getEamilsetPassword());
        //服务协议
        mailSender.setProtocol(blogSet.getEamilsetProtocol());
        //编码集
        mailSender.setDefaultEncoding(blogSet.getEamilsetDefaultEncoding());

        /*配置*/
        Properties javaMailProperties = new Properties();
        javaMailProperties.setProperty("mail.smtp.timeout", "25000");
        javaMailProperties.setProperty("mail.smtp.auth", "true");
        javaMailProperties.setProperty("mail.smtp.starttls.enable", "true");
        javaMailProperties.setProperty("mail.smtp.starttls.required", "true");
        mailSender.setJavaMailProperties(javaMailProperties);
        return mailSender;
    }
}
