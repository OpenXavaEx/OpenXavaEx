/**
 * 
 */
package org.openxava.web.layout.impl;

import org.openxava.view.View;
import org.openxava.web.layout.ILayoutPainter;
import org.openxava.web.layout.ILayoutViewBeginElement;
import org.openxava.web.layout.LayoutBaseContainerElement;

/**
 * @author Federico Alcantara
 *
 */
public class DefaultLayoutViewBeginElement extends LayoutBaseContainerElement implements
		ILayoutViewBeginElement {
	
	private boolean representsSection;
	
	public DefaultLayoutViewBeginElement(View view, int groupLevel) {
		super(view, groupLevel);
		representsSection = false;
	}

	public void render(ILayoutPainter layoutPainter) {
		layoutPainter.beginView(this);
	}

	public boolean isRepresentsSection() {
		return representsSection;
	}

	public void setRepresentsSection(boolean representsSection) {
		this.representsSection = representsSection;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ViewBegin [representsSection="
				+ representsSection + ", maxFramesCount=" + getMaxFramesCount()
				+ ", maxContainerColumnsCount=" + getMaxContainerColumnsCount()
				+ ", groupLevel=" + getGroupLevel() + "]";
	}

}
