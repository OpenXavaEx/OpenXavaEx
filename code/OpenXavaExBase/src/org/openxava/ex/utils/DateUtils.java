package org.openxava.ex.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for Date
 */
public class DateUtils {
	/** Regex for Time Zone such like ＋0800， －0700 */
	private static Pattern END_WITH_TIMEZONE = Pattern.compile(".*\\s+([\\+|-][0-1]\\d00)$");
	/** All standard date string formats */
	private static String[] STD_DATE_FORMATS = new String[]{
		"yyyy/M/d H:m:s.S Z", "yyyy/M/d H:m:s Z", "yyyy/M/d H:m Z", "yyyy/M/d H Z", "yyyy/M/d Z"
	};
	/** All standard time only string formats */
	private static String[] STD_TIME_FORMATS = new String[]{
		"H:m:s.S Z", "H:m:s Z", "H:m Z", "H Z"
	};
	
	/**
	 * Parse Date/Time String with given format, if fail, try the {@link #STD_DATE_FORMATS} and {@link #STD_TIME_FORMATS}
	 * @param dateString
	 * @param firstFmt The given format
	 * @return
	 */
	public static Date smartParseDate(String dateString, String firstFmt){
		List<String> fmts = new ArrayList<String>();
		if (null!=firstFmt){
			fmts.add(firstFmt);
		}
		fmts.addAll(Arrays.asList(STD_DATE_FORMATS));
		fmts.addAll(Arrays.asList(STD_TIME_FORMATS));
		return doParse(dateString, fmts.toArray(new String[]{}));
	}
	
	/**
	 * Convert standard format(see {@link #STD_DATE_FORMATS}) date/time string to Date object
	 * (Support time zone, or use current time zone)<br>
	 * The date splitter could be '/' and '-', but time splitter must be ':' ! <br>
	 * This method based on following facts(and maybe change in different JRE implementation):<br>
	 *  1)When parsing, pattern "yyyy/M/d H:m:s Z" equals "yyyy/MM/dd HH:mm:ss Z";<br>
	 *  2)BLANK between number will not change the parsing result(for example:" 2002/ 4/ 06 1: 05 +0000");<br>
	 * 
	 * @param dateString Date/Time String
	 * @return Date Object, or null if convert fail
	 */
	public static Date stdParseDate(String dateString) {
		return doParse(dateString, STD_DATE_FORMATS);
	}
	/**
	 * Convert standard format(see {@link #STD_TIME_FORMATS}) time string to Date object
	 * (Support time zone, or use current time zone), for more information, reference {@link #stdParseDate(String)}<br>
	 * @param timeString Time String
	 * @return Date Object, or null if convert fail
	 */
	public static Date stdParseTime(String timeString) {
		return doParse(timeString, STD_TIME_FORMATS);
	}

	private static Date doParse(String dateString, String[] fmts) {
		String sDate = dateString;
		if (null==sDate) return null;
		
		// Remove heading and tailing BLANK
		sDate = sDate.trim();

		String tz = splitTimeZone(sDate);
		String tm = sDate;
		if (null==tz){
			tz = getCurrTimeZone();
		}else{
			tm = sDate.substring(0, sDate.length() - tz.length());
		}
		// Replace splitter '-' to standard splitter: '/'
		tm = tm.replace('-', '/');
		sDate = tm.trim() + " " +tz;
		
		// Try different formats
		for (int i=0; i<fmts.length; i++){
			Date date = _string2Date(sDate, fmts[i], tz);
			if (date != null) {
				return (date);
			}
		}		
		// Parse fail ...
		return null;
	}
	private static String splitTimeZone(String s){
		Matcher m = END_WITH_TIMEZONE.matcher(s);
		if (m.matches()){
			return m.group(1);
		}else{
			return null;
		}
	}
	private static String getCurrTimeZone() {
		int offset = TimeZone.getDefault().getRawOffset();
		String wOrE = "";
		if (offset > 0)
			wOrE = "+";
		else
			wOrE = "-";
		String timeZoneCode = String.valueOf(offset / (60 * 60 * 1000));
		if (timeZoneCode.length() < 2) {
			timeZoneCode = wOrE + "0" + timeZoneCode + "00";
		} else {
			timeZoneCode = wOrE + timeZoneCode + "00";
		}
		return timeZoneCode;
	}
	private static Date _string2Date(String sDate, String fmt, String timeZone) {
		Date date;
		SimpleDateFormat ofmt = new SimpleDateFormat(fmt);
		try {
			date = ofmt.parse(sDate);
			
			return date;	//FIXME: This Date object has current time zone, not the specified time zone
		} catch (ParseException ex) {
			date = null;
		}
		return (date);
	}
}
