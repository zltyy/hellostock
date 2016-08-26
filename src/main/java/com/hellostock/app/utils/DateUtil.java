package com.hellostock.app.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	public static final String LONG_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String STR_DATE_PATTERN = "yyyyMMdd";
	public static final String STR_DATE_PATTERN2 = "yyyy/MM/dd";
	
	
	@SuppressWarnings("finally")
	public static Date format(String date, String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date d = null;
		try {
			d = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}finally{
			return d;
		}
	}
	
	public static int format2Int(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int result = 0;
		int year = c.get(Calendar.YEAR) * 10000;
		int month = (c.get(Calendar.MONTH) + 1) * 100;
		int day = c.get(Calendar.DAY_OF_MONTH);
		result = year + month + day;
		return result;
	}
	
	public static String format2String(Date date, String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

}
