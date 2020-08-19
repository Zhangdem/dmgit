package com.dm.DGCat.dao;

import com.dm.DGCat.model.SiteUser;
import com.dm.DGCat.model.UploadList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UploadListDAO  extends JpaRepository<UploadList,Integer>{

	//根据用户查询
	List<UploadList> findAllBySiteUserOrderByUploadTimeDesc(SiteUser siteuser);
	
	//根据存储key查询
	UploadList findAllByFileKeyOrderByUploadTimeDesc(String fileKey );
	
	//根据条件查询倒叙
	List<UploadList> findAllBySiteUserAndFileNameContainingOrderByUploadTimeDesc(SiteUser siteuser,String uploadText);
}
