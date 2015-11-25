package com.qvc.cn.it.report.manager;


import java.util.List;

import com.qvc.cn.it.report.model.Employee;

public interface LoginManager extends BaseManager {
	
	public List<Employee> isPasswordVaild(Employee emp);
	
}
