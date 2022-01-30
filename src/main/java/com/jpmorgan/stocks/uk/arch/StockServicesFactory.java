package com.jpmorgan.stocks.uk.arch;

import com.jpmorgan.stocks.uk.arch.impl.StockServicesFactoryImpl;
import com.jpmorgan.stocks.uk.services.StockService;

/**
 * Factory of the services in the Stock Service application. 
 * 
 * As design decision, all possible external systems or high level layers in the application 
 * will access to the services through this factory.
 * 
 */
public interface StockServicesFactory {
	
	/**
	 * Singleton instance of the factory StockServicesFactory.
	 */
	public StockServicesFactory INSTANCE = StockServicesFactoryImpl.getInstance();
	
	/**
	 * Service which implements the five operations to calculate the 
	 * -dividend yield, 
	 * -P/E Ratio, 
	 * -Stock Price, 
	 * -GBCE All Share Index 
	 * -record trades 	
	 * @return An object of type SimpleStockService, representing a instance of the Stock Service
	 */
	public StockService getSimpleStockService();

}
