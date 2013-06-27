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
import org.openxava.ex.cl.ClassLoaderUtil;
import org.openxava.ex.cl.ClassLoaderUtil.WhenClassReload;
import org.openxava.ex.cl.impl.ClassModifyChecker;
import org.openxava.jpa.XPersistence;

/**
 * To make change without restart server, Just for development phase
 * @author root
 */
public class DynamicLoaderFilter implements Filter {
	public static final String INIT_PARAM_NAME_CLASSPATH = "CLASSPATH";
	
	private List<File> classpath = new ArrayList<File>();
	private ClassModifyChecker checker = new ClassModifyChecker();

	public void init(FilterConfig cfg) throws ServletException {
		String pathList = cfg.getInitParameter(INIT_PARAM_NAME_CLASSPATH);
		String[] pathArray = pathList.split(";");
		for (int i = 0; i < pathArray.length; i++) {
			classpath.add(new File(pathArray[i]));
		}
	}

	public void destroy() {
		//Do nothing
	}

	public void doFilter(final ServletRequest request, final ServletResponse response, FilterChain chain) throws IOException, ServletException {
		ClassLoader oldCL = Thread.currentThread().getContextClassLoader();
		try{
			//Make class reloadable, and clean application context when class reloaded
			ClassLoaderUtil.injectIntoContextClassLoader(classpath, checker, new WhenClassReload(){
				public void doReload() {
					XPersistence.reset4Reload();
					MetaComponent.reset4Reload();
					//In jsp: <jsp:useBean id="context" class="org.openxava.controller.ModuleContext" scope="session"/>
					HttpServletRequest hreq = (HttpServletRequest)request;
					ModuleContext mc = (ModuleContext)hreq.getSession().getAttribute("context");
					if (null!=mc){
						mc.reset4Reload();
					}
				}
			});
			
			//Check and update schema
			
			
			chain.doFilter(request, response);
		}finally{
			Thread.currentThread().setContextClassLoader(oldCL);
		}

	}

}
