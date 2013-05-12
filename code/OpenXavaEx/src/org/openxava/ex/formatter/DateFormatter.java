package org.openxava.ex.formatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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

public class DateFormatter implements IFormatter {
	
	private static DateFormat defaultDateFormat = new SimpleDateFormat("yyyy/MM/dd");
	
	public String format(HttpServletRequest request, Object date) {
		if (date == null) return "";
		return defaultDateFormat.format(date);
	}
		
	public Object parse(HttpServletRequest request, String string) throws ParseException {
		if (Is.emptyString(string)) return null;				

		try {
			return defaultDateFormat.parse(string);
		} catch (ParseException ex) {
			//Ignore it
		}						
		throw new ParseException(XavaResources.getString("bad_date_format",string),-1);
	}
}
