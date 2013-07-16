/**
 * 
 */
package org.openxava.web.layout;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import org.openxava.view.View;

/**
 * Painter manager. Parses and paints views.
 * Views are painted from top to bottom, left to right.
 * @author Federico Alcantara
 *
 */
public class LayoutPainterManager implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 
	 * Render the view.
	 * @param view Originating view.
	 * @param pageContext page context.
	 * @return True if a suitable parser / painter combination is found and used.
	 */
	public boolean renderView(View view, PageContext pageContext) {
		boolean returnValue = false;
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		ILayoutParser parser = 	LayoutFactory.getLayoutParserInstance(request);
		if (parser != null) {
			ILayoutPainter painter = LayoutFactory.getLayoutPainterInstance(
					(HttpServletRequest) pageContext.getRequest());
			if (painter != null) {
				returnValue = true;				
				Collection<ILayoutElement> elements = parser.parseView(view, pageContext);
				painter.initialize(view, pageContext);
				renderElements(painter, elements, view, pageContext);
				painter.finalize(view, pageContext);
			}
		}
		return returnValue;
	}

	/**
	 * Render a section.
	 * @param view Originating view.
	 * @param pageContext page context.
	 * @return True if a suitable parser / painter combination is found and used.
	 */
	public boolean renderSection(View view, PageContext pageContext) {
		boolean returnValue = false;
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		ILayoutParser parser = 	LayoutFactory.getLayoutParserInstance(request);
		if (parser != null) {
			ILayoutPainter painter = LayoutFactory.getLayoutPainterInstance(
					(HttpServletRequest) pageContext.getRequest());
			if (painter != null) {				
				returnValue = true;
				Collection<ILayoutElement> elements = parser.parseView(view, pageContext, true);
				painter.initialize(view, pageContext);
				renderElements(painter, elements, view, pageContext);
				painter.finalize(view, pageContext);
			}
		}
		return returnValue;
	}
	
	/**
	 * Render each element.
	 * @param painter Painter which render the elements.
	 * @param elements Collection of layout elements.
	 */
	public void renderElements(ILayoutPainter painter, Collection<ILayoutElement> elements, View view, PageContext pageContext) {
		painter.setPainterManager(this);
		Iterator<ILayoutElement> it = elements.iterator();
		while(it.hasNext()) {
			ILayoutElement element = it.next();
			element.render(painter);
		}
	}
	
}
