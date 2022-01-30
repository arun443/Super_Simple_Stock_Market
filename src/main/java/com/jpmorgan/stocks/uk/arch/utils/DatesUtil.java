package com.jpmorgan.stocks.uk.arch.utils;

import java.util.Calendar;
import java.util.Date;

public class DatesUtil  {

	
	public Date getNowMovedMinutes(int minutes){
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MINUTE, minutes);
		return now.getTime();
	}
}
