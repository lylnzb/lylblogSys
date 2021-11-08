package com.lylblog.common.api.weibo.properties;

import lombok.Data;

/**
 * @Author: lyl
 * @Date: 2021/6/16 10:02
 */
@Data
public class WeiboProperties {

    private String client_id;
    private String client_secret;
    private String redirect_uri;
    private String code_callback_uri;
    private String access_token_callback_uri;
    private String openid_callback_uri;
    private String user_info_callback_uri;

}
