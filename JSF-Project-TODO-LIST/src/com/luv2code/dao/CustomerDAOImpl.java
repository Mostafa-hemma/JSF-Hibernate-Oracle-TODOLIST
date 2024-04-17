package com.luv2code.dao;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.luv2code.entity.Customer;
import com.luv2code.util.HibernateUtil;

public class CustomerDAOImpl  {
	
	
	
	public Customer getCustomerByEmail(String email) throws Exception {
		
		Customer myCustomer=null;
		
	
			Session session=HibernateUtil.getSessionFactory().openSession();
			Transaction transaction= session.beginTransaction();
			Query myQuery=session.createNamedQuery("Customer.findByEmail",Customer.class);
			myQuery.setParameter("email", email);
			myCustomer=(Customer) myQuery.getSingleResult();
			transaction.commit();
			return myCustomer;
		

	}
	
	
	public void register(Customer myCustomer) {
		try {
			Session session=HibernateUtil.getSessionFactory().openSession();
			Transaction transaction= session.beginTransaction();

			session.save(myCustomer);
			
			transaction.commit();
			session.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void logOut() {
		try {
			Session session=HibernateUtil.getSessionFactory().getCurrentSession();
			System.out.println("DAO Sign out function");
			session.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
