package org.openxava.actions;

import javax.inject.*;
import org.openxava.session.*;

/**
 * 
 * @author Javier Paniza
 */
public class RemoveCustomReportColumnAction extends CollectionBaseAction {
	
	@Inject
	private CustomReport customReport; 
	
	public void execute() throws Exception {
		for (Object o: getSelectedObjects()) {
			customReport.getColumns().remove(o);
		}
	}

}
