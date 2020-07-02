package com.zetton.thymeleaf.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
//表示开启使用STOMP协议来传输基于代理的消息，Broker就是代理的意思
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        //注册STOMP协议的节点，并指定映射的URL
        //用来注册STOMP协议节点，同时指定使用SockJS
        stompEndpointRegistry.addEndpoint("/simple")
                .setAllowedOrigins("*") //解决跨域问题
                .withSockJS();
    }

    //配置消息代理，由于我们是实现推送功能，这里的消息代理是/topic
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
    }
}
