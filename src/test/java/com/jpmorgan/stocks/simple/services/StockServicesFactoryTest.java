package com.jpmorgan.stocks.simple.services;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.jpmorgan.stocks.uk.arch.StockServicesFactory;
import com.jpmorgan.stocks.uk.arch.SpringService;
import com.jpmorgan.stocks.uk.backend.StocksEntityManager;
import com.jpmorgan.stocks.uk.model.Trade;
import com.jpmorgan.stocks.uk.services.StockService;

/**
 * 
 */
public class StockServicesFactoryTest {

	Logger logger = Logger.getLogger(StockServicesFactoryTest.class);

	@Test
	public void isStockServicesFactoryASingleton(){

		logger.info("Start  isStockServicesFactoryASingleton ...");

		StockServicesFactory factoryInstanceA = StockServicesFactory.INSTANCE;
		StockServicesFactory factoryInstanceB = StockServicesFactory.INSTANCE;

		Assert.assertNotNull(factoryInstanceA);
		Assert.assertNotNull(factoryInstanceB);

		Assert.assertTrue(factoryInstanceA.equals(factoryInstanceB));

		logger.info("End isStockServicesFactoryASingleton  ");

	}

	/**
	 * 
	 */
	@Test
	public void isStockServicesASingleton(){

		logger.info("Start  isStockServicesASingleton ...");

		StockServicesFactory factoryInstance = StockServicesFactory.INSTANCE;

		StockService simpleStockServiceA = factoryInstance.getSimpleStockService();
		StockService simpleStockServiceB = factoryInstance.getSimpleStockService();

		Assert.assertNotNull(simpleStockServiceA);
		Assert.assertNotNull(simpleStockServiceB);

		Assert.assertTrue(simpleStockServiceA.equals(simpleStockServiceB));

		logger.info("End isStockServicesASingleton ");

	}

	/**
	 * 
	 */
	@Test
	public void recordATradeTest(){
		logger.info("Start  recordATradeTest ...");

		// Create the stock service and verify it's not null object
		StockService stockService = StockServicesFactory.INSTANCE.getSimpleStockService();
		Assert.assertNotNull(stockService);

		// Recover the trades configured in spring for the unit test
		@SuppressWarnings("unchecked")
		ArrayList<Trade> tradeList = SpringService.INSTANCE.getBean("tradeList", ArrayList.class);
		Assert.assertNotNull(tradeList);
		logger.info("Trade List size: "+tradeList.size());


		try{
			// Initial trades are empty, means, trades number equls to cero (0)
			StocksEntityManager stocksEntityManager = SpringService.INSTANCE.getBean("stocksEntityManager", StocksEntityManager.class);
			int tradesNumber = stocksEntityManager.getTrades().size();
			logger.info("Trades number: "+tradesNumber);
			Assert.assertEquals(tradesNumber, 0);

			// Insert many trades in the stock system
			for(Trade trade: tradeList){
				boolean result = stockService.recordTrade(trade);
				Assert.assertTrue(result);
			}

			// After record trades, the number of trades should be equal to the trades list
			tradesNumber = stocksEntityManager.getTrades().size();
			logger.info("Trades number: "+tradesNumber);
			Assert.assertEquals(tradesNumber, tradeList.size());


		}catch(Exception exception){
			logger.error(exception);
			Assert.assertTrue(false);
		}

		logger.info("End recordATradeTest ..");

	}	

	@Test
	public void calculateDividendYieldTest(){
		logger.info("Start  calculateDividendYieldTest ...");

		try{
			// Create the stock service and verify it's not null object
			StockService stockService = StockServicesFactory.INSTANCE.getSimpleStockService();
			Assert.assertNotNull(stockService);

			StocksEntityManager stocksEntityManager = SpringService.INSTANCE.getBean("stocksEntityManager", StocksEntityManager.class);
			int tradesNumber = stocksEntityManager.getTrades().size();
			logger.info("Trades number: "+tradesNumber);

			// Calculates the dividend yield for the stock symbol
			String[] stockSymbols = {"TEA", "POP", "ALE", "GIN", "JOE"};
			//String[] stockSymbols = {"TEA"};
			for(String stockSymbol: stockSymbols){
				double dividendYield = stockService.calculateDividendYield(stockSymbol);
				logger.info(stockSymbol+" - DividendYield calculated: "+dividendYield);
				Assert.assertTrue(dividendYield >= 0.0);
			}

		}catch(Exception exception){
			logger.error(exception);
			Assert.assertTrue(false);
		}

		logger.info("End calculateDividendYieldTest ...");
	}

	@Test
	public void calculatePERatioTest(){
		logger.info("Start  calculatePERatioTest ...");

		try{
			// Create the stock service and verify it's not null object
			StockService stockService = StockServicesFactory.INSTANCE.getSimpleStockService();
			Assert.assertNotNull(stockService);

			StocksEntityManager stocksEntityManager = SpringService.INSTANCE.getBean("stocksEntityManager", StocksEntityManager.class);
			int tradesNumber = stocksEntityManager.getTrades().size();
			logger.info("Trades number: "+tradesNumber);

			// Calculates the P/E Ratio for the stock Symbol
			String[] stockSymbols = {"TEA", "POP", "ALE", "GIN", "JOE"};
			//String[] stockSymbols = {"TEA"};
			for(String stockSymbol: stockSymbols){
				double peRatio = stockService.calculatePERatio(stockSymbol);
				logger.info(stockSymbol+" - P/E Ratio calculated: "+peRatio);
				Assert.assertTrue(peRatio >= 0.0);
			}
		}catch(Exception exception){
			logger.error(exception);
			Assert.assertTrue(false);
		}

		logger.info("End calculatePERatioTest ...");
	}


	/**
	 * 
	 */
	@Test
	public void calculateStockPriceTest(){
		try{
			// stock service null check
			StockService stockService = StockServicesFactory.INSTANCE.getSimpleStockService();
			Assert.assertNotNull(stockService);

			StocksEntityManager stocksEntityManager = SpringService.INSTANCE.getBean("stocksEntityManager", StocksEntityManager.class);
			int tradesNumber = stocksEntityManager.getTrades().size();
			logger.info("Trades number: "+tradesNumber);
			
			// Calculates the Stock Price for all stocks
			String[] stockSymbols = {"TEA", "POP", "ALE", "GIN", "JOE"};
			//String[] stockSymbols = {"TEA"};
			for(String stockSymbol: stockSymbols){
				double stockPrice = stockService.calculateStockPrice(stockSymbol);
				logger.info(stockSymbol+" - Stock Price calculated: "+stockPrice);
				Assert.assertTrue(stockPrice >= 0.0);
			}

			
		}catch(Exception exception){
			logger.error(exception);
			Assert.assertTrue(false);
		}

	}

	/**
	 * 
	 */
	@Test
	public void calculateGBCEAllShareIndexTest(){
		try{
			// Create the stock service and verify it's not null object
			StockService stockService = StockServicesFactory.INSTANCE.getSimpleStockService();
			Assert.assertNotNull(stockService);

			StocksEntityManager stocksEntityManager = SpringService.INSTANCE.getBean("stocksEntityManager", StocksEntityManager.class);
			int tradesNumber = stocksEntityManager.getTrades().size();
			logger.info("Trades number: "+tradesNumber);
			
			double GBCEAllShareIndex = stockService.calculateGBCEAllShareIndex();
			logger.info("GBCE All Share Index: "+GBCEAllShareIndex);
			Assert.assertTrue(GBCEAllShareIndex > 0.0);
			
		}catch(Exception exception){
			logger.error(exception);
			Assert.assertTrue(false);
		}

	}

}
