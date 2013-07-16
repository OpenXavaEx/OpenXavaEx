/**
 * 
 */
package org.openxava.web.layout;

/**
 * @author Federico Alcantara
 *
 */
public interface ILayoutViewBeginElement extends ILayoutContainerElement {
	/**
	 * If true indicates that the contents of this view is a 
	 * tab of a section.
	 * @return True or false.
	 */
	boolean isRepresentsSection();
	
	/**
	 * Sets the type of view. False is a normal view, true is a section (tab) view.
	 * @param representsSection Value to be set.
	 */
	void setRepresentsSection(boolean representsSection);

}
