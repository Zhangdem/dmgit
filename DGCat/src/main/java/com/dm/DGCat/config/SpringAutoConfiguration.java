package com.dm.DGCat.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;

@EnableAutoConfiguration
public class SpringAutoConfiguration {
    @Bean //消息不指定序列化 使用java默认序列化  导致报错
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
