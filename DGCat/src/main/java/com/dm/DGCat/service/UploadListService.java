package com.dm.DGCat.service;

import com.dm.DGCat.dao.UploadListDAO;
import com.dm.DGCat.model.SiteUser;
import com.dm.DGCat.model.UploadList;
import com.dm.DGCat.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@Service
public class UploadListService {
	@Autowired UploadListDAO uploadListDAO;

	//执行后更新所有缓存

	public void add(UploadList uploadList) {
		uploadListDAO.save(uploadList);
	}

	public void delete(UploadList uploadList) {
		uploadListDAO.delete(uploadList);
	}

	public void update(UploadList uploadList) {
		uploadListDAO.save(uploadList);
	}

	public UploadList getByKey(String key) {
		return uploadListDAO.findAllByFileKeyOrderByUploadTimeDesc(key);
	}

	public List<UploadList> getByFileName(SiteUser siteUser,String fileName) {
		return uploadListDAO.findAllBySiteUserAndFileNameContainingOrderByUploadTimeDesc(siteUser,fileName);
	}

	public List<UploadList> getBySiteUser(SiteUser siteUser) {
		return uploadListDAO.findAllBySiteUserOrderByUploadTimeDesc(siteUser);
	}

	public Result uploadFile(MultipartFile myfile,String siteUser){
		File fileFolder= new File("C:/dgcatfile/"+siteUser);
		//判断是否是zip或rar格式  不然无法上传
		String filennameString=myfile.getOriginalFilename();
		File filenew = new File(fileFolder,filennameString);
		//获取截取数组
		String[] strU;
		while(filenew.exists()){
			strU=filennameString.split("\\.");
			String newfilennameString="";
			for (int i = 0; i < strU.length; i++) {
				if(i == strU.length - 2){
					newfilennameString+=strU[i]+"_复制.";
				}else if(i == strU.length - 1){
					newfilennameString+=strU[i];
				}else {
					newfilennameString+=strU[i]+".";
				}
			}
			filennameString=newfilennameString;
			filenew = new File(fileFolder,newfilennameString);
		}
		if(filennameString.substring(filennameString.lastIndexOf('.'),filennameString.length()).toUpperCase().equals(".RAR")||filennameString.substring(filennameString.lastIndexOf('.'),filennameString.length()).toUpperCase().equals(".ZIP"))
		{
			if(!filenew.getParentFile().exists())
				filenew.getParentFile().mkdirs();
			try {
				BufferedInputStream in = new BufferedInputStream(myfile.getInputStream());// 获得文件输入流
				BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(filenew));// 获得文件输出流
				org.apache.commons.fileupload.util.Streams.copy(in, out, true);// 开始把文件写到指定的上传文件夹
				return Result.success(filennameString);
			} catch (Exception e) {
				return Result.fail(e.getMessage());
			}
		}
		else
		{
			return Result.fail("仅支持上传rar/zip格式文件");
		}
	}
	//删除指定文件夹的文件
	public Result deleteFile(String fileKey,String siteUser){
		try {
			UploadList uploadList =getByKey(fileKey);
			File  imageFolder= new File("C:/dgcatfile/");
			File file = new File(imageFolder,siteUser+"/"+uploadList.getFileName());
			file.delete();
			return Result.success();
		} catch (Exception e) {
			return Result.fail(e.getMessage());
		}
	}
}
