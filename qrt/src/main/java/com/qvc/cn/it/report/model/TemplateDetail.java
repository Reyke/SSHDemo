package com.qvc.cn.it.report.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name = "template_detail")

public class TemplateDetail {
	private long tdId;
	private String columnLable;
	private String expression;
	private int columnIndex;
	private TemplateHead templateHead;
	private String createdBy;
	private Date createdAt;

	@Id
	@GeneratedValue
	@Column(name="td_id")
	public long getTdId() {
		return tdId;
	}

	public void setTdId(long tdId) {
		this.tdId = tdId;
	}

	@Column(name = "column_lable")
	public String getColumnLable() {
		return columnLable;
	}

	public void setColumnLable(String columnLable) {
		this.columnLable = columnLable;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	@Column(name = "column_index")
	public int getColumnIndex() {
		return columnIndex;
	}

	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}

	@ManyToOne(cascade = {CascadeType.PERSIST})
	@JoinColumn(name = "template_id")
	@JsonIgnore
	public TemplateHead getTemplateHead() {
		return templateHead;
	}

	public void setTemplateHead(TemplateHead templateHead) {
		this.templateHead = templateHead;
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

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

}
