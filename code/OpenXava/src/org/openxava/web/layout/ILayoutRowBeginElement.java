/**
 * 
 */
package org.openxava.web.layout;

/**
 * Begin marker for row elements.
 * @author Federico Alcantara
 *
 */
public interface ILayoutRowBeginElement extends ILayoutElement {
	/**
	 * 
	 * @return Maximum number of frames within the row.
	 */
	Integer getMaxFramesCount();
	
	/**
	 * Sets the maximum number of frames within the row.
	 * @param maxFramesCount New frames count.
	 */
	void setMaxFramesCount(Integer maxFramesCount);
	
	/**
	 * 
	 * @return Current row index relative to the whole page (without the sections).
	 */
	Integer getRowIndex();
	
	/**
	 * Sets the row index relative to the whole page.
	 * @param rowIndex New row index value.
	 */
	void setRowIndex(Integer rowIndex);
	
	/**
	 * 
	 * @return Maximum number of columns count within the row.
	 */
	Integer getMaxRowColumnsCount();
	
	/**
	 * Sets the maximum number of columns within the row.
	 * @param maxRowColumnsCount Maximum number of columns to set for the row.
	 */
	void setMaxRowColumnsCount(Integer maxRowColumnsCount);
	
	/**
	 * 
	 * @return Current rendered columns count.
	 */
	Integer getRowCurrentColumnsCount();
	
	/**
	 * Sets the current rendered columns.
	 * @param rowCurrentColumnsCount Number of columns already displayed.
	 */
	void setRowCurrentColumnsCount(Integer rowCurrentColumnsCount);

	/**
	 * 
	 * @return True if the row starts a block.
	 */
	boolean isBlockStart();
	
	/**
	 * Sets the block start state of the row.
	 * @param blockEnd New block start state to set.
	 */
	void setBlockStart(boolean blockStart);
	
	/**
	 * 
	 * @return True if the row ends a block.
	 */
	boolean isBlockEnd();
	
	/**
	 * Sets the block end state of the row.
	 * @param blockEnd New block end state to set.
	 */
	void setBlockEnd(boolean blockEnd);
	
	
	/**
	 * 
	 * @return True if this row is the first one.
	 */
	boolean isFirst();
	
	/**
	 * Sets the first state of the row.
	 * @param firstState Value to apply to first state property.
	 */
	void setFirst(boolean firstState);
	
	/**
	 * 
	 * @return True if this row is the last.
	 */
	boolean isLast();
	
	/**
	 * Sets the last state of the row.
	 * @param lastState Value to apply to last state property.
	 */
	void setLast(boolean lastState);
	
}
