package com.lylblog.common.api.QQ.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: lyl
 * @Date: 2021/6/16 10:03
 */
@Component//注入到spring容器，方便后面使用
@ConfigurationProperties(prefix = "oauth")//对应application.yml中，oauth下参数
public class OAuthProperties {

    //获取applicaiton.yml下qq下所有的参数
    private QQProperties qq = new QQProperties();

    public QQProperties getQq() {
        return qq;
    }

    public void setQq(QQProperties qq) {
        this.qq = qq;
    }
}
