package com.jpmorgan.stocks.uk.services.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.log4j.Logger;

import com.jpmorgan.stocks.uk.backend.StocksEntityManager;
import com.jpmorgan.stocks.uk.model.Stock;
import com.jpmorgan.stocks.uk.model.Trade;
import com.jpmorgan.stocks.uk.services.StockService;

/**
 * 
 */
public class StockServiceImpl implements StockService {


	private Logger logger = Logger.getLogger(StockServiceImpl.class);

	private StocksEntityManager stocksEntityManager = null;


	/**
	 * 
	 * @param stocksEntityManager
	 */
	public void setStocksEntityManager(StocksEntityManager stocksEntityManager) {
		this.stocksEntityManager = stocksEntityManager;
	}

	public double calculateDividendYield(String stockSymbol) throws Exception{
		double dividendYield = -1.0;

		try{
			logger.debug("Calculating Dividend Yield for the stock symbol: "+stockSymbol);
			Stock stock = stocksEntityManager.getStockBySymbol(stockSymbol);

			// If the stock is not supported the a exception is raised
			if(stock==null){
				throw new Exception("The stock symbol ["+stockSymbol+"] is not supported by the Super Simple Stock system.");
			}

			// Ticker price with value zero does not make any sense and could produce a zero division
			if(stock.getPrice() <= 0.0){
				throw new Exception("The ticker price for the stock ["+stockSymbol+"] should be greater than zero (0).");
			}
			dividendYield = stock.getDividendYield();

			logger.debug("Dividend Yield calculated: "+dividendYield);

		}catch(Exception exception){
			logger.error("Error calculating Dividend Yield for the stock symbol: "+stockSymbol+".", exception);
			throw new Exception("Error calculating Dividend Yield for the stock symbol: "+stockSymbol+".", exception);
		}
		return dividendYield;
	}

	public double calculatePERatio(String stockSymbol) throws Exception{
		double peRatio = -1.0;
		try{
			logger.debug("Calculating P/E Ratio for the stock symbol: "+stockSymbol);
			Stock stock = stocksEntityManager.getStockBySymbol(stockSymbol);

			// If the stock is not supported the a exception is raised
			if(stock==null){
				throw new Exception("The stock symbol ["+stockSymbol+"] is not supported by the Super Simple Stock system.");
			}

			// Ticker price with value zero does not make any sense and could produce a zero division
			if(stock.getPrice() <= 0.0){
				throw new Exception("The ticker price for the stock ["+stockSymbol+"] should be greater than zero (0).");
			}

			peRatio = stock.getPeRatio();

			logger.debug(" P/E Ratiocalculated: "+peRatio);

		}catch(Exception exception){
			logger.error("Error calculating P/E Ratio for the stock symbol: "+stockSymbol+".", exception);
			throw new Exception("Error calculating P/E Ratio for the stock symbol: "+stockSymbol+".", exception);
		}
		return peRatio;
	}

	public boolean recordTrade(Trade trade) throws Exception{
		boolean recordResult = false;
		try{
			logger.debug("Begin recordTrade with trade object: ");
			logger.debug(trade);

			// check trade object
			if(trade==null){
				throw new Exception("Trade object to record should be a valid object and it's null.");
			}

			// check stock object
			if(trade.getStock()==null){
				throw new Exception("A trade should be associated with a stock and the stock for the trade is null.");
			}

			// Check shares quantity 
			if(trade.getSharesQuantity()<=0){
				throw new Exception("Shares quantity in the trade to record should be greater than Zero.");
			}

			//  Check shares price 
			if(trade.getPrice()<=0.0){
				throw new Exception("Shares price in the trade to record should be greater than Zero.");
			}

			recordResult = stocksEntityManager.recordTrade(trade);

			// Update the ticker price for the stock
			if(recordResult){
				trade.getStock().setPrice(trade.getPrice());
				

			}


		}catch(Exception exception){
			logger.error("Error when trying to record a trade.", exception);
			throw new Exception("Error when trying to record a trade.", exception);
		}
		return recordResult;
	}

	
	private class StockPredicate implements Predicate{
	
