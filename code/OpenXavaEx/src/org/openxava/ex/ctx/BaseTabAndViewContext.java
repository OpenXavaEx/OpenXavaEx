package org.openxava.ex.ctx;

import javax.servlet.http.HttpServletRequest;

import org.openxava.controller.ModuleContext;
import org.openxava.ex.utils.JspUtils;
import org.openxava.tab.Tab;
import org.openxava.util.DataSourceConnectionProvider;
import org.openxava.util.IConnectionProvider;
import org.openxava.util.Is;
import org.openxava.util.Messages;
import org.openxava.view.View;

/**
 * The base information in a Tab or View's context (reference: {@link ModuleContext#get(HttpServletRequest, String)} listEditor.jsp, textEditor.jsp)
 */
public class BaseTabAndViewContext {
	private ModuleContext context;
	private Messages errors;
	private HttpServletRequest request;
	private String contextPath;
	private String application;
	private String module;
	private View view;
	private Tab tab;
	
	public BaseTabAndViewContext(HttpServletRequest request) {
		ModuleContext context = (ModuleContext) JspUtils.useBeanSession(request, "context", ModuleContext.class);
		Messages message = (Messages) JspUtils.useBeanRequest(request, "errors", Messages.class);
		
		this.context = context;
		this.errors = message;
		this.request = request;
		this.contextPath = request.getContextPath();

		String application = request.getParameter("application");
		String module = request.getParameter("module");
		this.application = application;
		this.module = module;

		if (Is.emptyString(application) || Is.emptyString(module)){
			//Do nothing
		}else{
			String viewObject = request.getParameter("viewObject");
			viewObject = (viewObject == null || viewObject.equals(""))?"xava_view":viewObject;
			View view = (View) context.get(request, viewObject);
			
			String tabObject = request.getParameter("tabObject");
			tabObject = (tabObject == null || tabObject.equals(""))?"xava_tab":tabObject;
			Tab tab = (Tab) context.get(request, tabObject);

			this.view = view;
			this.tab = tab;
		}
	}
	public ModuleContext getContext() {
		return context;
	}
	public Messages getErrors() {
		return errors;
	}
	public String getApplication() {
		return application;
	}
	public String getModule() {
		return module;
	}
	public View getView() {
		return view;
	}
	public Tab getTab() {
		return tab;
	}
	public HttpServletRequest getRequest() {
		return request;
	}

	/**
	 * Get connection provider of current component
	 * @return
	 */
	public IConnectionProvider getConnectionProvider(){
		String componentName = this.view.getMetaModel().getMetaComponent().getName();
		return DataSourceConnectionProvider.getByComponent(componentName);
	}
	
	/**
	 * Get the context path of current WebApp
	 * @return
	 */
	public String getContextPath() {
		return contextPath;
	}
}
