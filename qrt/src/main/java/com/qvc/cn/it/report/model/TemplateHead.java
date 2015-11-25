package com.qvc.cn.it.report.model;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "template_head")
public class TemplateHead {

	private Long templateId;
	private String name;
	private String status;
	private String createdBy;
	private Date createdAt;
	private Set<TemplateDetail> templateDetails = new TreeSet<TemplateDetail>();

	@Id
	@Column(name = "template_id")
	@GeneratedValue
	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "status", columnDefinition = "char(1)")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "created_by", columnDefinition = "char(8)")
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "created_at")
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createAt) {
		this.createdAt = createAt;
	}

	@OneToMany(mappedBy="templateHead", cascade=CascadeType.ALL)
	@Fetch(FetchMode.JOIN)
	@OrderBy("columnIndex ASC")
	public Set<TemplateDetail> getTemplateDetails() {
		return templateDetails;
	}

	public void setTemplateDetails(Set<TemplateDetail> templateDetails) {
		this.templateDetails = templateDetails;
	}
	
	

}
