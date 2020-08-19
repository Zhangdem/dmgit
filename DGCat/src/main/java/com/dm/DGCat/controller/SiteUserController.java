package com.dm.DGCat.controller;

import com.dm.DGCat.model.SiteUser;
import com.dm.DGCat.service.SiteUserService;
import com.dm.DGCat.util.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value="/siteUser")
public class SiteUserController  {
	@Autowired SiteUserService siteUserService;
    private RabbitTemplate rabbitTemplate;

	@GetMapping(value="/toLogin")
	public Object toLogin( SiteUser siteUser,HttpSession session )//@RequestBody
	{
		return Result.unlogin();
	}

	//登录                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
    @PostMapping(value="/login")
    public Object login(  SiteUser siteUser,HttpSession session ){//@RequestBody
    	
		String count =siteUser.getCount();
		//转译html
		count = HtmlUtils.htmlEscape(count);
		siteUser.setCount(count);
		//交给shiro校验
		//Realm作为用户校验的DAO层
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(count, siteUser.getPassWord());
		try {
            subject.login(token);
    		//根据账号获取user信息
    		siteUser=siteUserService.getByCount(count);
            session.setAttribute("siteUser", siteUser);
        	return Result.success();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return Result.fail("用户名密码错误!");
		}
    }
   
    //查看是否登陆
    @GetMapping("/checkLogin")
    public Object checkLogin(HttpSession session) {
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated())
        {
	    	SiteUser siteUser =(SiteUser)  session.getAttribute("siteUser");	
			if(null==siteUser)
			{
				//重新记录session  session会自动清除
				Object loginuser=subject.getPrincipal();
				siteUser=siteUserService.getByCount(loginuser.toString());
	            session.setAttribute("siteUser", siteUser);
			}
            return Result.success();
        }
        else
           return Result.fail("未登录");
    }

    //注册
    @PostMapping(value="/register")
    public Object add(  SiteUser siteUser,HttpSession session){//@RequestBody
    	return siteUserService.add(siteUser);
    }

    //退出登录
    @GetMapping("/logout")
    public Object logout(HttpSession session) {
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated())
        {
            subject.logout();
            return Result.success();
        }
        else {
            return Result.fail("nouser");
		}
    }
}