package com.dm.DGCat.config;
 
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
 
 
@Component
public class SpringContextUtil implements ApplicationContextAware {

    private SpringContextUtil() {

    }

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext){
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return SpringContextUtil.applicationContext;
    }

    public static Object getBean(String beanName) {
        return SpringContextUtil.applicationContext.getBean(beanName);
    }
 
}