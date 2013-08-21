<%@page import="org.openxava.ex.formatter.date.SmartDateFormatter"%>
<%@ page import="org.openxava.model.meta.MetaProperty" %>

<jsp:useBean id="style" class="org.openxava.web.style.Style" scope="request"/>
  
 <%
String propertyKey = request.getParameter("propertyKey");
MetaProperty p = (MetaProperty) request.getAttribute(propertyKey);
String fvalue = (String) request.getAttribute(propertyKey + ".fvalue");
String align = p.isNumber()?"right":"left";
boolean editable="true".equals(request.getParameter("editable"));
String disabled=editable?"":"disabled";
String script = request.getParameter("script");
boolean label = org.openxava.util.XavaPreferences.getInstance().isReadOnlyAsLabel();

String dateFmt = SmartDateFormatter.getFormat(request);
// yyyy/MM/dd HH:mm:ss == %Y/%m/%d %H:%M:%S
String jsDateFmt = dateFmt.replace("yyyy", "%Y").replace("MM", "%m").replace("dd", "%d")
                          .replace("HH", "%H").replace("mm", "%M").replace("ss", "%S");
String jsTimeFlag = // =24: show time input element in 24 hours mode, =null: input date only
    (jsDateFmt.indexOf("%H")>=0 || jsDateFmt.indexOf("%M")>=0 || jsDateFmt.indexOf("%S")>=0)?"'24'":"null";
if (editable || !label) {
%>
<input type="text" name="<%=propertyKey%>" id="<%=propertyKey%>" class=<%=style.getEditor()%> title="<%=p.getDescription(request)%>"
	tabindex="1" 
	align='<%=align%>'
	maxlength="19" 
	size="19"
	value="<%=fvalue%>"
	<%=disabled%>
	<%=script%>><%if (editable) {%><input type="image" 
	name="<%=propertyKey%>_CALENDAR_BUTTON_"
	src="<%=request.getContextPath() %>/xava/images/calendar.gif" alt="..."
	style='vertical-align: middle;'
	onclick="return showCalendar('<%=propertyKey%>', '<%=jsDateFmt%>', <%=jsTimeFlag%>);"><%} %>
<%

} else {
%>
<%=fvalue%>&nbsp;	
<%
}
%>
<% if (!editable) { %>
	<input type="hidden" name="<%=propertyKey%>" value="<%=fvalue%>">
<% } %>			
