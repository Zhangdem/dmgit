package com.dm.DGCat.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class RabbitPublisherConfiguration implements RabbitTemplate.ConfirmCallback {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    //由于rabbitTemplate的scope属性设置为ConfigurableBeanFactory.SCOPE_PROTOTYPE，所以不能自动注入
    private static RabbitTemplate rabbitTemplate;
    /**     * 构造方法注入rabbitTemplate     */
    @Autowired
    public RabbitPublisherConfiguration(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        //rabbitTemplate如果为单例的话，那回调就是最后confirm内容
        //rabbitTemplate.setConfirmCallback(this);
    }

    public void sendMsg(Map<String,Object> content) {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        //把消息放入ROUTINGKEY_A对应的队列当中去，对应的是队列A Direact 全匹配
        rabbitTemplate.convertAndSend(RabbitPropertiesConfiguration.EXCHANGE_A, RabbitPropertiesConfiguration.ROUTINGKEY_A, content, correlationId);
        //Topic 模糊匹配
        //发布成功
        //rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_B, "test1.test2.test3", "1", correlationId);
        //发布失败
        //rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_B, "test3", "3", correlationId);
        //Fanon 无匹配条件
        //rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_C, "1", "11", correlationId);
        //rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_C, "2", "12", correlationId);
        //rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_C, "3", "13", correlationId);
        //rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_C, "4", "14", correlationId);
        //headers 头条件
    }

    /**
     * 回调
     **/
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        logger.info(" 回调id:" + correlationData);
        if (ack) {
            logger.info("消息成功消费");
        } else {
            logger.info("消息消费失败:" + cause);
        }
    }
}