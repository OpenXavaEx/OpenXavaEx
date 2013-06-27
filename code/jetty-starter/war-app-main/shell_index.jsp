<%@page import="java.util.Map"%>
<%
String redirUrl = null;
String appCtx = null;
Map<String, String> userInfo = (Map<String, String>)session.getAttribute("app.userInfo");
if (null==userInfo || null==userInfo.get("id")){
	appCtx = "main";
	redirUrl = "shell_login.jsp";
}else{
	//Redirect to index page of App
	appCtx = System.getProperty("CTX_PATH");
	redirUrl = "/" + appCtx + "/"+appCtx+".jsp?mainApp=/main";
}
response.sendRedirect(redirUrl);
%>