package com.qvc.cn.it.report.manager;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.qvc.cn.it.report.dao.IGenericDAO;
import com.qvc.cn.it.report.model.ScreenShot;
import com.qvc.cn.it.report.utils.FileUtils;

@Service
public class FileUploadManagerImpl extends BaseManagerImpl implements FileUploadManager {

	@Resource
	private IGenericDAO<ScreenShot, Long> screenShotDAO;
	
	@Value(value = "${file.uploader.put.path}")
	private String filePath;
	@Value("${file.uploader.get.url}")
	private String fileURL;
	
	@Value(value = "${excel.import.path}")
	private String excelUploadPath;
	
	public String getExcelUploadPath() {
		return excelUploadPath;
	}

	public void setExcelUploadPath(String excelUploadPath) {
		this.excelUploadPath = excelUploadPath;
	}

	/* (non-Javadoc)
	 * @see com.qvc.cn.it.report.manager.FileUploadManager#getFilePath()
	 */
	@Override
	public String getFilePath() {
		return filePath;
	}

	/* (non-Javadoc)
	 * @see com.qvc.cn.it.report.manager.FileUploadManager#setFilePath(java.lang.String)
	 */
	@Override
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/* (non-Javadoc)
	 * @see com.qvc.cn.it.report.manager.FileUploadManager#getFileURL()
	 */
	@Override
	public String getFileURL() {
		return fileURL;
	}

	/* (non-Javadoc)
	 * @see com.qvc.cn.it.report.manager.FileUploadManager#setFileURL(java.lang.String)
	 */
	@Override
	public void setFileURL(String fileURL) {
		this.fileURL = fileURL;
	}

	/* (non-Javadoc)
	 * @see com.qvc.cn.it.report.manager.FileUploadManager#uploadFile(java.io.InputStream, java.lang.String)
	 */
	@Override
	public Map<String,Object> uploadFile (InputStream is, String orginalName) {
		//fileURL;
		String path =  FileUtils.moveFile(is, orginalName, filePath);
		ScreenShot screenShot = new ScreenShot();
		screenShot.setScreenShotLink(path);
		this.saveOrUpdateScreenShot(screenShot);
		ScreenShot scShot = this.findScreenShotByUrl(path).get(0);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("url", path);
		map.put("id", scShot.getSsId());
		
		return map;
	}
	
	/**
	 * 
	 * @param is inputstream of upload file
	 * @param orginalName file name
	 * @return
	 */
	public boolean uploadExcel (InputStream is, String orginalName) {
		//fileURL;
		String path =  FileUtils.moveFile(is, orginalName, "", filePath);
		
		return true;
	}

	@Override
	public boolean saveOrUpdateScreenShot(ScreenShot screenShot) {
		screenShotDAO.saveOrUpdate(screenShot);
		
		return true;
	}

	@Override
	public List<ScreenShot> findScreenShotByUrl(String imgUrl) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("screenShotLink",imgUrl);
		
		return screenShotDAO.getEntity(map);
	}
}
