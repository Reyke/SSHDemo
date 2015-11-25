package com.qvc.cn.it.report.control;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qvc.cn.it.report.manager.TemplateManager;
import com.qvc.cn.it.report.model.IssueComponent;
import com.qvc.cn.it.report.model.TemplateDetail;
import com.qvc.cn.it.report.model.TemplateHead;

@Controller
public class TemplateController {

	@Resource
	private TemplateManager templateManager;

	@RequestMapping("/template/allTemplate")
	@ResponseBody
	public List<TemplateHead> getAllTemplate() {
		return templateManager.getAllTemplate();
	}

	@RequestMapping("/template/{id}")
	@ResponseBody
	public TemplateHead getSpecialTemplate(@PathVariable Long id) {
		return templateManager.getSpecialTemplate(id);
	}

	@RequestMapping("/template/{id}/delete")
	@ResponseBody
	public boolean deleteTemplate(@PathVariable Long id) {
		try {
			templateManager.deleteTemplate(id);
			return true;
		} catch (Exception ex) {
			return false;
		}

	}
	
	@RequestMapping("/issueComponent")
	@ResponseBody
	public List<IssueComponent> getIssueComponentList() {
		return templateManager.getAllIssueComponent();
	}

	/*
	 * @Author: sampson
	 */
	@RequestMapping("/template/saveTemplate")
	@ResponseBody
	public boolean saveTemplate(@RequestBody TemplateHead templateHead) {
		try {
			Set<TemplateDetail> details = templateHead.getTemplateDetails();
			for (TemplateDetail detail : details) {
				detail.setTemplateHead(templateHead);
			}
			if (templateHead.getTemplateId() != null) {
				// exists template, call update
				templateManager.updateTemplate(templateHead);
			} else {
				// this is a new created template, call create new.
				templateManager.addTemplate(templateHead);
			}
			return true;
		} catch (Exception ex) {
			return false;
		}

	}

}
