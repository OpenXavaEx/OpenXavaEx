package org.openxava.ex.annotation.query;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.openxava.ex.model.base.BaseReportQuery;

/**
 * Define the template field's property for {@link BaseReportQuery}. <p>
 * 
 * Applies to classes and fields. <p> 
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface FieldProp {
	/**
	 * The property name
	 * @return
	 */
	String name() default "";
	
	String value();
}
