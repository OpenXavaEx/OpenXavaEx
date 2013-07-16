package org.openxava.ex.annotation.query;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.openxava.ex.model.base.BaseReportQuery;

/**
 * Define the behavior for {@link BaseReportQuery}. <p>
 * 
 * Applies to classes. <p> 
 * 
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface Sql {
	
	/**
	 * You can define several sql in a class, and set a name for each one. <p>
	 *  
	 * This name is used to indicate the sql that you want to use .
	 */
	String name() default "";
	
	/**
	 * The resource path of SQL Statement.
	 * @return
	 */
	String value();
	
	/**
	 * The Tag in sql statement where define the position of query condition fragments
	 * @return
	 */
	String conditionTag() default "${#Where}";
}
