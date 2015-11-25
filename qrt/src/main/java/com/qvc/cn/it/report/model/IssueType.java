package com.qvc.cn.it.report.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name = "issue_type")
public class IssueType {
	
	private String id;
	private String issueTypeDesc;

	@Id
	@Column(name = "issue_type")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "issue_type_desc", length = 1000)
	public String getIssueTypeDesc() {
		return issueTypeDesc;
	}

	public void setIssueTypeDesc(String issueTypeDesc) {
		this.issueTypeDesc = issueTypeDesc;
	}

}
