package com.qvc.cn.it.report.utils;

import java.io.InputStream;
import java.io.OutputStream;

public class IOUtil extends Thread{
	
	private InputStream inputStream;
	
	private OutputStream outputStream;
	
	private StringBuffer inputs = null;
	private StringBuffer outputs = null;
	
	private boolean inputStop = false;
	private boolean outputStop = false;
	
	
	public IOUtil(InputStream inputStream) {
		inputs = new StringBuffer();
		outputs = new StringBuffer();
		this.inputStream = inputStream;
	}
	
	public IOUtil(OutputStream outputStream) {
		inputs = new StringBuffer();
		outputs = new StringBuffer();
		this.outputStream = outputStream;
	}
	
	
	@Override
	public void run() {
		super.run();
		byte[] temp = new byte[1024*1024];
		int size = 0;
		try {
			while(!outputStop && (size = (inputStream.read(temp))) > 0){
				outputs.append(new String(temp,0,size,"GBK"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public StringBuffer getInputs() {
		return inputs;
	}


	public String getOutputs() {
		return outputs.toString();
	}

	public void setOutputStop(boolean outputStop) {
		this.outputStop = outputStop;
	}

	public void setInputStop(boolean inputStop) {
		this.inputStop = inputStop;
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public OutputStream getOutputStream() {
		return outputStream;
	}

	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}
}
