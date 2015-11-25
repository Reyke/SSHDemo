package com.qvc.cn.it.report.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/com/qvc/cn/it/report/applicationContext-test.xml")
public class TaskManagerTest {

	@Resource
	TaskManager taskManager;
	
	@Transactional
	@Test
	public void test() {
		List<String> tester = new ArrayList<String>();
		//tester.add("00909932");
		//tester.add("00917528");
		tester.add("00909932");
		//tester.add("00920110");
		//tester.add("00920170");
		
		Date date = new Date();
		
		taskManager.assignTask((long)1562, "00920178", tester, date,"Annie","00920216",date);
	}

}
