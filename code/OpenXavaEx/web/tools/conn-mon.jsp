<%@page import="java.util.Date"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="org.apache.commons.lang3.time.DateFormatUtils"%>
<%@page import="org.openxava.ex.datasource.ConnectionTrace"%>
<%@page import="java.util.List"%>
<%
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", 0);
%>
<%
    List<ConnectionTrace.TraceInfo> infos = ConnectionTrace.dumpTraceInfoList();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<title>Connection monitor</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<style>
	html,body {
		height: 100%;
	}
	body {
		font: 12px 'Lucida Sans Unicode', 'Trebuchet MS', Arial, Helvetica;
		margin: 12px;
		background-color: #d9dee2;
	}
	table {
		border-collapse:collapse;
		border:solid #999;
		border-width:1px 0 0 1px;
    }
    table th,table td {border:solid #999;border-width:0 1px 1px 0;padding:2px;}
	.stack {
	    height: 120px;
	    overflow-y: auto;
	    overflow-x: hidden;
	}
	</style>
</head>
<body>
<%
if (infos.size()<=0){
%>
    <h3>Congratulates! no no leaked connection found.</h3>
<%
}else{
%>
    <table>
       <tr>
           <th>No.</th><th>CreateTime</th><th>StackTrace</th>
       </tr>
       <%
       for (int i=0; i<infos.size(); i++){
           ConnectionTrace.TraceInfo info = infos.get(i);
           //The create time
           String ct = DateFormatUtils.format(info.getCreateTime(), "yyyy/MM/dd HH:mm:ss.S Z");
           //The stackTrace
           String st = StringUtils.join(info.getStackTrace(), "<br/>");
       %>
       <tr valign="top">
           <td align="center"><%=i+1%></td>
           <td><%=ct%></td>
           <td width="100%"><div class="stack"><%=st%></div></td>
       </tr>
       <%
       }
       %>
    </table>
<%
}
%>
<hr/>
<div style="text-align: right">Check time: <%= DateFormatUtils.format(new Date(), "yyyy/MM/dd HH:mm:ss.S Z") %></div>
</body>
</html>