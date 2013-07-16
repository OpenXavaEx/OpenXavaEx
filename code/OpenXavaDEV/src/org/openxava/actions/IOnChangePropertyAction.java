package org.openxava.actions;

import org.openxava.view.*;

/**
 * 
 * @author Javier Paniza
 */

public interface IOnChangePropertyAction extends IAction {
	
	void setChangedProperty(String propertyName);
	
	void setNewValue(Object value);
	
	// tmp doc y wiki
	void setView(View view);

}
