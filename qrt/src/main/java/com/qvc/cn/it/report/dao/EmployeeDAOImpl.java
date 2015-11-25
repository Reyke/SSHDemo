package com.qvc.cn.it.report.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.qvc.cn.it.report.model.Employee;

@Repository("employeeDAO")
public class EmployeeDAOImpl extends GenericDAO<Employee, String> implements EmployeeDAO {

	@Override
	public List<Employee> listAllValidEmployees() {
		@SuppressWarnings("unchecked")
		List<Employee> employees = (List<Employee>)sessionFactory.getCurrentSession()
				.createQuery("from Employee emp where emp.activeInd = 'Y'").list();
		
		return employees;
	}

}
