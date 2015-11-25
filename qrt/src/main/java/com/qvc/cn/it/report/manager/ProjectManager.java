package com.qvc.cn.it.report.manager;

import java.util.List;

import com.qvc.cn.it.report.model.Project;

public interface ProjectManager extends BaseManager {
	
	public List<Project> getAllProject();
	public void addProject(Project project);
	public void updateProject(Project project);
	public void deleteProject(Project project);
	public void deleteProjectByNumber(String number);

}
