package com.qvc.cn.it.report.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import com.qvc.cn.it.report.utils.Constants;

@XmlRootElement
@Entity
@Table(name = "issues")
@DiscriminatorValue(Constants.ISSUE_TYPE_KNOWISSUE)
public class KnownIssue extends CommonIssue {
	public KnownIssue() {
	}

	public KnownIssue(Issues issues) {
		super(issues);

	}

	private String errorPageName;
	private String data;
	private String title;
	private String defectNumber;
	private String defectStatus;
	private String actualResult;
	private String expectedResult;
	private String reproducedSteps;
	
	@Column(name = "error_page_name", length = 1000)
	public String getErrorPageName() {
		return errorPageName;
	}
	public void setErrorPageName(String errorPageName) {
		this.errorPageName = errorPageName;
	}
	@Column(name = "data", length = 1000)
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	@Column(name = "title", length = 1000)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name = "defect_number", length = 1000)
	public String getDefectNumber() {
		return defectNumber;
	}
	public void setDefectNumber(String defectNumber) {
		this.defectNumber = defectNumber;
	}
	
	@Column(name = "defect_status", length = 1000)
	public String getDefectStatus() {
		return defectStatus;
	}
	public void setDefectStatus(String defectStatus) {
		this.defectStatus = defectStatus;
	}
	
	@Column(name = "actual_result", length = 1000)
	public String getActualResult() {
		return actualResult;
	}
	public void setActualResult(String actualResult) {
		this.actualResult = actualResult;
	}
	
	@Column(name = "expected_result", length = 1000)
	public String getExpectedResult() {
		return expectedResult;
	}
	public void setExpectedResult(String expectedResult) {
		this.expectedResult = expectedResult;
	}
	
	@Column(name = "reproduced_steps", length = 1000)
	public String getReproducedSteps() {
		return reproducedSteps;
	}
	public void setReproducedSteps(String reproducedSteps) {
		this.reproducedSteps = reproducedSteps;
	}
	
	@Override
	@Transient
	public boolean isValidate() {
		if(isEmpty(defectNumber)){
			return false;
		}else{
			return true;
		}
		
	}

	
}
