package com.qvc.cn.it.report.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.qvc.cn.it.report.model.Issues;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/com/qvc/cn/it/report/applicationContext-test.xml")
public class TeseCaseDAOTest{

	private static final String TEST_Scope_NAME = "TestCaseDAO Test";
	private static final String TEST_Scope_NAME_CHANGED = "TestCaseDAO";
	
	@Resource
	private TestCaseDAO testCaseDAO;
	
	
	@Transactional
	@Test
	public void TestCaseDAO() {
/*		Scope scope 		= new Scope();
		Country country 	= new Country();
		country.setCountry_code("CN");
		scope.setCountry_code(country);
		
		List<Scope> list 			= scopeDAO.getEntity(scope);
		
		for (Scope scopes : list) {
			System.out.println(scopes.getCountry_code().getCountry_code());
		}*/
		
//		
//		List list = scopeDAO.getAllProjects("US");
//		
//		System.out.println(list.size());
//		Object[] obj =  (Object[]) list.get(0);
//		System.out.println(obj[0]);
//		System.out.println(((Project)obj[1]).getProject_name());
		
		try {
			
	
		
		List ls = testCaseDAO.getSmokingSummary(1539,"2014-01-20");
		
		for (Object object : ls) {
			System.out.println(((List)object).get(0));
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testMethods() {
		
		Long a = 100L;
		Long b = 235L;
		
		System.out.println(a*1.0/b);
		
	}
	@Transactional
	@Test
	public void testGetAssignedTestCaseByTaskIDAndEmployNo(){
		//List<Issues> results = testCaseDAO.getAssignedTestCaseByTaskIDAndEmployNo((long)1560, "00909932",false);
		
			
		//for(Issues iss:results){
		//	System.err.println(iss.getTestCase().getTestSet().getSet_name());
		//}
		
		
	}

}
