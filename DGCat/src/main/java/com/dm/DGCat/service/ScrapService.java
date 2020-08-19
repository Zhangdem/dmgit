package com.dm.DGCat.service;

import com.dm.DGCat.model.ScrapEntity0;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScrapService {
    public List<ScrapEntity0> getPlugins(String url){
        List<ScrapEntity0> scrapEntity0List=new ArrayList<>();
        try {
            //先获得的是整个页面的html标签页面
            Document doc = Jsoup.connect(url).get();
            //获取最外层标签
            Elements ejbtEls0 = doc.select("div[class=item project-item]");
            ejbtEls0=ejbtEls0.select("div[class=content]");
            for(Element el :ejbtEls0) {
                try{
                    ScrapEntity0 scrapEntity0=new ScrapEntity0();
                    Elements h3 = el.select("h3[class=header]");
                    if(h3!=null){
                        for(Element hsC :h3) {
                            //获取产品链接
                            scrapEntity0.setALink(hsC.select(".header a[href]").get(0).attributes().getIgnoreCase("href"));
                            //获取产品名称
                            scrapEntity0.setName(hsC.select(".project-name").get(0).childNode(0).toString());
                            //获取标题
                            scrapEntity0.setTitle(hsC.select(".project-title").get(0).childNode(0).toString());
                        }
                        //获取描述
                        Elements description = el.select("div[class=description]");
                        scrapEntity0.setDescription(description.select("p[class=line-clamp]").get(0).childNode(0).toString());
                    }
                    scrapEntity0List.add(scrapEntity0);
                }catch(Exception e){ }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scrapEntity0List;
    }
}
