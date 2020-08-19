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

import com.dm.DGCat.util.PrintToString;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//注明这是一个实体类
@Entity
//注明对应的表名是user
@Table(name = "diarylog")
/**diarylog
 * 	本项目使用JPA做实体类的持久化,默认使用Hibernate
	jpa会创建代理类继该类.并添加handler和hibernateLazyInitializer属性
	因此此处需要忽略handler及hibernateLazyInitializer的jjson序列化
 */
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class UserDiary {
	//注明这是ID
	@Id 
	//注明这是主键,有数据库自动生成
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//注明对应列名
	@Column(name="id")
	int id;
	/**
	 * 日志ID
	 * */
	String diaryContentId;
	/**
	 * 日志标题
	 * */
	String title;
	/**
	 * 是否公开
	 * */
	int publicY;
	/**
	 * 创建时间
	 * */
	Date createTime;
	
	@ManyToOne
	@JoinColumn(name="userId")
	private SiteUser siteUser;
	
	public int getId()
	{
		return this.id;
	}
	
	public void setId(int id)
	{
		this.id=id;
	}
	public String getDiaryContentId()
	{
		return this.diaryContentId;
	}
	
	public void setDiaryContentId(String diaryContentId)
	{
		this.diaryContentId=diaryContentId;
	}
	public String getTitle()
	{
		return this.title;
	}
	
	public void setTitle(String title)
	{
		this.title=title;
	}
	
	public int getPublicY()
	{
		return this.publicY;
	}
	
	public void setPublicY(int publicY)
	{
		this.publicY=publicY;
	}
	
	public Date getCreateTime()
	{
		return this.createTime;
	}
	
	public void setCreateTime(Date createTime)
	{
		this.createTime=createTime;
	}
	public SiteUser getSiteUser() {
		return siteUser;
	}
	public void setSiteUser(SiteUser siteUser) {
		this.siteUser = siteUser;
	}

    @Override
    public String toString() {
		PrintToString<UserDiary> ds=new PrintToString<UserDiary> ();
		ds.setT(this);
		return ds.printT();
    }
}
