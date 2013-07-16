/**
 * 
 */
package org.openxava.web.layout;

import org.openxava.view.View;

/**
 * Basic layout element
 * @author Federico Alcantara
 *
 */
public interface ILayoutElement {
	
	void setView(View view);
	
	View getView();
	
	void setViewObject(String viewObject);
	
	String getViewObject();
	
	void render(ILayoutPainter layoutPainter);
	
	int getGroupLevel();
	
	void setGroupLevel(int groupLevel);
}
