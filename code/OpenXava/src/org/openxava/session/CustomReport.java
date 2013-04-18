package org.openxava.session;

import java.util.*;
import java.util.prefs.*;

import javax.persistence.*;
import org.openxava.annotations.*;
import org.openxava.model.meta.*;
import org.openxava.tab.Tab;
import org.openxava.util.*;

/**
 * 
 * @author Javier Paniza 
 */

public class CustomReport implements java.io.Serializable {
	
	private static final String NAME = "name";
	private static final String LAST_NAME = "lastName"; 
	private static final String MODEL_NAME = "modelName";
			
	@Required @Column(length=80) 
	@OnChange(org.openxava.actions.OnChangeCustomReportNameAction.class) // It's only thrown in combo format, this is controlled from the editor 
	private String name;
	
	@Hidden
	private MetaModel metaModel;
	
	@RowActions({
		@RowAction("CustomReport.columnUp"),
		@RowAction("CustomReport.columnDown")
	})
	@RemoveSelectedAction("CustomReport.removeColumn")
	@AsEmbedded 
	@SaveAction("CustomReport.saveColumn")
	@EditAction("CustomReport.editColumn")
	@ListProperties("name, comparator, value, order")
	private List<CustomReportColumn> columns;
	
	private String rootNodeName;
	
	public static CustomReport create(org.openxava.tab.Tab tab) {  
		CustomReport report = createEmpty(tab);
		report.setColumns(createColumns(report, tab));
		return report;
	}
	
	public static CustomReport createEmpty(Tab tab) {
		CustomReport report = new CustomReport();
		report.setName(tab.getTitle()); 	
		report.setMetaModel(tab.getMetaTab().getMetaModel());
		report.setNodeName(tab);
		return report;
	}
	
	public static CustomReport find(org.openxava.tab.Tab tab, String name) throws BackingStoreException {   
		CustomReport report = new CustomReport();	
		report.setName(name);
		report.setNodeName(tab);
		report.load();
		return report;
	}
		
	/**
	 * The names of all the reports of the same Tab of the current one. 
	 */
	@Hidden
	public String [] getAllNames() throws BackingStoreException { 
		return Users.getCurrentPreferences().node(rootNodeName).childrenNames();
	}
	
	/**
	 * The name of the last generated report of the same Tab of the current one. 
	 */	
	@Hidden
	public String getLastName() throws BackingStoreException { 
		String lastName = getRootPreferences().get(LAST_NAME, ""); 
		String [] allNames = getAllNames();
		if (Arrays.binarySearch(allNames, lastName) >= 0) return lastName; 
		return allNames.length > 0?allNames[0]:""; 
	}
	
	private static List<CustomReportColumn> createColumns(CustomReport report, org.openxava.tab.Tab tab) {
		List<CustomReportColumn> columns = new ArrayList<CustomReportColumn>();
		for (MetaProperty property: tab.getMetaProperties()) {		
			CustomReportColumn column = new CustomReportColumn();
			column.setReport(report);
			column.setName(property.getQualifiedName());
			column.setCalculated(property.isCalculated());
			columns.add(column);
		}		
		return columns;		
	}
	
	public void load() throws BackingStoreException { 
		Preferences preferences = getPreferences();
		name = preferences.get(NAME, name);
		String modelName = preferences.get(MODEL_NAME, "Unknown MetaModel");
		setMetaModel(MetaModel.get(modelName));
		int i = 0;
		CustomReportColumn column = new CustomReportColumn();
		columns = new ArrayList();
		while (column.load(preferences, i++)) {
			columns.add(column);
			column.setReport(this);
			column = new CustomReportColumn();
		}
		preferences.flush();
	}	
	
	public void save() throws BackingStoreException { 
		Preferences preferences = getPreferences();		
		preferences.put(NAME, name);
		preferences.put(MODEL_NAME, getMetaModel().getName());
		int i = 0;
		for (CustomReportColumn column: columns) {
			column.save(preferences, i++);
		}
		preferences.flush();
		Preferences rootPreferences = getRootPreferences();
		rootPreferences.put(LAST_NAME, name);
		rootPreferences.flush();
	}
	
	public void remove() throws BackingStoreException { 
		getPreferences().removeNode();		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CustomReportColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<CustomReportColumn> columns) {
		this.columns = columns;
	}

	public MetaModel getMetaModel() {
		return metaModel;
	}

	public void setMetaModel(MetaModel metaModel) {
		this.metaModel = metaModel;
	}
	
	private void setNodeName(org.openxava.tab.Tab tab) { 
		rootNodeName = "customReport." + tab.friendCustomReportGetPreferencesNodeName();	
	}
	
	private Preferences getPreferences() throws BackingStoreException { 
		return Users.getCurrentPreferences().node(rootNodeName).node(name);
	}
	
	private Preferences getRootPreferences() throws BackingStoreException {		
		return Users.getCurrentPreferences().node(rootNodeName);
	}

	@Override
	public String toString() {
		return "CustomReport: " + name;
	}
		
}
