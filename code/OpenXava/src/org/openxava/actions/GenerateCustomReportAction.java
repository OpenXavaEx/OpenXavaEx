package org.openxava.actions;

import java.util.*;

import javax.inject.*;

import org.apache.commons.logging.*;
import org.openxava.session.*;
import org.openxava.tab.*;
import org.openxava.util.*;

/**
 * 
 * @author Javier Paniza
 */

public class GenerateCustomReportAction extends GenerateReportAction {
	
	private static Log log = LogFactory.getLog(GenerateCustomReportAction.class);
	
	@Inject
	private CustomReport customReport; 
	
	public void execute() throws Exception {		
		super.execute();
		customReport.setName(getView().getValueString("name")); 
		Tab tab = new Tab();
		tab.setModelName(getTab().getModelName());
		tab.setTabName(getTab().getTabName());
		tab.setTitle(getView().getValueString("name"));		
		tab.clearProperties();		
		Collection<String> comparators = new ArrayList<String>();
		Collection<String> values = new ArrayList<String>();
		StringBuffer order = new StringBuffer();
		for (CustomReportColumn column: customReport.getColumns()) {
			tab.addProperty(column.getName());
			if (column.isCalculated()) continue;
			if (column.getComparator() != null) {
				comparators.add(column.getComparator());
				values.add(column.getValueForCondition());				
			}
			else {
				comparators.add(null);
				values.add(null);				
			}
			if (column.getOrder() != null) {
				order.append(order.length() == 0?"":", ");
				order.append("${");
				order.append(column.getName());
				order.append("} ");
				order.append(column.getOrder() == CustomReportColumn.Order.ASCENDING?"ASC":"DESC");				
			}
		}
		if (order.length() > 0) {
			tab.setDefaultOrder(order.toString());			
		}		
		tab.setConditionComparators(comparators);
		tab.setConditionValues(values);
		
		try {
			customReport.save(); 
		}
		catch (Exception ex) {
			log.warn(XavaResources.getString("customer_report_save_problems"), ex);
		}
		getRequest().getSession().setAttribute("xava_reportTab", tab);		
		closeDialog();
	}
	
}
