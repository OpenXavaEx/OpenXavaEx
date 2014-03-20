<%@page import="org.openxava.ex.editor.RequiredMasksHelper"%>
<%@page import="org.openxava.util.XavaPreferences"%>

<%if (view.isEditable() || 
		!(!view.isEditable() && !XavaPreferences.getInstance().isShowIconForViewReadOnly())
	) {
    Boolean requiredMask = RequiredMasksHelper.isRequired(p, view);
    if (null!=requiredMask) {
    	if (requiredMask.booleanValue()){
    		%><img data-ex-notes="masked" src="<%=request.getContextPath()%>/xava/images/required.gif"/><%
    	}
    } else if (p.isKey()) { %>
	<img src="<%=request.getContextPath()%>/xava/images/key.gif"/>
	<% } else if (p.isRequired()) { %>	
	<img src="<%=request.getContextPath()%>/xava/images/required.gif"/>
	<% } %> 
<%} %>

<span id="<xava:id name='<%="error_image_" + p.getQualifiedName()%>'/>"> 
<% if (errors.memberHas(p)) { %>
<img src="<%=request.getContextPath()%>/xava/images/error.gif"/>
<% } %>
</span>
