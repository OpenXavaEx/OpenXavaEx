package org.openxava.actions;

import javax.inject.*;
import org.openxava.session.*;

/**
 * 
 * @author Javier Paniza 
 */

public class SaveCustomReportColumnAction extends CollectionElementViewBaseAction {
	
	@Inject
	private CustomReport customReport; 

	public void execute() throws Exception {
		CustomReportColumn column = new CustomReportColumn();
		column.setReport(customReport);
		String columnName = getCollectionElementView().getValueString("name");
		column.setName(columnName);		
		if (getCollectionElementView().getMembersNames().containsKey("comparator")) {
			String comparator = getCollectionElementView().getValueString("comparator");
			column.setComparator(comparator);
		}
		if (getCollectionElementView().getMembersNames().containsKey("value")) {
			String value = getCollectionElementView().getValueString("value");
			column.setValue(value);
		}
		Boolean booleanValue = (Boolean) getCollectionElementView().getValue("booleanValue");		
		column.setBooleanValue(booleanValue);
		int validValuesValue = getCollectionElementView().getValueInt("validValuesValue");
		column.setValidValuesValue(validValuesValue);

		CustomReportColumn.Order order = (CustomReportColumn.Order) getCollectionElementView().getValue("order");		
		column.setOrder(order);		
		
		if (getCollectionElementView().getCollectionEditingRow() < 0) {
			if (alreadyExists(columnName)) {
				addError("column_not_added_to_report", "'" + columnName + "'"); 
			}
			else {
				customReport.getColumns().add(column);
				addMessage("column_added_to_report", "'" + columnName + "'");
			}
		}
		else {
			customReport.getColumns().set(getCollectionElementView().getCollectionEditingRow(), column);
			addMessage("report_column_modified", "'" + columnName + "'");			
		}
		closeDialog();
	}

	private boolean alreadyExists(String columnName) {
		for (CustomReportColumn column: customReport.getColumns()) {
			if (column.getName().equals(columnName)) return true;
		}
		return false;
	}

}
