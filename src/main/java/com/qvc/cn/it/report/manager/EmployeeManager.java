package com.qvc.cn.it.report.manager;

import java.util.List;

import com.qvc.cn.it.report.model.Employee;
import com.qvc.cn.it.report.model.Role;

public interface EmployeeManager extends BaseManager {
	
	public List<Employee> getAllEmployee();
	public Employee addEmployee(Employee emp, String operater);
	public Employee updateEmployee(Employee emp, String operater);
	public boolean deleteEmployee(Employee emp,String operater);
	public boolean deleteEmployeeByNumber(String number,String operator);
	public Employee getEmployeeByNumber(String number);
	
	public List<Employee> getAllQATester();
	
	public List<Employee> getAllQaAdmin();

	public List<Role> getAllValidRoles();
}
