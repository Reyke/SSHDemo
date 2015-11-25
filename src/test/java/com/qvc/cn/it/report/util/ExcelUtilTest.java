package com.qvc.cn.it.report.util;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.qvc.cn.it.report.manager.ReportExportManager;
import com.qvc.cn.it.report.manager.TemplateManager;
import com.qvc.cn.it.report.model.ReportPOJO;
import com.qvc.cn.it.report.utils.ExcelUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/com/qvc/cn/it/report/applicationContext-test.xml")
public class ExcelUtilTest {

	@Resource
	ReportExportManager reportManager;
	
	@Resource
	TemplateManager templateManager;
	
	@Transactional
	@Test
	public void testGenerateQAReport() {
		List<ReportPOJO> list = reportManager.getReportByTaskId(1560L);

		HSSFWorkbook wb = ExcelUtil.generateQAReport(list, templateManager.getSpecialTemplate(10L), templateManager.getAllIssueComponent(),reportManager.getLatestThreeTaskSummaryByTaskId(1560L));
		
		try {
			String filePath = "C:/t.xls";
//			File file = new File(filePath);
//			file.deleteOnExit();
//			file = null;
			wb.write(new FileOutputStream(filePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
