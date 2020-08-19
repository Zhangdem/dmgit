package com.dm.DGCat.model;

import lombok.Data;

import javax.activation.FileDataSource;
import java.io.Serializable;
/**
 * 邮件实体类
 * */
@Data
public class EmailEntity0 implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 标题
     * */
    private String title;
    /**
     * 内容
     * */
    private String content;
    /**
     * 接收者
     * */
    private String reciver;
    /**
     * 文件
     * */
    private FileDataSource fileDataSource;
}
