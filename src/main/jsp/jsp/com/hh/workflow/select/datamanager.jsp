<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.BaseSystemUtil"%>
<%=BaseSystemUtil.getBaseDoctype()%>
<html>
<head>
<title>权限控制</title>
<%=BaseSystemUtil.getBaseJs("ztree")%>
<script type="text/javascript">
	var params = $.hh.getIframeParams();
	var rootobject = params.parentPage.params.rootobject;

	var href = params.parentPage.$('#href').getValue() || rootobject.href;
	var hrefckeditor = params.parentPage.$('#hrefckeditor').getValue()
			|| rootobject.hrefckeditor;

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
							itemselectConfig.data = dataList;

							var span = $('<span id="datamanagerspan" xtype="itemselect" configVar="itemselectConfig"></span>');
							$('#datamanagertd').append(span);
							span.render();
							
							var span1 = $('<span id="setDefaultValuespan" xtype="itemselect" configVar="itemselectConfig"></span>');
							$('#setDefaultValuetd').append(span1);
							span1.render();
							
							if (textField.val()) {
								var disstr = '';
								var setdefaultstr = '';
								var dataManager = $.hh.toObject(textField
										.val());
								$.each(dataManager, function(i, data) {
									if (data.readonly == 1) {
										disstr += data.field + ',';
									}else if (data.setdefault == 1) {
										setdefaultstr += data.field + ',';
									}
								});
								if (disstr) {
									disstr = disstr
											.substr(0, disstr.length - 1);
								}
								if (setdefaultstr) {
									setdefaultstr = setdefaultstr
											.substr(0, setdefaultstr.length - 1);
								}
								
								span.setValue(disstr);
								span1.setValue(setdefaultstr);
								
							}
						});
	}

	var textField = params.textField;
	var width = 600;
	var height = 500;

	function ok() {
		var dataList = [];
		var dislist = $('#datamanagerspan').getValueData();
		if (dislist && dislist.length>0) {
			for (var i = 0; i < dislist.length; i++) {
				dataList.push({
					text : dislist[i].text,
					field : dislist[i].id,
					readonly : 1
				});
			}
		}
		
		var setDefaultValuelist = $('#setDefaultValuespan').getValueData();
		if (setDefaultValuelist && setDefaultValuelist.length>0) {
			for (var i = 0; i < setDefaultValuelist.length; i++) {
				dataList.push({
					text : setDefaultValuelist[i].text,
					field : setDefaultValuelist[i].id,
					setdefault : 1
				});
			}
		}
		textField.val($.hh.toString(dataList));
		if(params.onChange){
			params.onChange($.hh.toString(dataList));
		}
		Dialog.close();
		textField.focus();
	}
	var itemselectConfig = {};
</script>
</head>
<body>
	<div xtype="hh_content" style="overflow: visible;" id="hh_content">
		<table xtype="form">
			<tr>
				<td style="padding: 3px; text-align: center;">只读</td>
			</tr>
			<tr>
				<td id="datamanagertd"></td>
			</tr>
			<tr>
				<td style="padding: 3px; text-align: center;">设置默认值</td>
			</tr>
			<tr>
				<td id="setDefaultValuetd"></td>
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