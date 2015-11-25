package com.qvc.cn.it.report.dao;

import java.util.List;

import com.qvc.cn.it.report.model.Employee;
import com.qvc.cn.it.report.model.Issues;

public interface IssuesDAO extends IGenericDAO<Issues, Long>{
	public List<Employee> getTesterByTask(String taskId);
}
