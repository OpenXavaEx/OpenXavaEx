/**
 * 
 */
package org.openxava.web.layout;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.openxava.view.View;

/**
 * Interface contract for a painter.
 * @author Federico Alcantara
 *
 */
public interface ILayoutPainter extends Serializable {
	/**
	 * Starts to paint form.
	 * @param view Originating view.
	 * @param request Originating request.
	 * @param pageContext Page within the form is going to be painted.
	 * @param errors Errors container.
	 * @throws IOException 
	 * @throws JspException 
	 */
	public void initialize(View view, PageContext pageContext);
	public void finalize(View view, PageContext pageContext);
	
	public void setPainterManager(LayoutPainterManager painterManager);
	
	public void beginView(ILayoutViewBeginElement element);
	public void endView(ILayoutViewEndElement element);
	
	public void beginGroup(ILayoutGroupBeginElement element);
	public void endGroup(ILayoutGroupEndElement element);

	public void beginFrame(ILayoutFrameBeginElement element);
	public void endFrame(ILayoutFrameEndElement element);
	
	public void beginRow(ILayoutRowBeginElement element);
	public void endRow(ILayoutRowEndElement element);
	
	public void beginColumn(ILayoutColumnBeginElement element);
	public void endColumn(ILayoutColumnEndElement element);
	
	public void beginProperty(ILayoutPropertyBeginElement element);
	public void endProperty(ILayoutPropertyEndElement element);
	
	public void beginCollection(ILayoutCollectionBeginElement element);
	public void endCollection(ILayoutCollectionEndElement element);
	
	public void beginSections(ILayoutSectionsBeginElement element);
	public void endSections(ILayoutSectionsEndElement element);
	
	public void beginSectionsRender(ILayoutSectionsRenderBeginElement element);
	public void endSectionsRender(ILayoutSectionsRenderEndElement element);
	
	public ILayoutSectionsRenderBeginElement defaultBeginSectionsRenderElement(View view);
	public ILayoutSectionsRenderEndElement defaultEndSectionsRenderElement(View view);

}
