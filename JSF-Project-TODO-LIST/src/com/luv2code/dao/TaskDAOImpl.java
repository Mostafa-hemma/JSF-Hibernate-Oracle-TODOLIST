package com.luv2code.dao;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.luv2code.entity.Task;
import com.luv2code.util.HibernateUtil;

public class TaskDAOImpl {

	public List<Task> getAllTasks(int customerId) {
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			Transaction transaction=session.beginTransaction();
			Query myQuery = session.createQuery("from Task where customerId=:id", Task.class);
			myQuery.setParameter("id", customerId);
			System.out.println("executing task query");
			List<Task> customerTasks = myQuery.getResultList();
			transaction.commit();
			return customerTasks;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
	}
	
	public void saveTask(Task myTask) throws Exception  {
		Transaction transaction=null;
		
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction=session.beginTransaction();
			if((myTask.getEndDate().compareTo(myTask.getStartDate()))<0) {
				transaction.commit();	
				throw new Exception();
			}else {
				session.saveOrUpdate(myTask);
				transaction.commit();	

			}
	}

	
	public void deleteTask(Task selectedTask){
		try {
			Session session=HibernateUtil.getSessionFactory().getCurrentSession();
			Transaction transaction=session.beginTransaction();
			session.delete(selectedTask);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();

		}
		
	}
}
