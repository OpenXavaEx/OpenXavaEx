/**
 * 
 */
package org.openxava.web.layout;

import java.util.ArrayList;
import java.util.List;

import org.openxava.view.View;

/**
 * Base Container element
 * @author Federico Alcantara
 *
 */
public abstract class LayoutBaseContainerElement extends LayoutBaseElement 
		implements ILayoutContainerElement {

	private Integer maxFramesCount;
	private Integer maxContainerColumnsCount;
	private List<ILayoutRowBeginElement> rows;

	public LayoutBaseContainerElement(View view, int groupLevel) {
		super(view, groupLevel);
		maxFramesCount = 0;
		maxContainerColumnsCount = 0;
	}

	/**
	 * @return the maxFramesCount
	 */
	public Integer getMaxFramesCount() {
		return maxFramesCount;
	}

	/**
	 * @return the maxContainerColumnsCount
	 */
	public Integer getMaxContainerColumnsCount() {
		return maxContainerColumnsCount;
	}

	/**
	 * @return list of rows.
	 */
	public List<ILayoutRowBeginElement> getRows() {
		if (rows == null) {
			rows = new ArrayList<ILayoutRowBeginElement>();
		}
		return rows;
	}

	/**
	 * @param maxFramesCount the maxFramesCount to set
	 */
	public void setMaxFramesCount(Integer maxFramesCount) {
		this.maxFramesCount = maxFramesCount;
	}

	/**
	 * @param maxContainerColumnsCount the maxContainerColumnsCount to set
	 */
	public void setMaxContainerColumnsCount(Integer maxContainerColumnsCount) {
		this.maxContainerColumnsCount = maxContainerColumnsCount;
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(List<ILayoutRowBeginElement> rows) {
		this.rows = rows;
	}

}
