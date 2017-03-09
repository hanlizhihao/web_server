package com.hlz;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 *
 * @author Administrator 2017-3-9
 */
@Configuration
@EnableWebSocketMessageBroker//注解开启使用STOMP协议来传输基于代理(message broker)的消息
//此时控制器可以使用@MessageRequest
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer{

    @Override
    public void registerStompEndpoints(StompEndpointRegistry ser) {
        ser.addEndpoint("/server").withSockJS();//设置一个节点，使得客户端可以连接服务器，通过这个节点
    }
    //配置消息代理，以便在消息代理下，配置广播或者点对点代理
    public void confiurerMessageBroker(MessageBrokerRegistry registry){
        registry.enableSimpleBroker("/topic");//配置一个广播式的topic的代理,客户端将可以订阅这个代理
    }
    
}
