package com.dm.DGCat.service;

import com.dm.DGCat.dao.UserDiaryDAO;
import com.dm.DGCat.model.SiteUser;
import com.dm.DGCat.model.UserDiary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//缓存配置
//@CacheConfig(cacheNames="userdiary")
public class UserDiaryService {
    @Autowired UserDiaryDAO userDiaryDAO;
    
    //执行后更新所有缓存
    //@CacheEvict(allEntries=true)
    public UserDiary get(int bean) {
    	return userDiaryDAO.getOne(bean);
    }
    
    public void delete(int bean) {
    	userDiaryDAO.deleteById(bean);
    }
    
    public void add(UserDiary user) {
    	userDiaryDAO.save(user);
    }  
    
    public void update(UserDiary user) {
    	userDiaryDAO.save(user);
    } 
    
    public List<UserDiary> getByTitle(SiteUser siteUser,String titleString) {
        return userDiaryDAO.findAllBySiteUserAndTitleContaining(siteUser,titleString);
    }
    
    public List<UserDiary> getByUser(SiteUser siteuser) {
        return userDiaryDAO.findAllByAndSiteUserAndPublicYOrderByCreateTimeDesc(siteuser,1);
    }
    
}
