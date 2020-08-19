package com.dm.DGCat.model;

import com.dm.DGCat.util.PrintToString;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

//注明这是一个实体类
@Entity
//注明对应的表名是user
@Table(name = "user")
/**
 * 	本项目使用JPA做实体类的持久化,默认使用Hibernate
	jpa会创建代理类继该类.并添加handler和hibernateLazyInitializer属性
	因此此处需要忽略handler及hibernateLazyInitializer的jjson序列化
 */
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class SiteUser implements Serializable {
	private static final long serialVersionUID = 1L;
	//注明这是ID
	@Id 
	//注明这是主键,有数据库自动生成
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//注明对应列名
	@Column(name="id")
	int id;
	/**
	 * 用户名
	 * */
	String  userName;
	/**
	 * 密码
	 * */
	String passWord;
	/**
	 * 账户
	 * */
	String count;
	/**
	 * 盐
	 * */
	String salt;

	public int getId()
	{
		return this.id;
	}
	
	public void setId(int id)
	{
		this.id=id;
	}
	
	public String getName()
	{
		return this.userName;
	}
	
	public void setName(String userName)
	{
		this.userName=userName;
	}
	public String getPassWord()
	{
		return this.passWord;
	}
	
	public void setPassWord(String passWord)
	{
		this.passWord=passWord;
	}
	
	public String getCount()
	{
		return this.count;
	}
	
	public void setCount(String count)
	{
		this.count=count;
	}
	
	public String getSalt()
	{
		return this.salt;
	}
	
	public void setSalt(String salt)
	{
		this.salt=salt;
	}
	
	@Override
	public String toString()
	{
		PrintToString<SiteUser> ds=new PrintToString<SiteUser> ();
		ds.setT(this);
		return ds.printT();
	}
}
