package org.openxava.annotations;

import java.lang.annotation.*;

/**
 * A group of <code>@{@link RemoveValidator}</code> associated to the same entity. <p>
 * 
 * Applies to entities.<p>
 * 
 * Example:
 * <pre>
 * &nbsp;@Entity
 * &nbsp;@RemoveValidators({
 * &nbsp;&nbsp;&nbsp;@EntityValidator(ProductRemoveValidator.class),
 * &nbsp;&nbsp;&nbsp;@EntityValidator(ProductNotUsedValidator.class)	
 * &nbsp;})
 * &nbsp;public class Product {
 * &nbsp;...
 * </pre>
 * 
 * @author Javier Paniza
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface RemoveValidators {
	
	RemoveValidator [] value();
	
}
