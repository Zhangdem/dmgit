package com.dm.DGCat.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Result {
    public static int SUCCESS_CODE = 0;
    public static int FAIL_CODE = 1;
    public static int UNLOGIN = 2;
	private static ObjectMapper MAPPER = new ObjectMapper();
	
    int code;
    
    String message;
    Object data;
     
    private Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Result success() {
    	try 
    	{
            return new Result(SUCCESS_CODE,null,null);
    	}
    	catch (Exception e) {
			// TODO: handle exception
    		return null;
		}
    }
    public static Result success(Object data) {
    	try 
    	{
            return new Result(SUCCESS_CODE,"",data);
    	}
    	catch (Exception e) {
			// TODO: handle exception
    		return null;
		}
    }
    public static Result fail(String message) {
    	try 
    	{
            return new Result(FAIL_CODE,message,null);
    	}
    	catch (Exception e) {
			// TODO: handle exception
    		return null;
		}
    }
    public static Result unlogin() {
    	try 
    	{
            return new Result(UNLOGIN,null,null);
    	}
    	catch (Exception e) {
			// TODO: handle exception
    		return null;
		}
    }
    public int getCode() {
        return code;
    }
 
    public void setCode(int code) {
        this.code = code;
    }
 
    public String getMessage() {
        return message;
    }
 
    public void setMessage(String message) {
        this.message = message;
    }
 
    public Object getData() {
        return data;
    }
 
    public void setData(Object data) {
        this.data = data;
    }
}