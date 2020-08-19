package com.dm.DGCat.AppTest.Demo;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DemoBean  {

    @Bean(value = "demoEnity")
    public DemoEntity<String> DemoEntity(){
        List<String> ls=new ArrayList<>();
        ls.add("first");
        ls.add("seconds");
        ls.add("thirds");

        DemoEntity<String> demoEntity
                = DemoEntity
                .<String>builder()
                .demoList(ls)
                .build();
        return demoEntity;
    }
    //@ConditionalOnClass(DemoEntity.class)  //当classpath中存在这个类时
    @ConditionalOnBean(DemoEntity.class) //当上下文中存在这个类型的bean时
    @Bean(value = "demoEnityUse")
    public DemoEntity<String> DemoEntityUse(){
        List<String> ls=new ArrayList<>();
        ls.add("first2");
        ls.add("seconds2");
        ls.add("thirds2");

        DemoEntity<String> demoEntity
                = DemoEntity
                .<String>builder()
                .demoList(ls)
                .build();
        return demoEntity;
    }
}
