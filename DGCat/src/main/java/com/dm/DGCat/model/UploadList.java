package com.dm.DGCat.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.OverridesAttribute;

import com.dm.DGCat.util.PrintToString;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//注明这是一个实体类
@Entity
//注明对应的表名是user
@Table(name = "uploadlist")
/**
 * 	本项目使用JPA做实体类的持久化,默认使用Hibernate
	jpa会创建代理类继该类.并添加handler和hibernateLazyInitializer属性
	因此此处需要忽略handler及hibernateLazyInitializer的jjson序列化
 */
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class UploadList {
	/**
	 * 文件Key
	 * */
	@Id 
	//注明对应列名
	@Column(name="FileKey")
	String fileKey ;
	/**
	 * 文件名称
	 * */
	String  fileName;
	/**
	 * 下载次数
	 * */
	String downCount;
	/**
	 * 上传时间
	 * */
	Date uploadTime;
	/**
	 * 上传备注
	 * */
	String uploadText;
	
	@ManyToOne
	@JoinColumn(name="userId")
	private SiteUser siteUser;
	
	
	public SiteUser getSiteUser()
	{
		return this.siteUser;
	}
	
	public void setSiteUser(SiteUser siteUser)
	{
		this.siteUser=siteUser;
	}
	
	
	public String getFileKey()
	{
		return this.fileKey;
	}
	
	public void setFileKey(String fileKey)
	{
		this.fileKey=fileKey;
	}
	
	public String getFileName()
	{
		return this.fileName;
	}
	
	public void setFileName(String fileName)
	{
		this.fileName=fileName;
	}
	public String getDownCount()
	{
		return this.downCount;
	}
	
	public void setDownCount(String downCount)
	{
		this.downCount=downCount;
	}
	
	public Date getUploadTime()
	{
		return this.uploadTime;
	}
	
	public void setUploadTime(Date uploadTime)
	{
		this.uploadTime=uploadTime;
	}
	
	public String getUploadText()
	{
		return this.uploadText;
	}
	
	public void setUploadText(String uploadText)
	{
		this.uploadText=uploadText;
	}	
	@Override
	public String toString()
	{
		PrintToString<UploadList> ds=new PrintToString<UploadList> ();
		ds.setT(this);
		return ds.printT();
	}
}
