package com.hh.system.util.email;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class JavaMail {
	private MimeMessage message;
	private Session session;
	private Transport transport;

	private String mailHost = "";
	private String sender_username = "";
	private String sender_usertext = "";
	private String sender_password = "";

	private Properties properties = new Properties();

	/*
	 * 初始化方法
	 */
	public JavaMail() {
		ClassLoader classloader = Thread.currentThread()
				.getContextClassLoader();
		InputStream in = classloader.getResourceAsStream("system.properties");
		try {
			properties.load(in);
			this.mailHost = properties.getProperty("mail.smtp.host");
			this.sender_username = properties
					.getProperty("mail.sender.username");
			
			this.sender_usertext = properties
					.getProperty("mail.sender.usertext");
			
			this.sender_password = properties
					.getProperty("mail.sender.password");
		} catch (IOException e) {
			e.printStackTrace();
		}
		MyAuthenticator myauth = new MyAuthenticator(this.sender_username,
				this.sender_password);
		session = Session.getDefaultInstance(properties, myauth);
		session.setDebug(false);// 开启后有调试信息
		message = new MimeMessage(session);
	}

	/**
	 * 发送邮件
	 * 
	 * @param subject
	 *            邮件主题
	 * @param sendHtml
	 *            邮件内容
	 * @param receiveUser
	 *            收件人地址
	 * @param attachment
	 *            附件
	 */
	public void doSendHtmlEmail(List<String> targetUserList, String title,
			String content) {
		try {
			if (targetUserList.size()==0) {
				return ;
			}
			
			transport = session.getTransport("smtp");
			transport.connect(mailHost, sender_username, sender_password);
			// 发件人
			InternetAddress from = new InternetAddress(sender_username);
			message.setFrom(from);
			// 收件人
			InternetAddress[] addrs = new InternetAddress[targetUserList.size()];
			int i = 0;
			for (String user : targetUserList) {
				InternetAddress to = new InternetAddress(user);
				addrs[i] = to;
				i++;
			}
			message.setRecipients(Message.RecipientType.TO, addrs);
			// 设置邮件主题
			message.setSubject(title);
			Multipart mainPart = new MimeMultipart();
			BodyPart html = new MimeBodyPart();
			html.setContent(content, "text/html; charset=UTF-8");
			mainPart.addBodyPart(html);
			message.setContent(mainPart);
			message.setSentDate(new Date());
			InternetAddress address = new InternetAddress(sender_username,
					MimeUtility.encodeText(sender_usertext,
							MimeUtility.mimeCharset("gb2312"), null));
			message.setFrom(address);
			message.saveChanges();
			transport.sendMessage(message, message.getAllRecipients());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (transport != null) {
				try {
					transport.close();
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		JavaMail se = new JavaMail();
		List<String> strings = new ArrayList<String>();
		strings.add("405038567@qq.com");
		se.doSendHtmlEmail(strings, "邮件主题", "<h1>邮件内容111</h1>");
	}
}
