/**
 * 
 */
package org.openxava.web.layout.impl;

import org.openxava.view.View;
import org.openxava.web.layout.ILayoutPainter;
import org.openxava.web.layout.ILayoutSectionsEndElement;

/**
 * @author Federico Alcantara
 *
 */
public class DefaultLayoutSectionsEndElement extends
		DefaultLayoutViewEndElement implements ILayoutSectionsEndElement {

	public DefaultLayoutSectionsEndElement(View view, int groupLevel) {
		super(view, groupLevel);
	}

	@Override
	public void render(ILayoutPainter layoutPainter) {
		layoutPainter.endSections(this);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SectionsEnd [groupLevel="
				+ getGroupLevel() + "]";
	}
}
