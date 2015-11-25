package com.qvc.cn.it.report.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TaskList {
	private List<Task> tasks;

	public TaskList(List<Task> tasks) {
		super();
		this.tasks = tasks;
	}

	public TaskList() {
		super();
		this.tasks = new ArrayList<Task>();
	}
	
	public void add(Task report) {
		tasks.add(report);
	}

	public List<Task> gettasks() {
		return tasks;
	}

	public void settasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	
	
}
