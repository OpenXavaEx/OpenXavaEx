package org.openxava.ex.json;

import java.io.IOException;
import java.util.Iterator;
import java.util.Stack;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.openxava.controller.ModuleContext;
import org.openxava.controller.ModuleManager;
import org.openxava.tab.Tab;
import org.openxava.util.Locales;
import org.openxava.util.Messages;
import org.openxava.util.Users;
import org.openxava.view.View;
import org.openxava.web.WebEditors;
import org.openxava.web.dwr.Module;
import org.openxava.web.servlets.Servlets;

import com.alibaba.fastjson.JSON;

public class JsonViewerServlet extends HttpServlet {
	private static final long serialVersionUID = 20130502L;

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			doService(request, response);
		} catch (ServletException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new ServletException(e);
		}		
	}
	public void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ModuleContext context = userBeanContext(request);
		Messages errors = new Messages();
		Messages messages = new Messages();

		//Locale and user
		Servlets.setCharacterEncoding(request, response);
		Locales.setCurrent(request);	
		request.getSession().setAttribute("xava.user", request.getRemoteUser());
		Users.setCurrent(request); 

		//Get application and module from request parameters
		String app = request.getParameter("application");
		String module = context.getCurrentModule(request);
		ModuleInfo result = new ModuleInfo(app, module, ModuleManager.getVersion());
		
		ModuleManager managerHome = 
				(ModuleManager) context.get(request, "manager","org.openxava.controller.ModuleManager");
		ModuleManager manager = 
				(ModuleManager) context.get(app, module, "manager","org.openxava.controller.ModuleManager");
		
		manager.setSession(session);
		manager.setApplicationName(app);
		manager.setModuleName(module); // In order to show the correct description in head
		if (manager.isFormUpload()) {
			new Module().requestMultipart(request, response, app, module);
		} else {
			Module.restoreLastMessages(request, app, module); 
		}	

		Module.setPortlet(false);
		
		/* <jsp:include page="execute.jsp"/> START ... */
		manager.resetPersistence();
		Tab tab = (Tab) context.get(request, "xava_tab");
		View view = (View) context.get(request, "xava_view");
		manager.executeBeforeEachRequestActions(request, errors, messages);
		view.setRequest(request);
		view.setErrors(errors);
		view.setMessages(messages);
		@SuppressWarnings("unchecked")
		Stack<View> previousViews = (Stack<View>) context.get(request, "xava_previousViews");
		for (Iterator<View> it = previousViews.iterator(); it.hasNext(); ) {
			View previousView = (View) it.next();
			previousView.setRequest(request);
			previousView.setErrors(errors);
			previousView.setMessages(messages);	
		}
		tab.setRequest(request);
		if (manager.isListMode() || manager.isSplitMode() && manager.getDialogLevel() == 0) {   
			tab.setModelName(manager.getModelName());
			if (tab.getTabName() == null) { 
				tab.setTabName(manager.getTabName());
			}
		}
		boolean hasProcessRequest = manager.hasProcessRequest(request);
		manager.preInitModule();
		if (manager.isXavaView(request)) { 
			if (hasProcessRequest) {	
				view.assignValuesToWebView();
			}
		}
		manager.initModule(request, errors, messages);
		manager.executeOnEachRequestActions(request, errors, messages); 
		if (hasProcessRequest) {
			manager.execute(request, errors, messages);	
			if (manager.isListMode() || manager.isSplitMode() && manager.getDialogLevel() == 0) { // here and before execute the action
				tab.setModelName(manager.getModelName());	
				if (tab.getTabName() == null) { 
					tab.setTabName(manager.getTabName());
				}
			}
		}
		//after-each-request
		manager.executeAfterEachRequestActions(request, errors, messages);
		result.setProperty("listUrl", WebEditors.getUrl(tab.getMetaTab()));
		result.setProperty("viewUrl", manager.getViewURL());
		/* <jsp:include page="execute.jsp"/> END . */
		
		result.setTitle(managerHome.getModuleDescription());
		result.setLanguage(request.getLocale().getLanguage());
		
		String json = JSON.toJSONString(result, true);
		response.getWriter().write(json);
	}
	
	//<jsp:useBean id="context" class="org.openxava.controller.ModuleContext" scope="session"/>
	private ModuleContext userBeanContext(HttpServletRequest request){
		HttpSession session = request.getSession();
		ModuleContext context = (ModuleContext)session.getAttribute("context");
		if (context == null){
			context = new ModuleContext();
			session.setAttribute("context", context);
		}
		return context;
	}

}
