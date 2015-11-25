package com.qvc.cn.it.report.dao;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

import com.qvc.cn.it.report.model.Issues;
import com.qvc.cn.it.report.model.TestCase;

/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
@Repository
public class TestCaseDAOImpl extends GenericDAO<TestCase, Long> implements TestCaseDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<TestCase> getTestCaseByTaskID(Long taskID) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("from TestCase ")
		.append("where testSet.task.task_id = ? and original_status <> 'Passed' ");
		
		Query query=sessionFactory.getCurrentSession().createQuery(sql.toString()); 
        query.setLong(0, taskID); 

        return query.list(); 
	}

	/**
	 * @param taskId
	 * @param EXETime
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<List<Long>> getSmokingSummary(int taskId,String EXETime)
	{
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		DateFormat dfs = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = df.parse(EXETime);
		} catch (ParseException e) {
			e.printStackTrace();
			date = new Date();
			try {
				date = df.parse(date.toString());
			} catch (ParseException e1) {
				e1.printStackTrace();

			}
		}
		EXETime=dfs.format(date);
		List<List<Long>> list=new ArrayList<List<Long>>();
		String smokingFailedCount 	= "SELECT COUNT(case_id) "
				+ " FROM TestCase "
				+ " WHERE testSet.set_name like '%Smoke%' "
				+ " AND (original_status != 'Passed' "
				+ " OR exe_date <"
				+"'"+EXETime+"')"
				+ " AND testSet.task.task_id = " 
				+ taskId;
		String smokingPasswdCount 	= "SELECT COUNT(case_id) "
				+ " FROM TestCase "
				+ " Where testSet.set_name like '%Smoke%' "
				+ " AND original_status = 'Passed' "
				+ " AND exe_date >= "
				+"'"+EXETime+"'"
				+ " AND testSet.task.task_id = " 
				+ taskId;
		
		String tesetCaseFailedCount = "SELECT COUNT(case_id) "
				+ " FROM TestCase"
				+ " WHERE (original_status != 'Passed' " 
				+ " OR exe_date <"
				+"'"+EXETime+"')"
				+ " AND testSet.task.task_id = " 
				+ taskId;
		
		String tesetCaseCount = "SELECT COUNT(case_id) "
				+ " FROM TestCase"
				+ "  where testSet.task.task_id = " 
				+ taskId;
		List<Long> list1 			= this.getSessionFactory().getCurrentSession()
										.createQuery(smokingFailedCount).list();
		List<Long> list2 			= this.getSessionFactory().getCurrentSession()
										.createQuery(smokingPasswdCount).list();
		List<Long> list3 			= this.getSessionFactory().getCurrentSession()
										.createQuery(tesetCaseFailedCount).list();
		List<Long> list4 			= this.getSessionFactory().getCurrentSession()
										.createQuery(tesetCaseCount).list();
		list.add(list1);
		list.add(list2);
		list.add(list3);
		list.add(list4);
		return list;
	}	
	@SuppressWarnings("unchecked")
	public List<TestCase> getSmokeTestCaseByTaskID(Long taskID,Date EXETime) {
		DateFormat dfs = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = null;
		dateStr=dfs.format(EXETime);
		StringBuffer sql = new StringBuffer();
		sql.append("from TestCase ")
		.append("where task_id = ?  and case_name like '%Smoke%' and ( original_status <> 'Passed' or exe_date <'"+dateStr+"')");
		
		Query query=sessionFactory.getCurrentSession().createQuery(sql.toString()); 
        query.setLong(0, taskID);

        return query.list(); 
	}
	
	@SuppressWarnings("unchecked")
	public List<TestCase> getnotSmokeTestCaseByTaskID(Long taskID ,Date EXETime) {
		DateFormat dfs = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = null;
		dateStr=dfs.format(EXETime);
		StringBuffer sql = new StringBuffer();
		sql.append("from TestCase ")
		.append("where task_id = ?  and case_name not like '%Smoke%' and ( original_status <> 'Passed' or exe_date <'"+dateStr+"')");
		
		Query query=sessionFactory.getCurrentSession().createQuery(sql.toString()); 
        query.setLong(0, taskID);

        return query.list(); 
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Issues> getAssignedTestCaseByTaskIDAndEmployNo(Long taskID, String empno, boolean isAdmin,String userId,String issueType){
		String queryStr = "FROM Issues WHERE ";
		Query query = null;

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("taskId",taskID);
		map.put("responser.number",userId);
		map.put("issueType",issueType);

		for(String key : map.keySet()){
			Object value = map.get(key);
			if(value != null && !"".equals(value) && !"null".equals(value)){
				queryStr += key + " = ? AND "; 
			}
		}
		if(!isAdmin){
			queryStr += " responser.number = :empno AND";
		}
		queryStr += " 1 = 1 ";
		System.out.println("==== : " + queryStr);
		
		query = sessionFactory
				.getCurrentSession()
				.createQuery(queryStr);
		int index = 0;
		for(String key : map.keySet()){
			Object value = map.get(key);
			if(value != null && !"".equals(value) && !"null".equals(value)){
				query.setParameter(index ++, map.get(key));
			}
		}

		if(!isAdmin){
			query.setParameter("empno",empno);
		}

		return query.list();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Issues> getCurrentTestCaseHis(long caseId) {
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT t1 FROM Issues t1,TestCase t2 ")
		.append(" WHERE t1.testId = t2.test_id ")
		.append(" AND t1.refFolderId = t2.refFolderId ")
		.append(" AND t1.testCase.case_id != t2.case_id ")
		.append(" AND t2.case_id = ? ");
		
		//time  02/25/2014
//		sql.append("SELECT t1 FROM Issues t1,TestCase t2,TestCase t3 ")
//		.append(" WHERE t1.caseId = t3.case_id ")
//		.append(" AND t2.testSet.set_id = t3.testSet.set_id ")
//		.append(" AND t2.case_name = t3.case_name ")
//		.append(" AND t2.task.task_id != t3.task.task_id ")
//		.append(" AND t2.case_id = ? ")
		
//		.append(" ORDER BY t1.versionDateNumber DESC");
//		sql.append("from Issues where case_id in (select case_id  from TestCase ");
//		sql.append(" where set_id=? and case_name = ?)" );
//		sql.append(" and task_id <> ?  order by task_id ");
		
		Query query=sessionFactory.getCurrentSession().createQuery(sql.toString()); 
		query.setLong(0, caseId);
        query.setFirstResult(0);
        query.setMaxResults(3);

        return query.list();	
	}
	
}
