package com.hlz;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.amqp.core.TopicExchange;
@SpringBootApplication
@EnableCaching
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
//rabbitMQ的主题
    @Bean
    public TopicExchange topicAdd(){
        return new TopicExchange("add-indent");
    }
    @Bean
    public TopicExchange topicUpdate(){
        return new TopicExchange("update-indent");
    }
    @Bean
    public TopicExchange topicStyle(){
        return new TopicExchange("style-indent");
    }
}
