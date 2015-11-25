package com.qvc.cn.it.report;

public interface Logable {
	public void debug(String message);
	
	public void error(String message);
	
	public void warn(String message);
	
	public void audit(String message);
}
