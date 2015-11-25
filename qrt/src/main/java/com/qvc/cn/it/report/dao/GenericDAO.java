package com.qvc.cn.it.report.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

public class GenericDAO<T, PK extends Serializable> implements
		IGenericDAO<T, PK> {

	private final static int BATCH_SIZE = 30;
	
	@Resource
	protected SessionFactory sessionFactory;

	private Class<T> entityClass;

	@SuppressWarnings("unchecked")
	public GenericDAO() {
		this.entityClass = null;
        Class<?> c = getClass();
        Type t = c.getGenericSuperclass();
        if (t instanceof ParameterizedType) {
            Type[] p = ((ParameterizedType) t).getActualTypeArguments();
            this.entityClass = (Class<T>) p[0];
        }
		
	}
	public GenericDAO(Class<T> entityClass) {
		this.entityClass = entityClass;
		
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PK save(T entity) {
		return (PK)sessionFactory.getCurrentSession().save(entity);
	}

	@Override
	public void delete(T entity) {
		sessionFactory.getCurrentSession().delete(entity);
	}

	public void deleteAll(Set<T> list) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		for(T entity : list) {
			currentSession.delete(entity);
		}
		
		currentSession.flush();
		currentSession = null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> listAll() {
		
		return (List<T>) (sessionFactory.getCurrentSession().createQuery(
				"from " + entityClass.getSimpleName()).list());
	
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getByKey(PK id) {
		return (T) sessionFactory.getCurrentSession().get(
				entityClass, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T loadByKey(PK id) {
		return (T) sessionFactory.getCurrentSession().load(
				entityClass, id);
	}

	@Override
	public void deleteByKey(PK pk) {
		sessionFactory.getCurrentSession().delete(this.loadByKey(pk));

	}

	@Override
	public void update(T entity) {
		sessionFactory.getCurrentSession().update(entity);
		

	}

	@Override
	public void saveOrUpdate(T entity) {
		sessionFactory.getCurrentSession().saveOrUpdate(entity);
	}
	
	@Override
	public void flush(){
		sessionFactory.getCurrentSession().flush();
	}

	/*
	 * @Override
	 
	public List<T> getEntity(T entity)  {
		Example exampleT=Example.create(entity);
		
		Session session=sessionFactory.openSession();
		List list=session.createCriteria(entityClass).add(exampleT).list();
		
		return list ;
		
		
		Criteria criteria = sessionFactory.openSession().createCriteria(entityClass);
		java.lang.reflect.Field[] fields = entity.getClass().getDeclaredFields();
		  for(java.lang.reflect.Field f:fields){
		   System.out.println("shuxing ming: "+f.getName()+"shuning zhi :");
		   
		   Restrictions.eq(f.getName(), "");
		  }
		
		return null ;
	} 

	*/
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getEntity(Map<String, Object> map) {
		Criteria criteria 		= sessionFactory.openSession().createCriteria(entityClass);
		Iterator<String> keys 	= map.keySet().iterator();
		while (keys.hasNext()) { 
		    String key 			= keys.next(); 
		    Object val 			= map.get(key); 
		    criteria.add(Restrictions.eq(key,val));
		} 
		List<T> list 			= criteria.list();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getEntityByAnd(Map<String, Object> map) {
		StringBuilder sb 		= new StringBuilder(" FROM " + entityClass.getName() + " WHERE (");
		Iterator<String> keys 	= map.keySet().iterator();
		while (keys.hasNext()) { 
		    String key 			= keys.next(); 
		    Object val 			= map.get(key); 
		    if(val instanceof String){
		    	sb.append(key + " = '" + val + "'");
		    }
		    sb.append(" AND ");
		} 
		sb.append(" 1 = 1 )");
		List<T> list 			=  this.getSessionFactory().getCurrentSession()
								.createQuery(sb.toString()).list();
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getEntityByOr(Map<String, Object> map) {
		StringBuilder sb 		= new StringBuilder(" FROM " + entityClass.getName() + " WHERE (");
		Iterator<String> keys 	= map.keySet().iterator();
		while (keys.hasNext()) { 
		    String key 			= keys.next(); 
		    Object val 			= map.get(key); 
		    if(val instanceof Integer ){
		    	sb.append(key + " = " + val);
		    }else if(val instanceof String){
		    	sb.append(key + " = '" + val + "'");
		    }
		    sb.append(" OR ");
		} 
		sb.append(" 1 = 0");
		List<T> list 			=  this.getSessionFactory().getCurrentSession()
								.createQuery(sb.toString()).list();
		
		return list;
	}
	
	@Override
	public List<T> getEntityByLikeAnd(Map<String, Object> map) {
		
		return null;
	}
	@Override
	public void saveAll(List<T> entities) {
		int i = 0;
		Session session = getSessionFactory().getCurrentSession();
		for (T entity : entities) {
			i++;
			session.save(entity);
			if(i != 0 && i % BATCH_SIZE == 0)
				session.flush();
		}
		session.flush();
		session = null;
	}

	
	@SuppressWarnings("unchecked")
	public List<T> getEntityListByKeyList(List<PK> keys) {
		List<T> result = new ArrayList<T>();
		Session session = getSessionFactory().getCurrentSession();
		for (PK key : keys) {
			result.add((T)session.get(entityClass, key));
		}
		session = null;
		return result;
	}
	
}
