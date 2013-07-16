package org.openxava.actions;

import java.util.*;
import javax.inject.*;
import org.openxava.session.*;

/**
 * 
 * @author Javier Paniza
 */
public class MoveCustomReportColumnAction extends CollectionBaseAction {
	
	@Inject
	private CustomReport customReport;
	private int increment;
	
	public void execute() throws Exception {
		int otherRow = getRow() + increment;
		if (otherRow < 0 || otherRow >= getMapValues().size()) return;
		Collections.swap(customReport.getColumns(), getRow(), otherRow);	
	}

	public int getIncrement() {
		return increment;
	}

	public void setIncrement(int increment) {
		this.increment = increment;
	}

}
