/**
 * 
 */
package org.openxava.web.layout;

import java.util.Collection;

import org.openxava.model.meta.MetaMember;
import org.openxava.model.meta.MetaProperty;
import org.openxava.model.meta.MetaReference;


/**
 * Represents a property begin marker element
 * @author Federico Alcantara
 *
 */
public interface ILayoutPropertyBeginElement extends ILayoutElement {
	
	MetaProperty getMetaProperty();
	
	/**
	 * Sets the associated meta property (if any).
	 * @param metaProperty Meta property.
	 */
	void setMetaProperty(MetaProperty metaProperty);
	
	MetaReference getMetaReference();
	
	/**
	 * Sets the associated meta references (if any).
	 * @param metaReference Meta reference.
	 */
	void setMetaReference(MetaReference metaReference);
	
	/**
	 * 
	 * @return True if this is a searchable property.
	 */
	boolean isSearch();
	
	/**
	 * Sets wether or not the property is searchable.
	 * @param isSearch Value to be set.
	 */
	void setSearch(boolean isSearch);
	
	/**
	 * 
	 * @return True if this element has a create new action.
	 */
	boolean isCreateNew();
	
	/**
	 * 
	 * @param isCreateNew New value to set.
	 */
	void setCreateNew(boolean isCreateNew);
	
	/**
	 * 
	 * @return True if this element has a modify action associated.
	 */
	boolean isModify();
	
	/**
	 * 
	 * @param isModify New value to set.
	 */
	void setModify(boolean isModify);
	
	/**
	 * 
	 * @return Property unqualified name.
	 */
	String getName();
	
	/**
	 * 
	 * @param name Name of the property to be set.
	 */
	void setName(String name);
	
	/**
	 * 
	 * @return If the property is editable.
	 */
	boolean isEditable();
	
	/**
	 * 
	 * @param editable Sets the edition capabilities of the property.
	 */
	void setEditable(boolean editable);
	
	/**
	 * 
	 * @return Default search action for the property.
	 */
	String getSearchAction();
	
	/**
	 * Sets of reset the search action.
	 * @param searchAction New search action for the property.
	 */
	void setSearchAction(String searchAction);
	
	/**
	 * 
	 * @return Current label.
	 */
	String getLabel();
	
	/**
	 * Sets the label of the property.
	 * @param label New label for the property. Might be null. 
	 */
	void setLabel(String label);
	
	/**
	 * 
	 * @return Label format for the property.
	 */
	Integer getLabelFormat();
	
	/**
	 * Sets the label format of the property.
	 * @param labelFormat New label format.
	 */
	void setLabelFormat(Integer labelFormat);
	
	/**
	 * 
	 * @return True if the property has property changed triggers.
	 */
	boolean isThrowPropertyChanged();
	
	/**
	 * Sets whether or not the property throws a property change action.
	 * @param isThrowPropertyChanged True if the property throws a property change action.
	 */
	void setThrowPropertyChanged(boolean isThrowPropertyChanged);
	
	/**
	 * @param propertyKey the propertyKey to set
	 */
	void setPropertyKey(String propertyKey);
	
	/**
	 * @return the propertyKey
	 */
	String getPropertyKey();
	
	/**
	 * @param lastSearchKey the lastSearchKey to set
	 */
	void setLastSearchKey(boolean lastSearchKey);
	
	/**
	 * @return the lastSearchKey
	 */
	boolean isLastSearchKey();
	
	/**
	 * @param referenceForDescriptionsList the referenceForDescriptionsList to set
	 */
	void setReferenceForDescriptionsList(
			String referenceForDescriptionsList);
	
	/**
	 * @return the referenceForDescriptionsList
	 */
	String getReferenceForDescriptionsList();

	/**
	 * @param displayAsDescriptionsList the displayAsDescriptionsList to set
	 */
	void setDisplayAsDescriptionsList(boolean displayAsDescriptionsList);
	
	/**
	 * @return the displayAsDescriptionsList
	 */
	boolean isDisplayAsDescriptionsList();
	
	/**
	 * @param actionsNameForReference the actionsNameForReference to set
	 */
	void setActionsNameForReference(Collection<String> actionsNameForReference);
	
	/**
	 * @return the actionsNameForReference
	 */
	public Collection<String> getActionsNameForReference();
	
	/**
	 * @param actionsNameForProperty the actionsNameForProperty to set
	 */
	public void setActionsNameForProperty(Collection<String> actionsNameForProperty);
	
	/**
	 * @return the actionsNameForProperty
	 */
	public Collection<String> getActionsNameForProperty();

	/**
	 * @param actions the actions to set
	 */
	void setActions(boolean hasActions);
	
	/**
	 * @return the actions
	 */
	boolean hasActions();

	/**
	 * @param propertyPrefix the propertyPrefix to set
	 */
	void setPropertyPrefix(String propertyPrefix);
	
	/**
	 * @return the propertyPrefix
	 */
	String getPropertyPrefix();
	
	/**
	 * 
	 * @return True if element represents a key.
	 */
	public boolean isKey();
	
	/**
	 * 
	 * @return True if the property is required.
	 */
	public boolean isRequired();
	
	/**
	 * 
	 * @return The property full qualified name.
	 */
	public String getQualifiedName();

	/**
	 * 
	 * @return The metamember. Either MetaReference or MetaProperty
	 */
	public MetaMember getMetaMember();
		
	/**
	 * 
	 * @return True if this property represents a MetaViewAction
	 */
	boolean isMetaViewAction();
}
