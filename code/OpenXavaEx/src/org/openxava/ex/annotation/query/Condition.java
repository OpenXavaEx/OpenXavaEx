package org.openxava.ex.annotation.query;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.openxava.ex.model.base.BaseReportQuery;

/**
 * Define the conditions for {@link BaseReportQuery}. <p>
 * 
 * Applies to classes. <p> 
 * 
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface Condition {
	/**
	 * The sql condition fragment which contain the placeholder of current field's value.
	 * @return
	 */
	String value();
}
