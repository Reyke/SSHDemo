package com.qvc.cn.it.report.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name = "demo")
public class Demo {
	private long id;
	private String name;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(nullable = false, name = "id", unique = true, updatable = false, precision=10, columnDefinition="int(11)")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(nullable = false, name = "name", length = 45, updatable = true, columnDefinition="varchar(45)")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
