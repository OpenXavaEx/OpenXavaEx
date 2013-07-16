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
	private List<Boolean> showColumnLabelStates;

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


	public void setShowColumnLabel(int columnIndex, boolean state) {
		if (columnIndex >= 0) {
			for (int index = 0; index <= columnIndex; index++) {
				getShowColumnLabelStates().add(false);
			}
			getShowColumnLabelStates().set(columnIndex, state);
		}
	}

	public boolean getShowColumnLabel(int columnIndex) {
		boolean returnValue = false;
		if (columnIndex >= 0 && 
				columnIndex < getShowColumnLabelStates().size()) {
			returnValue = getShowColumnLabelStates().get(columnIndex);
		}
		return returnValue;
	}
	
	private List<Boolean> getShowColumnLabelStates() {
		if (showColumnLabelStates == null) {
			showColumnLabelStates = new ArrayList<Boolean>();
		}
		return showColumnLabelStates;
	}

}
