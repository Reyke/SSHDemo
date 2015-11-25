package com.qvc.cn.it.report.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DemoList {
	private List<Demo> reports;

	public DemoList(List<Demo> reports) {
		super();
		this.reports = reports;
	}

	public DemoList() {
		super();
		this.reports = new ArrayList<Demo>();
	}
	
	public void add(Demo report) {
		reports.add(report);
	}

	public List<Demo> getReports() {
		return reports;
	}

	public void setReports(List<Demo> reports) {
		this.reports = reports;
	}
	
	
}
