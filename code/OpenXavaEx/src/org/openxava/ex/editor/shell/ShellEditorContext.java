package org.openxava.ex.editor.shell;

import javax.servlet.http.HttpServletRequest;

import org.openxava.controller.ModuleContext;
import org.openxava.ex.cl.ClassLoaderUtil;
import org.openxava.model.meta.MetaProperty;
import org.openxava.util.Messages;
import org.openxava.view.View;

/**
 * The wrapper for editor "/xava-ex/editors/Shell.jsp"
 * @author root
 *
 */
public class ShellEditorContext {
	public static final String PROP_SHELL_CLASS = "xava-ex.editor.shell.class";
	
	public static final String render(ShellEditorContext ctx){
		return getEditor(ctx).render(ctx);
	}
	public static final String renderReadOnly(ShellEditorContext ctx){
		return getEditor(ctx).renderReadOnly(ctx);
	}
	private static IShellEditor getEditor(ShellEditorContext ctx){
		IShellEditor ie;
		try {
			ie = (IShellEditor) ClassLoaderUtil.forName(IShellEditor.class, ctx.getRendererClassName()).newInstance();
			return ie;
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	private ModuleContext context;
	private Messages errors;
	private View view;
	private MetaProperty metaProperty;
	private String rawValue;
	private HttpServletRequest request;
	private String rendererClassName;
	public ShellEditorContext(
			ModuleContext context, View view, MetaProperty metaProperty, String rawValue, Messages errors, HttpServletRequest request) {
		this.context = context;
		this.view = view;
		this.metaProperty = metaProperty;
		this.rawValue = rawValue;
		this.errors = errors;
		this.request = request;
		this.rendererClassName = request.getParameter(PROP_SHELL_CLASS);
	}
	public ModuleContext getContext() {
		return context;
	}
	public Messages getErrors() {
		return errors;
	}
	public View getView() {
		return view;
	}
	public MetaProperty getMetaProperty() {
		return metaProperty;
	}
	public String getRawValue() {
		return rawValue;
	}
	public HttpServletRequest getRequest() {
		return request;
	}
	public String getRendererClassName() {
		return rendererClassName;
	}
}
