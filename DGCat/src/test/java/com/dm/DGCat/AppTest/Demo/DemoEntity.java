package com.dm.DGCat.AppTest.Demo;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder(toBuilder = true)
public class DemoEntity<T> {

    private T demoT;

    private List<T> demoList;
    private Map<String,T> demoMap;
    private T[] demoTArray;
}
