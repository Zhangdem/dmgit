package com.dm.DGCat.service;

import com.dm.DGCat.dao.SiteUserDAO;
import com.dm.DGCat.model.SiteUser;
import com.dm.DGCat.util.Result;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

@Service
//缓存配置
//@CacheConfig(cacheNames="siteusers")
public class SiteUserService {
    @Autowired SiteUserDAO siteUserDAO;
    
    //执行后更新所有缓存
    //@CacheEvict(allEntries=true)
    public SiteUser get(int bean) {
    	return siteUserDAO.getOne(bean);
    }
    
    public Result add(SiteUser user) {
        try {
            //shiro盐加密 二次加密
            String count =   user.getCount();
            String password =  user.getPassWord();
            //转译html  避免恶意注册  影响界面效果
            count = HtmlUtils.htmlEscape(count);
            user.setCount(count);
            //判断账号是否存在
            boolean exist = isCountExist(count);
            //判断账号是否存在
            if(exist){
                String message ="该账号已存在!";
                return Result.fail(message);
            }
            //获取随机盐
            String salt = new SecureRandomNumberGenerator().nextBytes().toString();
            //加密次数
            int times = 2;
            //加密方式
            String algorithmName = "md5";
            //加密结果
            String encodedPassword = new SimpleHash(algorithmName, password, salt, times).toString();
            user.setSalt(salt);
            user.setPassWord(encodedPassword);
            siteUserDAO.save(user);
            return Result.success();
        } catch (Exception e) {
            // TODO: handle exception
            return Result.fail("注册失败,请重试");
        }
    }  
    
    public SiteUser getByName(String userName) {
        return siteUserDAO.findByuserName(userName);
    }
    public SiteUser getByCount(String count) {
        return siteUserDAO.findBycount(count);
    }
    public boolean isCountExist(String count) {
    	//SiteUserService userService = SpringContextUtil.getBean(SiteUserService.class);
    	SiteUser user = getByCount(count);
        return null!=user;
    }
    public boolean isUserNameExist(String userName) {
    	//SiteUserService userService = SpringContextUtil.getBean(SiteUserService.class);
    	SiteUser user = getByName(userName);
        return null!=user;
    }
}
