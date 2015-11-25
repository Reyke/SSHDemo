package com.qvc.cn.it.report.control;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.qvc.cn.it.report.manager.ReportExportManager;
import com.qvc.cn.it.report.manager.TemplateManager;
import com.qvc.cn.it.report.model.ReportPOJO;
import com.qvc.cn.it.report.utils.ExcelUtil;

@Controller
public class ReportController extends BaseController {

	@Resource
	ReportExportManager reportExportManager;
	
	@Resource 
	TemplateManager templateManager;

	@RequestMapping(method=RequestMethod.GET, value="/report")
	public ModelAndView generateReport(ModelMap model,
			HttpServletRequest request, HttpServletResponse response) {

		Long taskId = Long.parseLong(request.getParameter("taskId"));
		Long templateId = Long.parseLong(request.getParameter("templateId"));

		ExcelReportView viewExcel 	= new ExcelReportView();
		Map<String, Object> obj 	= null;
		List<ReportPOJO> list 		= reportExportManager.getReportByTaskId(taskId);
		List<Map<String, Object>> hisSummary = reportExportManager.getLatestThreeTaskSummaryByTaskId(taskId);
		
		HSSFWorkbook workbook = ExcelUtil.generateQAReport(
								list, 
								templateManager.getSpecialTemplate(templateId), 
								templateManager.getAllIssueComponent(), 
								hisSummary
								);
		
		try {
			viewExcel.buildExcelDocument(obj, workbook, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ModelAndView(viewExcel, model);
	}
}
