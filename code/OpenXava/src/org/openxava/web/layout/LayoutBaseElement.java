/**
 * 
 */
package org.openxava.web.layout;

import org.openxava.view.View;

/**
 * Default implementation for Layout element
 * @author Federico Alcantara
 *
 */
public abstract class LayoutBaseElement implements ILayoutElement {

	private View view;
	private String viewObject;
	private int groupLevel;
	
	public LayoutBaseElement(View view, int groupLevel){
		this.view = view;
		this.viewObject = view.getViewObject();
		this.groupLevel = groupLevel;
	}
	
	/**
	 * @see org.openxava.web.layout.ILayoutElement#setView(org.openxava.view.View)
	 */
	public void setView(View view) {
		this.view = view;
	}

	/**
	 * @see org.openxava.web.layout.ILayoutElement#getView()
	 */
	public View getView() {
		return view;
	}

	public void setViewObject(String viewObject) {
		this.viewObject = viewObject;
	}
	
	public String getViewObject() {
		return viewObject;
	}

	/**
	 * @see org.openxava.web.layout.ILayoutElement#getGroupLevel()
	 */
	public int getGroupLevel() {
		return groupLevel;
	}

	/**
	 * @see org.openxava.web.layout.ILayoutElement#getGroupLevel(java.lang.int)
	 */
	public void setGroupLevel(int groupLevel) {
		this.groupLevel = groupLevel;
	}
	
}
