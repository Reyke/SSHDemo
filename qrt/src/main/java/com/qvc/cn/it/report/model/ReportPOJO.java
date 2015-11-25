package com.qvc.cn.it.report.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Subselect;

@Entity
@Subselect("select test_case.task_id as taskId, test_case.case_id as caseId, test_case.set_id as setId, "
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
		+ " order by test_case.task_id, test_case.set_id, test_case.case_id")
public class ReportPOJO {
	private Long taskId;
	private Long caseId;
	private Long setId;
	private String testSetName;
	private String testCaseName;
	private String responser;
	private String status;
	private String tester;
	private String issueType;
	private String actualResult;
	private String afterChange;
	private String beforeChange;
	private String data;
	private String dataType;
	private String defectNumber;
	private String defectStatus;
	private String deviceName;
	private String errorData;
	private String errorDescription;
	private String errorPageName;
	private String expectedResult;
	private String moduleActionName;
	private String reproducedSteps;
	private String title;
	private String manualPassedInd;
	
	public String getManualPassedInd() {
		return manualPassedInd;
	}
	public void setManualPassedInd(String manualPassedInd) {
		this.manualPassedInd = manualPassedInd;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getSetId() {
		return setId;
	}

	public void setSetId(Long setId) {
		this.setId = setId;
	}

	@Id
	@Column(insertable = false, updatable = false, unique = true, nullable = false)
	public Long getCaseId() {
		return caseId;
	}

	public void setCaseId(Long caseId) {
		this.caseId = caseId;
	}

	public String getTestSetName() {
		return testSetName;
	}

	public void setTestSetName(String testSetName) {
		this.testSetName = testSetName;
	}

	public String getTestCaseName() {
		return testCaseName;
	}

	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
	}

	public String getResponser() {
		return responser;
	}

	public void setResponser(String responser) {
		this.responser = responser;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTester() {
		return tester;
	}

	public void setTester(String tester) {
		this.tester = tester;
	}

	public String getIssueType() {
		return issueType;
	}

	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}

	public String getActualResult() {
		return actualResult;
	}

	public void setActualResult(String actualResult) {
		this.actualResult = actualResult;
	}

	public String getAfterChange() {
		return afterChange;
	}

	public void setAfterChange(String afterChange) {
		this.afterChange = afterChange;
	}

	public String getBeforeChange() {
		return beforeChange;
	}

	public void setBeforeChange(String beforeChange) {
		this.beforeChange = beforeChange;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDefectNumber() {
		return defectNumber;
	}

	public void setDefectNumber(String defectNumber) {
		this.defectNumber = defectNumber;
	}

	public String getDefectStatus() {
		return defectStatus;
	}

	public void setDefectStatus(String defectStatus) {
		this.defectStatus = defectStatus;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getErrorData() {
		return errorData;
	}

	public void setErrorData(String errorData) {
		this.errorData = errorData;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public String getErrorPageName() {
		return errorPageName;
	}

	public void setErrorPageName(String errorPageName) {
		this.errorPageName = errorPageName;
	}

	public String getExpectedResult() {
		return expectedResult;
	}

	public void setExpectedResult(String expectedResult) {
		this.expectedResult = expectedResult;
	}

	public String getModuleActionName() {
		return moduleActionName;
	}

	public void setModuleActionName(String moduleActionName) {
		this.moduleActionName = moduleActionName;
	}

	public String getReproducedSteps() {
		return reproducedSteps;
	}

	public void setReproducedSteps(String reproducedSteps) {
		this.reproducedSteps = reproducedSteps;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
