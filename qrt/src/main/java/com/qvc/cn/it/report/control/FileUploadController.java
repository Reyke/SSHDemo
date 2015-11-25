package com.qvc.cn.it.report.control;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.qvc.cn.it.report.manager.FileUploadManager;

@Controller
@Scope("prototype")
public class FileUploadController extends BaseController {

	@Resource
	private FileUploadManager fileUploadManager;
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/fileUpload")
	public Map<String,Object> fileUpload(@RequestParam("myfile") MultipartFile myfile){
		
		Map<String,Object> map = null;
		
		try {
		
		  map = fileUploadManager.uploadFile(
				myfile.getInputStream(),
				myfile.getOriginalFilename()
				);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	/**
	 * 
	 * Excel upload 
	 * 
	 * @param myfile
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/excelUpload")
	public boolean excelUpload(@RequestParam("myfile") MultipartFile myfile){
		
		boolean flag = false;
		
		try {
			flag = fileUploadManager.uploadExcel(
					myfile.getInputStream(),
					myfile.getOriginalFilename()
					);
		} catch (Exception e) {
			e.printStackTrace();
			
			flag = false;
		}
		
		return flag;
	}
	
}
