<%@page import="com.hh.system.util.Convert"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="com.hh.system.util.BaseSystemUtil"%>
<%
	String path = request.getContextPath();
	String basePath = "/hhcommon/opensource/mxgraph/examples/editors/";
	Gson gson = new Gson();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>流程设计器</title>
<style type="text/css" media="screen">
div.base {
	position: absolute;
	overflow: hidden;
	white-space: nowrap;
	font-family: Arial;
	font-size: 8pt;
}

div.base#graph {
	border-style: solid;
	border-color: #F2F2F2;
	border-width: 1px;
	background:
		url('/hhcommon/opensource/mxgraph/examples/editors/images/grid.gif');
}
</style>
<script type="text/javascript">
mxBasePath = '/hhcommon/opensource/mxgraph/src';
</script>
<%-- <%
	String userAgent = request.getHeader("User-Agent").toString();
	//System.out.println(userAgent + "   ");
	if (userAgent.indexOf("MSIE 6.") > -1) {
%>
<script type="text/javascript" src="<%=basePath%>js/mxclient-ie.js">
</script>
<%
	} else if (userAgent.indexOf("Firefox") > -1) {
%>
<script type="text/javascript" src="<%=basePath%>js/mxclient-ff.js">
</script>
<%
	} else if (userAgent.indexOf("Chrome") > -1) {
%>
<script type="text/javascript" src="<%=basePath%>js/mxclient-chrome.js">
</script>
<%
	} else if (userAgent.indexOf("Opera") > -1) {
%>
<script type="text/javascript" src="<%=basePath%>js/mxclient-opera.js">
</script>
<%
	} else if (userAgent.indexOf("Safari") > -1) {
%>
<script type="text/javascript" src="<%=basePath%>js/mxclient-safari.js">
</script>
<%
	} else {
%>
<script type="text/javascript" src="<%=basePath%>js/mxclient-ie.js">
</script>
<%
	}
%> --%>
<script type="text/javascript" src="<%=basePath%>js/mxclient-2.8.2.0.js">
</script>
<script type="text/javascript" src="<%=basePath%>js/mxApplication.js">
</script>
<script type="text/javascript">
mxConstants.DEFAULT_FONTFAMILY = '微软雅黑';
mxConstants.DEFAULT_FONTSIZE = 12;

mxConstants.DEFAULT_HOTSPOT = 1;
// Enables guides
mxGraphHandler.prototype.guidesEnabled = true;
// Alt disables guides
mxGuide.prototype.isEnabledForEvent = function(evt) {
	return !mxEvent.isAltDown(evt);
};

// Enables snapping waypoints to terminals
mxEdgeHandler.prototype.snapToTerminals = true;

window.onbeforeunload = function() {
	return mxResources.get('changesLost');
};
function createXml(str) {
    if (document.all) {
        var xmlDom = new ActiveXObject("Microsoft.XMLDOM");
        xmlDom.loadXML(str);
        return xmlDom;
    }else{
    return new DOMParser().parseFromString(str, "text/xml");
    }
};

function attToObject(attributes){
	var object = {};
	for ( var i = 0; i < attributes.length; i++) {
			object[attributes[i].nodeName]=attributes[i].nodeValue;
	}
	return object;
}
	var dataId = '<%=request.getParameter("dataId") == null ? "" : request
					.getParameter("dataId")%>';
	var workflowName = '<%=request.getParameter("workflowName") == null ? "" : request
					.getParameter("workflowName")%>';
	var url = '<%=request.getParameter("saveUrl") == null ? "workflow-WorkFlowInfo-save" : request.getParameter("saveUrl")%>';
	var findUrl = '<%=request.getParameter("findUrl") == null ? "" : request.getParameter("findUrl")%>';
	var objectId = '<%=request.getParameter("objectId") == null ? "" : request.getParameter("objectId")%>';
	var mxDocument = null;
</script>
<%=BaseSystemUtil.getBaseJs("checkform")%>
<script type="text/javascript">
<%
	String editType = Convert.toString(request.getParameter("hipd")).equals("1")?"-pdrev":"";
	if(Convert.toString(request.getParameter("hipd")).equals("2")){
		editType="-pdrev-2";
	}
