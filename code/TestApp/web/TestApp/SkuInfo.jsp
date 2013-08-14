<%@page import="org.openxava.ex.utils.DBUtils"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.Connection"%>
<%@page import="org.openxava.util.IConnectionProvider"%>
<%@page import="org.openxava.demoapp.model.md.SKU"%>
<%@page import="org.openxava.util.DataSourceConnectionProvider"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", -10);
%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SKU Info Page</title>
</head>
<%
IConnectionProvider cp = DataSourceConnectionProvider.getByComponent(SKU.class.getSimpleName());
List<Map<String, Object>> data = DBUtils.findAndClose(
		cp.getConnection(),
		"SELECT * FROM MD_SKU WHERE id=?",
		new Object[]{request.getParameter("skuId")}
);
if (data.size()>0){
	Map<String, Object> sku = data.get(0);
	%>
	<pre>
    id      = <%=sku.get("id")%>
    code    = <%=sku.get("code")%>
    name    = <%=sku.get("name")%>
    price   = <%=sku.get("price")%>
    descr   = <%=sku.get("descr")%>
    version = <%=sku.get("version")%>
	</pre>
	<%
}else{
	%><h2>Data not found</h2><%
}
%>
<body>

</body>
</html>