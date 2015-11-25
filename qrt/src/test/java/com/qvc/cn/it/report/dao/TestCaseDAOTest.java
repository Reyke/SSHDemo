package com.qvc.cn.it.report.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.qvc.cn.it.report.model.Issues;
import com.qvc.cn.it.report.model.TestCase;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/com/qvc/cn/it/report/applicationContext-test.xml")
public class TestCaseDAOTest {
	private static final String TEST_DEMO_NAME = "Annie's Test";
	private static final String TEST_DEMO_NAME_CHANGED = "Annie's Test 2";
	
	@Resource
	TestCaseDAO testCaseDAO;
	
	@Transactional
	@Test
	public void Test(){
		
		List<TestCase> list = testCaseDAO.getTestCaseByTaskID((long)1539);
		
		if (list != null && list.size() > 0) {
			for (TestCase testCase : list) {
				System.out.println(testCase.getCase_id());
			}
		}
		
	}
//	@Transactional
//	@Test
	public void testGetAssignedTestCaseByTaskIDAndEmployNo(){

//		List<TestCase> list = testCaseDAO.getAssignedTestCaseByTaskIDAndEmployNo((long)1539,"00920013");
//		Map<String,List<TestCase>> testCases = new HashMap<String, List<TestCase>>();
//		if (list != null && list.size() > 0) {
//			for (TestCase testCase : list) {
//				List<TestCase> lists = testCases.get(testCase.getTestSet().getSet_name());
//				if(null == lists){
//					lists = new ArrayList<TestCase>();
//					testCases.put(testCase.getTestSet().getSet_name(), lists);
//				}
//				lists.add(testCase);
//			}
//		}
//		System.out.println(testCases);
	}
	
	@Transactional
	@Test
	public void testGetCurrentTestCaseHis(){
		List<Issues> list = testCaseDAO.getCurrentTestCaseHis(3079);
		
		for(Issues issue : list){
			System.out.println(issue.getCaseId() + " : " + issue.getVersionDateNumber());
		}
	}
}
