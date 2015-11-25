package com.qvc.cn.it.report.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.qvc.cn.it.report.model.Employee;
import com.qvc.cn.it.report.model.Issues;
@Repository
public class IssuesDAOImpl extends GenericDAO<Issues, Long> implements IssuesDAO {

	@Override
	public List<Employee> getTesterByTask(String taskId) {
		
		String sql = "SELECT DISTINCT(responser.number),responser.name FROM Issues "
				+ " where taskId = :taskId";
		
		return  sessionFactory
				.getCurrentSession()
				.createQuery(sql)
				.setParameter("taskId", Long.parseLong(taskId))
				.list();
	}

}
