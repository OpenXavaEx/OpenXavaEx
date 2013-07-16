package org.openxava.actions;

import javax.inject.*;

import org.openxava.session.*;

/**
 * 
 * @author Javier Paniza 
 */

public class RemoveCustomReportAction extends TabBaseAction {
	
	@Inject
	private CustomReport customReport;
	
	public void execute() throws Exception {
		String name = getView().getValueString("name");
		if (!getView().isEditable("name")) {
			customReport.remove();					
		}
		
		if (customReport.getAllNames().length > 0) {
			if (getView().isEditable("name")) {
				getView().removeActionForProperty("name", "CustomReport.remove");
				getView().addActionForProperty("name", "CustomReport.createNew");
				getView().addActionForProperty("name", "CustomReport.remove");
			}
			getView().setEditable("name", false);
			getView().setValueNotifying("name", customReport.getLastName());
			customReport = (CustomReport) getView().getModel();
		}
		else {
			getView().setEditable("name", true);			
			customReport = CustomReport.create(getTab()); 
			getView().setModel(customReport);		
			getView().removeActionForProperty("name", "CustomReport.createNew");
		}
		addMessage("report_removed", "'" + name + "'");
	}

}
