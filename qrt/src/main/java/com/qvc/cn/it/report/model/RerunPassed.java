package com.qvc.cn.it.report.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import com.qvc.cn.it.report.utils.Constants;

@XmlRootElement
@Entity
@Table(name = "issues")
@DiscriminatorValue(Constants.ISSUE_TYPE_RERUNPASSED)
public class RerunPassed extends CommonIssue {
	

	public RerunPassed() {
	}

	public RerunPassed(Issues issues) {
		super(issues);
	}
	
	@Override
	@Transient
	public boolean isValidate() {
		return true;

	}

}
