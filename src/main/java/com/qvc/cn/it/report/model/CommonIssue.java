package com.qvc.cn.it.report.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@MappedSuperclass

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "issue_type")
public class CommonIssue extends Issues {

	protected String errorDescription;
	protected Set<ScreenShot> errorScreenshotSet;

	@Column(name = "error_description",length = 1000)
	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	@OneToMany
	@JoinColumn(name = "issues_case_id")
	public Set<ScreenShot> getErrorScreenshotSet() {
		return errorScreenshotSet;
	}

	public void setErrorScreenshotSet(Set<ScreenShot> errorScreenshotSet) {
		this.errorScreenshotSet = errorScreenshotSet;
	}

	public CommonIssue() {
		super();
	}

	public CommonIssue(Issues issues) {
		super();
		this.setCaseId(issues.getCaseId());
		this.setInstanceId(issues.getInstanceId());
		this.setIssueType(issues.getIssueType());
		this.setResponser(issues.getResponser());
		this.setTaskId(issues.getTaskId());
		this.setTestCase(issues.getTestCase());
		this.setTestSetId(issues.getTestSetId());
		this.setVersionDateNumber(issues.getVersionDateNumber());
	}
	
	
}