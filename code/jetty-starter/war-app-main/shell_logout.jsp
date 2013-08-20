<%
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", -10);

//Clean current session
session.removeAttribute("app.userInfo");
response.sendRedirect("shell_login.jsp");
%>
