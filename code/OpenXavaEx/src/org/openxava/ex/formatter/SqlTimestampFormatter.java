package org.openxava.ex.formatter;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.openxava.formatters.IFormatter;
import org.openxava.util.Is;
import org.openxava.util.XavaResources;

/**
 * Date formatter with multilocale support. <p>
 * 
 * Although it does some refinement in Spanish case, it support formatting
 * on locale basis.<br>
 *  
 * @author Javier Paniza
 */

public class SqlTimestampFormatter implements IFormatter {
	
	private static DateFormat defaultDateTimeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	public String format(HttpServletRequest request, Object date) {
		if (date == null) return "";
		return defaultDateTimeFormat.format(date);
	}
		
	public Object parse(HttpServletRequest request, String string) throws ParseException {
		if (Is.emptyString(string)) return null;				

		try {
			Date d = defaultDateTimeFormat.parse(string);
			return new Timestamp(d.getTime());
		} catch (ParseException ex) {
			//Ignore it
		}						
		throw new ParseException(XavaResources.getString("bad_date_format",string),-1);
	}
}
