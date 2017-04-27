<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.BaseSystemUtil"%>
<%@page import="com.hh.system.util.Convert"%>
<%=BaseSystemUtil.getBaseDoctype()%>

<html>
<head>
<title>数据编辑</title>
<%=BaseSystemUtil.getBaseJs("checkform","date")%>

<script type="text/javascript">
	var params = $.hh.getIframeParams();
	var width = 450;
	var height = 280;

	var objectid = '<%=Convert.toString(request.getParameter("id"))%>';
	var dateStr = '';
	function save() {
		$.hh.validation.check('form', function(formData) {
			Request.request('system-FeastDay-save', {
				data : formData,
				callback : function(result) {
					if (result.success!=false) {
						params.callback(formData);
						Dialog.close();
					}
				}
			});
		});
	}

	function findData() {
		if (objectid) {
			Request.request('system-FeastDay-findObjectById', {
				data : {
					id : objectid
				},
				callback : function(result) {
					$('#form').setValue(result);
				}
			});
		}
	}

	function init() {
		findData();
	}
</script>
</head>
<body>
	<div xtype="hh_content">
		<form id="form" xtype="form" class="form">
			<span xtype="text" config=" hidden:true,name : 'id'"></span>
			<table xtype="form">
				
				
					<tr>
						<td xtype="label">日期：</td>
						<td><span xtype="date" config=" name : 'feastDay' "></span></td>
					</tr>
					<tr>
						<td xtype="label">类型：</td>
						<td><span xtype="radio"
						config="name: 'type' ,value : 1, data :[{id:1,text:'休'},{id:2,text:'班'}]"></span></td>
					</tr>
				
			</table>
		</form>
	</div>
	<div xtype="toolbar">
		<span xtype="button" config="text:'保存' , onClick : save "></span> <span
			xtype="button" config="text:'取消' , onClick : Dialog.close "></span>
	</div>
</body>
</html>

 
 