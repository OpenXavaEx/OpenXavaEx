package org.openxava.ex.formatter.date;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.openxava.ex.utils.DateUtils;
import org.openxava.formatters.IFormatter;
import org.openxava.util.Is;

/**
 * The Date Formatter with configurable format and Date subclass type.
 */
public class SmartDateFormatter implements IFormatter {
	/** Property in editor definition, to set the Date/Time format. */
	public static final String FORMAT = SmartDateFormatter.class.getName() + ".FORMAT";
	/** Class of the parse result object, support {@link java.sql.Date}, {@link java.sql.Time}, {@link java.sql.Timestamp} */
	public static final String CLASS = SmartDateFormatter.class.getName() + ".CLASS";
	
	public static String getFormat(HttpServletRequest request){
		String fmt = request.getParameter(FORMAT);
		return (null==fmt)?"yyyy/MM/dd":fmt;
	}
	private Object wrap(Date d, HttpServletRequest request){
		String cls = request.getParameter(CLASS);
		if (null==cls){
			return d;
		}else{
			if (cls.equals(Timestamp.class.getName())){
				return new Timestamp(d.getTime());
			}else if (cls.equals(Time.class.getName())){
				return new Time(d.getTime());
			}else if (cls.equals(java.sql.Date.class.getName())){
				return new java.sql.Date(d.getTime());
			}else if (cls.equals(Date.class.getName())){
				return d;
			}else{
				throw new ClassCastException("Can't case Date to class <"+cls+">");
			}
		}
	}

	public String format(HttpServletRequest request, Object object) throws Exception {
		if (object == null) return "";
		DateFormat fmt = new SimpleDateFormat(getFormat(request));
		return fmt.format(object);
	}

	public Object parse(HttpServletRequest request, String string) throws Exception {
		if (Is.emptyString(string)) return null;
		
		Date d = DateUtils.smartParseDate(string, getFormat(request));
		if (null!=d){
			return this.wrap(d, request);
		}else{
			return null;
		}
	}

}
