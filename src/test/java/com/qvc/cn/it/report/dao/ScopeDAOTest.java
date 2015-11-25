package com.qvc.cn.it.report.dao;

import com.qvc.cn.it.report.model.Country;
import com.qvc.cn.it.report.model.Project;
import com.qvc.cn.it.report.model.Scope;
import com.qvc.cn.it.report.model.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/com/qvc/cn/it/report/applicationContext-test.xml")
public class ScopeDAOTest{

	private static final String TEST_Scope_NAME = "ScopeDAO Test";
	private static final String TEST_Scope_NAME_CHANGED = "Eric's Scope 2";
	
	@Resource
	ScopeDAO scopeDAO;
	
	
	@Transactional
	@Test
	public void testscopeDAO() {
		
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
		
	}
	
	
}