%>
	var Init = {
		init : function() {
			new mxApplication(
					'/hhcommon/opensource/mxgraph/examples/editors/config/workfloweditor<%=editType%>.xml');
		}
	}
	var uitype = 'jquery';
	function init() {
	}
	function initMxgraph(){
		findObject();
		Doing.hide();
	}
	
	function viewXml(page,editor){
		var nodexml = page.writeGraphModel(editor.linefeed);
		Dialog.open({
			url : 'jsp-workflow-wfdes-viewXml',
			params : {
				nodexml : nodexml,
				ok :function(value){
					var xml = createXml(value);
					var dec = new mxCodec(
							xml.documentElement.ownerDocument);
					dec.decode(xml.documentElement, mxDocument.graph
							.getModel());
				}
			}
		});
	}
	
	function findObject(){
		if(findUrl && objectId){
			Request
			.request(
					findUrl,
					{
						data : {
							id : objectId
						},
						defaultMsg : false,
						callback : function(object) {
							var graph = mxDocument.graph;
							if (object != null) {
								var mxgraphXml = object.mxgraphXml;
								var xml = createXml(mxgraphXml);
								var dec = new mxCodec(
										xml.documentElement.ownerDocument);
								dec.decode(xml.documentElement, graph
										.getModel());
							} else {
								initDefaultMxgraph();
							}
						}
					});
		}else{
			initDefaultMxgraph();
		}
	}
	
	function initDefaultMxgraph(){
		var graph = mxDocument.graph;
		var xml = createXml('<mxGraphModel><root><Workflow label="'
				+ workflowName
				+ '" description="" href="" id="0" hrefParams="" data="{}"><mxCell/></Workflow><Layer label="Default Layer" id="1"><mxCell parent="0"/></Layer></root></mxGraphModel>');
		var dec = new mxCodec(
				xml.documentElement.ownerDocument);
		dec.decode(xml.documentElement, graph
				.getModel());
	}
	
	function addCellNodes(paramsObject,cellObjectList,nodeCells){
		var parentNode = paramsObject.parentNode;
		var parentX = 0;
		var parentY = 0;
		if(parentNode){
			parentX=parentNode.x;
			parentY=parentNode.y;
		}
		for (var i = 0; i < nodeCells.length; i++) {
			var nodeCell = nodeCells[i];
			var attributes = nodeCell.value.attributes;
			var cellObject = attToObject(attributes);
				$.extend(cellObject, $.hh.toObject(cellObject.data));
				if(cellObject.href){
					cellObject.href=cellObject.href+'?hrefckeditor='+cellObject.hrefckeditor;
				}
			var geometry = nodeCell.geometry;
			cellObject.id = cellObject.type + nodeCell.id;
			cellObject.x = parentX+geometry.x;
			cellObject.y = parentY+geometry.y;
			cellObject.width = geometry.width;
			cellObject.height = geometry.height;

			if(paramsObject.imageHeight<cellObject.y){
				paramsObject.imageHeight=cellObject.y;
				paramsObject.widgetHeight=cellObject.height;
			}
			if(paramsObject.imageWidth<cellObject.x){
				paramsObject.imageWidth=cellObject.x;
				paramsObject.widgetWidth=cellObject.width;
			}

			if (cellObject.type == 'sequenceFlow') {
				var source = nodeCell.source;
				var target = nodeCell.target;
				var sourceObject = attToObject(source.value.attributes);
				var targetObject = attToObject(target.value.attributes);
				cellObject.sourceRef = sourceObject.type + source.id;
				cellObject.targetRef = targetObject.type + target.id;
			}else if (cellObject.type == 'startEvent') {
				paramsObject.startEventId = cellObject.id;
			}else if (cellObject.type == 'swimlane') {
				paramsObject.parentNode = cellObject;
				addCellNodes(paramsObject,cellObjectList,nodeCell.children)
			}
			cellObject.data = null;
			cellObjectList.push(cellObject);
		}
	}

	function saveMxgraphData(page,editor,deploy) {
		var graph = editor.graph;
		var xmlDoc = mxUtils.createXmlDocument();
		var root = xmlDoc.createElement('output');
		xmlDoc.appendChild(root);
		var xmlCanvas = new mxXmlCanvas2D(root);
		var imgExport = new mxImageExport();
		imgExport.drawState(graph.getView().getState(graph.model.root),
				xmlCanvas);
		var bounds = graph.getGraphBounds();
		var w = Math.round(bounds.x + bounds.width + 4);
		var h = Math.round(bounds.y + bounds.height + 4);
		var imgxml = mxUtils.getXml(root);
		var nodexml = page.writeGraphModel(editor.linefeed);
		//if (this.escapePostData) {
		//   imgxml = encodeURIComponent(imgxml);
		// }
		var text = '';
		var root_cell = page.graph.getModel().getRoot();
		var root_cell_attributes = root_cell.value.attributes;
		var rootObject = attToObject(root_cell_attributes);

			$.extend(rootObject, $.hh.toObject(rootObject.data));
			if(rootObject.href){
				rootObject.href=rootObject.href+'?hrefckeditor='+rootObject.hrefckeditor;
			}

		rootObject.data = null;
		rootObject.label = workflowName;
		var nodeCells = root_cell.children[0].children;

		
		
		
		var paramsObject = {
		};
		var startEventTargetRefId = null;
		paramsObject.startEventId = null;
		paramsObject.imageHeight = 100;
		paramsObject.imageWidth = 100;
		
		paramsObject.widgetHeight = 40;
		paramsObject.widgetWidth = 40;
		

		var cellObjectList = [];
		if (nodeCells != null) {
			addCellNodes(paramsObject,cellObjectList,nodeCells)
		}
		
		for (var i = 0; i < cellObjectList.length; i++) {
			var cellObject = cellObjectList[i];
			if (cellObject.type == 'sequenceFlow') {
				if (cellObject.sourceRef == paramsObject.startEventId) {
					rootObject.startEventTargetRefId = cellObject.targetRef;
					break;
				}
			}
		}
		
		nodexml = nodexml.replace(/&gt;/g, '!gt;').replace(/&lt;/g, '!lt;')
				.replace(/&quot;/g, '!quot;').replace(/&amp;/g, '!amp;');
			cellObjectList = $.hh.toString(cellObjectList);
			rootObject = $.hh.toString(rootObject);
			
			imageWidth=paramsObject.imageWidth+paramsObject.widgetWidth+150;
			imageHeight=paramsObject.imageHeight+paramsObject.widgetHeight+150;
			
			Request.request(url, {
				data : {
					cellObjectList : cellObjectList,
					rootObject : rootObject,
					mxgraphXml : nodexml,
					imgxml : imgxml,
					text : text,
					dataId : dataId,
					id : objectId,
					imageWidth:imageWidth+50,
					imageHeight:imageHeight,
					deploy : deploy,
					pdid : pdid
				}
			}, function(result) {
			});
	}
	
	var pdid = null;
	
	function showProperties(page,editor, cell){
		cell = cell || page.graph.getSelectionCell();

		var rootcell = page.graph.getCurrentRoot();
		if (rootcell == null) {
			rootcell = page.graph.getModel().getRoot();
		}

		var rootattributes = rootcell.value.attributes;
		var rootobject = {id:rootcell.id};
		for ( var i = 0; i < rootattributes.length; i++) {
				if(rootattributes[i].nodeName=='data'){
						$.extend(rootobject,$.hh.toObject(rootattributes[i].nodeValue));
				}else{
					rootobject[rootattributes[i].nodeName]=rootattributes[i].nodeValue;
				}
		}

		if (cell == null) {
			cell=rootcell;
		}

		var attributes = cell.value.attributes;
		var type ='';
		var object = {id:cell.id};
		for ( var i = 0; i < attributes.length; i++) {
				if(attributes[i].nodeName=='data'){
						$.extend(object,$.hh.toObject(attributes[i].nodeValue));
				}else{
					object[attributes[i].nodeName]=attributes[i].nodeValue;
				}
		}
		var params = {rootobject:rootobject,object:object,cell:cell,objdocument: page}; 
			var winClass = "";
			if(object.type=='userTask'){
				winClass = "jsp-workflow-properties-userTask";
			}else if(object.type=='sequenceFlow'){
				winClass = "jsp-workflow-properties-sequenceFlow";
			}else if(object.type=='parallelGateway'){
				winClass = "jsp-workflow-properties-gateway";
			}else if(object.type=='startEvent'){
				winClass = "jsp-workflow-properties-startEvent";
			}else if(object.type=='endEvent' || object.type=='endEventError'){
				winClass = "jsp-workflow-properties-endEvent";
			}else if(object.type==null){
				params.object.label = workflowName;
				winClass = "jsp-workflow-properties-workflow";
			}
			if(winClass!=''){
				params.page=window;
				Dialog.open({
					url : winClass,
					params : params
				});
			}else{
				Dialog.infomsg('此节点没有可编辑的属性！');
			}
	}
</script>
</head>
<body onload="Init.init();">
	<table id="splash" width="100%" height="100%"
		style="background: white; position: absolute; top: 0px; left: 0px; z-index: 4;">
		<tr>
			<td align="center" valign="middle"><img
				src="<%=basePath%>images/loading.gif"></td>
		</tr>
	</table>
	<div id="graph" class="base"></div>
	<div id="status" class="base" align="right"></div>
</body>
</html>
