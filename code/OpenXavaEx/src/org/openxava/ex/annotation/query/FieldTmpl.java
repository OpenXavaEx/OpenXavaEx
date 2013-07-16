package org.openxava.ex.annotation.query;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.openxava.ex.model.base.BaseReportQuery;

/**
 * Define the template field's properties for {@link BaseReportQuery}. <p>
 * 
 * Applies to classes. <p> 
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface FieldTmpl {
	public static final String NAME_DEFAULT = "*";
	
	/**
	 * You can config the field template properties by it's name
	 * @return
	 */
	String fieldName() default NAME_DEFAULT;
	/**
	 * You can config the field template properties by it's class
	 * @return
	 */
	Class<?> fieldClass() default Object.class;

	FieldProp[] value();
	
}
