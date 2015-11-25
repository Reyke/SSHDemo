package com.qvc.cn.it.report.utils;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.springframework.aop.ThrowsAdvice;

public class ExceptionLogger implements ThrowsAdvice {
	
	private String loggerName;

	private Logger logger;
	
	public String getLoggerName() {
		return loggerName;
	}

	public void setLoggerName(String loggerName) {
		this.loggerName = loggerName;
	}
	
	private synchronized Logger initLogger() {
		if (logger == null) {
			logger = Logger.getLogger(loggerName);
		}
		return logger;
	}
	
	/**
	 * log exception of DAO layer via Spring AOP.
	 * @param method	the method throws this exception
	 * @param args		args for this method.
	 * @param target	the object contained this method.
	 * @param ex		the exception object.
	 */
	public void afterThrowing(JoinPoint jp, Exception ex) {
		initLogger();
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("Error occurred, the exception object is : [");
		Object o = jp.getThis();
		sb.append(o.getClass().getName());
		sb.append("], \nthe exception method is : [");
		Signature s = jp.getSignature();
		sb.append(s.getDeclaringTypeName());
		sb.append(" ");
		sb.append(s.getName());
		sb.append(" (");
		for (Object c : jp.getArgs()) {
			sb.append(c.getClass().getName());
			sb.append(", ");
		}
		sb.append(") ], ");
		
		Object[] args = jp.getArgs();
		
		if (args != null && args.length > 0 ) {
			sb.append("the parameter[s] is/are : [");
			for (Object p : args) {
				sb.append(p.getClass().getName());
				sb.append(" ");
				sb.append(p.toString());
				sb.append(", ");
			}
			sb.append("], ");
		}
		
		sb.append("\nexception is : [");
		sb.append(ex.getClass().getName());
		sb.append("], \nError Description : [");
		sb.append(ex.getMessage());
		sb.append("], \nError Stack : [");
		
		for (StackTraceElement ele : ex.getStackTrace()) {
			sb.append(ele.toString() + "\n");
		}
		sb.append("]");
		
		logger.error(sb.toString());
	}
}
