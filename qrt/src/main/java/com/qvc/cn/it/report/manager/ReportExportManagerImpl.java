package com.qvc.cn.it.report.manager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qvc.cn.it.report.dao.IGenericDAO;
import com.qvc.cn.it.report.dao.IssuesDAO;
import com.qvc.cn.it.report.dao.TaskDAO;
import com.qvc.cn.it.report.dao.TestSetDAO;
import com.qvc.cn.it.report.model.Issues;
import com.qvc.cn.it.report.model.ReportPOJO;
import com.qvc.cn.it.report.model.Task;
import com.qvc.cn.it.report.utils.ExcelUtil;

@Service
public class ReportExportManagerImpl extends BaseManagerImpl implements ReportExportManager {
	private final SimpleDateFormat originalFormat = new SimpleDateFormat("yyyyMMdd");
	private final SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Resource
	private TestSetDAO testSetDAO;

	@Resource
	private TaskDAO taskDAO;
	
	@Resource
	private IssuesDAO issuesDAO;
	
	@Resource(name = "reportDAO")
	private IGenericDAO<ReportPOJO, Long> reportDAO;
	
	@Resource
	protected SessionFactory sessionFactory;
	
	@Transactional
	@Override
	public List<ReportPOJO> getReportByTaskId(Long taskID) {
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("taskId", taskID);
//		List<ReportPOJO> result = reportDAO.getEntity(params);
		 String sql ="select test_case.task_id as taskId, test_case.case_id as caseId, test_case.set_id as setId, "
				+ " test_set.set_name as testSetName, test_case.case_name as testCaseName, test_case.tester as responser, "
				+ " case when issues.case_id is not null and test_case.original_status = 'Passed' then 'NOT RUN' else test_case.original_status end as status, "
				+ " issues.emp_nbr as tester, issues.issue_type as issueType, issues.actual_result as actualResult, "
				+ " issues.after_change as afterChange, issues.before_change as beforeChange, issues.data as data, "
				+ " issues.data_type as dataType, issues.defect_number as defectNumber, issues.defect_status as defectStatus, "
				+ " issues.device_name as deviceName, issues.error_data as errorData, issues.error_description as errorDescription, "
				+ " issues.error_page_name as errorPageName, issues.expected_result as expectedResult, issues.module_action_name as moduleActionName, "
				+ " issues.reproduced_steps as reproducedSteps, issues.title as title, issues.manual_passed_ind as manualPassedInd"
				+ " from test_case test_case"
				+ " left outer join issues issues on test_case.case_id = issues.case_id and test_case.task_id = issues.task_id "
				+ " and test_case.set_id = issues.set_id and test_case.test_instance_id = issues.test_instance_id "
				+ " and test_case.version_date_nbr = issues.version_date_nbr "
				+ " left outer join test_set test_set on test_set.set_id = test_case.set_id"
				+ " where test_case.task_id ='" + taskID +"'"
				+ " order by test_case.task_id, test_case.set_id, test_case.case_id";
		
		List<ReportPOJO> result = sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(ReportPOJO.class).list();
			
		return result;
	}

	@Override
	public List<Map<String,Object>> getLatestThreeTaskSummaryByTaskId(Long taskID) {
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		List<Task> tasks = taskDAO.getLatestTaskHisByCurrentTaskId(taskID);
		Map<String,Object> summary = null;
		
		for(Task task: tasks){
			summary = new HashMap<String, Object>();
			long totalTestCase = taskDAO.getAllTestCaseCountByTaskId(task.getTask_id());
			long passedTestCase = taskDAO.getPassedTestCaseCountByTaskId(task.getTask_id());
			long failedTestCase = totalTestCase - passedTestCase;
			summary.put(ExcelUtil.EXECUTION_SUMMARY_TITLES[5], totalTestCase);
			summary.put(ExcelUtil.EXECUTION_SUMMARY_TITLES[3], passedTestCase);
			summary.put(ExcelUtil.EXECUTION_SUMMARY_TITLES[4], failedTestCase);
			summary.put(ExcelUtil.EXECUTION_SUMMARY_TITLES[6], "");
			summary.put(ExcelUtil.EXECUTION_SUMMARY_TITLES[0], convertDate(task.getVersionDateNbr()+""));
			summary.put(ExcelUtil.EXECUTION_SUMMARY_TITLES[2], "total");
			summary.put(ExcelUtil.EXECUTION_SUMMARY_TITLES[1], task.getVersionDateNbr());
			result.add(summary);
		}
		return result;
	}
	private String convertDate(String oldFormat){
		String result ="";
		try{
			result = newFormat.format(originalFormat.parse(oldFormat));
		}catch(ParseException pe){
			result = newFormat.format(new Date());
		}
		return result;
	}

}
