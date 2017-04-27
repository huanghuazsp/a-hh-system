<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.BaseSystemUtil"%>
<%=BaseSystemUtil.getBaseDoctype()%>
<html>
<head>
<title>开始节点属性</title>
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
	
	function setHeight(h){
		$('#tab').height(h-42);
		$('#basicDiv').height(h-80);
		$('#developerDiv').height(h-80);
	}
	
	function dataManagerRenderText(td,value){
		var dataList = $.hh.toObject(value)||[];
		var readonlyStr = '';
		var defaultStr = '';
		for(var i=0;i<dataList.length;i++){
			var data = dataList[i];
			if(data.readonly==1){
				readonlyStr+=data.text+',';
			}else if(data.setdefault==1){
				defaultStr+=data.text+',';
			}
		}
		if(defaultStr){
			defaultStr='<div>默认：'+(defaultStr.substr(0,defaultStr.length-1))+'</div>';
		}
		if(readonlyStr){
			readonlyStr='<div>只读：'+(readonlyStr.substr(0,readonlyStr.length-1))+'</div>';
		}
		td.html(defaultStr+readonlyStr);
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
								<td xtype="label">表单地址：</td>
								<td><span id="href" config=" name : 'href'  " xtype="text"></span></td>
							</tr>
							<tr>
								<td xtype="label">配置表单：</td>
								<td><span id="hrefckeditor" xtype="selectTree"
									configVar=" hrefckeditorConfig "></span></td>
							</tr>
							<tr>
								<td xtype="label">数据权限：</td>
								<td><span
									config=" name : 'dataManager' , openurl : 'jsp-workflow-select-datamanager',renderText: dataManagerRenderText "
									xtype="selectInput"></span></td>
							</tr>
							<tr>
								<td xtype="label">描述：</td>
								<td><span config=" name : 'describe'  " xtype="textarea"></span></td>
							</tr>
					</table>
				</div>
				<div id="developerDiv"  >
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