package com.qvc.cn.it.report.manager;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.qvc.cn.it.report.dao.IGenericDAO;
import com.qvc.cn.it.report.dao.IssuesDAO;
import com.qvc.cn.it.report.dao.TestCaseDAO;
import com.qvc.cn.it.report.model.CommonIssue;
import com.qvc.cn.it.report.model.DataIssueType;
import com.qvc.cn.it.report.model.DefectStatusType;
import com.qvc.cn.it.report.model.Employee;
import com.qvc.cn.it.report.model.IssueType;
import com.qvc.cn.it.report.model.Issues;
import com.qvc.cn.it.report.model.IssuesPOJO;
import com.qvc.cn.it.report.model.ScreenShot;
import com.qvc.cn.it.report.model.TestCase;
import com.qvc.cn.it.report.utils.IssueFactory;

@Service
public class IssueManagerImpl extends BaseManagerImpl implements IssuesManager {
	@Resource
	private IssuesDAO issuesDAO;
	
	@Resource
	private TestCaseDAO testCaseDAO;
	
	@Resource
	private IGenericDAO<IssueType, String> issueTypeDAO;
	
	@Resource
	private IGenericDAO<DataIssueType, String> dataIssueTypeDAO;
	
	@Resource
	private IGenericDAO<DefectStatusType, String> defectStatusTypeDAO;
	
	@Resource
	private IGenericDAO<ScreenShot, Long> screenShotDAO;
	
	@Override
	public Issues getIssueByCaseId(Long caseId){
		
		return issuesDAO.getByKey(caseId);
	}
	
	public String updateIssue(IssuesPOJO issuesPOJO){
	
		long caseId = issuesPOJO.getCaseId();
		Issues issues = issuesDAO.getByKey(caseId);
		
		/**
		 * set the testId and refFolderId
		 */
		TestCase testCase = testCaseDAO.getByKey(issuesPOJO.getCaseId());
		issuesPOJO.setTestId(testCase.getTest_id());
		issuesPOJO.setRefFolderId(testCase.getRefFolderId());
		
		Issues issuesNew = IssueFactory.createIssueInstance(issues, issuesPOJO);
		
		if (issuesNew instanceof CommonIssue) {
			CommonIssue ci = (CommonIssue)issuesNew;
			
			Iterator<ScreenShot> sss = ci.getErrorScreenshotSet().iterator();
			
			while(sss.hasNext()) {
				ScreenShot s = sss.next();
				if (s.isDelInd()) {
					sss.remove();
					screenShotDAO.delete(s);
				}
			}
		}


		issuesDAO.delete(issues);
		issuesDAO.flush();
		issuesDAO.save(issuesNew);
		return issuesNew.getStatus();
		
	
	}
	
	@Override
	@Cacheable(value="issueType")
	public List<IssueType> getAllIssueType() {
		
		return issueTypeDAO.listAll();
	}
	@Override
	@Cacheable(value="dataIssueType")
	public List<DataIssueType> getAllDataIssueType(){
		
		return dataIssueTypeDAO.listAll();
	}
	@Override
	@Cacheable(value="defectStatusType")
	public List<DefectStatusType> getAllDefectStatusType(){
		
		return defectStatusTypeDAO.listAll();
	}

	@Override
	public List<Employee> getTesterByTask(String taskId){

		return issuesDAO.getTesterByTask(taskId);
	}

}
