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
@DiscriminatorValue(Constants.ISSUE_TYPE_PERFORMANCEISSUE)
public class PerformanceIssue extends CommonIssue {
	public PerformanceIssue() {
	}

	public PerformanceIssue(Issues issues) {
		super(issues);
	}
	
	private String moduleActionName;
	private String errorPageName;
	
	@Column(name = "module_action_name", length = 1000)
	public String getModuleActionName() {
		return moduleActionName;
	}
	public void setModuleActionName(String moduleActionName) {
		this.moduleActionName = moduleActionName;
	}
	
	@Column(name = "error_page_name", length = 1000)
	public String getErrorPageName() {
		return errorPageName;
	}
	public void setErrorPageName(String errorPageName) {
		this.errorPageName = errorPageName;
	}
	
	@Override
	@Transient
	public boolean isValidate() {
		if (isEmpty(moduleActionName) || isEmpty(errorPageName)
				|| isEmpty(errorDescription)) {
			return false;
		} else {
			return true;
		}

	}
	
}
