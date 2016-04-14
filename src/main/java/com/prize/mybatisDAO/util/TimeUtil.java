package com.prize.mybatisDAO.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
	static String pat = "yyyy-MM-dd";
	static SimpleDateFormat sdf = new SimpleDateFormat(pat);
	public static boolean checkTerm(String begin,String end) throws ParseException{
		Date beginDay = sdf.parse(begin);
		Date endDay = sdf.parse(end);
		long current = System.currentTimeMillis();
		if(current>beginDay.getTime()&&current<endDay.getTime())
			return true;
		else
			return false;
	}
	
}
