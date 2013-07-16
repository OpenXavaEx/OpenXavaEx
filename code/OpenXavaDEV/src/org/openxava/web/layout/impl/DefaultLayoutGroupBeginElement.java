/**
 * 
 */
package org.openxava.web.layout.impl;

import org.openxava.util.meta.MetaElement;
import org.openxava.view.View;
import org.openxava.web.layout.ILayoutGroupBeginElement;
import org.openxava.web.layout.ILayoutPainter;

/**
 * @author Federico Alcantara
 *
 */
public class DefaultLayoutGroupBeginElement extends
		DefaultLayoutFrameBeginElement implements ILayoutGroupBeginElement {

	public DefaultLayoutGroupBeginElement(View view, int groupLevel) {
		super(view, groupLevel);
	}

	public DefaultLayoutGroupBeginElement(View view, int groupLevel,
			MetaElement metaElement) {
		super(view, groupLevel, metaElement);
	}

	@Override
	public void render(ILayoutPainter layoutPainter) {
		layoutPainter.beginGroup(this);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GroupBegin [propertyPrefix="
				+ getPropertyPrefix() + ", label=" + getLabel()
				+ ", name=" + getName() + ", maxFramesCount="
				+ getMaxFramesCount() + ", maxContainerColumnsCount="
				+ getMaxContainerColumnsCount() + ", groupLevel="
				+ getGroupLevel() + "]";
	}
}
