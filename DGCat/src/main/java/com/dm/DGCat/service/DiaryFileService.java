package com.dm.DGCat.service;

import com.dm.DGCat.dao.DiaryFileDAO;
import com.dm.DGCat.model.DiaryFile;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class DiaryFileService implements DiaryFileDAO{

	@Autowired
	private MongoTemplate mongoTemplate;
	
	/**
     * 创建对象
     * @param user
     */
	@Override
    public void saveDiaryFile(DiaryFile diaryFile) {
        mongoTemplate.save(diaryFile);
    }

    /**
     * 根据用户名查询对象
     * @param userName
     * @return
     */
	@Override
    public DiaryFile findByFileId(String fileId) {
        Query query=new Query(Criteria.where("_id").is(fileId));
        DiaryFile user =  mongoTemplate.findOne(query , DiaryFile.class);
        return user;
    }

    /**
     * 更新对象
     * @param user
     */
	@Override
    public long updateDiaryFile(DiaryFile diaryFile) {
        Query query=new Query(Criteria.where("_id").is(diaryFile.getFileId()));
        Update update= new Update().set("filecontent", diaryFile.getFileContent());
        //更新查询返回结果集的第一条
        UpdateResult result =mongoTemplate.updateFirst(query,update,DiaryFile.class);
        //更新查询返回结果集的所有
        // mongoTemplate.updateMulti(query,update,UserEntity.class);
        if(result!=null)
            return result.getModifiedCount();
        else
            return 0;
    }

    /**
     * 删除对象
     * @param id
     */
	@Override
    public void deleteById(String fileId) {
        Query query=new Query(Criteria.where("_id").is(fileId));
        mongoTemplate.remove(query,DiaryFile.class);
    }
	
}
