package com.dm.DGCat.controller;

import com.dm.DGCat.model.DiaryAdd;
import com.dm.DGCat.model.DiaryFile;
import com.dm.DGCat.model.SiteUser;
import com.dm.DGCat.model.UserDiary;
import com.dm.DGCat.service.DiaryFileService;
import com.dm.DGCat.service.SiteUserService;
import com.dm.DGCat.service.UserDiaryService;
import com.dm.DGCat.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;


@RestController
@RequestMapping(value="/diary")
public class DiaryController {
	
	@Autowired SiteUserService siteUserService;
	@Autowired UserDiaryService userDiaryService;
	@Autowired DiaryFileService diaryFileService;
	//获取所有日志信息
	@GetMapping(value="/get")
	public Object getDiary(HttpSession session){
		//判断是否登陆
    	//获取缓存用户数据
		SiteUser siteUser =(SiteUser)  session.getAttribute("siteUser");	
		if(null==siteUser)
			return Result.unlogin();
		else {
			try {	
	        	//获取该用户的全部日志信息
	        	List<UserDiary> userDiary =userDiaryService.getByUser(siteUser);
	        	return Result.success( userDiary);
			} catch (Exception e) {
				// TODO: handle exception
				return Result.fail(e.getMessage());
			}
		}
	}
	//获取所有日志信息
	@GetMapping(value="/getSome/{title}")
	public Object getDiarySome(@PathVariable("title") String title,HttpSession session){
		//判断是否登陆
    	//获取缓存用户数据
		SiteUser siteUser =(SiteUser)  session.getAttribute("siteUser");	
		if(null==siteUser)
			return Result.unlogin();
		else {
			try {	
	        	//获取该用户的全部日志信息
	        	List<UserDiary> userDiary =userDiaryService.getByTitle(siteUser,title);
	        	return Result.success( userDiary);
			} catch (Exception e) {
				// TODO: handle exception
				return Result.fail(e.getMessage());
			}
		}
	}
	//添加日志
	@PostMapping(value="/add")
	public Result addDiary(  DiaryAdd  diaryAdd,HttpSession session){//@RequestBody
		//判断是否登陆
    	//获取缓存用户数据
		SiteUser siteUser =(SiteUser)  session.getAttribute("siteUser");	
		if(null==siteUser)
			return Result.unlogin();
		else {
			try {
				UserDiary userDiary=new UserDiary();
				DiaryFile diaryFile=new DiaryFile();
				//用户日志赋值
				userDiary.setTitle(diaryAdd.getTitle());
				userDiary.setDiaryContentId(diaryAdd.getDiaryContentId());
				userDiary.setPublicY(diaryAdd.getPublicY());
				//日志信息赋值
				diaryFile.setFileId(diaryAdd.getFileId());
				diaryFile.setFileContent(diaryAdd.getFileContent());
				//获取该用户的全部日志信息
				try {
					userDiary.setSiteUser(siteUser);
					userDiaryService.add(userDiary);
					diaryFileService.saveDiaryFile(diaryFile);
					return Result.success( userDiary);
				} catch (Exception e) {
					// TODO: handle exception
					//提交失败删除日志
					userDiaryService.delete(userDiary.getId());
					return Result.fail("提交失败");
				}
			}catch (Exception e){
				return Result.fail("提交失败");
			}
		} 
	}
	//删除日志
	@GetMapping(value="/delete/{id}/{diaryContentId}")
	public Object deleteDiary(@PathVariable("id") int id,@PathVariable("diaryContentId") String diaryContentId,HttpSession session){
		//判断是否登陆
		//获取缓存用户数据
		SiteUser siteUser =(SiteUser)  session.getAttribute("siteUser");	
		if(null==siteUser)
			return Result.unlogin();
		else {
			try {
				userDiaryService.delete(id);
				//删除日志
				diaryFileService.deleteById(diaryContentId);
	        	return Result.success();
			} catch (Exception e) {
				// TODO: handle exception
				return Result.fail(e.getMessage());
			}
		} 
	}
	//更改日志信息
	@PostMapping(value="/update")
	public Object updateDiary( DiaryAdd diaryAdd, HttpSession session){//@RequestBody
		//判断是否登陆
		//获取缓存用户数据
		SiteUser siteUser =(SiteUser)  session.getAttribute("siteUser");	
		if(null==siteUser)
			return Result.unlogin();
		else {
			try {
				UserDiary userDiary=new UserDiary();
				DiaryFile diaryFile=new DiaryFile();
				//用户日志赋值
				userDiary.setId(diaryAdd.getId());
				userDiary.setTitle(diaryAdd.getTitle());
				userDiary.setDiaryContentId(diaryAdd.getDiaryContentId());
				userDiary.setPublicY(diaryAdd.getPublicY());
				//日志信息赋值
				diaryFile.setFileId(diaryAdd.getFileId());
				diaryFile.setFileContent(diaryAdd.getFileContent());
				userDiary.setSiteUser(siteUser);
				diaryFileService.updateDiaryFile(diaryFile);
				userDiaryService.update(userDiary);
	        	return Result.success();
			} catch (Exception e) {
				// TODO: handle exception
				return Result.fail(e.getMessage());
			}
		} 
	}
	//获取日志详细文本
	@GetMapping(value="/getOneDiary/{fileId}")
	public Object getOneDiary(@PathVariable("fileId") String fileId, HttpSession session){
		//判断是否登陆
		//获取缓存用户数据
		SiteUser siteUser =(SiteUser)  session.getAttribute("siteUser");
		if(null==siteUser)
			return Result.unlogin();
		else {
			try {	
				DiaryFile diaryFile=diaryFileService.findByFileId(fileId);
	        	return Result.success(diaryFile);
			} catch (Exception e) {
				// TODO: handle exception
				return Result.fail(e.getMessage());
			}
		} 
	}
}
