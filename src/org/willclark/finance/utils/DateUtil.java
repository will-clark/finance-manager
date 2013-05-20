package org.willclark.finance.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static final String INTERNAL_DATETIME_FORMAT = "yyyyMMddHHmm";
	public static final String EXTERNAL_DATETIME_FORMAT = "MM/dd/yyyy hh:mm a";
	
	public static Date addDays(Date date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);		
		calendar.add(Calendar.DAY_OF_YEAR, days);
		return calendar.getTime();
	}

	public static Date parse(String str) {
		return parse(str, INTERNAL_DATETIME_FORMAT);
	}
	
	public static Date parse(String str, String format) {
		try {
			return new SimpleDateFormat(format).parse(str);
		}
		catch (ParseException e) {
			// do nothing
		}
		
		return null;
	}
	
	public String format(Date date) {
		return format(date, EXTERNAL_DATETIME_FORMAT);
	}
	
	public String format(Date date, String format) {
		if (date == null) return "";
		
		try {
			return new SimpleDateFormat(format).format(date);
		}
		catch (Exception e) {
			return "";
		}		
	}
	
}
