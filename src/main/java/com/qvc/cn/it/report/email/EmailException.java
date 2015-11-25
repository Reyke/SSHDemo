package com.qvc.cn.it.report.email;

import com.qvc.cn.it.report.BaseException;

public class EmailException extends BaseException {
	private static final long serialVersionUID = 1L;

	public EmailException() {
		super();
	}

	public EmailException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public EmailException(String arg0) {
		super(arg0);
	}

	public EmailException(Throwable arg0) {
		super(arg0);
	}

}
