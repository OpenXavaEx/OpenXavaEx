/**
 * 
 */
package org.openxava.web.layout.impl;

import org.openxava.util.meta.MetaElement;
import org.openxava.view.View;
import org.openxava.web.layout.ILayoutFrameEndElement;
import org.openxava.web.layout.ILayoutPainter;
import org.openxava.web.layout.LayoutBaseElement;

/**
 * @author Federico Alcantara
 *
 */
public class DefaultLayoutFrameEndElement extends LayoutBaseElement
		implements ILayoutFrameEndElement {

	private String propertyPrefix;
	private String label;
	private String name;

	public DefaultLayoutFrameEndElement(View view, int groupLevel) {
		super(view, groupLevel);
	}

	public DefaultLayoutFrameEndElement(View view, int groupLevel, MetaElement metaElement) {
		super(view, groupLevel);
		setPropertyPrefix("");
		setLabel(metaElement.getLabel());
		setName(metaElement.getName());
	}

	/**
	 * @see org.openxava.web.layout.ILayoutElement#render(org.openxava.web.layout.ILayoutPainter)
	 */
	public void render(ILayoutPainter layoutPainter) {
		layoutPainter.endFrame(this);
	}

	/**
	 * @return the propertyPrefix
	 */
	public String getPropertyPrefix() {
		return propertyPrefix;
	}

	/**
	 * @param propertyPrefix the propertyPrefix to set
	 */
	public void setPropertyPrefix(String propertyPrefix) {
		this.propertyPrefix = propertyPrefix;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FrameEnd [groupLevel="
				+ getGroupLevel() + "]";
	}

}
