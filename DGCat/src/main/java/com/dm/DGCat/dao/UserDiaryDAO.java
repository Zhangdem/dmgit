package com.dm.DGCat.dao;

import com.dm.DGCat.model.SiteUser;
import com.dm.DGCat.model.UserDiary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDiaryDAO  extends JpaRepository<UserDiary,Integer> {

	List<UserDiary> findAllBySiteUserAndTitleContaining(SiteUser siteuser,String titleString );
	
	List<UserDiary> findAllByAndSiteUserAndPublicYOrderByCreateTimeDesc(SiteUser siteuser,int pblicY);
}
