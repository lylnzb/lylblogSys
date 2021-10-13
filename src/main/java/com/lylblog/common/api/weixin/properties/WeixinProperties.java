package com.lylblog.common.api.weixin.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: lyl
 * @Date: 2021/10/8 17:20
 */
@Component//注入到spring容器，方便后面使用
@ConfigurationProperties(prefix = "oauth.weixin")//对应application.yml中，oauth下参数
@Data
public class WeixinProperties {

    /** toKen */
    private String token;

    private String encodingAesKey;

    private String appid;
}
