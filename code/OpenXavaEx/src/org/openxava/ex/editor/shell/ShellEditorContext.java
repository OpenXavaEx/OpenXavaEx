package org.openxava.ex.editor.shell;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.openxava.ex.cl.ClassLoaderUtil;
import org.openxava.ex.ctx.BaseEditorContext;
import org.openxava.ex.utils.FreeMarkerEngine;

import freemarker.template.TemplateException;

/**
 * The wrapper for editor "/xava-ex/editors/Shell.jsp"
 * @author root
 *
 */
public class ShellEditorContext extends BaseEditorContext{
	public static final String PROP_SHELL_CLASS = "xava-ex.editor.shell.class";
	
	public static final String render(ShellEditorContext ctx){
		String html = getEditor(ctx).render(ctx);
		return html;
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
	
	private String rendererClassName;
	public ShellEditorContext(HttpServletRequest request) {
		super(request);
		this.rendererClassName = request.getParameter(PROP_SHELL_CLASS);
	}
	public String getRendererClassName() {
		return rendererClassName;
	}
	/**
	 * render html segment from FreeMarker Template
	 * @param resource
	 * @param moreDatas
	 * @param loaderClass
	 * @return
	 * @throws TemplateException
	 */
	public String parseFtl(String resource, Map<String, Object> moreDatas, Class<?> loaderClass){
		try {
			FreeMarkerEngine engine = new FreeMarkerEngine();
			engine.setModel("ctx", this);
			engine.setModel("contextPath", this.getContextPath());
			engine.setModel("propertyKey", this.getPropertyKey());
			engine.setModel("rawValue", this.getRawValue());
			engine.setModel("values", this.getView().getValues());
			engine.setModels(moreDatas);
			String result;
			result = engine.parseResource(loaderClass, resource, "UTF-8");
			return result;
		} catch (TemplateException e) {
			throw new RuntimeException(e);
		}
	}
}
