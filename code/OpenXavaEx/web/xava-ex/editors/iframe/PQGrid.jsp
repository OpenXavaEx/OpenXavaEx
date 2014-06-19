<%@page import="org.openxava.util.Labels"%>
<%@page import="java.util.Arrays"%>
<%@page import="org.openxava.ex.utils.VersionInfo"%>
<%@page import="org.openxava.ex.model.pqgrid.PQGrid"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title></title>

    <script src="../../libs/jquery/jquery-1.10.2.js"></script>
    <link href="../../libs/jquery-ui/jquery-ui-1.10.3/themes/base/jquery-ui.css" rel="stylesheet" />
    <script src="../../libs/jquery-ui/jquery-ui-1.10.3/ui/jquery-ui.js"></script>
    
    <link href="../../libs/jQuery-Timepicker-Addon/jquery-ui-timepicker-addon.css" rel="stylesheet" />
    <script src="../../libs/jQuery-Timepicker-Addon/jquery-ui-timepicker-addon.js"></script>
    
    <script src="../../libs/accounting.js/accounting.js"></script>
    <script src="../../libs/autoNumeric/autoNumeric.js"></script>

    <link href="../../libs/PQGrid/pqgrid.dev.css" rel="stylesheet" />
    <script src="../../libs/PQGrid/pqgrid.dev.js"></script>
    <link rel="stylesheet" href="../../libs/PQGrid/themes/office/pqgrid.css" />

    <script src="../../libs/Moment.js/moment-2.1.0.js"></script>

    <script src="../../libs/a/xava-ex.js"></script>
    <script src="../../libs/a/xava-ex.PQGrid.js"></script>

    <!-- The project bootstarp javascript libs (In [project name]/js folder) -->
    <%
    String contextPath = (String) request.getAttribute("xava.contextPath");
    if (contextPath == null) contextPath = request.getContextPath();

    String[] prjJses = (String[])application.getResourcePaths(contextPath + "/js").toArray(new String[0]);
    Arrays.sort(prjJses);
    for (int i = 0; i < prjJses.length; i++) {
        if (prjJses[i].endsWith(".js")) {
        %><script type="text/javascript" src="<%=contextPath%><%=prjJses[i]%>"></script><%
        }
    }
    %>
    
    <!-- The labels -->
    <%
    String lblFreeze = Labels.getQualified("PQGrid.label.freeze");
    String optionNone = Labels.getQualified("PQGrid.option.freeze.none");;
    String lblBlankResult = Labels.getQualified("PQGrid.label.blankResult");;
    %>
</head>
<body style="padding:0px;margin:0px">
    <div id="pgGrid" class="pq-grid"></div>
    <div id="pq-freeze-columns-select-div" style="display:none">
        <%=lblFreeze%>
        <select></select>
    </div>
    <script>
	//(document).ready
        $(function () {
            //Patch: make grid area all page width, and hide some useless element
            var iframe = window.frameElement;
            <%if ("4.7.1".equals(VersionInfo.getOpenXavaVersion())){%>
                $(parent.$(iframe).parents("table")[0]).css("width", "100%");
                $(parent.$(iframe).parents("table")[1]).css("width", "100%");
                $(parent.$(iframe).parents("tr")[0]).prev().hide();
                $($(parent.$(iframe).parents("tr")[1]).children("td")[0]).hide();
                $($(parent.$(iframe).parents("tr")[1]).children("td")[1]).hide();
            <%}%>
            <%if ("4.7".equals(VersionInfo.getOpenXavaVersion())){%>
                $($($(parent.$(iframe).parents("table")[0]).children("tbody")).children()[0]).hide();
                $(parent.$(iframe).parents("table")[0]).css("width", "100%");
                $(parent.$(iframe).parents("table")[1]).css("width", "100%");
                $(parent.$(iframe).parents("table")[2]).css("width", "100%");
                $($($($(parent.$(iframe).parents("table")[2]).children("tbody")).children()[0]).children()[0]).hide();
                $($($($(parent.$(iframe).parents("table")[2]).children("tbody")).children()[0]).children()[1]).hide();
            <%}%>

            var perfectWidth = "99.7%"; //The width to display all table content
            
            var getPrefectHeight = function(){
                var _iframeTop = parent.$(iframe).position().top;
                var _winHi = parent.$(parent).height();
                var prefectHeight = _winHi - _iframeTop;
                if (prefectHeight < _winHi/2){
                    prefectHeight = _winHi/2;
                }
                return prefectHeight;
            }
        	
        	//Build PQGrid
            var gridModel = <%=PQGrid.getModelData(request)%>;
            
            if (gridModel){
            	gridModel.height = getPrefectHeight();  //gridModel.flexHeight = true;
                gridModel.width = perfectWidth;         //gridModel.flexWidth = true;
                gridModel.numberCellWidth = 30;
                gridModel.editModel = {clicksToEdit: 1, saveKey: 13};
                gridModel.quitEditMode = function( event, ui ) {
                    $(this).pqGrid("saveEditCell");     //FIX: Use mouse to leave a cell shouldn't update value into data
                }
                gridModel.dataModel.paging = "local";
                gridModel.dataModel.rPP = 50;
                gridModel.dataModel.rPPOptions = [10, 20, 50, 100, 500, 1000];
                
                gridModel.cellClick = function( event, ui ) {
                    return xavaEx.PQGrid.doAction(event, ui);
                }
                
                gridModel.render = function (evt, ui) {
                	
                    $("div.pq-grid-title").css("height", "22px");
                    var _sel = $("#pq-freeze-columns-select-div")
                        .css({ position: "absolute",left:"0px",top:"0px",padding:"6px", display:"block" })
                        .appendTo($("div.pq-grid-top", this).css({ position: "relative" }))
                        .find("select");
                    
                    var cols = gridModel.colModel;
                    _sel.append("<option value='0'><%=optionNone%></option>"); 
                    for (var i=0; i<cols.length-1; i++){
                    	_sel.append("<option value='"+(i+1)+"'>"+cols[i].title+"</option>"); 
                    }
                    _sel.change(function (evt) {
                        $("#pgGrid").pqGrid("option", "freezeCols", $(this).val());
                    });
                    
                }
                
                xavaEx.PQGrid.prepare(gridModel);
                $("#pgGrid").pqGrid(gridModel);
                $("#pgGrid").pqGrid({minWidth:5});
                $("#pgGrid").pqGrid({editable:true});
                $("#pgGrid").pqGrid( {width: perfectWidth} );  //Parent page's scrollbar may change after table render
            }else{
            	$("#pgGrid").append('<div class="pq-grid-title"><%=lblBlankResult%></div>');
            }
            
            //Window and iframe auto height
            var window_onresize=function(){
                $( "#pgGrid" ).pqGrid( {height: getPrefectHeight()} );
                xavaEx.setIFrameAutoHeight(window);
            }
            window.onresize = window_onresize;
            window_onresize();
            $("div.pq-grid-footer select").bind("change", function(evt){
            	window_onresize();
            	$( "#pgGrid" ).pqGrid( {width: perfectWidth} );  //Parent page's scrollbar may change the client area width
            });
        });
    </script>
</body>
</html>