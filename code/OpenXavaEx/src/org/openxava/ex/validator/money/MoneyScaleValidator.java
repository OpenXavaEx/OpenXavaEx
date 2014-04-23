package org.openxava.ex.validator.money;

import org.openxava.util.Messages;
import org.openxava.validators.IPropertyValidator;

/**
 * copied from {@link org.openxava.validators.MoneyScaleValidator}. <p>
 */

public class MoneyScaleValidator implements IPropertyValidator {
	private static final long serialVersionUID = 20140423L;

	public void validate(Messages errors, Object value, String propertyName, String modelName) throws Exception {
		if (value == null) return; // The validity of a null as number is not the business of this validator
		if (!(value instanceof Number)) {
			errors.add("numeric", propertyName, modelName);
			return;
		}
	}

}
