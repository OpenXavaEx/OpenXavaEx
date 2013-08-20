<%@page import="org.openxava.util.Users"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", -10);
%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Info Page</title>
</head>
<%
String givenName = Users.getCurrentUserInfo().getGivenName();
if (null==givenName || "".equals(givenName)){
	givenName = "<i>Unknown</i>";
}
%>
<h2>The Login information:</h2>
<div style="padding: 12px"><b>givenName</b> = <%=givenName%></div>
<body>

</body>
</html>