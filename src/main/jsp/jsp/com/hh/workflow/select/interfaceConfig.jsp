<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.BaseSystemUtil"%>
<%=BaseSystemUtil.getBaseDoctype()%>
<html>
<head>
<title>设值</title>
<%=BaseSystemUtil.getBaseJs("ztree")%>
<script type="text/javascript">
	var params = $.hh.getIframeParams();
	var rootobject = params.parentPage.params.rootobject;
	
	var width = 700;
	var height = 500;
	
	var textField = params.textField;

	var comboboxTypeConfig ={
			emptyItem:false,
			data:[
				{id:'taskStart',text:'任务开始'},
				{id:'taskEnd',text:'任务结束'}
			]
	}
	
	var comboboxTypeConfig2 ={
			emptyItem:false,
			data:[
				{id:'class',text:'class'},
				{id:'sql',text:'sql'}
			]
	}
	
	var setValueConfig = {
			name:'setValueConfig',
			trhtml:'<table style="width:100%"><tr>'
				+'<td style="padding-right:5px;width:90px;"><span xtype="combobox" valuekey="type" configVar=" comboboxTypeConfig "></span></td>'
				+'<td style="padding-right:5px;width:90px;"><span xtype="combobox" valuekey="implType" configVar=" comboboxTypeConfig2 "></span></td>'
				+'<td xtype="label" style="width:80px;">值：</td><td><span xtype="text" valuekey="value" configVar="valueConfig"></span></td></tr></table>'
	}
	
	function init() {
		$('#span_setValueConfig').setValue(textField.val());
		var dataList = $.hh.property.defaultValueConfig	;
		var lsStr = '';
		for(var i=0;i<dataList.length;i++){
			var data = dataList[i];
			lsStr+=data+',';
			
		}
		$('#cs').html(lsStr);
	}

	function ok() {
		textField.val($('#span_setValueConfig').getValue());
		if(params.onChange){
			params.onChange(textField.val());
		}
		Dialog.close();
		textField.focus();
	}
	var comboboxConfig = {};
	var valueConfig = {
		//data:$.hh.property.defaultValueConfig	
	};
</script>
</head>
<body>
	<div xtype="hh_content" style="overflow: visible;" id="hh_content">
		<table xtype="form">
			<tr>
				<td style="padding: 3px; text-align: center;">接口设置</td>
			</tr>
			<tr>
				<td>
				<span xtype="tableitem" configVar="setValueConfig" ></span>
				</td>
			</tr>
			<tr>
				<td style="padding: 3px; ">例子</td>
			</tr>
			<tr>
				<td style="padding: 3px; " id="lz">
				class:例：类全名#方法名<br/>
				class:例：com.hh.system.service.impl.SysDataService#checkTextOnly<br/>
				sql:例：update table_name set field_ = ${当前登录人} where id=:id
				</td>
			</tr>
			<tr>
				<td style="padding: 3px; ">参数</td>
			</tr>
			<tr>
				<td style="padding: 3px; " id="cs"></td>
			</tr>
		</table>
		<br />
		<br />
	</div>
	<div xtype="toolbar">
		<span xtype="button" config="text:'确定' , onClick : ok "></span>
	</div>
</body>
</html>