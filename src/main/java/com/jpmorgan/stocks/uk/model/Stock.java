package com.jpmorgan.stocks.uk.model;

import org.apache.log4j.Logger;

public class Stock {
	
	
	private Logger logger = Logger.getLogger(Stock.class);
	

	private String stockSymbol = null;

    private String stockType = StockConstants.StockTypeCOMMON;

	private double lastDividend = 0.0;

	private double fixedDividend = 0.0;
	

	private double parValue = 0.0;
		
	private double price = 0.0; 
	
	public Stock(){
		
	}

	public String getStockSymbol() {
		return stockSymbol;
	}

	/**
	 * 
	 * @param stockSymbol
	 */
	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}

	/**
	 * 
	 * @return
	 */
	public String getStockType() {
		return stockType;
	}

	/**
	 * 
	 * @param stockType
	 */
	public void setStockType(String stockType) {
		this.stockType = stockType;
	}

	/**
	 * 
	 * @return
	 */
	public double getLastDividend() {
		return lastDividend;
	}

	/**
	 * 
	 * @param lastDividend
	 */
	public void setLastDividend(double lastDividend) {
		this.lastDividend = lastDividend;
	}

	/**
	 * 
	 * @return
	 */
	public double getFixedDividend() {
		return fixedDividend;
	}

	/**
	 * 
	 * @param fixedDividend
	 */
	public void setFixedDividend(double fixedDividend) {
		this.fixedDividend = fixedDividend;
	}

	/**
	 * 
	 * @return
	 */
	public double getParValue() {
		return parValue;
	}

	/**
	 * 
	 * @param parValue
	 */
	public void setParValue(double parValue) {
		this.parValue = parValue;
	}

	/**
	 * 
	 * @return
	 */
	public double getDividendYield() {
		double dividendYield = -1.0;
		if(price > 0.0){
			if( stockType == StockConstants.StockTypeCOMMON){
				dividendYield = lastDividend / price;
			}else{
				// PREFERRED
				dividendYield = (fixedDividend * parValue ) / price;
			}
		}
		return dividendYield;
	}


	/**
	 * 
	 * @return
	 */
	public double getPeRatio() {
		double peRatio = -1.0;
		
		if(price > 0.0){
			peRatio = price / getDividendYield();	
		}
		
		return peRatio;
	}

	/**
	 * 
	 * @return
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * 
	 * @param tickerPrice
	 */
	public void setPrice(double price) {
		logger.debug("Changing ticker price to: "+price);
		this.price = price;
		logger.debug("Ticker price for "+stockSymbol+": "+price);

	}
	
	@Override
	public String toString() {
		String pattern = "Stock Object [stockSymbol: %s, stockType: %s, lastDividend: %7.0f, fixedDividend: %7.2f, parValue: %7.2f]";
		return String.format(pattern, stockSymbol, stockType, lastDividend, fixedDividend, parValue);
	}
	
}
