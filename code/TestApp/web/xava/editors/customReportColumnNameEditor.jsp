<%@ page import="org.openxava.model.meta.MetaProperty" %>

<jsp:useBean id="context" class="org.openxava.controller.ModuleContext" scope="session"/>
<jsp:useBean id="style" class="org.openxava.web.style.Style" scope="request"/>

<% 
boolean editable="true".equals(request.getParameter("editable"));
if (!editable) {
%>

<jsp:include page="textEditor.jsp"/>

<% } else { %>

<%
String propertyKey = request.getParameter("propertyKey");
MetaProperty p = (MetaProperty) request.getAttribute(propertyKey);
String fvalue = (String) request.getAttribute(propertyKey + ".fvalue");
String script = request.getParameter("script");

String tabObject = request.getParameter("tabObject");
tabObject = (tabObject == null || tabObject.equals(""))?"xava_tab":tabObject;
org.openxava.tab.Tab tab = (org.openxava.tab.Tab) context.get(request, tabObject);
java.util.Collection columns = tab.getMetaTab().getMetaModel().getRecursiveQualifiedPropertiesNames();
%>

<select id="<%=propertyKey%>" name="<%=propertyKey%>" tabindex="1" class=<%=style.getEditor()%> <%=script%> title="<%=p.getDescription(request)%>">
	<option value=""></option>
	<% 
	for (java.util.Iterator it = columns.iterator(); it.hasNext(); ) {
		Object column = it.next();
		String selected = column.equals(fvalue)?"selected":""; 
	%>
	<option value="<%=column%>" <%=selected%>><%=column%></option>
	<% 
	}
	%>
</select>	

<% } %>