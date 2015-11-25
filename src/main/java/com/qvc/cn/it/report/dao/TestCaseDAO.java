package com.qvc.cn.it.report.dao;

import java.util.Date;
import java.util.List;

import com.qvc.cn.it.report.model.Issues;
import com.qvc.cn.it.report.model.TestCase;

public interface TestCaseDAO extends IGenericDAO<TestCase, Long> {
	
	public List<TestCase> getTestCaseByTaskID(Long taskID);
	public List<List<Long>> getSmokingSummary(int taskId,String EXETime);
	public List<TestCase> getnotSmokeTestCaseByTaskID(Long taskID,Date EXETime);
	public List<TestCase> getSmokeTestCaseByTaskID(Long taskID,Date EXETime);
	public List<Issues> getAssignedTestCaseByTaskIDAndEmployNo(Long taskID, String empno, boolean isAdmin,String userId,String issueType);
	
	public List<Issues> getCurrentTestCaseHis(long caseId);

}
