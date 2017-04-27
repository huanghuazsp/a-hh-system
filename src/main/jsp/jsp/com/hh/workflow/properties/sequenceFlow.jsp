<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.BaseSystemUtil"%>
<%=BaseSystemUtil.getBaseDoctype()%>
<html>
<head>
<title>连接线属性</title>
<%=BaseSystemUtil.getBaseJs("checkform")%>
<script type="text/javascript"
	src="/hhcommon/opensource/mxgraph/examples/editors/properties/jqueruiproperties.js"></script>
<script type="text/javascript">
	var height = 350;
	var width = 600;
	var params = $.hh.getIframeParams();
	var object = params.object;
	function init() {
		$("form").setValue(object);
	}

	function save() {
		var values = $("form").getValue();
		saveProperties(values);
	}
</script>
</head>
<body>
	<div xtype="hh_content">
		<form id="form" xtype="form" class="form">
			<table xtype="form">
				<tbody>
					<tr>
						<td xtype="label">名称：</td>
						<td><span config=" name : 'label'  " xtype="text"></span></td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	<div xtype="toolbar">
		<span xtype="button" config="text:'确定' , onClick : save "></span>
		<span xtype="button" config="text:'取消' , onClick : Dialog.close "></span>
	</div>
</body>
</html>