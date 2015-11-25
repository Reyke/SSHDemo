package com.qvc.cn.it.report.dao;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.qvc.cn.it.report.model.Employee;
import com.qvc.cn.it.report.model.Role;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/com/qvc/cn/it/report/applicationContext-test.xml")

public class EmployeeDAOTest {
//	private static final String TEST_DEMO_EMAIL= "lichun1076@sina.com";
	@Resource
	private IGenericDAO<Employee, Long> employeeDAO;
	
	@Transactional
	@Test
	public void testEmployeeDAO() {
		// we'll remove all exists records before our testing.
//		List<Employee> employees = employeeDAO.listAll();
//		if (employees != null && employees.size() > 0) {
//			for (Employee employee : employees) {
//				employeeDAO.delete(employee);
//			}
//		}
		
//		assertEquals("delete method not work.", 0, employeeDAO.listAll().size());
		
		Role role = new Role();
		role.setId((short)1);
		new Employee("1","spring","740056710@qq.com",new Date(),new Date(),"1","123456",new Date(),"sampsonD","sampsonU","sampsonC","play",role);
//		employeeDAO.save(emp);
		
		
//		assertEquals("save method not work.", 1, employeeDAO.listAll().size());
		
//		emp.setEmail(TEST_DEMO_EMAIL);
//		employeeDAO.update(emp);
		
//		emp = employeeDAO.listAll().get(0);
//		assertEquals("update method not work.", TEST_DEMO_EMAIL, emp.getEmail());
		
		
		
		
//		employeeDAO.delete(emp);
		
//		assertEquals("delete moethod not work.", 0, employeeDAO.listAll().size());
		
		
	}
	
	
}
