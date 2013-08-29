package org.openxava.ex.tools;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openxava.util.UserInfo;
import org.openxava.util.Users;

/**
 * SSO support based on "token" mechanism, the token is stored in cookie.
 */
public class TokenCookieSSOFilter implements Filter {
	/** Filter init-param, specify the url to check the SSO token. for example: http://localhost:8080/main/bridge.jsp?token= */
	public static final String TOKEN_CHECK_URL_INIT_PARAME = "TOKEN_CHECK_URL";

	/** The system property which should overwrite init-param(NOTE: take effect only when init-param is not null) */
	public static final String TOKEN_CHECK_URL_SYSTEM_PROPERTY = "PROP_TOKEN_CHECK_URL";
	
	/** The cookie name which store the token */
	public static final String SSO_TOKEN_COOKIE = "SSO_TOKEN";
	/** Session attribute name to store the UserInfo Object */
	public static final String SSO_USERINFO_IN_SESSION = "SSO_USERINFO_IN_SESSION";
	
	/** The url to make current application logout(session invalidate) */
	public static final String SSO_LOGOUT_URL = "/SSO+LOGOUT+NOW+.jsp";
	
	private static final Log log = LogFactory.getLog(TokenCookieSSOFilter.class);
	
	private String checkUrl;
	
	public void init(FilterConfig cfg) throws ServletException {
		checkUrl = cfg.getInitParameter(TOKEN_CHECK_URL_INIT_PARAME);
		if ( null!=checkUrl && checkUrl.trim().length() > 0){
			String prop = System.getProperty(TOKEN_CHECK_URL_SYSTEM_PROPERTY);
			if ( null!=prop && prop.trim().length() > 0){
				checkUrl = prop;
			}
		}
	}
	public void destroy() {
		//Do nothing
	}

	public void doFilter(final ServletRequest request, final ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		try{
			if (req.getRequestURI().endsWith(SSO_LOGOUT_URL)){	//Logout request
				try {
					req.getSession().invalidate();
				} catch (Exception e) {
					//Ignore it
				}
				resp.getWriter().write("OK");
				return;
			}
			if (null!=this.checkUrl){
				doCookieSSO(req, resp);
			}
			//Integrate UserInfo with OpenXava
			SSOUserInfo ui = (SSOUserInfo)req.getSession().getAttribute(SSO_USERINFO_IN_SESSION);
			if (null!=ui && null!=ui.getId()){
				log.info(">>> ["+this.getClass().getSimpleName() + "] SSO User id='" + ui.getId() + "' .");
				//ref: org.openxava.util.Users#setCurrent(HttpServletRequest)
				req.getSession().setAttribute("xava.user", ui.getId());
				req.getSession().setAttribute("xava.portal.user", ui.getId());
				req.getSession().setAttribute("xava.portal.userinfo", ui);
				Users.setCurrent(ui.getId());
				Users.setCurrentUserInfo(ui);
			}else{
				req.getSession().removeAttribute("xava.user");
				req.getSession().removeAttribute("xava.portal.user");
				req.getSession().removeAttribute("xava.portal.userinfo");
				Users.setCurrent((String)null);
				Users.setCurrentUserInfo(new SSOUserInfo());
			}
			
			chain.doFilter(request, response);
		}finally{
			//Do nothing;
		}

	}
	private void doCookieSSO(HttpServletRequest req, HttpServletResponse resp) throws MalformedURLException, IOException {
		Cookie[] cookies = req.getCookies();
		if (null==cookies) return;
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (null!=cookie && SSO_TOKEN_COOKIE.equalsIgnoreCase(cookie.getName()) && cookie.getMaxAge()!=0 ){
				String cv = cookie.getValue();
				log.info(">>> ["+this.getClass().getSimpleName() + "] Check cookie '"+SSO_TOKEN_COOKIE+"' with value='" + cv + "' ...");
				if (null==cv || cv.trim().length()==0){
					return;
				}
				String localUrl = checkUrl + cv;
				//Remove cookie after read it
				cookie.setMaxAge(0);	//if zero, deletes the cookie
				cookie.setPath("/");
				cookie.setValue("");
				resp.addCookie(cookie);
				//Read output into Properties object
				URL url = new URL(localUrl);
				Properties p = new Properties();
				BufferedReader in = null;
				try{
					in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
			        StringBuffer buf = new StringBuffer();
			        String inputLine;
			        while ((inputLine = in.readLine()) != null){
			        	buf.append(inputLine);
			        }
			        String xml = buf.toString();
					p.loadFromXML(new ByteArrayInputStream(xml.getBytes("UTF-8")));
					//Fix the unicode characters
					for (Entry<Object, Object> e: p.entrySet()){
						String v = (String)e.getValue();
						v = StringEscapeUtils.unescapeXml(v);
						e.setValue(v);
					}
				}finally{
					if (null!=in) in.close();
				}
				//Fill UserInfo Bean with Properties
				//TODO - complete all fields of UserInfo
				SSOUserInfo ui = new SSOUserInfo();
				ui.setId(p.getProperty("id"));
				ui.setGivenName(p.getProperty("givenName"));
				ui.setProperties(p);
				//Remember UserInfo into Session
				if (null!=ui.getId()){
					req.getSession().setAttribute(SSO_USERINFO_IN_SESSION, ui);
				}
				
				break;
			}
		}
	}

	public static final class SSOUserInfo extends UserInfo {
		private static final long serialVersionUID = 20130826L;
		
		private Properties properties = new Properties();

		public Properties getProperties() {
			return new Properties(properties);
		}
		private void setProperties(Properties properties) {
			this.properties = properties;
		}
		public Object getProperty(String key){
			return this.properties.get(key);
		}
		public void setProperty(String key, String value){
			this.properties.put(key, value);
		}
		
	}
}
