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
@DiscriminatorValue(Constants.ISSUE_TYPE_OTHERISSUE)
public class OtherIssue extends CommonIssue {
	public OtherIssue() {
	}

	public OtherIssue(Issues issues) {
		super(issues);
	}
	private String title;
	private String manualPassedInd = "N";
	
	@Column(name = "manual_passed_ind", columnDefinition = "char")
	public String getManualPassedInd() {
		return manualPassedInd;
	}
	public void setManualPassedInd(String manualPassedInd) {
		this.manualPassedInd = manualPassedInd;
	}

	@Column(name = "title", length = 1000)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	@Transient
	public boolean isValidate() {
		if(isEmpty(title)||isEmpty(errorDescription)){
			return false;
		}else{
			return true;
		}
		
	}
}
