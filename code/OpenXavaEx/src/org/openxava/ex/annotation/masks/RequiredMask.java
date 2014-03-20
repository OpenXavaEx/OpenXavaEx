package org.openxava.ex.annotation.masks;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Just a mask to to force a field looked "required". <p>
 * 
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface RequiredMask {
	/**
	 * Indicate the field should display as "required" or not.
	 * @return
	 */
	boolean value() default true;
}
