package com.qvc.cn.it.report.model;

import java.util.Arrays;

public class IssuesPOJO extends CommonIssue {
	private Long[] ssId;
	
	
	
	
	public Long[] getSsId() {
		return ssId;
	}
	public void setSsId(Long[] ssId) {
		this.ssId = ssId;
	}
	/*
	 * DataIssue
	 */
	private String dataType;
	private String errorData;
	
	/*
	 * DeviceIssue
	 */
	private String deviceName;
	
	/*
	 * KnownIssue
	 */
	private String errorPageName;
	private String data;
	private String title;
	private String  defectNumber;
	private String defectStatus;
	private String actualResult;
	private String expectedResult;
	private String reproducedSteps;

	/*
	 * ObjectChange
	 */

	private String moduleActionName;
//	private String errorPageName;
	private String beforeChange;
	private String afterChange;
	
	private String manualPassedInd;
	
	public String getManualPassedInd() {
		return manualPassedInd;
	}
	public void setManualPassedInd(String manualPassedInd) {
		this.manualPassedInd = manualPassedInd;
	}
	
	
	/*
	 * OtherIssue
	 */
//	private String title;
	
	/*
	 * PerformanceIssue
	 */
//	private String moduleActionName;
//	private String errorPageName;
	
	/*
	 * PossibleDefect
	 */
//	private String errorPageName;
//	private String data;
//	private String title;
//	private String defectNumber;
//	private String defectStatus;
//	private String actualResult;
//	private String expectedResult;
//	private String reproducedSteps;
	
	/*
	 * RerunPassed
	 */

	/*
	 * ScriptIssue
	 */
//	private String moduleActionName;
//	private String errorPageName;
	
	
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getErrorData() {
		return errorData;
	}
	public void setErrorData(String errorData) {
		this.errorData = errorData;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getErrorPageName() {
		return errorPageName;
	}
	public void setErrorPageName(String errorPageName) {
		this.errorPageName = errorPageName;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public String getActualResult() {
		return actualResult;
	}
	public void setActualResult(String actualResult) {
		this.actualResult = actualResult;
	}
	public String getExpectedResult() {
		return expectedResult;
	}
	public void setExpectedResult(String expectedResult) {
		this.expectedResult = expectedResult;
	}
	public String getReproducedSteps() {
		return reproducedSteps;
	}
	public void setReproducedSteps(String reproducedSteps) {
		this.reproducedSteps = reproducedSteps;
	}
	public String getModuleActionName() {
		return moduleActionName;
	}
	public void setModuleActionName(String moduleActionName) {
		this.moduleActionName = moduleActionName;
	}
	public String getBeforeChange() {
		return beforeChange;
	}
	public void setBeforeChange(String beforeChange) {
		this.beforeChange = beforeChange;
	}
	public String getAfterChange() {
		return afterChange;
	}
	public void setAfterChange(String afterChange) {
		this.afterChange = afterChange;
	}
	@Override
	public String toString() {
		return "IssuesPOJO [ssId=" + Arrays.toString(ssId) + ", dataType="
				+ dataType + ", errorData=" + errorData + ", deviceName="
				+ deviceName + ", errorPageName=" + errorPageName + ", data="
				+ data + ", title=" + title + ", defectNumber=" + defectNumber
				+ ", defectStatus=" + defectStatus + ", actualResult="
				+ actualResult + ", expectedResult=" + expectedResult
				+ ", reproducedSteps=" + reproducedSteps
				+ ", moduleActionName=" + moduleActionName + ", beforeChange="
				+ beforeChange + ", afterChange=" + afterChange + "]";
	}
	
	
	
	
}
