package com.qvc.cn.it.report.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qvc.cn.it.report.model.Task;

@Repository
public class TaskDAOImpl extends GenericDAO<Task, Long> implements TaskDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<Task> getAllTaskByScopeId(int scopeId) {

		String hql = "FROM Task T WHERE  T.task_id NOT IN (SELECT ETS.task.task_id FROM EmpTaskSummary ETS) "
				+ " AND T.active_ind = 'Y' "
				+ " AND T.scope.scope_id = "
				+ scopeId;

		List<Task> taskList = this.getSessionFactory()
				.getCurrentSession()
				.createQuery(hql)
				.list();

		taskList = (taskList != null && taskList.size() > 0) 
				? taskList
				: new ArrayList<Task>();

		return taskList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAllLastestWeekTask(String empno, boolean isAdmin) {
		List<Object[]> contents = null;
		if (isAdmin) {
			String queryStr = "select distinct s.country_code.country_code as country_code,"
					+ " s.project_name.project_name as project_name,"
					+ " t.task_name as task_name," + " t.task_id as task_id "
					+ " from Task t, Scope s,EmpTaskSummary ets "
					+ " where t.scope.scope_id = s.scope_id "
					+ " and ets.task.task_id = t.task_id "
					+ " and t.active_ind = 'Y' ";
			contents=sessionFactory.getCurrentSession()
			.createQuery(queryStr)
			.list();

		} else {
			String queryStr = "select s.country_code.country_code as country_code,"
					+ " s.project_name.project_name as project_name,"
					+ " t.task_name as task_name," + " t.task_id as task_id "
					+ " from Task t, Scope s,EmpTaskSummary ets "
					+ " where t.scope.scope_id = s.scope_id "
					+ " and ets.task.task_id = t.task_id "
					+ " and t.active_ind = 'Y' "
					+ " and ets.emp.number =:empno";
			contents=sessionFactory.getCurrentSession()
			.createQuery(queryStr)
			.setParameter("empno", empno)
			.list();
		}
		
		return contents;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Task> getAssignedTaskByScopeId(int scopeId) {
		String hql = "FROM Task T WHERE  T.task_id IN (SELECT ETS.task.task_id FROM EmpTaskSummary ETS) "
				+ " AND T.active_ind = 'Y' "
				+ " AND T.scope.scope_id = "
				+ scopeId;

		List<Task> taskList = this.getSessionFactory()
				.getCurrentSession()
				.createQuery(hql)
				.list();

		taskList = (taskList != null && taskList.size() > 0) 
				? taskList
				: new ArrayList<Task>();

		return taskList;
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getEmpTaskSummary(Long taskId) {
		List<Map<String, Object>> result = null;
		String hql = "select new map(entity.emp as emp,entity.finishedInt as finishedInd) from EmpTaskSummary entity where entity.task.task_id =:taskId";
		result = sessionFactory.getCurrentSession().createQuery(hql)
				.setLong("taskId", taskId).list();
		return result;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Task> getLatestTaskHisByCurrentTaskId(Long taskId) {
		
		String sql = "select t1 from Task t1, Task t2 "
				+ " where t2.folder_id = t2.folder_id "
				+ " and t2.versionDateNbr >= t1.versionDateNbr "
				+ " and t2.task_id =:taskId "
				+ " order by t1.versionDateNbr desc ";
		
		return (List<Task>) sessionFactory
				.getCurrentSession()
				.createQuery(sql)
				.setLong("taskId", taskId)
				.setMaxResults(3)
				.list();
	}

	@Override
	public Long getAllTestCaseCountByTaskId(Long taskId) {
		
		String sql = "select count(*) from TestCase tc where tc.task.task_id = :taskId";
		
		return (Long) sessionFactory
				.getCurrentSession()
				.createQuery(sql)
				.setLong("taskId", taskId)
				.uniqueResult();
	}

	@Override
	public Long getPassedTestCaseCountByTaskId(Long taskId) {
		
		String sql = "select count(*) from TestCase tc "
				+ " where tc.original_status = 'Passed' "
				+ " and tc.task.task_id = :taskId";
		
		return (Long) sessionFactory
				.getCurrentSession()
				.createQuery(sql)
				.setLong("taskId", taskId)
				.uniqueResult();
	}

}
