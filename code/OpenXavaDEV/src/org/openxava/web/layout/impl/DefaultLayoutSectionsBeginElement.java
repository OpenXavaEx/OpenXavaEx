/**
 * 
 */
package org.openxava.web.layout.impl;

import org.openxava.view.View;
import org.openxava.web.layout.ILayoutPainter;
import org.openxava.web.layout.ILayoutSectionsBeginElement;

/**
 * @author Federico Alcantara
 *
 */
public class DefaultLayoutSectionsBeginElement extends
		DefaultLayoutViewBeginElement implements
		ILayoutSectionsBeginElement {

	public DefaultLayoutSectionsBeginElement(View view, int groupLevel) {
		super(view, groupLevel);
	}

	@Override
	public void render(ILayoutPainter layoutPainter) {
		layoutPainter.beginSections(this);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SectionsBegin [representsSection="
				+ isRepresentsSection() + ", maxFramesCount="
				+ getMaxFramesCount() + ", maxContainerColumnsCount="
				+ getMaxContainerColumnsCount() + ", rows=" + getRows()
				+ ", groupLevel=" + getGroupLevel() + "]";
	}

	
}
