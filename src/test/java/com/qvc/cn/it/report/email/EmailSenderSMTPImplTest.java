package com.qvc.cn.it.report.email;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/com/qvc/cn/it/report/email/config/email-beans.xml")
public class EmailSenderSMTPImplTest {

	@Resource
	EmailSender emailSender;
	
	@Test
	public void testSendEmail() {
//		EmailSender emailSender = new EmailSenderSMTPImpl();
//		emailSender.setFromAddress("qvc_qrt@163.com");
//		emailSender.setPassword("Liang123");
//		emailSender.setUserName("qvc_qrt");
//		emailSender.setSmtpHost("smtp.163.com");
//		emailSender.setSmtpPort("25");

		List<String> recipients = new ArrayList<String>();
		// recipients.add("00910006@qvc.com");
		recipients.add("00909932@qvc.com");
//		recipients.add("fengyu.chuang@gmail.com");
		
		emailSender.sendEmail(recipients, "Hello world.",  "<b>Test email.</b>", EmailSender.EMIAL_CONTEXT_HTML);
	}

}
