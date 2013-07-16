/**
 * 
 */
package org.openxava.web.layout;

import java.util.List;

/**
 * @author Federico Alcantara
 *
 */
public interface ILayoutContainerElement extends ILayoutElement {
	/**
	 * 
	 * @return Maximum number of frames within the container.
	 */
	Integer getMaxFramesCount();
	
	/**
	 * Sets the maximum number of frames within the container.
	 * @param maxFramesCount New frames count.
	 */
	void setMaxFramesCount(Integer maxFramesCount);

	/**
	 * 
	 * @return The maximum number of columns for the container.
	 */
	Integer getMaxContainerColumnsCount();
	
	/**
	 * Sets the new maximum number of columns for the container.
	 * @param maxContainerColumnsCount New value to set.
	 */
	void setMaxContainerColumnsCount(Integer maxContainerColumnsCount);
	
	/**
	 * 
	 * @return A list of valid container rows.
	 */
	List<ILayoutRowBeginElement> getRows();
	
}
