package com.qvc.cn.it.report.manager;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;

import com.qvc.cn.it.report.model.Task;
import com.qvc.cn.it.report.model.TestCase;

public interface TaskManager extends BaseManager {
	
	public List<Task> getAllTask();
	public void addTask(Task task);
	public void updateTask(Task task);
	public void deleteTask(Task task);
	public List<Task> getTasksByScopeId(int id);
	public List<TestCase> getAllTestCase(long taskid);
	public List<Map<String,Object>> getTaskSummary(int taskId,String EXETime);
	public String assignTask(Long taskId, String admin, List<String> testers, Date deadline,String sendman,String empnum,Date EXETime);
	
	public boolean checkTaskByEmpnoAndTaskId(long taskId,String empno);
	public List<Map<String, Object>> getAllLastestWeekTaskByEmpNo(String empno,short flag);

	public boolean updateCompleteTask(Long taskId, String empno, String operator);

	
	public List<Task> getAssignedTasksByScopeId(int id);
	public List<Map<String, Object>> getEmpTaskSummary(Long taskId);
}
