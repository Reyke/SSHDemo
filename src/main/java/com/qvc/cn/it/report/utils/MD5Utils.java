package com.qvc.cn.it.report.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

	public static String getMD5(String str){
		char[] hexDigits 			= {'0','1','2','3','4','5','6','7','8','9',  
                						'a','b','c','d','e','f'};
		MessageDigest messageDigest = null;
		try {
			messageDigest 			= MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			return str;
		}
		
		if (str == null) {
			return str;
		}
		
		messageDigest.update(str.getBytes());
		byte[] md 					= messageDigest.digest();
        int j 						= md.length;
        char chars[] 				= new char[j * 2];
        int k = 0;
        for (int i = 0; i < j; i++) {
            byte byte0=md[i];  
            chars[k++] = hexDigits[byte0>>>4 & 0xf];
            chars[k++] = hexDigits[byte0 & 0xf];    
        }
		
		return new String(chars);
	}
	
}
