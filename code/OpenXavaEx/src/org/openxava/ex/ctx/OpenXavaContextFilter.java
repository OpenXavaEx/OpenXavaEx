package org.openxava.ex.ctx;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * The Filter to create and destroy OX Context in ThreadLocal 
 */
public class OpenXavaContextFilter implements Filter {
	private static ThreadLocal<BaseTabAndViewContext> threadLocalCtx = new ThreadLocal<BaseTabAndViewContext>();

	public void init(FilterConfig arg0) throws ServletException {}
	public void destroy() {}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		BaseTabAndViewContext ctx = new BaseTabAndViewContext((HttpServletRequest) req);
		threadLocalCtx.set(ctx);
		try{
			chain.doFilter(req, resp);
		}finally{
			threadLocalCtx.remove();
		}
	}
	public static final BaseTabAndViewContext getContext(){
		return threadLocalCtx.get();
	}
}
