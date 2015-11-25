package com.qvc.cn.it.report.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.qvc.cn.it.report.dao.IGenericDAO;
import com.qvc.cn.it.report.model.IssueComponent;
import com.qvc.cn.it.report.model.TemplateDetail;
import com.qvc.cn.it.report.model.TemplateHead;

@Service
public class TemplateManagerImpl extends BaseManagerImpl implements
		TemplateManager {
	@Resource
	private IGenericDAO<TemplateHead, Long> templateHeadDAO;
	@Resource
	private IGenericDAO<TemplateDetail,Long> templateDetailDAO;
	@Resource
	private IGenericDAO<IssueComponent, String> issueComponentDAO;
	
	@Override
	public List<TemplateHead> getAllTemplate(){
		return templateHeadDAO.listAll();
	}
	@Override
	public TemplateHead updateTemplate(TemplateHead templateHead){
		TemplateHead templateDb = templateHeadDAO.loadByKey(templateHead.getTemplateId());
		templateDb.setName(templateHead.getName());
		templateDb.setStatus(templateHead.getStatus());
		templateDb.setCreatedBy(templateHead.getCreatedBy());
		
		for (TemplateDetail td : templateDb.getTemplateDetails()) {
			td.setTemplateHead(null);
			templateDetailDAO.delete(td);
		}
		
		templateDb.getTemplateDetails().clear();
		
		templateDb.getTemplateDetails().addAll(templateHead.getTemplateDetails());
		
		for (TemplateDetail td : templateDb.getTemplateDetails()) {
			td.setTemplateHead(templateDb);
		}
		
		return templateDb;
	}
	@Override
	public void addTemplate(TemplateHead templateHead){
		templateHeadDAO.save(templateHead);
	}
	@Override
	public void deleteTemplate(TemplateHead templateHead){
		templateHeadDAO.delete(templateHead);
	}
	@Override
	public List<IssueComponent> getAllIssueComponent() {
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("enableInd", "Y");
		
		return issueComponentDAO.getEntity(params);
	}
	
	@Override
	public TemplateHead getSpecialTemplate(Long id) {
		return templateHeadDAO.getByKey(id);
	}
	@Override
	public void deleteTemplate(Long id) {
		templateHeadDAO.deleteByKey(id);
	}

}
