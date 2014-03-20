<%@page import="org.openxava.ex.editor.RequiredMasksHelper"%>
<%@page import="org.openxava.util.XavaPreferences"%>

<%if (view.isEditable() || 
		!(!view.isEditable() && !XavaPreferences.getInstance().isShowIconForViewReadOnly())
	) {
	Boolean requiredMask = RequiredMasksHelper.isRequired(ref, view);
	if (null!=requiredMask) {
        if (requiredMask.booleanValue()){
            %><img data-ex-notes="masked" src="<%=request.getContextPath()%>/xava/images/required.gif"/><%
        }
    } else if (ref.isKey()) { %>
	<img src="<%=request.getContextPath()%>/xava/images/key.gif"/>
	<% } else if (ref.isRequired()) {  %>	
	<img src="<%=request.getContextPath()%>/xava/images/required.gif"/>
	<% } %> 
<%} %>

<span id="<xava:id name='<%="error_image_" + ref.getQualifiedName()%>'/>">
<% if ( errors.memberHas(ref)) {%>
<img src="<%=request.getContextPath()%>/xava/images/error.gif"/>
<% } %>
</span>