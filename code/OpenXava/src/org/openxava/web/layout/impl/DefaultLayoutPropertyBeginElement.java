/**
 * 
 */
package org.openxava.web.layout.impl;

import java.util.Collection;

import org.openxava.model.meta.MetaMember;
import org.openxava.model.meta.MetaProperty;
import org.openxava.model.meta.MetaReference;
import org.openxava.view.View;
import org.openxava.view.meta.MetaViewAction;
import org.openxava.web.layout.ILayoutPainter;
import org.openxava.web.layout.ILayoutPropertyBeginElement;
import org.openxava.web.layout.LayoutBaseElement;

/**
 * @author Federico Alcantara
 *
 */
public class DefaultLayoutPropertyBeginElement extends LayoutBaseElement
		implements ILayoutPropertyBeginElement {

 	private MetaProperty metaProperty;
	private MetaReference metaReference;
	private Collection<String> actionsNameForReference;
	private Collection<String> actionsNameForProperty;
	
	private boolean hasActions;
	private boolean editable;
	private boolean search;
	private boolean lastSearchKey;
	private boolean createNew;
	private boolean modify;
	private boolean throwPropertyChanged;
	private boolean displayAsDescriptionsList;
	private String label;
	private String searchAction;
	private int labelFormat;
	private String propertyKey;
	private String propertyPrefix = "";
	private String referenceForDescriptionsList;
	private String name;

	public DefaultLayoutPropertyBeginElement(View view, int groupLevel) {
		super(view, groupLevel);
		hasActions = false;
		editable = false;
		search = false;
		lastSearchKey = false;
		createNew = false;
		modify = false;
		throwPropertyChanged = false;
		displayAsDescriptionsList = false;
		label = "";
		searchAction = "";
		labelFormat = 0;
		propertyKey = "";
		propertyPrefix = "";
		referenceForDescriptionsList = "";
		name = "";
	}

	/**
	 * @see org.openxava.web.layout.ILayoutElement#render(org.openxava.web.layout.ILayoutPainter)
	 */
	public void render(ILayoutPainter layoutPainter) {
		layoutPainter.beginProperty(this);
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPropertyBeginElement#getMetaProperty()
	 */
	public MetaProperty getMetaProperty() {
		return metaProperty;
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPropertyBeginElement#setMetaProperty(org.openxava.model.meta.MetaProperty)
	 */
	public void setMetaProperty(MetaProperty metaProperty) {
		this.metaProperty = metaProperty; 
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPropertyBeginElement#getMetaReference()
	 */
	public MetaReference getMetaReference() {
		return metaReference;
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPropertyBeginElement#setMetaReference(org.openxava.model.meta.MetaReference)
	 */
	public void setMetaReference(MetaReference metaReference) {
		this.metaReference = metaReference; 
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPropertyBeginElement#isSearch()
	 */
	public boolean isSearch() {
		return search;
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPropertyBeginElement#setSearch(boolean)
	 */
	public void setSearch(boolean isSearch) {
		this.search = isSearch; 
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPropertyBeginElement#isCreateNew()
	 */
	public boolean isCreateNew() {
		return createNew;
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPropertyBeginElement#setCreateNew(boolean)
	 */
	public void setCreateNew(boolean isCreateNew) {
		this.createNew = isCreateNew; 
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPropertyBeginElement#isModify()
	 */
	public boolean isModify() {
		return modify;
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPropertyBeginElement#setModify(boolean)
	 */
	public void setModify(boolean isModify) {
		this.modify = isModify; 
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPropertyBeginElement#getName()
	 */
	public String getName() {
		return name;
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPropertyBeginElement#setName(java.lang.String)
	 */
	public void setName(String name) {
		this.name = name; 
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPropertyBeginElement#isEditable()
	 */
	public boolean isEditable() {
		return editable;
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPropertyBeginElement#setEditable(boolean)
	 */
	public void setEditable(boolean editable) {
		this.editable = editable; 
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPropertyBeginElement#getSearchAction()
	 */
	public String getSearchAction() {
		return searchAction;
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPropertyBeginElement#setSearchAction(java.lang.String)
	 */
	public void setSearchAction(String searchAction) {
		this.searchAction = searchAction; 
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPropertyBeginElement#getLabel()
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPropertyBeginElement#setLabel(java.lang.String)
	 */
	public void setLabel(String label) {
		this.label = label; 
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPropertyBeginElement#getLabelFormat()
	 */
	public Integer getLabelFormat() {
		return labelFormat;
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPropertyBeginElement#setLabelFormat(java.lang.Integer)
	 */
	public void setLabelFormat(Integer labelFormat) {
		this.labelFormat = labelFormat; 
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPropertyBeginElement#isThrowPropertyChanged()
	 */
	public boolean isThrowPropertyChanged() {
		return throwPropertyChanged;
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPropertyBeginElement#setThrowPropertyChanged(boolean)
	 */
	public void setThrowPropertyChanged(boolean isThrowPropertyChanged) {
		this.throwPropertyChanged = isThrowPropertyChanged; 
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPropertyBeginElement#setPropertyKey(java.lang.String)
	 */
	public void setPropertyKey(String propertyKey) {
		this.propertyKey = propertyKey; 
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPropertyBeginElement#getPropertyKey()
	 */
	public String getPropertyKey() {
		return propertyKey;
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPropertyBeginElement#setLastSearchKey(boolean)
	 */
	public void setLastSearchKey(boolean lastSearchKey) {
		this.lastSearchKey = lastSearchKey; 
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPropertyBeginElement#isLastSearchKey()
	 */
	public boolean isLastSearchKey() {
		return lastSearchKey;
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPropertyBeginElement#setReferenceForDescriptionsList(java.lang.String)
	 */
	public void setReferenceForDescriptionsList(
			String referenceForDescriptionsList) {
		this.referenceForDescriptionsList = referenceForDescriptionsList;
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPropertyBeginElement#getReferenceForDescriptionsList()
	 */
	public String getReferenceForDescriptionsList() {
		return referenceForDescriptionsList;
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPropertyBeginElement#setDisplayAsDescriptionsList(boolean)
	 */
	public void setDisplayAsDescriptionsList(boolean displayAsDescriptionsList) {
		this.displayAsDescriptionsList = displayAsDescriptionsList; 
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPropertyBeginElement#isDisplayAsDescriptionsList()
	 */
	public boolean isDisplayAsDescriptionsList() {
		return displayAsDescriptionsList;
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPropertyBeginElement#setActionsNameForReference(java.util.Collection)
	 */
	public void setActionsNameForReference(
			Collection<String> actionsNameForReference) {
		this.actionsNameForReference = actionsNameForReference;
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPropertyBeginElement#getActionsNameForReference()
	 */
	public Collection<String> getActionsNameForReference() {
		return actionsNameForReference;
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPropertyBeginElement#setActionsNameForProperty(java.util.Collection)
	 */
	public void setActionsNameForProperty(
			Collection<String> actionsNameForProperty) {
		this.actionsNameForProperty = actionsNameForProperty;
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPropertyBeginElement#getActionsNameForProperty()
	 */
	public Collection<String> getActionsNameForProperty() {
		return actionsNameForProperty;
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPropertyBeginElement#setActions(boolean)
	 */
	public void setActions(boolean hasActions) {
		this.hasActions = hasActions; 
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPropertyBeginElement#hasActions()
	 */
	public boolean hasActions() {
		return hasActions;
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPropertyBeginElement#setPropertyPrefix(java.lang.String)
	 */
	public void setPropertyPrefix(String propertyPrefix) {
		this.propertyPrefix = propertyPrefix;
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPropertyBeginElement#getPropertyPrefix()
	 */
	public String getPropertyPrefix() {
		return propertyPrefix;
	}

	/**
	 * 
	 * @return True if element represents a key
	 */
	public boolean isKey() {
		boolean returnValue = false;
		if (metaProperty != null) {
			returnValue = metaProperty.isKey();
		} else if (metaReference != null) {
			returnValue = metaReference.isKey();
		}
		return returnValue;
	}
	
	public boolean isRequired() {
		boolean returnValue = false;
		if (metaProperty != null) {
			returnValue = metaProperty.isRequired();
		} else if (metaReference != null) {
			returnValue = metaReference.isRequired();
		}
		return returnValue;
	}
	
	public String getQualifiedName() {
		String returnValue = "";
		if (metaProperty != null) {
			returnValue = metaProperty.getQualifiedName();
		} else if (metaReference != null) {
			returnValue = metaReference.getQualifiedName();
		}
		return returnValue;
	}

	public MetaMember getMetaMember() {
		MetaMember returnValue = null;
		if (metaProperty != null) {
			returnValue = metaProperty;
		} else if (metaReference != null) {
			returnValue = metaReference;
		}
		return returnValue;
	}

	public boolean isMetaViewAction() {
		if (metaProperty != null &&
				(metaProperty instanceof MetaViewAction)) {
			return true;
		}
		return false;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PropertyBegin [label=" + label
				+ ", propertyKey=" + propertyKey + ", propertyPrefix="
				+ propertyPrefix + ", name=" + name
				+ ", viewObject=" + getViewObject()
				+ ", groupLevel="
				+ getGroupLevel() + "]";
	}
	
}
