package org.openxava.ex.formatter.money;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.openxava.formatters.IFormatter;
import org.openxava.util.Is;
import org.openxava.util.Strings;

/**
 * For MONEY and DINERO stereotypes, copied from {@link org.openxava.formatters.MoneyFormatter}. <p>
 */

public class MoneyFormatter implements IFormatter {

	public String format(HttpServletRequest request, Object object)	throws Exception {
		if (object == null) return "";
		return object.toString();
	}

	public Object parse(HttpServletRequest request, String string) throws Exception {
		if (Is.emptyString(string)) return null; 
		string = Strings.change(string, " ", ""); // In order to work with Polish		
		return new BigDecimal(string);
	}
}
