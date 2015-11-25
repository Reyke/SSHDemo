package com.qvc.cn.it.report.manager;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/com/qvc/cn/it/report/applicationContext-test.xml")
public class FileUploadManagerTest {

	@Resource
	FileUploadManager fileUploadManager;
	
	@Test
	public void test() {
		InputStream is = null;
		try {
			is = new FileInputStream("D:/DATA_MODEL_0109.jpg");
			System.out.println(fileUploadManager.uploadFile(is, "TestImage.jpg"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (Exception ex){} 
		}
	}

}
