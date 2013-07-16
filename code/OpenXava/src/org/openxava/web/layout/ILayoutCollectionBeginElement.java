/**
 * 
 */
package org.openxava.web.layout;

import org.openxava.model.meta.MetaCollection;

/**
 * Collection begin marker for collection elements.
 * @author Federico Alcantara
 *
 */
public interface ILayoutCollectionBeginElement extends ILayoutElement {
	/**
	 * 
	 * @return True if this collection is framed.
	 */
	boolean hasFrame();
	
	/**
	 * Sets whether or not the collection is within a frame.
	 * @param hasFrame
	 */
	void setFrame(boolean hasFrame);
	
	/**
	 * 
	 * @return Associated metacollection.
	 */
	MetaCollection getMetaCollection();
	
	/**
	 * Sets a new meta collection.
	 * @param metaCollection Meta collection to set.
	 */
	void setMetaCollection(MetaCollection metaCollection);
	
	/**
	 * 
	 * @return Current collection label.
	 */
	String getLabel();
	
	/**
	 * @param label Sets the collection label.
	 */
	void setLabel(String label);
}
