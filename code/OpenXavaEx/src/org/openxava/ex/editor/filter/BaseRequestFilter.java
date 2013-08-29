package org.openxava.ex.editor.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.openxava.ex.ctx.BaseEditorContext;
import org.openxava.filters.FilterException;
import org.openxava.filters.IRequestFilter;

/**
 * The base class of IRequestFilter implementations
 */
public abstract class BaseRequestFilter implements IRequestFilter {
	private static final long serialVersionUID = 20130827L;
	
	private BaseEditorContext editorContext;

	public Object filter(Object o) throws FilterException {
		List<Object> parameters = new ArrayList<Object>();
		if (o == null) {
			//Keep the EMPTY parameter list
		}else if (o instanceof Object []) {
			//Prepare with the existing parameters
			List<Object> c = Arrays.asList((Object []) o);
			parameters.addAll(c);
		} else {
			parameters.add(o);
		}
		
		fillParameters(parameters);
		return parameters.toArray();
	}
	public void setRequest(HttpServletRequest request) {
		this.editorContext = new BaseEditorContext(request);
	}

	protected BaseEditorContext getEditorContext() {
		return editorContext;
	}

	protected abstract void fillParameters(List<Object> parameters) throws FilterException;
}
