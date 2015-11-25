package com.qvc.cn.it.report.manager;

import java.util.List;

import com.qvc.cn.it.report.model.IssueComponent;
import com.qvc.cn.it.report.model.TemplateHead;

public interface TemplateManager extends BaseManager {

	public List<TemplateHead> getAllTemplate();
	public TemplateHead updateTemplate(TemplateHead templateHead);
	public void addTemplate(TemplateHead templateHead);
	public void deleteTemplate(TemplateHead templateHead);
	public List<IssueComponent> getAllIssueComponent();
	public TemplateHead getSpecialTemplate(Long id);
	public void deleteTemplate(Long id);
}
