package com.dm.DGCat.controller;

import com.dm.DGCat.config.RabbitPublisherConfiguration;
import com.dm.DGCat.model.EmailEntity0;
import com.dm.DGCat.model.SiteUser;
import com.dm.DGCat.service.DgcatService;
import com.dm.DGCat.util.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.dm.DGCat.util.Result.success;

@RestController
@RequestMapping(value="/dgcatController")
public class DgcatController {

    @Autowired
    DgcatService dgcatService;
    @Autowired
    RabbitPublisherConfiguration rabbitPublisher;

    /**
     * 多线程测试变量
     */
    CountDownLatch cd =new CountDownLatch(10);
    Lock lock =new ReentrantLock();
    private int hhh=100;


    @PostMapping(value="/mqEmail")
    public Object mqEmail(EmailEntity0 emailEntity0,HttpSession session){
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated())
        {
            SiteUser siteUser =(SiteUser)  session.getAttribute("siteUser");
            Map<String,Object> map=new HashMap<>();
            map.put("messageKey","5e8dc264fc5c41e991b5708e6a8bbe21");
            map.put("siteUser",siteUser);
            map.put("messageContent",emailEntity0);
            rabbitPublisher.sendMsg(map);
            //发送邮件将消息放到消息队列，直接返回成功
            return success();
        }
        else
            return Result.fail("未登录");
    }
}
