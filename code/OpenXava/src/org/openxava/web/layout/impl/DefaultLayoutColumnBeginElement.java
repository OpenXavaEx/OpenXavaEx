/**
 * 
 */
package org.openxava.web.layout.impl;

import org.openxava.view.View;
import org.openxava.web.layout.ILayoutColumnBeginElement;
import org.openxava.web.layout.ILayoutPainter;
import org.openxava.web.layout.LayoutBaseElement;

/**
 * @author Federico Alcantara
 *
 */
public class DefaultLayoutColumnBeginElement extends LayoutBaseElement
		implements ILayoutColumnBeginElement {

	public DefaultLayoutColumnBeginElement(View view, int groupLevel) {
		super(view, groupLevel);
	}

	/**
	 * @see org.openxava.web.layout.ILayoutElement#render(org.openxava.web.layout.ILayoutPainter)
	 */
	public void render(ILayoutPainter layoutPainter) {
		layoutPainter.beginColumn(this);
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ColumnBegin [groupLevel=" + getGroupLevel() + "]";
	}

}