		private Logger logger = Logger.getLogger(StockPredicate.class);

	
		private String stockSymbol = "";

	
		private Calendar dateRange = null;

		/**
		 * 
		 * @param stockSymbol
		 * @param minutesRange
		 */
		public StockPredicate(String stockSymbol, int minutesRange){
			this.stockSymbol = stockSymbol;
			if( minutesRange > 0 ){
				dateRange = Calendar.getInstance();
				dateRange.add(Calendar.MINUTE, -minutesRange);
				logger.debug(String.format("Filter date pivot: %tF %tT", dateRange.getTime(), dateRange.getTime()));
			}

		}

		/**
		 * 
		 */
		public boolean evaluate(Object tradeObject) {
			Trade trade = (Trade) tradeObject;
			boolean shouldBeInclude = trade.getStock().getStockSymbol().equals(stockSymbol);
			if(shouldBeInclude && dateRange != null){
				shouldBeInclude = dateRange.getTime().compareTo(trade.getTimeStamp())<=0;
			}
			return shouldBeInclude;
		}

	}

	/**
	 * 
	 * @param stockSymbol
	 * @param minutesRange
	 * @return
	 * @throws Exception
	 */
	private double calculateStockPriceinRange(String stockSymbol, int minutesRange) throws Exception{
		double stockPrice = 0.0;

		logger.debug("Trades in the original collection: "+getTradesNumber());

		
		@SuppressWarnings("unchecked")
		Collection<Trade> trades = CollectionUtils.select(stocksEntityManager.getTrades(), new StockPredicate(stockSymbol, minutesRange));

		logger.debug("Trades in the filtered collection by ["+stockSymbol+","+minutesRange+"]: "+trades.size());

		// Calculate the summation
		double shareQuantityAcum = 0.0;
		double tradePriceAcum = 0.0;
		for(Trade trade : trades){
			// Calculate the summation of Trade Price x Quantity
			tradePriceAcum += (trade.getPrice() * trade.getSharesQuantity());
			// Acumulate Quantity
			shareQuantityAcum += trade.getSharesQuantity();
		}

		// calculate the stock price
		if(shareQuantityAcum > 0.0){
			stockPrice = tradePriceAcum / shareQuantityAcum;	
		}


		return stockPrice;
	}


	public double calculateStockPrice(String stockSymbol) throws Exception{
		double stockPrice = 0.0;

		try{
			logger.debug("Calculating Stock Price for the stock symbol: "+stockSymbol);
			Stock stock = stocksEntityManager.getStockBySymbol(stockSymbol);

			// If the stock is not supported the a exception is raised
			if(stock==null){
				throw new Exception("The stock symbol ["+stockSymbol+"] is not supported by the Super Simple Stock system.");
			}

			stockPrice = calculateStockPriceinRange(stockSymbol, 15);

			logger.debug(" Stock Price calculated: "+stockPrice);


		}catch(Exception exception){
			logger.error("Error calculating P/E Ratio for the stock symbol: "+stockSymbol+".", exception);
			throw new Exception("Error calculating P/E Ratio for the stock symbol: "+stockSymbol+".", exception);

		}


		return stockPrice;
	}


	public double calculateGBCEAllShareIndex() throws Exception{
		double allShareIndex = 0.0;
		
		// To Calculate stock price for all stock in the system
		HashMap<String, Stock> stocks = stocksEntityManager.getStocks();
		ArrayList<Double> stockPrices = new ArrayList<Double>();
		for(String stockSymbol: stocks.keySet() ){
			double stockPrice = calculateStockPriceinRange(stockSymbol, 0);
			if(stockPrice > 0){
				stockPrices.add(stockPrice);
			}
		}
		
		if(stockPrices.size()>=1){
			double[] stockPricesArray = new double[stockPrices.size()];
			
			for(int i=0; i<=(stockPrices.size()-1); i++){
				stockPricesArray[i] = stockPrices.get(i).doubleValue();
			}
			// To Calculates the GBCE All Share Index
			allShareIndex = StatUtils.geometricMean(stockPricesArray);
		}
		return allShareIndex;
	}


	public int getTradesNumber() throws Exception {
		return stocksEntityManager.getTrades().size();
	}

}
