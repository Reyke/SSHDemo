package com.qvc.cn.it.report.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class EmployeeList {
	private List<Employee> employees;

	public EmployeeList(List<Employee> employees) {
		
		this.employees = employees;
	}

	public EmployeeList() {
		
		this.employees = new ArrayList<Employee>();
	}
	
	public void add(Employee employee){
		this.employees.add(employee);
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
	
	

}
