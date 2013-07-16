package org.openxava.actions;

import javax.inject.*;

import org.openxava.session.*;

/**
 * 
 * @author Javier Paniza 
 */

public class OnChangeCustomReportNameAction extends TabBaseAction implements IOnChangePropertyAction  {
	
	@Inject
	private CustomReport customReport;

	private String name;
	
	public void execute() throws Exception {
		customReport = CustomReport.find(getTab(), name);
		getView().setModel(customReport);		
	}

	public void setChangedProperty(String propertyName) {
	}

	public void setNewValue(Object value) {
		name = (String) value;		
	}

}
