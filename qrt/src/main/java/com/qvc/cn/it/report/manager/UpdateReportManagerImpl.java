package com.qvc.cn.it.report.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.qvc.cn.it.report.dao.TestCaseDAO;
import com.qvc.cn.it.report.model.Issues;

@Service
public class UpdateReportManagerImpl extends BaseManagerImpl implements
UpdateReportManager {
	
	@Resource
	private TestCaseDAO testCaseDAO ;
	
	@Override
	public List<Issues> getTestCaseHis(long caseId) {
		return testCaseDAO.getCurrentTestCaseHis(caseId);
	}

}
