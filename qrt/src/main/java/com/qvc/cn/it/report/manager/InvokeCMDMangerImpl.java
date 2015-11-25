package com.qvc.cn.it.report.manager;

import java.io.IOException;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.qvc.cn.it.report.utils.IOUtil;

@Service
public class InvokeCMDMangerImpl implements InvokeCMDManager {

	@Value(value = "${excel.import.path}")
	private String excelPath;
	
	@Value(value = "${excel.import.executor}")
	private String excelExcutor;
	
	public String getExcelPath() {
		return excelPath;
	}

	public void setExcelPath(String excelPath) {
		this.excelPath = excelPath;
	}

	public String getExcelExcutor() {
		return excelExcutor;
	}

	public void setExcelExcutor(String excelExcutor) {
		this.excelExcutor = excelExcutor;
	}
	
	
	/**
	 * <li>this method is for ALM data resource import</li>
	 * <li>execute the cmd command</li>
	 * <li>excelExcutor cmd from properties file</li>
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	@Override
	public String executeExcelImportCMD() throws InterruptedException, IOException {
		
		Process process 	= Runtime.getRuntime().exec(excelExcutor);
		InputStream is  	= process.getInputStream();
		IOUtil ioUtil 		= new IOUtil(is);
		ioUtil.start();
		ioUtil.join();
		String result 		= ioUtil.getOutputs();
		
		if(process != null){
			process.destroy();
			process = null;
		}
		
		return result;
	}
	
}
