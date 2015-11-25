package com.qvc.cn.it.report;

public class BaseException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public BaseException() {
		super();
	}

	public BaseException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public BaseException(String arg0) {
		super(arg0);
	}

	public BaseException(Throwable arg0) {
		super(arg0);
	}

}
