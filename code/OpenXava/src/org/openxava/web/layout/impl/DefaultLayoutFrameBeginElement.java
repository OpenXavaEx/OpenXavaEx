/**
 * 
 */
package org.openxava.web.layout.impl;

import org.openxava.model.meta.MetaMember;
import org.openxava.util.meta.MetaElement;
import org.openxava.view.View;
import org.openxava.web.layout.ILayoutFrameBeginElement;
import org.openxava.web.layout.ILayoutPainter;
import org.openxava.web.layout.LayoutBaseContainerElement;

/**
 * @author Federico Alcantara
 *
 */
public class DefaultLayoutFrameBeginElement extends LayoutBaseContainerElement
		implements ILayoutFrameBeginElement {
	
	private String propertyPrefix;
	private String label;
	private String name;
	
	public DefaultLayoutFrameBeginElement(View view, int groupLevel) {
		super(view, groupLevel);
	}

	public DefaultLayoutFrameBeginElement(View view, int groupLevel, MetaElement metaElement) {
		super(view, groupLevel);
		setPropertyPrefix("");
		if (metaElement instanceof MetaMember) {
			setLabel(view.getLabelFor((MetaMember)metaElement));
		} else {
			setLabel(metaElement.getLabel());
		}
		setName(metaElement.getName());
		setMaxFramesCount(0);
		setMaxContainerColumnsCount(0);
	}
	
	/**
	 * @see org.openxava.web.layout.ILayoutElement#render(org.openxava.web.layout.ILayoutPainter)
	 */
	public void render(ILayoutPainter layoutPainter) {
		layoutPainter.beginFrame(this);
	}

	/**
	 * @return the propertyPrefix
	 */
	public String getPropertyPrefix() {
		return propertyPrefix;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param propertyPrefix the propertyPrefix to set
	 */
	public void setPropertyPrefix(String propertyPrefix) {
		this.propertyPrefix = propertyPrefix;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
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
		return "FrameBegin [propertyPrefix="
				+ propertyPrefix + ", label=" + label + ", name=" + name
				+ ", maxFramesCount=" + getMaxFramesCount()
				+ ", maxContainerColumnsCount=" + getMaxContainerColumnsCount()
				+ ", groupLevel=" + getGroupLevel() + "]";
	}

}
