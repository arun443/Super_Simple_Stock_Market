package com.jpmorgan.coding.test;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 */
public class SequeceTest {

	static Logger logger = Logger.getLogger(SequeceTest.class);

	@Test
	public void printSequenceTest1 (){		              
		
		logger.info("Start  SequeceTest1 ...");		
		
		  String input = "1 2 3 5 9 a 6 7 8 4 @ -5 -7 -3 -2 -1";

	       Sequence.pirntSequence(input);

	       logger.info("End iSequeceTest1  ");

	}	
	
	
	@Test
	public void printSequenceTest2 (){		
              
		logger.info("Start  SequeceTest2 ...");		
		
		  String input = "-1 -4 -6 -8 -9 -10 -11 -12 0 1 5 6 7 8";

	       Sequence.pirntSequence(input);

	       logger.info("End iSequeceTest2 ");

	}	
	
	
	@Test
	public void printSequenceTest3 (){		
              
		logger.info("Start  SequeceTest3 ...");		
		
		  String input = "-1 1 5 6 7 8";

	       Sequence.pirntSequence(input);

	       logger.info("End iSequeceTest3  ");

	}	
	
	@Test
	public void printSequenceTest4 (){		
		
             
		  logger.info("Start  SequeceTest4 ...");		
		
		  String input = "a b c d 1";

	       Sequence.pirntSequence(input);

	       logger.info("End iSequeceTest4  ");

	}	

}
