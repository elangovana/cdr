package com.ae.assignment.cdrproject.cdrstream.rulesEvaluator;

import java.util.Calendar;
import java.util.Date;

public class DatesSlicer {
	
	public static int GetYearlyIndex(Date date, int periodIndays)
	{
		Calendar calender =  Calendar.getInstance();
		calender.setTime(date);
		return (int) Math.ceil(( (float) calender.get(Calendar.DAY_OF_YEAR) / periodIndays) );
		};
	}


