package com.dm.DGCat.dao;

import com.dm.DGCat.model.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 
 * Author DM 
 * DAO层继承jpa自带CRUD和分页
 * JpaRepository<T,id>   id 对应实体类中的id
 * */
public interface SiteUserDAO extends JpaRepository<SiteUser,Integer>{
	 
	SiteUser findByuserName(String name);
	
	SiteUser findBycount(String count);
 
}