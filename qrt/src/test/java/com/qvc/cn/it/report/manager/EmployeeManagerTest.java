package com.qvc.cn.it.report.manager;

import static org.junit.Assert.*;

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
public class EmployeeManagerTest {
	
	private final static String TEST_NUMBER = "99999999";
	@Resource
	private EmployeeManager employeeManager;
	
	@Transactional
	@Test
	public void testCache() {
		// first call, will be cached.
		Employee e1 = employeeManager.getAllEmployee().get(0);
		
		// second time, get from cache, the same object
		Employee e2 = employeeManager.getAllEmployee().get(0);
		
		assertEquals(e1, e2);
		
		e2 = employeeManager.getEmployeeByNumber("00909932");
		e2.setEmail("00909932@qvc.com");
		
		employeeManager.updateEmployee(e2, "00909932");
		// cache cleared
		
		Employee e3 = employeeManager.getAllEmployee().get(0);
		assertNotEquals(e1, e3);
		
	}
	
	@Transactional
	@Test
	public void testRegister(){
		Employee emp = new Employee();
		emp.setName("sampson");
		emp.setNumber(TEST_NUMBER);
		emp.setEmail("Chun.Li@qvc.com");
		Role role = new Role();
		role.setId((short)3);
		emp.setRole(role);
		emp.setPassword("1234565");
		employeeManager.addEmployee(emp, "Eric");
		Employee empdb = employeeManager.getEmployeeByNumber(TEST_NUMBER);
		System.out.println(empdb.getName());
		assertEquals("save method not work.", "sampson", empdb.getName());
		
	}

}
