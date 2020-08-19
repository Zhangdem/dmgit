package com.dm.DGCat.controller;

import com.dm.DGCat.model.ScrapEntity0;
import com.dm.DGCat.service.ScrapService;
import com.dm.DGCat.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpSession;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/webClient")
public class ScrapController {

    @Autowired
    ScrapService scrapService;

    private WebClient webClient = WebClient.builder()
            .baseUrl("https://time.geekbang.org")
            .defaultHeader(HttpHeaders.CONTENT_TYPE,"application/x-www-form-urlencoded")
            .defaultHeader(HttpHeaders.USER_AGENT,"Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:75.0) Gecko/20100101 Firefox/75.0")
            .build();

    @PostMapping(value="/getGeekList") //451 法律不允许
    public Object getScrap(String searchStr,HttpSession session){
        MultiValueMap<String,String> formData = new LinkedMultiValueMap<>(3);
        formData.add("keyword", searchStr);
        formData.add("prev", "1");
        formData.add("size", "20");
        Flux<Object> response =
                webClient.post().uri("/serv/v3/search")
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToFlux(Object.class)
                .timeout(Duration.of(10, ChronoUnit.SECONDS));

        Object webClientGeek = Mono.from(response).block();
        System.out.println(webClientGeek);
        return 1;
    }

    //简单JSoup爬虫
    @PostMapping(value="/getCnblogsList")
    public Result scrap(int seekBranck,int pageNum,HttpSession session){
        //这个就是博客中的java反射的url 默认eclipse
        if(pageNum<1){
            return Result.fail("outindexstart");
        }
        String url=  "https://www.oschina.net/project/widgets/_project_list?company=0&tag=148&lang=0&os=0&sort=time&recommend=false&cn=false&weekly=false&p="+String.valueOf(pageNum)+"&type=ajax";
        String branck="Eclipse";
        switch(seekBranck){
            case 0: //Eclipse
                url=  "https://www.oschina.net/project/widgets/_project_list?company=0&tag=148&lang=0&os=0&sort=time&recommend=false&cn=false&weekly=false&p="+String.valueOf(pageNum)+"&type=ajax";
                branck="Eclipse";
                break;
            case 1: //Idea
                url=  "https://www.oschina.net/project/widgets/_project_list?company=0&tag=260&lang=0&os=0&sort=time&recommend=false&cn=false&weekly=false&p="+String.valueOf(pageNum)+"&type=ajax";
                branck="Idea";
                break;
            case 2: //VsCode
                url=  "https://www.oschina.net/project/widgets/_project_list?company=0&tag=480&lang=0&os=0&sort=time&recommend=false&cn=false&weekly=false&p="+String.valueOf(pageNum)+"&type=ajax";
                branck="VsCode";
                break;
            default:
                break;
        }
        try{
            List<ScrapEntity0> scrapEntity0List =scrapService.getPlugins(url);
            if(scrapEntity0List.size()==0){
                return Result.fail("outindexend");
            }else{
                Map<String,List<ScrapEntity0>> rsl =new HashMap<>();
                rsl.put(branck,scrapEntity0List);
                return Result.success(rsl);
            }
        }catch (Exception e){
            return Result.fail("获取信息失败");
        }
    }
}
