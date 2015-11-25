package com.qvc.cn.it.report.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.qvc.cn.it.report.manager.DemoManager;
import com.qvc.cn.it.report.model.DemoList;

@Controller
public class DemoController extends BaseController {

	@Autowired
	private DemoManager reportManager;

	@RequestMapping(method = RequestMethod.GET, value = "/demo/{id}")
	public ModelAndView getReport(@PathVariable String id) {
		return new ModelAndView(MARSHALLED_VIEW, "object", reportManager.find(Long
				.parseLong(id)));
	}

	@RequestMapping(method = RequestMethod.GET, value = "/demo")
	public ModelAndView list() {
		return new ModelAndView(MARSHALLED_VIEW, "object", new DemoList(reportManager.list()));
	}
}
