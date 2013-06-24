<%@page import="java.io.ByteArrayOutputStream"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Properties"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.UUID"%>
<%!
private static final String ATTR_SSO_TOKENS = "SSO_TOKENS";
private static final String COOKIE_SSO_TOKEN = "SSO_TOKEN";
private Map<String, Object> _getTokens(ServletContext app){
	Map<String, Object> tokens = (Map<String, Object>)app.getAttribute(ATTR_SSO_TOKENS);
	if (null==tokens){
		tokens = new HashMap<String, Object>();
		app.setAttribute(ATTR_SSO_TOKENS, tokens);
	}
	return tokens;
}
private String _createToken(ServletContext app, Object userInfo){
	Map<String, Object> tokens = _getTokens(app);
	String uuid = UUID.randomUUID().toString();
	tokens.put(uuid, userInfo);
	return uuid;
}
private Object _checkToken(ServletContext app, String token){
	Map<String, Object> tokens = _getTokens(app);
	Object userInfo = tokens.get(token);
	tokens.remove(token);	//Remove token after read
	return userInfo;
}

/**
 * Get the userInfo from session.
 * NOTE: Must replace the method with you own user info fetching mechanism
 */
private Object _getUserInfoFromSession(HttpSession session){
	Map<String, String> userInfo = (Map<String, String>)session.getAttribute("app.userInfo");
	return userInfo;
}

/**
 * Serialize userInfo into a Properties object.
 * NOTE: Must replace the method with you own user info serialize mechanism
 */
private Properties _serializeUserInfo(Object userInfoObject){
	Map<String, String> userInfo = (Map<String, String>)userInfoObject;
	Properties p = new Properties();
	if (null!=userInfo){
		for(Entry<String, String> e: userInfo.entrySet()){
			p.put(e.getKey(), e.getValue());
		}
	}
	return p;
}
%>
<%
String token = request.getParameter("token");
String toUrl = request.getParameter("to");
if (null!=token){	//Required from server - SSO check
	Object userInfoObject = _checkToken(application, token);
	Properties p = _serializeUserInfo(userInfoObject);
	
	ByteArrayOutputStream bo = new ByteArrayOutputStream();
	p.storeToXML(bo, "UserInfo of token: "+token, "UTF-8");
	String xml = bo.toString("UTF-8");
	%><%=xml%><%
}else if (null!=toUrl){	//Required from client
	Object userInfoObject = _getUserInfoFromSession(session);
	if (null==userInfoObject){
		//NOTE: replace you own login page here
		response.sendRedirect("login.jsp");
	}else{
		//Create token and save as cookie
		String t = _createToken(application, userInfoObject);
		Cookie cookie=new Cookie(COOKIE_SSO_TOKEN, t);
		cookie.setPath("/");
		cookie.setMaxAge(-1);	//A negative value means that the cookie is not stored persistently and will be deleted when the Web browser exits
		response.addCookie(cookie); 
		//Redirect to specified url
		response.sendRedirect(toUrl);
	}
}else{
	%>#INVALID REQUEST<%
}
%>
