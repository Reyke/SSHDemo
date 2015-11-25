package com.qvc.cn.it.report;

import org.apache.log4j.Logger;

public class BaseSpringManagedBean implements SpringManagedBean {

	public Logger getLogger() {
		return Logger.getLogger(this.getClass());
	}

	public void init() {
		debug(this.getClass().getName() + " init...");
	}

	public void destroy() {
		debug(this.getClass().getName() + " destroy...");
	}

	public void debug(String message) {
		getLogger().debug(this.getClass().getName() + " : " + message);
	}

	public void error(String message) {
		getLogger().error(this.getClass().getName() + " : " + message);
	}

	public void warn(String message) {
		getLogger().warn(this.getClass().getName() + " : " + message);
	}

	public void audit(String message) {
		getLogger().debug(this.getClass().getName() + " : " + message);
	}
}
