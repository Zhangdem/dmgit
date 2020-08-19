package com.dm.DGCat.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.dm.DGCat.util.PrintToString;

@Document(collection = "diarycontent")
public class DiaryFile implements Serializable {
    private static final long serialVersionUID = -3258839839160856613L;
    /**
     * 日志ID
     * */
    @Id
    @Field("fileid")
    private String fileId;
    /**
     * 日志内容
     * */
    @Field("filecontent")
    private String fileContent;
    
    public long getSerialVersionUID()
    {
    	return this.serialVersionUID;
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
	
	@Override
	public String toString()
	{
		PrintToString<DiaryFile> ds=new PrintToString<DiaryFile> ();
		ds.setT(this);
		return ds.printT();
	}
}