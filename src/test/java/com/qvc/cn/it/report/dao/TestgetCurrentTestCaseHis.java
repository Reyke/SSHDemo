package com.qvc.cn.it.report.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.qvc.cn.it.report.model.Issues;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/com/qvc/cn/it/report/applicationContext-test.xml")
public class TestgetCurrentTestCaseHis {
	
	@Resource
	TestCaseDAOImpl testCaseDAO;

	@Transactional
	@Test
	public void test() {
		String casename = "17527_ProgramGuide_NotStoringTimezone";
		long taskID = 1556 ;
		long setID = 75;
	    List<Issues> list = testCaseDAO.getCurrentTestCaseHis(taskID);
	    
	    assertEquals("Get test case history failed.", 1, list.size());
	}

}
