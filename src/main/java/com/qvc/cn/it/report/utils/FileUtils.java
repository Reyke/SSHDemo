package com.qvc.cn.it.report.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class FileUtils {
	
	/**
	 * 
	 * @param is input file stream
	 * @param tempName old file name
	 * @param putPath upload file path
	 * @return
	 */
	public static String moveFile(InputStream is, String tempName, String putPath){
		FileOutputStream fs 	= null;
		byte[] buffer 			= new byte[1024*1024];
		Calendar calendar 		= Calendar.getInstance();
		int year 				= calendar.get(Calendar.YEAR);
		int month 				= calendar.get(Calendar.MONTH) + 1;
		int day 				= calendar.get(Calendar.DAY_OF_MONTH);
		String path  			= year + "/" + month+"/" + day + "/";
		
		Random random 			= new Random(1000000);
		String secrityName 		= MD5Utils.getMD5(tempName + random);
		String fileName 		= path + secrityName + tempName.substring(tempName.lastIndexOf("."),tempName.length());
		path 					= putPath + path;
		String filePath 		= putPath + fileName;
		
		FileUtils._createDir(path);
		FileUtils._createFile(filePath);
		
		try {
			fs 					= new FileOutputStream(filePath);
			int bufferSize 		= 0;
			while( (bufferSize = is.read(buffer) ) != -1){
				fs.write(buffer, 0, bufferSize);
				fs.flush();
			}
			fs.close();
			is.close();
			fs = null;
			is = null;
			
			return fileName;
		} catch (Exception e) {
			e.printStackTrace();
			
			return null;
		}
	}
	
	/**
	 * 
	 * @param is input file stream
	 * @param tempName old file name
	 * @param reName rename to
	 * @param putPath file path
	 * @return new file path
	 */
	public static String moveFile(InputStream is, String tempName, String reName, String putPath){
		FileOutputStream fs 	= null;
		byte[] buffer 			= new byte[1024*1024];
		
		String fileName 		= reName + tempName.substring(tempName.lastIndexOf("."),tempName.length());
		String filePath 		= putPath + fileName;
		
		FileUtils._createDir(putPath);
		
		File file = new File(filePath);
		if(file.exists()){
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String newFileName 	= dateFormat.format(new Date());
			FileUtils.reName(filePath, newFileName);
		}
		
		FileUtils._createFile(filePath);
		
		try {
			fs 					= new FileOutputStream(filePath);
			int bufferSize 		= 0;
			while( (bufferSize = is.read(buffer) ) != -1){
				fs.write(buffer, 0, bufferSize);
				fs.flush();
			}
			fs.close();
			is.close();
			fs = null;
			is = null;
			
			return fileName;
		} catch (Exception e) {
			e.printStackTrace();
			
			return null;
		}
	}
	
	private static boolean _createDir(String path){
		File file = new File(path);
		if(!file.exists()){
			file.mkdirs();
			
			return true;
		}
		
		return false;
	}
	
	private static boolean _createFile(String path){
		File file = new File(path);
		if(!file.exists()){
			try {
				file.createNewFile();
				
				return true;
			} catch (IOException e) {
				
				return false;
			}
		}
		
		return false;
	}
	
	public static boolean reName(String path,String reName){
		
		File file = null;
		String tempFilePath = path;
		try {
			file = new File(path);
			String newFile = tempFilePath.substring(0, tempFilePath.lastIndexOf("/") + 1);
			newFile += reName ;
			newFile += tempFilePath.substring(tempFilePath.lastIndexOf("."),tempFilePath.length());
			
			FileUtils._createFile(newFile);
			
			file.renameTo(new File(newFile));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
