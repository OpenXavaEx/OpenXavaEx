<%@page import="org.openxava.ex.editor.shell.ShellEditorContext"%>
<%@page import="org.openxava.util.XavaPreferences"%>
<%@page import="org.openxava.util.Is"%>
<jsp:useBean id="errors" class="org.openxava.util.Messages" scope="request"/>
<jsp:useBean id="context" class="org.openxava.controller.ModuleContext" scope="session"/>
<jsp:useBean id="style" class="org.openxava.web.style.Style" scope="request"/>

<%@ page import="org.openxava.model.meta.MetaProperty" %>
<%@ page import="org.openxava.view.meta.MetaPropertyView" %>
<%
String viewObject = request.getParameter("viewObject");
viewObject = (viewObject == null || viewObject.equals(""))?"xava_view":viewObject;
org.openxava.view.View view = (org.openxava.view.View) context.get(request, viewObject);
String propertyKey = request.getParameter("propertyKey");
MetaProperty p = (MetaProperty) request.getAttribute(propertyKey);
boolean editable = view.isEditable(p);
boolean lastSearchKey = view.isLastSearchKey(p); 
boolean throwPropertyChanged = view.throwsPropertyChanged(p);

int labelFormat = view.getLabelFormatForProperty(p);
String labelStyle = view.getLabelStyleForProperty(p);
if (Is.empty(labelStyle)) labelStyle = XavaPreferences.getInstance().getDefaultLabelStyle();
String label = view.getLabelFor(p);

String fvalue = (String) request.getAttribute(propertyKey + ".fvalue");
String script = request.getParameter("script");
boolean readOnlyAsLabel = org.openxava.util.XavaPreferences.getInstance().isReadOnlyAsLabel();

//Get the editor class and render it
ShellEditorContext ctx = new ShellEditorContext(context, view, p, fvalue, errors, request);
if (editable || !readOnlyAsLabel) { 
%>
<div><%=ShellEditorContext.render(ctx)%></div>
<%
} else {
%>
<!-- <%=fvalue%> -->	
<div><%=ShellEditorContext.renderReadOnly(ctx)%></div>
<%
}
%>
<input type="hidden" name="<%=propertyKey%>" value="<%=fvalue%>">
