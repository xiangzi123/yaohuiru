package com.core.base.util;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DateUtil {
	
	public static String formatToDateTime(Date date) {
		if(date==null)
			return null;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(date);
	}
	
	public static Date convertToDateTime(String date) throws Exception{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.parse(date);
	}
	public static String formatToDateTimeNoSec(Date date) {
		if(date==null)
			return null;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return df.format(date);
	}
	
	public static Date convertToDateTimeNoSec(String date) throws Exception{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return df.parse(date);
	}
	public static String formatToDate(Date date) {
		if(date==null)
			return null;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);
	}
	
	public static Date convertToDate(String date) throws Exception{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.parse(date);
	}
	
}
