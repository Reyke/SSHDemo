package com.qvc.cn.it.report.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.qvc.cn.it.report.dao.IGenericDAO;
import com.qvc.cn.it.report.model.Project;

@Service
@Scope("prototype")
public class ProjectManagerImpl extends BaseManagerImpl implements
		ProjectManager {
	@Resource
	private IGenericDAO<Project, String> projectDAO;

	@Override
	public List<Project> getAllProject() {
		return projectDAO.listAll();
	}

	@Override
	public void addProject(Project project) {
		projectDAO.save(project);
	}

	@Override
	public void updateProject(Project project) {
		projectDAO.update(project);

	}

	@Override
	public void deleteProject(Project project) {
		projectDAO.delete(project);
	}

	@Override
	public void deleteProjectByNumber(String number) {
		// TODO Auto-generated method stub

	}

}
