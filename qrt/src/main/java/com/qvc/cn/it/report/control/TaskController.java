package com.qvc.cn.it.report.control;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.qvc.cn.it.report.manager.InvokeCMDManager;
import com.qvc.cn.it.report.manager.InvokeCMDMangerImpl;
import com.qvc.cn.it.report.manager.IssuesManager;
import com.qvc.cn.it.report.manager.ScopeManager;
import com.qvc.cn.it.report.manager.TaskManager;
import com.qvc.cn.it.report.manager.TreeManager;
import com.qvc.cn.it.report.model.Employee;
import com.qvc.cn.it.report.model.TestCaseVO;

@Controller
public class TaskController extends BaseController {

	@Resource
	private ScopeManager scopeManager;

	@Resource
	private IssuesManager issueManager;

	@Resource
	private TaskManager taskManager;
	@Resource
	private TreeManager treeManager;
	
	@Resource
	private InvokeCMDManager invokeCMDManager;

	public TaskController() {
	}

	@RequestMapping(method = RequestMethod.GET, value = "/findAllCountries")
	public ModelAndView findAllCountries() {

		return new ModelAndView(MARSHALLED_VIEW, "object",
				scopeManager.getAllCountry());
	}

	@RequestMapping(method = RequestMethod.GET, value = "/findAllProjects/{countryCode}")
	public ModelAndView findAllProjects(@PathVariable String countryCode) {

		return new ModelAndView(MARSHALLED_VIEW, "object",
				scopeManager.getProjectsByCountryCode(countryCode));
	}

	@RequestMapping(method = RequestMethod.GET, value = "/findAllTasks/{scopeId}")
	public ModelAndView findAllTasks(@PathVariable int scopeId) {

		return new ModelAndView(MARSHALLED_VIEW, "object",
				taskManager.getTasksByScopeId(scopeId));
	}

	@RequestMapping(method = RequestMethod.POST, value = "/findTaskSummary")
	public ModelAndView findTaskSummary(@RequestParam("taskId") String taskId,
			@RequestParam("EXETime") String EXETime) {
		int taskIDValue=Integer.parseInt(taskId);
		return new ModelAndView(MARSHALLED_VIEW, "object",
		taskManager.getTaskSummary(taskIDValue,EXETime));
	}
	@RequestMapping(method = RequestMethod.POST, value = "/assignTask")
	public ModelAndView assignTask(@RequestParam("taskId") String taskId,
			@RequestParam("tester") String tester,
			@RequestParam("adminId") String adminId,
			@RequestParam("finishDate") String finishDate,
			@RequestParam("sendman") String sendman,
			@RequestParam("empnum") String empnum,
			@RequestParam("EXETime") String EXETime) 
	{
		
		String[] teseters = null;
		List<String> testerList = new ArrayList<String>();
		
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Date date = null;
		Date dateEXETime=null;
		if (null != tester && !tester.equals("") && tester.length() > 0) {
			teseters = tester.split("_");
			Collections.addAll(testerList, teseters);
		}
		try {
			date = df.parse(finishDate);
		} catch (ParseException e) {
			e.printStackTrace();
			date = new Date();
			try {
				date = df.parse(date.toString());
			} catch (ParseException e1) {
				e1.printStackTrace();
				
			}
		}
		try {
			dateEXETime = df.parse(EXETime);
		} catch (ParseException e) {
			e.printStackTrace();
			dateEXETime = new Date();
		}
		taskManager.assignTask(Long.parseLong(taskId), adminId, testerList,
				date,sendman,empnum,dateEXETime);
		
		return null;
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "/lastTaskList/{empno}/{flag}")
	public List<Map<String, Object>> getAllLastestWeekTaskByEmpNo(
			@PathVariable("empno") String empno,@PathVariable("flag") Short flag) {

		return taskManager.getAllLastestWeekTaskByEmpNo(empno,flag);
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/testSetCaseList")
	public Map<String, List<TestCaseVO>> generateTree(
			@RequestParam("taskId") Long taskId,
			@RequestParam("empno") String empno,
			@RequestParam("flag") Short flag,
			@RequestParam("userId") String userId,
			@RequestParam("issueType") String issueType) {

		return treeManager.generateTreeDate(taskId, empno, flag==1,userId,issueType);

	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "/findTesterByTask/{taskId}")
	public List<Employee> generateTree(@PathVariable("taskId") String taskId) {

		return issueManager.getTesterByTask(taskId);

	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "/completeTask/{taskId}/{empno}")
	public boolean completeTask(@PathVariable String empno,@PathVariable Long taskId) {
		
		return taskManager.updateCompleteTask(taskId, empno, empno);
		
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/findAssignTasks/{scopeId}")
	public ModelAndView findAssignedTasks(@PathVariable int scopeId) {

		return new ModelAndView(MARSHALLED_VIEW, "object",
				taskManager.getAssignedTasksByScopeId(scopeId));
	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "/taskSummary/{taskId}")
	public List<Map<String, Object>> getEmpTaskSummary(@PathVariable("taskId") Long taskId){
		return taskManager.getEmpTaskSummary(taskId);
	}
	
	/**
	 *  excute excel file 
	 * @return
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "/executeImport")
	public String executeImport() throws InterruptedException, IOException{
		((InvokeCMDMangerImpl)invokeCMDManager).setExcelExcutor("cmd /c dir");
		String result = invokeCMDManager.executeExcelImportCMD();
		
		return result;
	}


}
