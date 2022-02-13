package com.jpmorgan.coding.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class Sequence {
	
    public static void pirntSequence(String input) {
    	
    	
    	Logger logger = Logger.getLogger(Sequence.class);

        String[] splitInput = input.split(" ");
        List<Integer> sequence = new ArrayList<>();
        List<List<Integer>> sequences = new ArrayList<>();
        Integer previous = null;
        for (String s : splitInput) {
            try {
                Integer current = Integer.parseInt(s);
                if (null != previous) {
                    if ((current - previous) == 1) {
                        sequence.add(current);
                    } else {
                        if (sequence.size() > 1) {
                            sequences.add(sequence);
                        }
                        sequence = new ArrayList<>();
                        sequence.add(current);
                    }
                    previous = current;
                } else {
                    previous = current;
                    sequence.add(previous);
                }
            } catch (NumberFormatException nfe) {
            	
            	logger.info("input String is not valid/Number -----> "+nfe);
                if (sequence.size() > 1) {
                    sequences.add(sequence);
                }
                previous = null;
                sequence = new ArrayList<>();
            }
            
            catch (Exception e) {
            	            	
            	logger.info("Exception " + e);
            	e.printStackTrace();

               
            }
        }
        if(sequence.size() > 1) {
            sequences.add(sequence);
        }
        sequences.stream().forEach(logger::info);
    }
}