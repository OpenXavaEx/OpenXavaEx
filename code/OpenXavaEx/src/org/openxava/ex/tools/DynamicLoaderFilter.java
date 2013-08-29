package org.openxava.ex.tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.openxava.component.MetaComponent;
import org.openxava.controller.ModuleContext;
import org.openxava.controller.meta.MetaControllers;
import org.openxava.ex.cl.ClassLoaderUtil;
import org.openxava.ex.cl.ClassLoaderUtil.WhenClassReload;
import org.openxava.ex.utils.JspUtils;
import org.openxava.jpa.XPersistence;

/**
 * To make change without restart server, Just for development phase
 * @author root
 */
public class DynamicLoaderFilter implements Filter {
	/** The path(String split by ";") to detect class change and reload */
	public static final String INIT_PARAM_NAME_CLASSPATH = "CLASSPATH";
	/** Url list(String split by ";") to detect and reload */
	public static final String INIT_PARAM_NAME_RELOAD_URI_LIST = "RELOAD_URI_LIST";
	
	private List<File> classpath = new ArrayList<File>();
	private List<String> reloadChkUri = new ArrayList<String>();

	public void init(FilterConfig cfg) throws ServletException {
		String pathList = cfg.getInitParameter(INIT_PARAM_NAME_CLASSPATH);
		String[] pathArray = pathList.split(";");
		for (int i = 0; i < pathArray.length; i++) {
			classpath.add(new File(pathArray[i]));
		}
		
		String uris = cfg.getInitParameter(INIT_PARAM_NAME_RELOAD_URI_LIST);
		String[] uriArray = uris.split(";");
		for (int i = 0; i < uriArray.length; i++) {
			reloadChkUri.add(uriArray[i]);
		}
	}

	public void destroy() {
		//Do nothing
	}

	public void doFilter(final ServletRequest request, final ServletResponse response, FilterChain chain) throws IOException, ServletException {
		ClassLoader oldCL = Thread.currentThread().getContextClassLoader();
		try{
			//Make class reloadable, and clean application context when class reloaded
			ClassLoaderUtil.injectIntoContextClassLoader(
					classpath, (shouldCheck((HttpServletRequest)request)), new WhenClassReload(){
				public void doReload() {
					contextReload(request);
				}
			});
			chain.doFilter(request, response);
		}finally{
			Thread.currentThread().setContextClassLoader(oldCL);
		}

	}

	/**
	 * Reset OpenXava Context
	 * @param request
	 */
	private void contextReload(final ServletRequest request) {		
		HttpServletRequest hreq = (HttpServletRequest)request;
		
		MetaComponent.reset4Reload();
		MetaControllers.reset4Reload();
		XPersistence.reset4Reload();
		//In jsp: <jsp:useBean id="context" class="org.openxava.controller.ModuleContext" scope="session"/>
		ModuleContext mc = (ModuleContext) JspUtils.useBeanSession(hreq, "context", ModuleContext.class);
		if (null!=mc){
			mc.reset4Reload();
		}
	}

	private boolean shouldCheck(HttpServletRequest hreq) {
		String uri = hreq.getRequestURI();
		String cp = hreq.getContextPath();
		for(String s: this.reloadChkUri){
			String withCtxPath = cp + s;
			if (uri.startsWith(withCtxPath)) return true;
		}
		return false;
	}

}
