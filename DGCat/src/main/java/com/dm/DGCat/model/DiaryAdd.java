package com.dm.DGCat.model;

import com.dm.DGCat.util.PrintToString;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class DiaryAdd {
	/**
	 * ID
	 * */
	private int id;
	/**
	 * 日志ID
	 * */
    private String fileId;
	/**
	 * 日志内容
	 * */
    private String fileContent;
	/**
	 * 日志ID
	 * */
    private String diaryContentId;
	/**
	 * 标题
	 * */
    private String title;
	/**
	 * 是否公开
	 * */
    private int publicY;

    public void setId(int id)
    {
    	 this.id=id;
    }
    
    public int getId()
    {
    	return this.id;
    }
	
    public String getFileContent()
    {
    	return this.fileContent;
    }
    
    public String getFileId()
    {
    	return this.fileId;
    }
    
    public void setFileId(String fileId)
    {
    	this.fileId=fileId;
    }
    
    public void setFileContent(String fileContent)
    {
    	this.fileContent=fileContent;
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
	
	@Override
	public String toString()
	{
		PrintToString<DiaryAdd> ds=new PrintToString<DiaryAdd> ();
		ds.setT(this);
		return ds.printT();
	}
}
