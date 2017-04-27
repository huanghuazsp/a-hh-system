<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.BaseSystemUtil"%>
<%=BaseSystemUtil.getBaseDoctype()%>
<html>
<head>
<title>结束节点属性</title>
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
	
	function setValueConfigRenderText(td,valueStr){
		var dataList = $.hh.toObject(valueStr);
		var str = '';
		if(dataList){
			for(var i=0;i<dataList.length;i++){
				var data = dataList[i];
				str+=data.typeText+'：'+(data.fieldText||'')+'设置为-》'+data.value+'<br>';
			}
		}
		td.html(str);
	}
	

	function interfaceConfigRenderText(td,valueStr){
		var dataList = $.hh.toObject(valueStr);
		var str = '';
		if(dataList){
			for(var i=0;i<dataList.length;i++){
				var data = dataList[i];
				str+=data.typeText+'：'+(data.implType||'')+'：'+data.value+'<br>';
			}
		}
		td.html(str);
	}
	
	function setHeight(h){
		$('#tab').height(h-42);
		$('#basicDiv').height(h-80);
		$('#developerDiv').height(h-80);
	}
</script>
</head>
<body>
	<div xtype="hh_content">
		<form id="form" xtype="form" >
			<div id="tab" xtype="tab" >
				<ul>
					<li><a href="#basicDiv">基本配置</a></li>
					<li><a href="#developerDiv">高级配置</a></li>
				</ul>
				<div id="basicDiv">
					<table xtype="form">
							<tr>
								<td xtype="label">名称：</td>
								<td><span config=" name : 'label'  " xtype="text"></span></td>
							</tr>
							<tr>
								<td xtype="label">设值：</td>
								<td><span
									config="openWidth:700, name : 'setValueConfig' , openurl : 'jsp-workflow-select-setValueConfig',renderText: setValueConfigRenderText "
									xtype="selectInput"></span></td>
							</tr>
					</table>
				</div>
				<div id="developerDiv"  >
					<table xtype="form">
							<tr>
								<td xtype="label">接口设置：</td>
								<td><span
									config="openWidth:700, name : 'interfaceConfig' , openurl : 'jsp-workflow-select-interfaceConfig',renderText: interfaceConfigRenderText "
									xtype="selectInput"></span></td>
							</tr>
					</table>
				</div>
			</div>
		</form>
	</div>
	<div xtype="toolbar">
		<span xtype="button" config="text:'确定' , onClick : save "></span>
		<span xtype="button" config="text:'取消' , onClick : Dialog.close "></span>
	</div>
</body>
</html>