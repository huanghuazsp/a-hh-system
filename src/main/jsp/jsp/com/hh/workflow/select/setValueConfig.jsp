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
	
	var width = 750;
	var height = 500;
	var href = '';
	var hrefckeditor = '';
	if(params.parentPage.$('#href').length>0){
		href = params.parentPage.$('#href').getValue() || rootobject.href;
	}else{
		href = rootobject.href;
	}
	if(params.parentPage.$('#hrefckeditor').length>0){
		hrefckeditor = params.parentPage.$('#hrefckeditor').getValue() || rootobject.hrefckeditor;
	}else{
		hrefckeditor = rootobject.hrefckeditor;
	}
	

	var comboboxTypeConfig ={
			emptyItem:false,
			data:[
				{id:'taskStart',text:'任务开始'},
				{id:'taskEnd',text:'任务结束'}
			]
	}
	
	var setValueConfig = {
			name:'setValueConfig',
			trhtml:'<table style="width:100%"><tr>'
				+'<td style="padding-right:5px;"><span xtype="combobox" valuekey="type" configVar=" comboboxTypeConfig "></span></td>'
				+'<td xtype="label">字段：</td><td style="padding-right:5px;"><span xtype="combobox" valuekey="field" configVar=" comboboxConfig "></span></td>'
				+'<td xtype="label">值：</td><td><span xtype="text" valuekey="value" configVar="valueConfig"></span></td></tr></table>'
	}
	
	function init() {
		var iframe = $('<iframe style="display:none;" id="hrefform"  name="hrefform"  width=98%	height=100  src=""></iframe>');
		iframe.attr('src', href + '?hrefckeditor=' + hrefckeditor
				+ '&type=workflow');
		$('#hh_content').append(iframe);
		var iframe = window.frames['hrefform'];
		$.hh
				.iframeLoad(
						iframe,
						function(result) {
							var dataList = [];
							$(iframe.document).find($.hh.widgetFind).each(
									function() {
										var config = iframe.$.hh.getConfig($(this));
										dataList.push({
											id : config.name,
											text : config.textfield || config.name
										});
									});
							comboboxConfig.data = dataList;

							var span = $('<span xtype="tableitem" configVar="setValueConfig" ></span>');
							$('#setValueConfigtd').append(span);
							span.render();
							
							if (textField.val()) {
								span.setValue(textField.val());
							}
						});
	}

	var textField = params.textField;

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
		data:$.hh.property.defaultValueConfig	
	};
</script>
</head>
<body>
	<div xtype="hh_content" style="overflow: visible;" id="hh_content">
		<table xtype="form">
			<tr>
				<td style="padding: 3px; text-align: center;">设值</td>
			</tr>
			<tr>
				<td id="setValueConfigtd"></td>
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