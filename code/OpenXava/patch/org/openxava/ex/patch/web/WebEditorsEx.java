package org.openxava.ex.patch.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.openxava.ex.utils.Misc;
import org.openxava.model.meta.MetaProperty;
import org.openxava.util.XavaException;
import org.openxava.web.WebEditors;
import org.openxava.web.meta.MetaEditor;

/**
 * The patch of {@link WebEditors}, for:
 *  1. Make formatter can read the editor's properties defined in editors.xml
 */
public class WebEditorsEx extends WebEditors {
	
	/**
	 * Put properties of editor's definition(editors.xml) into request, to make Formatter can read these properties
	 * @param request
	 * @param med
	 * @return
	 * @throws XavaException
	 */
	public static HttpServletRequest wrapRequest4Formatter(HttpServletRequest request, MetaEditor metaEditor) {
		RequestWrapper w = new RequestWrapper(request, metaEditor);
		return w;
	}
	public static class RequestWrapper extends HttpServletRequestWrapper {
		private MetaEditor metaEditor;
		
		@SuppressWarnings("rawtypes")
		private Map editorProperties;

		public RequestWrapper(HttpServletRequest request, MetaEditor metaEditor) {
			super(request);
			this.metaEditor = metaEditor;
			this.editorProperties = getEditorProperties();
		}

		/**
		 * Get the properties defined in editors.xml, see {@link WebEditors#getUrl(MetaProperty, String)} for reference
		 * @return
		 */
		@SuppressWarnings("rawtypes")
		private Map getEditorProperties(){
			try {
				Map properties = (Map)FieldUtils.readField(metaEditor, "properties", true);
				if (null==properties){
					properties = new HashMap();
				}
				return properties;
			} catch (IllegalAccessException e) {
				Misc.throwRuntime(e);
				return null;
			}
		}
		
		@Override
		public String getParameter(String name) {
			String val = (String)this.editorProperties.get(name);
			return (null!=val)?val:super.getParameter(name);
		}
	}
}
