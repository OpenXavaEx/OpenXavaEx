package org.openxava.ex.dwr;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.directwebremoting.servlet.DwrServlet;

/**
 * Force the request is UTF-8, fix the encoding problem in Internet Explorer
 * @author root
 *
 */
public class ForceUtf8DwrServlet extends DwrServlet {
	private static final long serialVersionUID = 20130511L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		super.doPost(request, response);
	}

}
