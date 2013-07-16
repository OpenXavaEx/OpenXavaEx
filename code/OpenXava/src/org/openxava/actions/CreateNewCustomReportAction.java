package org.openxava.actions;

import javax.inject.*;

import org.openxava.session.*;

/**
 * 
 * @author Javier Paniza
 */
public class CreateNewCustomReportAction extends TabBaseAction {
	
	@Inject
	private CustomReport customReport; 

	public void execute() throws Exception {
		customReport = CustomReport.create(getTab());
		getView().setModel(customReport);		
		getView().setEditable("name", true);			
		getView().removeActionForProperty("name", "CustomReport.createNew");
	}

}
