package com.dm.DGCat.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class PrintToString<T> {

	private T t;
	
    public  void setT(T t) { //泛型构造方法形参key的类型也为T，T的类型由外部指定
        this.t = t;
    }

    public T getT(){ //泛型方法getKey的返回值类型为T，T的类型由外部指定
        return t;
    }
	
    //printToString()
    public String printT()
    {
    	//遍历对象中的属性
    	String resultString= t.getClass().getName()+"===>[ ";
        Field[] fields=t.getClass().getDeclaredFields();  
        String[] fieldNames=new String[fields.length];
        //获取对象名数组
	    for(int i=0;i<fields.length;i++){  
	        fieldNames[i]=fields[i].getName();  
	    }  

    	for(int j=0;j<fieldNames.length;j++)
    	{
    		Object value=null;
			try {
			//获取方法名
			    String firstLetter = fieldNames[j].substring(0, 1).toUpperCase();    
			    String getter = "get" + firstLetter+fieldNames[j].substring(1);    
			    //实力化方法
			    Method method = t.getClass().getMethod(getter, new Class[] {});    
			    //获取属性至
			    value = method.invoke(t, new Object[] {});  
			} catch (Exception e) {   
			}   
			if(value!=null)
			{
	    		resultString+=fieldNames[j]+"=\""+value.toString()+"\";";
			}
			else {
				resultString+=fieldNames[j]+"=##null##;";
			}
    		
    	}
    	resultString+=" ]";
    	return resultString;
    }
}
