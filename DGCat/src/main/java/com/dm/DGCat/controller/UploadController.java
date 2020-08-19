package com.dm.DGCat.controller;

import com.dm.DGCat.model.SiteUser;
import com.dm.DGCat.model.UploadList;
import com.dm.DGCat.service.UploadListService;
import com.dm.DGCat.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value="/upload")
public class UploadController {

	@Autowired UploadListService uploadListService;
	
	//根据key获取
	@GetMapping(value="/getOne/{key}")
	public Object getOne(@PathVariable("key") String key, HttpSession session){
		//判断是否登陆
		//获取缓存用户数据
		SiteUser siteUser =(SiteUser)  session.getAttribute("siteUser");
		if(null==siteUser)
			return Result.unlogin();
		else {
			try {	
				UploadList uploadList=uploadListService.getByKey(key);
	        	return Result.success(uploadList);
			} catch (Exception e) {
				// TODO: handle exception
				return Result.fail(e.getMessage());
			}
		} 
	}
	//根据名称获取获取
	@GetMapping(value="/getCondition/{filename}")
	public Object getByName(@PathVariable("filename") String filename, HttpSession session){
		//判断是否登陆
		//获取缓存用户数据
		SiteUser siteUser =(SiteUser)  session.getAttribute("siteUser");
		if(null==siteUser)
			return Result.unlogin();
		else {
			try {	
				List<UploadList> resList=uploadListService.getByFileName(siteUser, filename);
	        	return Result.success(resList);
			} catch (Exception e) {
				// TODO: handle exception
				return Result.fail(e.getMessage());
			}
		} 
	}
	//根据用户信息获取                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
    @GetMapping(value="/getbusr")
    public Object getBUsr(HttpSession session){//@RequestBody
    	
		//判断是否登陆
		//获取缓存用户数据
		SiteUser siteUser =(SiteUser)  session.getAttribute("siteUser");
		if(null==siteUser)
			return Result.unlogin();
		else {
			try {	
				List<UploadList> resList=uploadListService.getBySiteUser(siteUser);
	        	return Result.success(resList);
			} catch (Exception e) {
				// TODO: handle exception
				return Result.fail(e.getMessage());
			}
		} 
    }
	//新增                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
    @PostMapping(value="/add")
    public Object add( UploadList uploadList ,HttpSession session ){//@RequestBody
    	
    	SiteUser siteUser =(SiteUser)  session.getAttribute("siteUser");
		if(null==siteUser)
			return Result.unlogin();
		else {
			try {
				uploadList.setSiteUser(siteUser);
				uploadList.setUploadTime(new Date());// new Date()为获取当前系统时间
				uploadListService.add(uploadList);
				//将文件上传
	        	return Result.success();
			} catch (Exception e) {
				// TODO: handle exception
				return Result.fail(e.getMessage());
			}
		} 
    }
	//修改                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
    @PostMapping(value="/update")
    public Object update(  UploadList uploadList ,HttpSession session ){//@RequestBody
    	SiteUser siteUser =(SiteUser)  session.getAttribute("siteUser");
		if(null==siteUser)
			return Result.unlogin();
		else {
			try {	
				uploadList.setSiteUser(siteUser);
				uploadListService.update(uploadList);
	        	return Result.success();
			} catch (Exception e) {
				// TODO: handle exception
				return Result.fail(e.getMessage());
			}
		} 

    }
	//删除                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
    @PostMapping(value="/delete")
    public Object delete(  UploadList uploadList ,HttpSession session ){//@RequestBody
    	
    	SiteUser siteUser =(SiteUser)  session.getAttribute("siteUser");
		if(null==siteUser)
			return Result.unlogin();
		else {
			try {	
				uploadList.setSiteUser(siteUser);
				uploadListService.delete(uploadList);
				//将齐牛文件删除
				return Result.success();
			} catch (Exception e) {
				// TODO: handle exception
				return Result.fail(e.getMessage());
			}
		} 
    }

	//上传文件
	//前端限制只上传单个文件
    @PostMapping(value="/fileupload")
    public Object uploadFile( MultipartFile myfile,HttpServletRequest request,HttpSession session)
    {
    	SiteUser siteUser =(SiteUser)  session.getAttribute("siteUser");
		if(null==siteUser)
			return Result.unlogin();
		else {
			return uploadListService.uploadFile(myfile,siteUser.getCount().toUpperCase());
		} 		
    }

	//上传文件
	//前端限制只上传单个文件
    @GetMapping(value="/deletefile/{fileKey}")
    public Object deleteFile( @PathVariable("fileKey") String fileKey ,HttpServletRequest request,HttpSession session)
    {
		SiteUser siteUser =(SiteUser)  session.getAttribute("siteUser");
		if(null==siteUser)
			return Result.unlogin();
		else{
			return uploadListService.deleteFile(fileKey,siteUser.getCount().toUpperCase());
		}
    }
}
