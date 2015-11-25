package com.qvc.cn.it.report.manager;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.qvc.cn.it.report.dao.EmployeeDAO;
import com.qvc.cn.it.report.dao.IGenericDAO;
import com.qvc.cn.it.report.dao.IssuesDAO;
import com.qvc.cn.it.report.dao.TaskDAO;
import com.qvc.cn.it.report.dao.TestCaseDAO;
import com.qvc.cn.it.report.email.EmailSender;
import com.qvc.cn.it.report.model.EmpTaskSummary;
import com.qvc.cn.it.report.model.Employee;
import com.qvc.cn.it.report.model.Issues;
import com.qvc.cn.it.report.model.Task;
import com.qvc.cn.it.report.model.TestCase;
import com.qvc.cn.it.report.utils.IssueFactory;

@Service
@Scope("prototype")
public class TaskManagerImpl extends BaseManagerImpl implements TaskManager {
	private SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd");
	public final static String ADMIN_NOT_SELECTED = "";
	@Value(value = "${task.latest.day}")
	private int taskLatestDay;

	@Resource
	private TaskDAO taskDAO;

	@Resource
	private TestCaseDAO testCaseDAO;

	@Resource
	private EmailSender emailSender;

	@Resource
	private EmployeeDAO employeeDAO;

	@Resource(name = "empTaskSummaryDAO")
	private IGenericDAO<EmpTaskSummary, Long> empTaskSummaryDAO;

	@Resource
	private IssuesDAO issuesDAO;

	@Override
	public List<Task> getAllTask() {
		return taskDAO.listAll();
	}

	@Override
	public void addTask(Task task) {
		taskDAO.save(task);
	}

	@Override
	public void updateTask(Task task) {
		taskDAO.update(task);

	}

	@Override
	public void deleteTask(Task task) {
		taskDAO.delete(task);
	}

	@Override
	public List<Task> getTasksByScopeId(int id) {

		// Map<String,Object> map = new HashMap<String,Object>();
		// map.put("active", "Y");
		// map.put("scope_id", id);

		return taskDAO.getAllTaskByScopeId(id); // taskDAO.getEntity(map);
	}

	@Override
	public List<TestCase> getAllTestCase(long taskId) {
		return testCaseDAO.getTestCaseByTaskID(taskId);
	}

	@Override
	public List<Map<String, Object>> getTaskSummary(int taskId,String EXETime) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		List<List<Long>> ls = testCaseDAO.getSmokingSummary(taskId,EXETime);
		String smoking_pass_rate = "0%";

		Long smoking_total = ls.get(0).get(0) + ls.get(1).get(0);
		Long smoking_failed = ls.get(0).get(0);
		Long smoking_passed = smoking_total - smoking_failed;
		Long test_failed = ls.get(2).get(0);
		Long test_total = ls.get(3).get(0);
		map.put("smoking_total", smoking_total);
		map.put("smoking_failed", smoking_failed);
		map.put("smoking_passed", smoking_passed);
		map.put("test_failed", test_failed);
		map.put("test_total", test_total);

		if (smoking_failed < 1) {
			smoking_pass_rate = "100%";
		} else {
			DecimalFormat df2 = new DecimalFormat("##");
			smoking_pass_rate = df2.format(smoking_failed * 100.0
					/ smoking_total)
					+ "%";
		}
		map.put("smoking_passed_rate", smoking_pass_rate);
		list.add(map);

