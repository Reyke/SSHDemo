package com.qvc.cn.it.report.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ScopeList {
	private List<Scope> scopes;

	public ScopeList() {
		super();
		this.scopes = new ArrayList<Scope>();
	}
	
	public void add(Scope report) {
		scopes.add(report);
	}

	public List<Scope> getscopes() {
		return scopes;
	}

	public void setscopes(List<Scope> scopes) {
		this.scopes = scopes;
	}
	
	
}
