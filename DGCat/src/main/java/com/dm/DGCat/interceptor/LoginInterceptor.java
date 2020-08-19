package com.dm.DGCat.interceptor;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //拦截器用来拦截页面或路径请求
    	
    	HttpSession session = httpServletRequest.getSession();
        ServletContext dssdServletContext =session.getServletContext();
        String contextPath=session.getServletContext().getContextPath();
        String[] requireAuthPages = new String[]{
        		"test/yestest"
        };
  
        String uri = httpServletRequest.getRequestURI();
 
        if(contextPath.equals(""))
        {
        	uri = StringUtils.substring(uri, 1);
        }
        else {
            uri = StringUtils.remove(uri, contextPath+"/");
		}
        String page = uri;
 
        if(begingWith(page, requireAuthPages)){
            Subject subject = SecurityUtils.getSubject();
            if(!subject.isAuthenticated()) {
            	//全路径
            	httpServletResponse.sendRedirect("/siteUser/toLogin");
            	//相对路径会重定向到当前Servlet下的toLogin
            	//httpServletResponse.sendRedirect("toLogin");
                return false;
            }
        }
        return true;  
    }
 
    private boolean begingWith(String page, String[] requiredAuthPages) {
        boolean result = false;
        for (String requiredAuthPage : requiredAuthPages) {
            if(StringUtils.startsWith(page, requiredAuthPage)) {
                result = true; 
                break;
            }
        }
        return result;
    }
 
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
 
    }
 
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}