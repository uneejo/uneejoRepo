package com.unicompany.common.email;

import java.io.File;
import java.net.URLEncoder;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class EmailManagement {
	
	private String host;
	private String username;
	private String password;
	private int port;
	private String pdfBaseUrl;
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getPdfBaseUrl() {
		return pdfBaseUrl;
	}
	public void setPdfBaseUrl(String pdfBaseUrl) {
		this.pdfBaseUrl = pdfBaseUrl;
	}
  
public void sendEmailMgt(String empCode, String sEmail) throws Exception{
			// TODO Auto-generated method stub
	
    // 메일 내용
    String recipient = sEmail;                   //받는 사람의 이메일
    String subject = empCode+"님의 재직 증명서 입니다.";    //이메일 제목
    String body = "확인 후 회신바랍니다";                //이메일 내용
     
    Properties props = System.getProperties();
     
    props.put("mail.smtp.host", host);
    props.put("mail.smtp.port", port);
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.ssl.enable", "true");
    props.put("mail.smtp.ssl.trust", host);
      
    Session session = Session.getInstance(props, new javax.mail.Authenticator() {
        String un=username;
        String pw=password;
        protected PasswordAuthentication getPasswordAuthentication() {
       return new PasswordAuthentication(un, pw);
        }
    });
    session.setDebug(true);
      
    Message msg = new MimeMessage(session);
    msg.setFrom(new InternetAddress("jm~~~~~@naver.com","테스트발송"));    //첫인자가 보내는 사람 이메일, 두번째 인자가 보내는 사람 이름
    msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
    msg.setSubject(subject);
     
    // 파일첨부를 위한 Multipart
    Multipart multipart = new MimeMultipart();
     
    // BodyPart를 생성
    BodyPart bodyPart = new MimeBodyPart();
    bodyPart.setText(body);
     
    //Multipart에 BodyPart를 붙인다.
    multipart.addBodyPart(bodyPart);
     
    //이미지를 첨부한다.
    bodyPart = new MimeBodyPart();
    String filename = pdfBaseUrl+empCode+".pdf";
    System.out.println(filename);

    //첨부할 파일 경로
    FileDataSource source = new FileDataSource(filename);
    bodyPart.setDataHandler(new DataHandler(source));
    
    bodyPart.setFileName(empCode+".pdf");
    //첨부할 파일의 이름을 바꿔서 보낼수 있음
    
    bodyPart.setHeader("Content-ID", "image_id");
    multipart.addBodyPart(bodyPart);
     
    // 이메일 메시지의 내용에 Multipart를 붙인다.
    msg.setContent(multipart);
    Transport.send(msg);
	
	}

	
	
}
