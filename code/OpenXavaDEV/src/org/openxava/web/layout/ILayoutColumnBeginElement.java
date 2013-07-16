/**
 * 
 */
package org.openxava.web.layout;

/**
 * Begin marker for layout's column elements.
 * @author Federico Alcantara
 *
 */
public interface ILayoutColumnBeginElement extends ILayoutElement {
	/**
	 * Sets the column current index.
	 * @param columnIndex New column index to set.
	 */
	void setColumnIndex(int columnIndex);
	
	/**
	 * Gets the column index relative to its container.
	 * @return Column column index.
	 */
	int getColumnIndex();
}
