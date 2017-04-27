<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.BaseSystemUtil"%>
<%=BaseSystemUtil.getBaseDoctype()%>
<html>
<head>
<title>流程XML</title>
<%=BaseSystemUtil.getBaseJs("ztree")%>
<script type="text/javascript">
	var params = $.hh.getIframeParams();

	function init() {
		$('#textarea').val(params.nodexml);
	}

	var width = 800;
	var height = 600;

	function ok() {
		params.ok($('#textarea').val());
		Dialog.close();
	}
	
	function setHeight(height){
		$('#textarea').height(height-60);
	}
</script>
</head>
<body>
	<div xtype="hh_content" style="overflow: visible;" id="hh_content">
		<table xtype="form">
			<tr>
				<td><textarea id="textarea"></textarea></td>
			</tr>
		</table>
	</div>
	<div xtype="toolbar">
		<span xtype="button" config="text:'确定' , onClick : ok "></span>
	</div>
</body>
</html>