package com.dm.DGCat.dao;

import com.dm.DGCat.model.DiaryFile;

/**
 * 
 * Author DM 
 * DAO层继承jpa自带CRUD和分页
 * JpaRepository<T,id>   id 对应实体类中的id
 * */
public interface DiaryFileDAO {
	  void saveDiaryFile(DiaryFile diaryFile);
	
	  DiaryFile findByFileId(String fileId);
	  
	  long updateDiaryFile(DiaryFile diaryFile);
	  
	  void deleteById(String fileId);
}