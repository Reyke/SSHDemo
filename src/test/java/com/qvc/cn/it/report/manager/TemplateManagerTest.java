package com.qvc.cn.it.report.manager;

import static org.junit.Assert.*;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.qvc.cn.it.report.model.Employee;
import com.qvc.cn.it.report.model.Role;
import com.qvc.cn.it.report.model.TemplateDetail;
import com.qvc.cn.it.report.model.TemplateHead;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/com/qvc/cn/it/report/applicationContext-test.xml")
public class TemplateManagerTest {
	
	@Resource
	private TemplateManager templateManager;
	
	@Transactional
	@Test
	public void testAdd(){
		TemplateHead th = new TemplateHead();
		
		th.setName("Eric Test");
		th.setStatus("Y");
		th.setCreatedAt(new Date());
		th.setCreatedBy("00909932");
		
		TemplateDetail td = new TemplateDetail();
		td.setColumnIndex(0);
		td.setColumnLable("Name");
		td.setCreatedAt(new Date());
		td.setCreatedBy("00909932");
		td.setExpression("########3");
//		td.setTemplateHead(th);
		
		th.getTemplateDetails().add(td);

		templateManager.addTemplate(th);
	}

}
