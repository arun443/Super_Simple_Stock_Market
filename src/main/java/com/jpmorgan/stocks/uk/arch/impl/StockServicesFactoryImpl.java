package com.jpmorgan.stocks.uk.arch.impl;

import com.jpmorgan.stocks.uk.arch.StockServicesFactory;
import com.jpmorgan.stocks.uk.arch.SpringService;
import com.jpmorgan.stocks.uk.services.StockService;

/**
 * Implementation of the Factory of the services in the Super Simple Stock application. 
 */
public class StockServicesFactoryImpl implements StockServicesFactory {

	/**
	 * Private constructor for the factory which prevents new instance
	 */
	private StockServicesFactoryImpl(){
	
		SpringService.INSTANCE.getClass();
	}

	
	private static class SimpleStockServicesFactoryHolder{
		private static final StockServicesFactory INSTANCE = new StockServicesFactoryImpl();
	}

	/**
	 * Gets the singleton instance of the factory of the services in the Stock application. 
	 * 
	 */
	public static StockServicesFactory getInstance(){
		return SimpleStockServicesFactoryHolder.INSTANCE;
	}

	public StockService getSimpleStockService(){
		return SpringService.INSTANCE.getBean("stocksService", StockService.class);
	}


}
