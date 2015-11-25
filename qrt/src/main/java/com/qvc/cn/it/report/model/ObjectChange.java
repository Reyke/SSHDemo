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
@DiscriminatorValue(Constants.ISSUE_TYPE_OBJECTCHANGE)
public class ObjectChange extends CommonIssue {
	public ObjectChange() {
	}

	public ObjectChange(Issues issues) {
		super(issues);

	}
	private String moduleActionName;
	private String errorPageName;
	private String beforeChange;
	private String afterChange;
	
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
	
	@Column(name = "before_change", length = 1000)
	public String getBeforeChange() {
		return beforeChange;
	}
	public void setBeforeChange(String beforeChange) {
		this.beforeChange = beforeChange;
	}
	
	@Column(name = "after_change", length = 1000)
	public String getAfterChange() {
		return afterChange;
	}
	public void setAfterChange(String afterChange) {
		this.afterChange = afterChange;
	}
	
	@Override
	@Transient
	public boolean isValidate() {
		if (isEmpty(moduleActionName) || isEmpty(errorPageName)||isEmpty(beforeChange)||isEmpty(afterChange)
				|| isEmpty(errorDescription)) {
			return false;
		} else {
			return true;
		}

	}

}
