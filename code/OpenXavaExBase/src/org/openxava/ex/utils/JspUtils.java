package org.openxava.ex.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Utilities for JSP/Servlet
 */
public class JspUtils {
	/**
	 * The same as &lt;jsp:useBean id="errors" class="org.openxava.util.Messages" scope="request"/&gt;
	 */
	public static final Object useBeanRequest(HttpServletRequest request, String id, Class<?> clazz){
		try {
			Object bean = request.getAttribute(id);
			if (null==bean){
				bean = clazz.newInstance();
				request.setAttribute(id, bean);
			}
			return bean;
		} catch (Exception e) {
			Misc.throwRuntime(e);
			return null;
		}
	}

	/**
	 * The same as &lt;jsp:useBean id="context" class="org.openxava.controller.ModuleContext" scope="session"/&gt;
	 */
	public static final Object useBeanSession(HttpServletRequest request, String id, Class<?> clazz){
		try {
			HttpSession session = request.getSession();
			Object bean = session.getAttribute(id);
			if (null==bean){
				bean = clazz.newInstance();
				session.setAttribute(id, bean);
			}
			return bean;
		} catch (Exception e) {
			Misc.throwRuntime(e);
			return null;
		}
	}
}
