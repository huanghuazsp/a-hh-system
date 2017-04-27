<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.BaseSystemUtil"%>
<%=BaseSystemUtil.getBaseDoctype()%>
<html>
<head>
<title>任务节点属性</title>
<%=BaseSystemUtil.getBaseJs("checkform")%>
<script type="text/javascript"
	src="/hhcommon/opensource/mxgraph/examples/editors/properties/jqueruiproperties.js"></script>
<script type="text/javascript">
	var height = 550;
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
	
	function serviceUserConfigRenderText(td,valueStr){
		var dataList = $.hh.toObject(valueStr);
		var str = '';
		if(dataList){
			for(var i=0;i<dataList.length;i++){
				var data = dataList[i];
				str+=data.text+',';
			}
		}
		if(str){
			str=str.substr(0,str.length-1);
		}
		td.html(str);
	}
	
	function userConfigRenderText(td,valueStr){
		var dataList = $.hh.toObject(valueStr);
		var str = '';
		if(dataList){
			for(var i=0;i<dataList.length;i++){
				var data = dataList[i];
				var cond = data.cond=='or'?'或':'和';
				cond='&nbsp;<font color=red>'+cond+'</font>';
				if(i==0){
					cond='';
				}
				
				var type = '';
				if (data.type == 'dept') {
					type = '部门';
				} else if (data.type == 'org') {
					type = '机构';
				} else if (data.type == 'role') {
					type = '角色';
				}else if (data.type == 'sysgroup') {
					type = '系统组';
				}else if (data.type == 'usgroup') {
					type = '自定义组';
				}else if (data.type == 'user') {
					type = '用户';
				}else if (data.type == 'custom') {
					type = '自定义';
				}
				str+=cond+'【'+type+'】'+data.text;
				if(data.roleText){
					str+='['+data.roleText+']';
				}
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
								<td xtype="label">表单地址：</td>
								<td><span id="href" config=" name : 'href'  " xtype="text"></span></td>
							</tr>
							<tr>
								<td xtype="label">配置表单：</td>
								<td><span id="hrefckeditor" xtype="selectTree"
									configVar=" hrefckeditorConfig "></span></td>
							</tr>
							<tr>
								<td xtype="label">会签：</td>
								<td><span
									config=" name : 'jointlySign'  "
									xtype="check"></span></td>
							</tr>
							<tr>
								<td xtype="label">办里人范围：</td>
								<td><span
									config=" name : 'userConfig' , openurl : 'jsp-workflow-select-userConfig',renderText: userConfigRenderText "
									xtype="selectInput"></span></td>
							</tr>
							<tr>
								<td xtype="label">业务办里人：</td>
								<td><span
									config=" name : 'serviceUserConfig' , openurl : 'jsp-workflow-select-serviceUserConfig',renderText: serviceUserConfigRenderText "
									xtype="selectInput"></span></td>
							</tr>
							<tr>
								<td xtype="label">数据权限：</td>
								<td><span
									config=" name : 'dataManager' , openurl : 'jsp-workflow-select-datamanager',renderText: dataManagerRenderText "
									xtype="selectInput"></span></td>
							</tr>
							<tr>
								<td xtype="label">设值：</td>
								<td><span
									config="openWidth:700, name : 'setValueConfig' , openurl : 'jsp-workflow-select-setValueConfig',renderText: setValueConfigRenderText "
									xtype="selectInput"></span></td>
							</tr>
							<tr>
								<td xtype="label">描述：</td>
								<td><span config=" name : 'describe'  " xtype="textarea"></span></td>
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
		<span xtype="button" config="text:'确定' , onClick : save "></span> <span
			xtype="button" config="text:'取消' , onClick : Dialog.close "></span>
	</div>
</body>
</html>