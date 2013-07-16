/**
 * 
 */
package org.openxava.web.layout;

import java.io.Serializable;
import java.util.List;

import javax.servlet.jsp.PageContext;

import org.openxava.view.View;

/**
 * Layout manager interface.
 * @author Federico Alcantara
 *
 */
public interface ILayoutParser extends Serializable {
	/**
	 * Prepares the layout, so that future requests aren't processed.
	 * @param view Originating view.
	 * @param pageContext Where page are rendered.
	 */
	public List<ILayoutElement> parseView(View view, PageContext pageContext);

	/**
	 * Prepares the layout, so that future requests aren't processed.
	 * @param view Originating view.
	 * @param pageContext Where page are rendered.
	 * @param representsSection If true the view is representing a section.
	 */
	public List<ILayoutElement> parseView(View view, PageContext pageContext, boolean representsSection);
}
