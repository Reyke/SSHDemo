package com.qvc.cn.it.report.dao;

import java.util.List;

import com.qvc.cn.it.report.model.Employee;

public interface EmployeeDAO extends IGenericDAO<Employee,String>{

	public List<Employee> listAllValidEmployees();
	
}
