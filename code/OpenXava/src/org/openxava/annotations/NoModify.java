package org.openxava.annotations;

import java.lang.annotation.*;

/**
 * The final user cannot modify the current referenced object from here. <p>
 * 
 * Applies to references and collections. <p>
 * 
 * Example:
 * <pre>
 * &nbsp;@OneToMany(mappedBy="seller")
 * &nbsp;@NoModify
 * &nbsp;private Collection<Customer> customers;
 * </pre>
 * 
 * @author Javier Paniza
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
public @interface NoModify {
		
	/**
	 * List of comma separated view names where this annotation applies. <p>
	 * 
	 * Exclusive with notForViews.<br>
	 * If both forViews and notForViews are omitted then this annotation
	 * apply to all views.<br>
	 * You can use the string "DEFAULT" for referencing to the default
	 * view (the view with no name).
	 */			
	String forViews() default "";
	
	/**
	 * List of comma separated view names where this annotation does not apply. <p>
	 * 
	 * Exclusive with forViews.<br>
	 * If both forViews and notForViews are omitted then this annotation
	 * apply to all views.<br>
	 * You can use the string "DEFAULT" for referencing to the default
	 * view (the view with no name).
	 */ 			
	String notForViews() default "";
	
}
