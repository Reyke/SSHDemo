package com.qvc.cn.it.report.manager;

import java.util.List;

import com.qvc.cn.it.report.model.DataIssueType;
import com.qvc.cn.it.report.model.DefectStatusType;
import com.qvc.cn.it.report.model.Employee;
import com.qvc.cn.it.report.model.IssueType;
import com.qvc.cn.it.report.model.Issues;
import com.qvc.cn.it.report.model.IssuesPOJO;

public interface IssuesManager extends BaseManager {
	public Issues getIssueByCaseId(Long caseId);
	public String updateIssue(IssuesPOJO issuesPOJO);
	public List<IssueType> getAllIssueType();
	public List<DataIssueType> getAllDataIssueType();
	public List<DefectStatusType> getAllDefectStatusType();
	public List<Employee> getTesterByTask(String taskId);
}
