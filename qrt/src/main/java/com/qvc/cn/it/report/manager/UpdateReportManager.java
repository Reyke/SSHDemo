package com.qvc.cn.it.report.manager;

import java.util.List;

import com.qvc.cn.it.report.model.Issues;

public interface UpdateReportManager extends BaseManager {
	
	public List<Issues> getTestCaseHis(long caseId);
}