		return list;
	}
	@Override
	public String assignTask(Long taskId, String admin, List<String> testers,
			Date deadline, String sendman, String empnum,Date EXETime) {
		String result = "";
		int totalTestCasesCount = 0, smokeTestCasesCount = 0, nonSmokeTestCasesCount = 0;
		int totalTesters = 0, avgTestCases = 0, intDelta = 0, intNonSmokeTestsAssigned = 0;
		// List<TestCase> allCases = new ArrayList<TestCase>();
		
		List<EmpTaskSummary> taskSuammary = new ArrayList<EmpTaskSummary>();
		
		String adminUser = admin;
		
		if (ADMIN_NOT_SELECTED.equals(admin)) {
			adminUser = testers.get(0);
			testers.remove(0);
		}
		
		// 1. retrieve smoke & none smoke test case list.
		// List<TestCase> smokeTests =
		// testCaseDAO.getSmokeTestCaseByTaskID(taskId);
		// List<TestCase> nonSmokeTests =
		// testCaseDAO.getnotSmokeTestCaseByTaskID(taskId);
		
		List<TestCase> smokeTests = testCaseDAO
				.getSmokeTestCaseByTaskID(taskId,EXETime);
		List<TestCase> nonSmokeTests = testCaseDAO
				.getnotSmokeTestCaseByTaskID(taskId,EXETime);
		List<Issues> issues = new ArrayList<Issues>();
		
		// 2. calculate avg qty for each member
		smokeTestCasesCount = smokeTests.size();
		nonSmokeTestCasesCount = nonSmokeTests.size();
		totalTestCasesCount = smokeTestCasesCount + nonSmokeTestCasesCount;
		
		totalTesters = testers.size() + 1;
		avgTestCases = totalTestCasesCount / totalTesters;
		intDelta = totalTestCasesCount % totalTesters;
		
		// 3. assign task to testers
		
		for (TestCase testCase : smokeTests) {
			assignIssueToEmployee(testCase, adminUser, issues);
		}
		assignTaskToEmployee(taskId, taskSuammary, adminUser);
		
		// assign task to adminuser.
		if (smokeTestCasesCount <= avgTestCases) {
			// we need to assign more non-smoke test cases to the adminuser.
			if (intDelta > 0) {
				intNonSmokeTestsAssigned = avgTestCases + 1
						- smokeTestCasesCount;
				intDelta--;
			} else {
				intNonSmokeTestsAssigned = avgTestCases - smokeTestCasesCount;
			}
			// assign additional test cases to adminuser.
			for (int i = 0; i < intNonSmokeTestsAssigned; i++) {
				assignIssueToEmployee(nonSmokeTests.get(i), adminUser, issues);
			}
		} else {
			avgTestCases = (nonSmokeTestCasesCount - intNonSmokeTestsAssigned)
					/ testers.size();
			intDelta = (nonSmokeTestCasesCount - intNonSmokeTestsAssigned)
					% testers.size();
		}
		if (testers.size() > 0) {
			// assign task to normal user.
			for (String tester : testers) {
				int j = intNonSmokeTestsAssigned;
				intDelta--;
				for (int i = j; i < j + avgTestCases + (intDelta >= 0 ? 1 : 0); i++) {
					assignIssueToEmployee(nonSmokeTests.get(i), tester, issues);
					intNonSmokeTestsAssigned++;
				}
				assignTaskToEmployee(taskId, taskSuammary, tester);
			}
		}
		
		// 4. update to datebase
		issuesDAO.saveAll(issues);
		empTaskSummaryDAO.saveAll(taskSuammary);
		
		Task task = taskDAO.loadByKey(taskId);
		task.setDeadline(deadline);
        task.setAlm_start_exe_at(EXETime);
		// 5. send email.
		// load List<Employee> object by list of employee numbers.
		testers.add(0, adminUser);
		
		// emailSender.sendEmail(getEmialList(testers), "New Task Assigned",
		// generateEmailContent(issues,deadline,task,sendman,empnum),
		// EmailSender.EMIAL_CONTEXT_HTML);
		
		List<Employee> emps = getEmployeeList(testers);
		for (Employee tester : emps) {
			emailSender.sendEmail(
					tester.getEmail(),
					"New Task Assigned",
					generateEmailContent(issues, deadline, task, sendman,
							empnum, tester.getNumber()),
							EmailSender.EMIAL_CONTEXT_HTML);
		}
		
		return result;
	}

	private void assignIssueToEmployee(TestCase testCase, String adminUser,
			List<Issues> issues) {
		Issues issue = IssueFactory.generateIssuesFromTestCase(testCase);

		issue.setResponser(new Employee(adminUser));
		issues.add(issue);
	}

	private void assignTaskToEmployee(Long taskId,
			List<EmpTaskSummary> taskSuammary, String adminUser) {
		EmpTaskSummary ets = new EmpTaskSummary();
		Task task = new Task(taskId);
		ets.setTask(task);
		ets.setEmp(new Employee(adminUser));
		taskSuammary.add(ets);
	}

	private String generateEmailContent(List<Issues> cases, Date date,
			Task task, String sendman, String empnum, String tester ) {

		int totalCase = cases.size();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd E");
		String deadline = sdf.format(date);
		StringBuilder sb = new StringBuilder();

		int i = 0;
		for (Issues tc : cases) {
			Employee emp = tc.getResponser();
			if (emp.getNumber() == tester) {
				i++;
			}
		}

		sb.append("<B>Dear All</B> :<br>");

		sb.append("Please help to analyze failed case .(Total testcases: ");
		sb.append(totalCase + ". &nbsp;You got " + i + " testcases.)<br> ");
		sb.append("All this need finish before ");
		sb.append(deadline + "5:30 PM");
		sb.append(" <br>If  you meet any problem during analysis, please contact ");
		sb.append(sendman + "(" + empnum + ")  .<br>");
		sb.append("<br>");
		sb.append(" <B>Country</B>:  ");
		sb.append(task.getScope().getCountry_code().getCountry_name());
		sb.append(" <br><B>Project</B>:  ");
		sb.append(task.getScope().getProject_name().getProject_name());
		sb.append("<br><B>Task </B>:");
		sb.append(task.getTask_name());

		/*
		 * sb.append("<br><br>"); sb.append(
		 * "<span style='color:red;font-size:22px;line-height:150%;background:Yellow'>Details Shows below:</span><br>"
		 * ); sb.append("<table>"); sb.append("<tr>"); sb.append("<td><h3>");
		 * sb.append("Tester"); sb.append("</h3></td> &nbsp;&nbsp;");
		 * sb.append("<td><h3>"); sb.append("Test Case");
		 * sb.append("</h3></td>"); sb.append("</tr>"); for (Issues tc : cases)
		 * { Employee emp= tc.getResponser() ; if(emp.getNumber()== tester){
		 * sb.append("<tr>"); sb.append("<td>");
		 * sb.append(tc.getResponser().getNumber());
		 * sb.append("</td>&nbsp;&nbsp;"); sb.append("<td>");
		 * sb.append(tc.getTestCase().getCase_name()); sb.append("</td>");
		 * sb.append("</tr>"); } } sb.append("</table>");
		 */

		return sb.toString();
	}

	@SuppressWarnings("unused")
	private List<String> getEmialList(List<String> employees) {
		List<String> emails = new ArrayList<String>();
		List<Employee> emps = employeeDAO.getEntityListByKeyList(employees);
		for (Employee e : emps) {
			emails.add(e.getEmail());
		}
		return emails;
	}

	private List<Employee> getEmployeeList(List<String> employees) {
		List<Employee> emps = employeeDAO.getEntityListByKeyList(employees);
		return emps;

	}

	@Override
	public boolean checkTaskByEmpnoAndTaskId(long taskId, String empno) {

		List<Issues> temps = testCaseDAO
				.getAssignedTestCaseByTaskIDAndEmployNo(taskId, empno,false,null,null);
		for (Issues issues : temps) {
			if (!issues.isValidate()) {
				return false;
			}
		}

		return true;
	}

	@Override
	public List<Map<String, Object>> getAllLastestWeekTaskByEmpNo(String empno,
			short flag) {
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();

		List<Object[]> contents = taskDAO.getAllLastestWeekTask(empno,
				(flag == 1));
		Map<String, Object> result = null;

		Date tpoint = new Date(System.currentTimeMillis() - taskLatestDay * 24 * 60 * 60
				* 1000);

		for (Object[] objs : contents) {
			Date prjDt = generateDate(((String) objs[2]).split("-")[1].trim());

			if (prjDt.after(tpoint)) {
				result = new HashMap<String, Object>();
				result.put("country_code", objs[0]);
				result.put("project_name", objs[1]);
				result.put("task_name", objs[2]);
				result.put("task_id", objs[3]);
				results.add(result);
			}

		}

		return results;
	}

	private Date generateDate(String date) {
		Date d = null;
		try {
			d = dateFormater.parse(date);
		} catch (ParseException pe) {
			d = new Date();
		}
		return d;
	}

	@Override
	public boolean updateCompleteTask(Long taskId, String empno, String operator) {
		if (checkTaskByEmpnoAndTaskId(taskId, empno)) {
			Map<String, Object> restrictions = new HashMap<String, Object>();
			Employee emp = new Employee();
			emp.setNumber(empno);
			Task task = new Task();
			task.setTask_id(taskId);
			restrictions.put("task", task);
			restrictions.put("emp", emp);
			List<EmpTaskSummary> etss = empTaskSummaryDAO
					.getEntity(restrictions);
			EmpTaskSummary ets = null;
			if (etss.size() > 0) {
				ets = etss.get(0);
				ets.setFinishedInt("Y");
				ets.setUpdateAT(new Date());
				ets.setUpdateBy(operator);
				empTaskSummaryDAO.update(ets);

				return true;
			}
		} else {
			Map<String, Object> restrictions = new HashMap<String, Object>();
			Employee emp = new Employee();
			emp.setNumber(empno);
			Task task = new Task();
			task.setTask_id(taskId);
			restrictions.put("task", task);
			restrictions.put("emp", emp);
			List<EmpTaskSummary> etss = empTaskSummaryDAO
					.getEntity(restrictions);
			EmpTaskSummary ets = null;
			if (etss.size() > 0) {
				ets = etss.get(0);
				ets.setFinishedInt("N");
				ets.setUpdateAT(new Date());
				ets.setUpdateBy(operator);
				empTaskSummaryDAO.update(ets);

			}
		}
		return false;
	}

	public List<Map<String, Object>> getEmpTaskSummary(Long taskId) {
		return taskDAO.getEmpTaskSummary(taskId);
	}

	@Override
	public List<Task> getAssignedTasksByScopeId(int id) {

		return taskDAO.getAssignedTaskByScopeId(id);
	}
}
