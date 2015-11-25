package com.qvc.cn.it.report.dao;

import java.util.List;
import java.util.Map;

import com.qvc.cn.it.report.model.Task;

public interface TaskDAO extends IGenericDAO<Task, Long> {
	
	public List<Task> getAllTaskByScopeId(int scopeId);
	public List<Task> getAssignedTaskByScopeId(int scopeId);
	public List<Map<String, Object>> getEmpTaskSummary(Long taskId);
	public List<Task> getLatestTaskHisByCurrentTaskId(Long taskId);
	public Long getPassedTestCaseCountByTaskId(Long taskId);
	public Long getAllTestCaseCountByTaskId(Long taskId);
	
	public List<Object[]> getAllLastestWeekTask(String empno, boolean isAdmin);
}
