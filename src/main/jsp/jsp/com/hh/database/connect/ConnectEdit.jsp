<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.BaseSystemUtil"%>
<%@page import="com.hh.system.util.Convert"%>
<%=BaseSystemUtil.getBaseDoctype()%>

<html>
<head>
<title>数据编辑</title>
<%=BaseSystemUtil.getBaseJs("checkform", "date")%>

<script type="text/javascript">
	var params = $.hh.getIframeParams();
	var width = 600;
	var height = 450;
	var objectid = '<%=Convert.toString(request.getParameter("id"))%>' || '';

	function callback() {
	}
	function save() {
		$.hh.validation.check('form', function(formData) {
			Request.request('database-Connect-saveTree', {
				data : formData,
				callback : function(result) {
					if (result.success != false) {
						callback(formData);
						Dialog.close();
					}
				}
			});
		});
	}
	
	function testConn(){
		$.hh.validation.check('form', function(formData) {
			Request.request('database-Connect-testConn', {
				data : formData,
				callback : function(result) {
				}
			});
		});
	}

	function findData(objid) {
		if (objid) {
			Request.request('database-Connect-findObjectById', {
				data : {
					id : objid
				},
				callback : function(result) {
					$('#form').setValue(result);
				}
			});
		}
	}


	function init() {
		findData(objectid);
	}
</script>
</head>
<body>
	<div xtype="hh_content">
		<form id="form" xtype="form" class="form">
			<span xtype="text" config=" hidden:true,name : 'id'"></span>
			<table xtype="form" width=80% align=center>
				<tr>
					<td xtype="label">名称：</td>
					<td><span xtype="text" config=" name : 'text',required :true"></span></td>
				</tr>
				<tr style="display: none;">
					<td xtype="label">上级节点：</td>
					<td><span id="node_span" xtype="selectTree"
						config="name: 'node' , findTextAction : 'database-Connect-findObjectById' , url : 'database-Connect-queryTreeList' "></span>
					</td>
				</tr>
				<tr style="display: none;">
					<td xtype="label">是否展开：</td>
					<td><span xtype="radio"
						config="name: 'expanded' ,value : 0,  data :[{id:1,text:'是'},{id:0,text:'否'}]"></span></td>
				</tr>
				<tr>
					<td xtype="label">类型：</td>
					<td><span xtype="radio"
						config="name: 'type' ,required :true,value : 'mysql', data :[{id:'mysql',text:'mysql'},{id:'oracle',text:'oracle'}]"></span></td>
				</tr>

				<tr>
					<td xtype="label">连接串：</td>
					<td><span xtype="text"
						config=" name : 'connect',required :true "></span></td>
				</tr>

				<tr>
					<td xtype="label">帐号：</td>
					<td><span xtype="text" config=" name : 'user',required :true "></span></td>
				</tr>

				<tr>
					<td xtype="label">密码：</td>
					<td><span xtype="text"
						config=" name : 'password' ,required :true"></span></td>
				</tr>

			</table>
		</form>
	</div>
	<div xtype="toolbar">
		<span xtype="button" config="text:'测试连接' , onClick : testConn "></span>
		<span xtype="button" config="text:'保存' , onClick : save "></span>
	</div>
</body>
</html>