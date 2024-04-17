package com.luv2code.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
@WebListener
public class HibernateStartupListener implements ServletContextListener {

	 @Override
	    public void contextInitialized(ServletContextEvent sce) {
	        // Call HibernateUtil.getSessionFactory() when the web application starts
	        HibernateUtil.getSessionFactory();
	        
	        // Optionally, you can log or perform other actions here
	        System.out.println("Hibernate SessionFactory initialized at startup.");
	    }

	    @Override
	    public void contextDestroyed(ServletContextEvent sce) {
	        // Clean up resources when the web application shuts down
	        HibernateUtil.shutdown();
	    }

}
