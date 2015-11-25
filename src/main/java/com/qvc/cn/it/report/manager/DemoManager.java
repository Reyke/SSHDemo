package com.qvc.cn.it.report.manager;

import java.util.List;

import com.qvc.cn.it.report.model.Demo;


public interface DemoManager extends BaseManager{
	public Demo find(long id);
	
	public List<Demo> list();
}