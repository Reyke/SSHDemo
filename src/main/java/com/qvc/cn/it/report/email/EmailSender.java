package com.qvc.cn.it.report.email;

import java.util.List;

import com.qvc.cn.it.report.SpringManagedBean;

public interface EmailSender extends SpringManagedBean {
	public final static int EMAIL_CONTENT_PLAIN = 0;
	public final static int EMIAL_CONTEXT_HTML = 1;
	/**
	 * This method will send a email out to all recipients.
	 * 
	 * @param recipients
	 *            email addresses for all recipients.
	 * @param subject
	 *            subject for the email sent.
	 * @param content
	 *            content of the email sent.
	 */
	public void sendEmail(List<String> recipients, String subject, String content, int contentType);
	public void sendEmail(String recipient, String subject,String content, int contentType);
	public abstract void setFromAddress(String fromAddress);
	public abstract String getFromAddress();
	public abstract void setPassword(String password);
	public abstract String getPassword();
	public abstract void setUserName(String userName);
	public abstract String getUserName();
	public abstract void setSmtpPort(String smtpPort);
	public abstract String getSmtpPort();
	public abstract void setSmtpHost(String smtpHost);
	public abstract String getSmtpHost();
}
