package org.openxava.actions;

import javax.inject.*;
import org.openxava.session.*;

/**
 * 
 * @author Javier Paniza 
 */
public class MyReportsAction extends TabBaseAction {
	
	@Inject
	private CustomReport customReport; 

	public void execute() throws Exception {
		setNextMode(DETAIL);
		showDialog();	
		getView().setTitleId("myReports");
		customReport = CustomReport.createEmpty(getTab()); 
		getView().setModel(customReport);			
		if (customReport.getAllNames().length > 0) {
			getView().setEditable("name", false);
			getView().addActionForProperty("name", "CustomReport.createNew");
			getView().setValueNotifying("name", customReport.getLastName()); 
			customReport = (CustomReport) getView().getModel(); 
		}
		else {
			customReport = CustomReport.create(getTab()); 
			getView().setModel(customReport);			
		}
		getView().addActionForProperty("name", "CustomReport.remove");						
		setControllers("CustomReport");
	}
	
}
