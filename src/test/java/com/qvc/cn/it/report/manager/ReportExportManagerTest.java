package com.qvc.cn.it.report.manager;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/com/qvc/cn/it/report/applicationContext-test.xml")
public class ReportExportManagerTest {

	@Resource
	private ReportExportManager reportExportManager;
	
	@Test
	public void test() {
		reportExportManager.getReportByTaskId(1560L);
	}

}
