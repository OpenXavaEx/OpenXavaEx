/**
 * 
 */
package org.openxava.web.layout.impl;

import org.openxava.util.meta.MetaElement;
import org.openxava.view.View;
import org.openxava.web.layout.ILayoutGroupEndElement;
import org.openxava.web.layout.ILayoutPainter;

/**
 * @author Federico Alcantara
 *
 */
public class DefaultLayoutGroupEndElement extends
		DefaultLayoutFrameEndElement implements ILayoutGroupEndElement {

	public DefaultLayoutGroupEndElement(View view, int groupLevel) {
		super(view, groupLevel);
	}

	public DefaultLayoutGroupEndElement(View view, int groupLevel,
			MetaElement metaElement) {
		super(view, groupLevel, metaElement);
	}

	@Override
	public void render(ILayoutPainter layoutPainter) {
		layoutPainter.endGroup(this);
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GroupEnd [groupLevel="
				+ getGroupLevel() + "]";
	}
	
}
