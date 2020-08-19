package com.dm.DGCat.service;

import com.dm.DGCat.model.EmailEntity0;
import com.dm.DGCat.model.SiteUser;
import com.dm.DGCat.util.EmailUtils;
import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class DgcatService {
    EmailUtils emailUtils=new EmailUtils();
    public boolean isEmail(String email){
        if (null==email || "".equals(email)){
            return false;
        }
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(regEx1);
        Matcher m = p.matcher(email);
        if(m.matches()){
            return true;
        }else{
            return false;
        }
    }
    public  void senEmail(EmailEntity0 emailEntity0, SiteUser siteUser) throws GeneralSecurityException, MessagingException , MailException {


        //先对收件人信息进行拆分
        String[] recivers =emailEntity0.getReciver().split(";");
        List<EmailEntity0> emailEntity0List=new ArrayList<>();
        if(recivers.length>0)
        {
            for(String reciver : recivers)
            {
                if(isEmail(reciver)){
                    EmailEntity0 emailEntity0Temp =new EmailEntity0();
                    emailEntity0Temp.setContent(emailEntity0.getContent());
                    emailEntity0Temp.setFileDataSource(emailEntity0.getFileDataSource());
                    emailEntity0Temp.setReciver(reciver);
                    emailEntity0Temp.setTitle("来自"+siteUser.getCount().toUpperCase()+"的日志推送---https://www.dgcat.xyz");
                    emailEntity0List.add(emailEntity0Temp);
                }
            }
        }
        Properties prop = new Properties();
        prop.setProperty("mail.host", "smtp.qq.com"); //// 设置QQ邮件服务器
        prop.setProperty("mail.transport.protocol", "smtp"); // 邮件发送协议
        prop.setProperty("mail.smtp.auth", "true"); // 需要验证用户名密码

        // QQ邮箱设置SSL加密
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.socketFactory", sf);

        //1、创建定义整个应用程序所需的环境信息的 Session 对象
        Session session = Session.getDefaultInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {//agtcytpdajytbach
                //传入发件人的姓名和授权码
                return new PasswordAuthentication("1312877090@qq.com","thazxdpyukgwjhgg");
            }
        });

        //2、通过session获取transport对象
        Transport transport = session.getTransport();

        //3、通过transport对象邮箱用户名和授权码连接邮箱服务器
        transport.connect("smtp.qq.com","1312877090@qq.com","thazxdpyukgwjhgg");

        //循环发送
        for(EmailEntity0 emailEntity0Send : emailEntity0List)
        {
            //4、创建邮件,传入session对象
            MimeMessage mimeMessage = emailUtils.complexEmail("1312877090@qq.com",emailEntity0Send,session);
            //5、发送邮件
            transport.sendMessage(mimeMessage,mimeMessage.getAllRecipients());
        }

        //6、关闭连接
        transport.close();
    }

}
