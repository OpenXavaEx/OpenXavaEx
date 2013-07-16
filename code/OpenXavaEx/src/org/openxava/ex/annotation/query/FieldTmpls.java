package org.openxava.ex.annotation.query;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.openxava.ex.model.base.BaseReportQuery;

/**
 * Define the template fields' properties for {@link BaseReportQuery}. <p>
 * 
 * Applies to classes. <p> 
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Inherited
public @interface FieldTmpls {
	FieldTmpl[] value();
}
