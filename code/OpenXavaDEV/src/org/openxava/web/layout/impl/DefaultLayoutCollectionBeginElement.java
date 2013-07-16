/**
 * 
 */
package org.openxava.web.layout.impl;

import org.openxava.model.meta.MetaCollection;
import org.openxava.view.View;
import org.openxava.web.layout.ILayoutCollectionBeginElement;
import org.openxava.web.layout.ILayoutPainter;
import org.openxava.web.layout.LayoutBaseElement;

/**
 * Default implementation of collection layout element.
 * @author Federico Alcantara
 *
 */
public class DefaultLayoutCollectionBeginElement extends LayoutBaseElement
		implements ILayoutCollectionBeginElement {
	
	private boolean hasFrame;
	private MetaCollection metaCollection;
	private String label;
	
	public DefaultLayoutCollectionBeginElement(View view, int groupLevel) {
		super(view, groupLevel);
		setLabel("");
	}

	/**
	 * @see org.openxava.web.layout.ILayoutElement#render(org.openxava.web.layout.ILayoutPainter)
	 */
	public void render(ILayoutPainter layoutPainter) {
		layoutPainter.beginCollection(this);
	}

	/**
	 * @see org.openxava.web.layout.ILayoutCollectionBeginElement#hasFrame()
	 */
	public boolean hasFrame() {
		return hasFrame;
	}

	/**
	 * @see org.openxava.web.layout.ILayoutCollectionBeginElement#setFrame(boolean)
	 */
	public void setFrame(boolean hasFrame) {
		this.hasFrame = hasFrame;
	}

	/**
	 * @see org.openxava.web.layout.ILayoutCollectionBeginElement#getMetaCollection()
	 */
	public MetaCollection getMetaCollection() {
		return metaCollection;
	}

	/**
	 * @see org.openxava.web.layout.ILayoutCollectionBeginElement#setMetaCollection(org.openxava.model.meta.MetaCollection)
	 */
	public void setMetaCollection(MetaCollection metaCollection) {
		this.metaCollection = metaCollection;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CollectionBegin [label=" + label
				+ ", groupLevel=" + getGroupLevel() + "]";
	}

}
