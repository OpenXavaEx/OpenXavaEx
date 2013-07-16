package org.openxava.annotations;

import java.lang.annotation.*;

/**
 * A group of <code>@{@link RowAction}</code> associated to the same collection. <p>
 * 
 * Applies to collections.<p>
 * 
 * It allows to define a value different for <code>@{@link RowAction}</code> in each view.<br>
 * Example:
 * <pre>
 * &nbsp;@RowActions({
 * &nbsp;&nbsp;&nbsp;@RowAction(forViews="DEFAULT", value= ... ),
 * &nbsp;&nbsp;&nbsp;@RowAction(forViews="Simple, VerySimple", value= ... ),
 * &nbsp;&nbsp;&nbsp;@RowAction(forViews="Complete", value= ... )
 * &nbsp;})
 * </pre>
 *
 * @author Oscar Kozto
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
public @interface RowActions {
	
	RowAction [] value();
	
}
