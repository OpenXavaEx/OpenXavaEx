/**
 * 
 */
package org.openxava.web.layout.impl;

import org.openxava.view.View;
import org.openxava.web.layout.ILayoutPainter;
import org.openxava.web.layout.ILayoutRowBeginElement;
import org.openxava.web.layout.LayoutBaseElement;

/**
 * @author Federico Alcantara
 *
 */
public class DefaultLayoutRowBeginElement extends LayoutBaseElement
		implements ILayoutRowBeginElement {

	private Integer maxFramesCount;
	private Integer rowIndex;
	private Integer maxRowColumnsCount;
	private Integer rowCurrentColumnsCount;
	private boolean blockStart;
	private boolean blockEnd;
	private boolean first;
	private boolean last;
	
	public DefaultLayoutRowBeginElement(View view, int groupLevel) {
		super(view, groupLevel);
		maxFramesCount = 0;
		rowIndex = -1;
		maxRowColumnsCount = 0;
		rowCurrentColumnsCount = 0;
		blockStart = false;
		blockEnd = false;
	}

	/**
	 * @see org.openxava.web.layout.ILayoutElement#render(org.openxava.web.layout.ILayoutPainter)
	 */
	public void render(ILayoutPainter layoutPainter) {
		layoutPainter.beginRow(this);
	}

	/**
	 * @see org.openxava.web.layout.ILayoutRowBeginElement#getMaxFramesCount()
	 */
	public Integer getMaxFramesCount() {
		return maxFramesCount;
	}

	/**
	 * @see org.openxava.web.layout.ILayoutRowBeginElement#setMaxFramesCount(java.lang.Integer)
	 */
	public void setMaxFramesCount(Integer maxFramesCount) {
		this.maxFramesCount = maxFramesCount;
	}

	/**
	 * @see org.openxava.web.layout.ILayoutRowBeginElement#getRowIndex()
	 */
	public Integer getRowIndex() {
		return rowIndex;
	}

	/**
	 * @see org.openxava.web.layout.ILayoutRowBeginElement#setRowIndex(java.lang.Integer)
	 */
	public void setRowIndex(Integer rowIndex) {
		this.rowIndex = rowIndex;
	}

	public Integer getMaxRowColumnsCount() {
		return maxRowColumnsCount;
	}

	public void setMaxRowColumnsCount(Integer maxRowColumnsCount) {
		this.maxRowColumnsCount = maxRowColumnsCount;
	}

	public Integer getRowCurrentColumnsCount() {
		return rowCurrentColumnsCount;
	}

	public void setRowCurrentColumnsCount(Integer rowCurrentColumnsCount) {
		this.rowCurrentColumnsCount = rowCurrentColumnsCount;
	}

	/**
	 * @return the blockStart
	 */
	public boolean isBlockStart() {
		return blockStart;
	}

	/**
	 * @return the blockEnd
	 */
	public boolean isBlockEnd() {
		return blockEnd;
	}

	/**
	 * @param blockStart the blockStart to set
	 */
	public void setBlockStart(boolean blockStart) {
		this.blockStart = blockStart;
	}

	/**
	 * @param blockEnd the blockEnd to set
	 */
	public void setBlockEnd(boolean blockEnd) {
		this.blockEnd = blockEnd;
	}

	/**
	 * @return the first
	 */
	public boolean isFirst() {
		return first;
	}

	/**
	 * @param first the first to set
	 */
	public void setFirst(boolean first) {
		this.first = first;
	}

	/**
	 * @return the last
	 */
	public boolean isLast() {
		return last;
	}

	/**
	 * @param last the last to set
	 */
	public void setLast(boolean last) {
		this.last = last;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RowBegin [maxFramesCount="
				+ maxFramesCount + ", rowIndex=" + rowIndex
				+ ", maxRowColumnsCount=" + maxRowColumnsCount
				+ ", rowCurrentColumnsCount=" + rowCurrentColumnsCount
				+ ", blockStart=" + blockStart + ", blockEnd=" + blockEnd
				+ ", first=" + first + ", last=" + last
				+ ", groupLevel=" + getGroupLevel() + "]";
	}

}
