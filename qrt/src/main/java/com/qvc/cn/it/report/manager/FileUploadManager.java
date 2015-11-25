package com.qvc.cn.it.report.manager;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.qvc.cn.it.report.model.ScreenShot;

public interface FileUploadManager {

	public abstract String getFilePath();

	public abstract void setFilePath(String filePath);

	public abstract String getFileURL();

	public abstract void setFileURL(String fileURL);

	public abstract Map<String,Object> uploadFile(InputStream is, String orginalName);
	
	public boolean uploadExcel (InputStream is, String orginalName);
	
	public boolean saveOrUpdateScreenShot(ScreenShot screenShot);
	
	public List<ScreenShot> findScreenShotByUrl(String imgUrl);

}