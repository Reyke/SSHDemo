package com.qvc.cn.it.report.control;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qvc.cn.it.report.manager.UpdateReportManager;
import com.qvc.cn.it.report.model.Issues;

@Controller
public class UpdateReportController extends BaseController {
	
	@Resource
	private UpdateReportManager updateReportManager ;
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "/getTestCaseHistory/{caseId}")
	public List<Issues> getTestCaseHistory(@PathVariable Long caseId) {
		
		return updateReportManager.getTestCaseHis(caseId);
	}
}
