package com.lylblog.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @Author: lyl
 * @Date: 2021/2/23 9:33
 */
@Configuration
public class WebSocketConfig {

    /**
     * 开启WebSocket支持
     *
     *  ServerEndpointExporter 作用
     *
     *  这个Bean会自动注册使用@ServerEndpoint注解声明的websocket endpoint
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
