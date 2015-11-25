package com.qvc.cn.it.report.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProjectList {
	private List<Project> projects;

	public ProjectList(List<Project> projects) {
		super();
		this.projects = projects;
	}

	public ProjectList() {
		super();
		this.projects = new ArrayList<Project>();
	}
	
	public void add(Project report) {
		projects.add(report);
	}

	public List<Project> getprojects() {
		return projects;
	}

	public void setprojects(List<Project> projects) {
		this.projects = projects;
	}
	
	
}
