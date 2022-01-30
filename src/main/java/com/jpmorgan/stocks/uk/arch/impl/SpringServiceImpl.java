package com.jpmorgan.stocks.uk.arch.impl;

import org.apache.log4j.Logger;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jpmorgan.stocks.uk.arch.SpringService;

/**
 * Implementation of the Spring Service.
 * 
 */
public class SpringServiceImpl implements SpringService {
	
	private Logger logger = Logger.getLogger(SpringServiceImpl.class);

	/**
	 * Spring context files pattern defined for the Stocks application.  
	 */
	private static final String SPRING_CONTEXT_FILE_NAME = "classpath*:*stocks-*-context.xml";

	private AbstractApplicationContext springContext = null;

	/**
	 *constructor is to load the Spring context for the Stocks application. 
	 */
	private SpringServiceImpl(){
		logger.info("Loading Spring Context for Super Simple Stocks.");
		springContext = new ClassPathXmlApplicationContext(SPRING_CONTEXT_FILE_NAME);
		springContext.registerShutdownHook();
		logger.info("Spring Context created !!!! Successfully !!!!");
	}
	
	
	private static class SpringServiceHolder{
		private static final SpringService INSTANCE = new SpringServiceImpl();
	}


	
	/**
	 * Gets the singleton instance of the spring services. 
	 */
	public static SpringService getInstance(){
		return SpringServiceHolder.INSTANCE;
	}	
	

	public <T> T getBean(String beanName, Class<T> objectClass) {
		return springContext.getBean(beanName, objectClass);
	}


	public <T> T getBean(Class<T> objectClass) {
		return springContext.getBean(objectClass);
	}

}
