package org.willclark.finance;

import javax.persistence.EntityManager;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.willclark.finance.utils.ServiceUtil;

public class Bootstrap implements ServletContextListener {

	private static Logger log = Logger.getLogger(Bootstrap.class);
	
	public void contextInitialized(ServletContextEvent context) {				
		log.info("Staring up");

		EntityManager em = ServiceUtil.getEntityManager();
		em.close();
	}
	
	public void contextDestroyed(ServletContextEvent context) {
		log.info("Shutting down");
	}

}
