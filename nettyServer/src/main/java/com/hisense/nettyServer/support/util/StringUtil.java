package com.hisense.nettyServer.support.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author qiush
 *
 */
public class StringUtil {
	
	private static Logger logger = LoggerFactory.getLogger("errorLog");
	public static boolean isNull(String s){
		if(s!=null&&s.trim().length()!=0){
			return false;
		}else{
			return true;
		}
	}
	public static boolean isFloat(String s){
		boolean contains = s.contains(".");
		return contains;
	}
	
	public static BigDecimal parseBigDecimal(String s,int scale,int UpOrDown){
		if(StringUtil.isNull(s)){
			return null;
		}else{
			BigDecimal bigDecimal;
			bigDecimal = new BigDecimal(s).setScale(scale, UpOrDown);
			return bigDecimal;
		}
	}
	
	public static Short parseShort(String s){
		if(StringUtil.isNull(s)){
			return null;
		}else{
			if(StringUtil.isFloat(s)){
				return Short.parseShort(s.split("\\.")[0]);
			}else{
				return Short.parseShort(s);
			}
		}
	}
	
	public static Integer parseInteger(String s){
		if(StringUtil.isNull(s)){
			return null;
		}else{
			if(StringUtil.isFloat(s)){
				return Integer.parseInt(s.split("\\.")[0]);
			}else{
				return Integer.parseInt(s);
			}
		}
		
	}
	
	public static Long parseLong(String s){
		if(StringUtil.isNull(s)){
			return null;
		}else{
			if(StringUtil.isFloat(s)){
				return Long.parseLong(s.split("\\.")[0]);
			}else{
				return Long.parseLong(s);
			}
		}
		
	}
	
	public static Date parseDate(String s){
		if(StringUtil.isNull(s)){
			return null;
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = null;
			try {
				date = sdf.parse(s);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				logger.error("日期解析异常"+e.getMessage(),e);
			}
			return date;
		}
	}
	
	public static Date parseDate1(String s){
		if(StringUtil.isNull(s)){
			return null;
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Date date = null;
			try {
				date = sdf.parse(s);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				logger.error("日期解析异常"+e.getMessage(),e);
			}
			return date;
		}
	}
	
}
