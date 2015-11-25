package com.qvc.cn.it.report.email;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
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

import com.qvc.cn.it.report.BaseSpringManagedBean;

public class EmailSenderSMTPImpl extends BaseSpringManagedBean implements
		EmailSender {

	private String smtpHost;
	private String smtpPort;
	private String userName;
	private String password;
	private String fromAddress;

	@Override
	public String getSmtpHost() {
		return smtpHost;
	}

	@Override
	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	@Override
	public String getSmtpPort() {
		return smtpPort;
	}

	@Override
	public void setSmtpPort(String smtpPort) {
		this.smtpPort = smtpPort;
	}

	@Override
	public String getUserName() {
		return userName;
	}

	@Override
	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getFromAddress() {
		return fromAddress;
	}

	@Override
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	private Properties getProperties() {
		Properties p = new Properties();
		p.put("mail.smtp.host", this.smtpHost);
		p.put("mail.smtp.port", this.smtpPort);
		p.put("mail.smtp.auth", "true");
		return p;
	}

	@Override
	public void sendEmail(List<String> recipients, String subject,
			String content, int contentType) {
		MyAuthenticator authenticator = new MyAuthenticator(getUserName(),
				getPassword());
		Session sendMailSession = Session.getDefaultInstance(getProperties(),
				authenticator);
		try {
			Message mailMessage = new MimeMessage(sendMailSession);
			Address from = new InternetAddress(getFromAddress());
			mailMessage.setFrom(from);
			Address[] addresses = new Address[recipients.size()];
			int i = 0;
			for (String recipient : recipients) {
				addresses[i++] = new InternetAddress(recipient);
			}
			mailMessage.setRecipients(Message.RecipientType.TO, addresses);
			mailMessage.setSubject(subject);
			mailMessage.setSentDate(new Date());
			String mailContent = content;
			if (contentType == EMAIL_CONTENT_PLAIN) {
				mailMessage.setText(mailContent);
			} else {
				Multipart mainPart = new MimeMultipart();
				BodyPart html = new MimeBodyPart();
				html.setContent(content, "text/html; charset=utf-8");
				mainPart.addBodyPart(html);
				mailMessage.setContent(mainPart);
				mailMessage.saveChanges();
			}
			Transport.send(mailMessage);
		} catch (MessagingException ex) {
			error("send mail failed, " + ex.getMessage());
			throw new EmailException(ex);
		}
	}
		
		@Override
		public void sendEmail(String recipient, String subject,
				String content, int contentType) {
			MyAuthenticator authenticator = new MyAuthenticator(getUserName(),
					getPassword());
			Session sendMailSession = Session.getDefaultInstance(getProperties(),
					authenticator);
			try {
				Message mailMessage = new MimeMessage(sendMailSession);
				Address from = new InternetAddress(getFromAddress());
				mailMessage.setFrom(from);
				Address[] address = new Address[1];
				address[0] = new InternetAddress(recipient);

				mailMessage.setRecipients(Message.RecipientType.TO, address);
				mailMessage.setSubject(subject);
				mailMessage.setSentDate(new Date());
				String mailContent = content;
				if (contentType == EMAIL_CONTENT_PLAIN) {
					mailMessage.setText(mailContent);
				} else {
					Multipart mainPart = new MimeMultipart();
					BodyPart html = new MimeBodyPart();
					html.setContent(content, "text/html; charset=utf-8");
					mainPart.addBodyPart(html);
					mailMessage.setContent(mainPart);
					mailMessage.saveChanges();
				}
				Transport.send(mailMessage);
			} catch (MessagingException ex) {
				error("send mail failed, " + ex.getMessage());
				throw new EmailException(ex);
			}
	}
}
