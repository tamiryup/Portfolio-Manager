package com.fyber.tamir.FyberChallenge;

import java.util.Calendar;
import java.util.Date;

public class Helper {
	
	public static Date addDaysToDate(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		Date before = cal.getTime();
		return before;
	}

}
