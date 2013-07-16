/**
 * 
 */
package org.openxava.web.layout;

/**
 * Begin marker for frame begin element.
 * @author Federico Alcantara
 *
 */
public interface ILayoutFrameBeginElement extends ILayoutElement, ILayoutContainerElement {
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
	 * @return Property unqualified name.
	 */
	String getName();
	
	/**
	 * 
	 * @param name Name of the property to be set.
	 */
	void setName(String name);
	

}
