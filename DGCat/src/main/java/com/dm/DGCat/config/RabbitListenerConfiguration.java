package com.dm.DGCat.config;


import com.dm.DGCat.model.EmailEntity0;
import com.dm.DGCat.model.SiteUser;
import com.dm.DGCat.service.DgcatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class RabbitListenerConfiguration {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    DgcatService dgcatService;

    @RabbitHandler
    @RabbitListener(queues={RabbitPropertiesConfiguration.QUEUE_B, RabbitPropertiesConfiguration.QUEUE_C})
    public void process(String map) {
        logger.info("接收处理队列A当中的消息： " + map);
        System.out.println(map.toString());
    }

    @RabbitHandler
    @RabbitListener(queues = RabbitPropertiesConfiguration.QUEUE_A)
    public void process(HashMap<String,Object> map) {
        //发送邮件guid  5e8dc264fc5c41e991b5708e6a8bbe21
        if(map.containsKey("messageKey")){
            if("5e8dc264fc5c41e991b5708e6a8bbe21".equals(map.get("messageKey").toString())){
                EmailEntity0 emailEntity0 = map.containsKey("messageContent")
                ?(EmailEntity0)map.get("messageContent")
                :new EmailEntity0();
                if(map.containsKey("siteUser")){
                    try{
                        dgcatService.senEmail(emailEntity0,(SiteUser)map.get("siteUser"));
                    }catch (Exception e){

                    }
                }
            }else{

            }
        }
    }
}

