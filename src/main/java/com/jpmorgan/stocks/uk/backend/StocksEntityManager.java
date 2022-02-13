package com.jpmorgan.stocks.uk.backend;

import java.util.ArrayList;
import java.util.HashMap;

import com.jpmorgan.stocks.uk.model.Stock;
import com.jpmorgan.stocks.uk.model.Trade;

/**
 * Store service for the Stocks and Trades. It holds in memory all the information of Stocks application.
 * 
 */
public interface StocksEntityManager {
	

	public boolean recordTrade(Trade trade) throws Exception;
	
	public ArrayList<Trade> getTrades();
	
	/**
	 * @param stockSymbol
	 */
	public Stock getStockBySymbol(String stockSymbol);
	
	/**
	 * Gets all the stocks supported by Stocks application.
	 * 
	 */
	public HashMap<String, Stock> getStocks();
}
