package org.openxava.actions;

import org.openxava.model.meta.*;
import org.openxava.util.*;

/**
 * 
 * @author Javier Paniza 
 */

public class OnChangeCustomReportColumnNameAction extends TabBaseAction implements IOnChangePropertyAction {
	
	public final static String STRING_COMPARATOR = "__STRING__";
	public final static String DATE_COMPARATOR = "__DATE__";
	public final static String EMPTY_COMPARATOR = "__EMPTY__";
	public final static String OTHER_COMPARATOR = "__OTHER__";
		
	private Object newValue;
	
	public void execute() throws Exception {		
		String propertyName = (String) newValue;
		if (Is.emptyString(propertyName)) {
			getView().setValue("comparator", EMPTY_COMPARATOR); 
			showStandardMembers();
			return;
		}		
		MetaProperty property = getTab().getMetaTab().getMetaModel().getMetaProperty(propertyName);		
		if (property.isCalculated()) {
			hideMembers();
			return;
		}
		
		if ("boolean".equals(property.getType().getName()) || "java.lang.Boolean".equals(property.getType().getName())) {
			showBooleanValue();
			return;
		}
		
		if (property.hasValidValues()) {
			showValidValuesValue();
			return;
		}
				
		String comparatorValue = getView().getValueString("comparator"); 
		if ("java.lang.String".equals(property.getType().getName())) {
			getView().setValue("comparator", STRING_COMPARATOR + ":" + comparatorValue); 
		}
		else if (java.util.Date.class.isAssignableFrom(property.getType()) && 
			!property.getType().equals(java.sql.Time.class)) 
		{ 			
			getView().setValue("comparator", DATE_COMPARATOR + ":" + comparatorValue); 
		}
		else {			
			getView().setValue("comparator", OTHER_COMPARATOR + ":" + comparatorValue); 
		}
		showStandardMembers();
	}

	private void showBooleanValue() {
		getView().setHidden("comparator", true);
		getView().setHidden("value", true);
		getView().setHidden("booleanValue", false);
		getView().setHidden("validValuesValue", true);
		getView().setHidden("order", false);
	}
	
	private void showValidValuesValue() {
		getView().setHidden("comparator", true);
		getView().setHidden("value", true);
		getView().setHidden("booleanValue", true);
		getView().setHidden("validValuesValue", false);
		getView().setHidden("order", false);
	}	

	private void hideMembers() {
		getView().setHidden("comparator", true);
		getView().setHidden("value", true);
		getView().setHidden("booleanValue", true);
		getView().setHidden("validValuesValue", true);
		getView().setHidden("order", true);
	}

	private void showStandardMembers() {
		getView().setHidden("comparator", false);
		getView().setHidden("value", false);			
		getView().setHidden("booleanValue", true);
		getView().setHidden("validValuesValue", true);
		getView().setHidden("order", false);
	}

	public void setChangedProperty(String propertyName) {		
	}

	public void setNewValue(Object value) {
		newValue = value;		
	}

}
