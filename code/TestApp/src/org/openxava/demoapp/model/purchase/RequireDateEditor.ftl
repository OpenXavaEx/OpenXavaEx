<textarea id="${propertyKey}-htmlSource" style="display:none">
<!DOCTYPE html>
<head>
	<title>iframe Demo</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<script type="text/javascript" src="${contextPath}/xava-ex/libs/jquery/jquery-1.9.1.js"></script>
	<script type="text/javascript" src="${contextPath}/xava-ex/libs/zTree/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="${contextPath}/xava-ex/libs/zTree/js/jquery.ztree.excheck-3.5.js"></script>
	<link rel="stylesheet" type="text/css" media="all" href="${contextPath}/xava-ex/libs/zTree/css/zTreeStyle/zTreeStyle.css"/> 	
	<style>
		html, body {height: 100%; padding:0px; margin:0px;font-family: Consolas, Comic Sans MS; background-color:aliceblue; font-size: 10pt;}
		tr {font-size:0.8em; padding:0px; margin:0px}
		tr .caption {font-weight:bold}
	</style>
</head>
<body onload="initTree()"><!-- BP: To fit the behavior of IE, use onload to init the page is recommanded -->
	<!--
		Firefox/Chrome - above "<script ... src=...>" should run before below embeded script;
		IE - The embeded script should run before "<script ... src=...>";
	 -->
	<table style="padding:0px; margin:0px" border="0">
		<tr><td class="caption">Vendor: </td><td>${(vendor!'')?html}</td></tr>
		<tr><td class="caption">LeadTime: </td><td>${leadTime!''} 天</td></tr>
	</table>
	<hr/>
	<div id="${propertyKey}-navTree" class="ztree"></div>
	<script>
	function initTree() {
		var setting = {
				callback:{
					onCheck: function(event, treeId, treeNode) {
						parent.document.getElementById("${propertyKey}").value = treeNode.name;	//The "real" field
					}
				},
				check: {
					enable: true,
					chkStyle: "radio",
					radioType: "all"
				},
				data: {
					simpleData: {
						enable: true
					}
				}
		};
		var zNodes = ${nodes};
		var tree = $.fn.zTree.init($("#${propertyKey}-navTree"), setting, zNodes);
	}
	</script>
</body>
</textarea>
<iframe id="${propertyKey}-ifmWorkspace" frameborder="0" src="" style="width:360px;height:160px" scrolling="auto"></iframe>
<script>
xavaEx.fillIFrame(
		"${propertyKey}-ifmWorkspace",
		document.getElementById("${propertyKey}-htmlSource").value
)
</script>
