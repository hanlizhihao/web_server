package com.hlz;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.amqp.core.FanoutExchange;
@SpringBootApplication
@EnableCaching
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
//rabbitMQ的主题
    @Bean
    public FanoutExchange topicAdd(){
        return new FanoutExchange("indent");
    }
}
