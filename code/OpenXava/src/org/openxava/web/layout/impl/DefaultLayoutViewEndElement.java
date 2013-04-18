/**
 * 
 */
package org.openxava.web.layout.impl;

import org.openxava.view.View;
import org.openxava.web.layout.ILayoutPainter;
import org.openxava.web.layout.ILayoutViewEndElement;
import org.openxava.web.layout.LayoutBaseElement;

/**
 * Default implementation for ILayoutViewEndElement.
 * @author Federico Alcantara
 *
 */
public class DefaultLayoutViewEndElement extends LayoutBaseElement 
		implements ILayoutViewEndElement {

	public DefaultLayoutViewEndElement(View view, int groupLevel) {
		super(view, groupLevel);
	}

	/**
	 * @see org.openxava.web.layout.ILayoutElement#render(org.openxava.web.layout.ILayoutPainter)
	 */
	public void render(ILayoutPainter layoutPainter) {
		layoutPainter.endView(this);
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ViewEnd [groupLevel="
				+ getGroupLevel() + "]";
	}

}
