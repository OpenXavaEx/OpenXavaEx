package org.openxava.ex.ctx;

import javax.servlet.http.HttpServletRequest;

import org.openxava.formatters.IFormatter;
import org.openxava.model.meta.MetaProperty;
import org.openxava.web.WebEditors;
import org.openxava.web.meta.MetaEditor;
import org.openxava.web.meta.MetaWebEditors;

/**
 * The base information in an editor's context
 */
public class BaseEditorContext extends BaseTabAndViewContext{
	private MetaProperty metaProperty;
	private String rawValue;
	private String propertyKey;
	
	public BaseEditorContext(HttpServletRequest request) {
		super(request);
		String propertyKey = request.getParameter("propertyKey");
		MetaProperty metaProperty = (MetaProperty) request.getAttribute(propertyKey);
		String rawValue = (String) request.getAttribute(propertyKey + ".fvalue");

		this.propertyKey = propertyKey;
		this.metaProperty = metaProperty;
		this.rawValue = rawValue;
	}
	public MetaProperty getMetaProperty() {
		return metaProperty;
	}
	public String getRawValue() {
		return rawValue;
	}
	public String getPropertyKey() {
		return propertyKey;
	}
	
	/**
	 * Return the formatter of current property;
	 * FIXME: Now always return the formatter defined in editors.xml, see {@link WebEditors#getMetaEditorFor(org.openxava.model.meta.MetaMember, String)}.
	 */
	public IFormatter getPropertyFormatter(){
		//FIXME: Now always return the formatter defined in editors.xml
		MetaEditor editor = MetaWebEditors.getMetaEditorFor(metaProperty);
		return editor.getFormatter();
	}
}
