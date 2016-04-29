package com.prize.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
	static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static final SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyyMMddHHmmss");
	static final SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 获取格式为yyyy-MM-dd HH:mm:ss的时间字符串
	 * @return
	 */
	public static String getStringTime(){
		
		return simpleDateFormat.format(new Date());
	}
	/**
	 * 获取格式为yyyyMMddHHmmss的时间字符串
	 * @return
	 */
	public static String getStringTime1(){
		return simpleDateFormat1.format(new Date());
	}
	public static String getStringTimeAndRandom(){
		return simpleDateFormat1.format(new Date())+(Math.random()*100);
	}
	
	public static String getStringTime(Date date){
		
		return simpleDateFormat.format(date);
	}
	
	public static String getStringTimeAndRandom(Date date){
		return simpleDateFormat1.format(date)+((int)(Math.random()*100));
	}
	/**
	 * 判断传入的时间是否在当前时间的三天之内  如果是三天之内返回true  三天之外返回false  
	 * @param time   传入的时间
	 * @return
	 * @throws ParseException 
	 */
	public static boolean judgeDateInThreeDays(String time) throws ParseException{

		long days = (new Date().getTime()-simpleDateFormat.parse(time).getTime())/(24*60*60*1000);
		if(days>=3)
			return false;
		else
			return true;
	} 
	/**
	 * 将传入的时间加三天
	 * @param time
	 * @return
	 */
	public static String addThreeDays(String time) {
		
		try {
			long newTime =simpleDateFormat.parse(time).getTime()+3*24*60*60*1000;
			return simpleDateFormat.format(new Date(newTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * 将传入的时间和当前时间进行比较  如果传入时间晚于当前时间返回true   如果早于当前时间返回false
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static boolean judgeDateIsLatterThanNow(String time) {


		//传入时间早于当前时间
		try {
			if(new Date().getTime()>simpleDateFormat.parse(time).getTime())
				return false;
			else
				return true;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	} 
	
	public static String getDay(){
		return simpleDateFormat2.format(new Date());
	}
	/**
	 * 判断传入的时间和当前时间是否是在指定的day期间  如果是的话返回true
	 * @param time   传入的时间
	 * @param days	 间隔天数
	 * @return
	 * @throws ParseException
	 */
	public static boolean judgeInSomeDays(String time,float days) throws ParseException{
		
		if(new Date().getTime() - simpleDateFormat.parse(time).getTime() < days*24*60*60*1000)//
			return true;
		else
			return false;

	}

	static String pat = "yyyy-MM-dd";
	static SimpleDateFormat sdf = new SimpleDateFormat(pat);
	/**
	 * 根据当前时间和传入的学期开始和结束时间判断是否是这个学期 如果是返回true 不是返回false
	 * @param begin
	 * @param end
	 * @return
	 * @throws ParseException
	 */
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
