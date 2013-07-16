<%@page import="org.openxava.util.Users"%>
<%@page import="org.openxava.util.UserInfo"%>
<%
String modulesUrl = "./modules";
String mainApp = request.getParameter("mainApp");
if (null!=mainApp){
	String appCtx = request.getContextPath();
	modulesUrl = mainApp + "/bridge.jsp?to="+appCtx+"/modules";
}
%>
<!DOCTYPE html>
<head>
	<title>OpenXavaEx Develop Framework - TestApp</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">

	<script type="text/javascript" src="xava-ex/libs/LABjs/LAB.js"></script>
	<script type="text/javascript" src="xava-ex/libs/a/xava-ex.js"></script>
	<script>
	$LAB.script("xava-ex/libs/jquery/jquery-1.9.1.min.js")
		.script("xava-ex/libs/zTree/js/jquery.ztree.core-3.5.js")
		.wait(function(){
			xavaEx.loadCSS("xava-ex/libs/zTree/css/zTreeStyle/zTreeStyle.css");
			var setting = {
					callback:{
						onClick: function(event, treeId, treeNode, clickFlag){
							var url = treeNode._url;
							if (url){
								$("#addressBar").text(url);
								$("#ifmWorkspace").attr("src", url); 
							}
						}
					}
			};

			var zNodes =[{
				name:"Management", open:true,
				children: [{
					name:"Master Data", open:true,
					children: [{
						name:"UOM", 	_url:"<%=modulesUrl%>/UOM"
					},{
						name:"Vendor", 	_url:"<%=modulesUrl%>/Vendor"
					},{
						name:"SKU", 	_url:"<%=modulesUrl%>/SKU"
					}]
				},{
					name:"Purchase", open:true,
					children: [{
						name:"Request", _url:"<%=modulesUrl%>/RequirementForm"
					},{
						name:"Inquiry", _url:"<%=modulesUrl%>/SourceInquiry"
					},{
						name:"PO", 		_url:"<%=modulesUrl%>/PurchaseOrder"
					},{
						name:"Receive", _url:"<%=modulesUrl%>/ReceiveTicket"
					}]
				},{
					name:"Inventory", open:true,
					isParent:true
				},{
					name:"Reports", open:true,
					children:[{
						name:"Sku Vendor LeadTime(Days)", _url:"<%=modulesUrl%>/SkuVendorLeadTimeReport"
					}]
				}]
			},{
					name:"StudentTest", open:true,
					children: [{
						name:"Student", _url:"<%=modulesUrl%>/Student"
					}]
				},{
				name:"System", open:true,
				children: [{
					name:"Schema Update", open:true,
					children: [{
						name:"Preview", _url:"./schema-update/preview"
					},{
						name:"Execute", _url:"./schema-update/update"
					}]
				}, {
					name:"Demo and documents", open:true,
					children:[{
						name:"zTree", open:false,
						children: [{
							name:"zTree demo(en)", _url:"./xava-ex/libs/zTree/demo/en/index.html"
						}, {
							name:"zTree demo(cn)", _url:"./xava-ex/libs/zTree/demo/cn/index.html"
						}, {
							name:"zTree API(en)", _url:"./xava-ex/libs/zTree/api/API_en.html"
						}, {
							name:"zTree API(cn)", _url:"./xava-ex/libs/zTree/api/API_cn.html"
						}]
					}]
				}]
			}];

			var sizeAdjust = function(){
				var h = xavaEx.getWindowSize().height;
				$("#ifmWorkspace").css("height", (h-120)+"px");
			};
			$(window).resize(sizeAdjust);
			$(document).ready(function(){
				$.fn.zTree.init($("#navTree"), setting, zNodes);
				sizeAdjust();
			});
		});
	</script>
	<style>
		html, body {height: 100%; padding:0px; margin:0px;font-family: Consolas, Comic Sans MS; font-size: 10pt;}
	    h2 {font-size: 14pt; padding: 16px; margin: 0px;}
	    hr {border-width:0px; border-top:1px solid CornflowerBlue; height:0px}
	</style>
</head>
<body>
	<table style="height:100%;width:100%" border="0">
		<tr style="height:20px;background-color:LightSteelBlue">
			<td colspan="2"><h2>OpenXavaEx Demo</h2></td>
		</tr>
		<tr valign="top">
			<td style="width:160px;background-color:aliceblue"><div id="navTree" class="ztree"></div></td>
			<td>
				<div id="addressBar" style="padding:4px;padding-bottom:0px;color:darkgray">about:blank</div>
				<hr/>
				<iframe id="ifmWorkspace" name="ifmWorkspace " frameborder="0" src=""
				        style="width:100%;height:100%" scrolling="auto"></iframe>
			</td>
		</tr>
	</table>
</body>