package com.dm.DGCat.util;

import com.dm.DGCat.model.EmailEntity0;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailUtils {
    /**
     * 发送复杂邮件
     *
     * */
    public MimeMessage complexEmail(String sender, EmailEntity0 emailEntity0, Session session) throws MessagingException {
        //消息的固定信息
        MimeMessage mimeMessage = new MimeMessage(session);
        //发件人
        mimeMessage.setFrom(new InternetAddress(sender));
        //收件人
        mimeMessage.setRecipient(Message.RecipientType.TO,new InternetAddress(emailEntity0.getReciver()));
        //邮件标题
        mimeMessage.setSubject(emailEntity0.getTitle());

        //邮件内容
        //准备图片数据
//        MimeBodyPart image = new MimeBodyPart();
//        DataHandler handler = new DataHandler(new FileDataSource("E:\\IdeaProjects\\WebEmail\\resources\\测试图片.png"));
//        image.setDataHandler(handler);
//        image.setContentID("test.png"); //设置图片id

        //准备文本
        MimeBodyPart text = new MimeBodyPart();
        text.setContent(emailEntity0.getContent(),"text/html;charset=utf-8");

        //附件
        MimeBodyPart appendix = new MimeBodyPart();
        if(null!=emailEntity0.getFileDataSource()){
            appendix.setDataHandler(new DataHandler(emailEntity0.getFileDataSource()));
            appendix.setFileName(emailEntity0.getFileDataSource().getFile().getName());
        }
        //拼装邮件正文
        MimeMultipart mimeMultipart = new MimeMultipart();
//        mimeMultipart.addBodyPart(image);
        mimeMultipart.addBodyPart(text);
        mimeMultipart.setSubType("related");//文本和图片内嵌成功

        //将拼装好的正文内容设置为主体
        MimeBodyPart contentText = new MimeBodyPart();
        contentText.setContent(mimeMultipart);

        //拼接附件
        MimeMultipart allFile = new MimeMultipart();
        if(null!=emailEntity0.getFileDataSource()){
            allFile.addBodyPart(appendix);//附件
        }
        allFile.addBodyPart(contentText);//正文
        allFile.setSubType("mixed"); //正文和附件都存在邮件中，所有类型设置为mixed

        //放到Message消息中
        mimeMessage.setContent(allFile);
        mimeMessage.saveChanges();//保存修改

        return mimeMessage;
    }
}
