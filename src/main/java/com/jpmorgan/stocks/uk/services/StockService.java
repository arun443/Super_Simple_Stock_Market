package com.jpmorgan.stocks.uk.services;

import com.jpmorgan.stocks.uk.model.Trade;

/**
 * Super Simple Service, which implements the five operations to calculate the dividend yield, 
 * P/E Ratio, Stock Price, GBCE All Share Index and record trades for a given stock.
 * 
 */
public interface StockService {
	
	/**
	 * To Calculate the dividend yield for a given stock.
	 * 
	 * @param stockSymbol Stock symbol.
	 * 
	 * @return A double value which represents the dividend yield for a given stock.
	 */
	public double calculateDividendYield(String stockSymbol) throws Exception;
	
	/**
	 * To Calculates the P/E Ratio for a given stock.
	 * 
	 * @param stockSymbol Stock symbol.
	 * 
	 */
	public double calculatePERatio(String stockSymbol) throws Exception;
	
	/**
	 * To Record a trade in the Stocks application.
	 * 
	 * @param trade Trade object to record.
	 * 
	 * @return True, when the record is successful. Other case, False.
	 */
	public boolean recordTrade(Trade trade) throws Exception;
	
	/**
	 * 
	 * @param stockSymbol
	 * @return
	 * @throws Exception
	 */
	public double calculateStockPrice(String stockSymbol) throws Exception;
	
	/**
	 *  To Calculate the GBCE All Share Index using the geometric mean of prices for all stocks.
	 * For the technical test purpose we have assumed the next:
	 * 
	 * 
	 * @return
	 * @throws Exception
	 */
	public double calculateGBCEAllShareIndex() throws Exception;

}
