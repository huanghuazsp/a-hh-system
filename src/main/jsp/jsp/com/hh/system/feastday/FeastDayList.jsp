<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.BaseSystemUtil"%>
<%=BaseSystemUtil.getBaseDoctype()%>

<html>
<head>
<title>数据列表</title>
<%=BaseSystemUtil.getBaseJs()%>

<script type="text/javascript">
	function doDelete() {
		$.hh.pagelist.deleteData({
			pageid : 'pagelist',
			action : 'system-FeastDay-deleteByIds'
		});
	}
	function doAdd() {
		Dialog.open({
			url : 'jsp-system-feastday-FeastDayEdit',
			params : {
				callback : function() {
					$("#pagelist").loadData();
				}
			}
		});
	}
	function doEdit() {
		$.hh.pagelist.callRow("pagelist", function(row) {
			Dialog.open({
				url : 'jsp-system-feastday-FeastDayEdit',
				urlParams : {
					id : row.id
				},
				params : {
					callback : function() {
						$("#pagelist").loadData();
					}
				}
			});
		});
	}
	function doQuery() {
		$('#pagelist').loadData({
			params : $('#queryForm').getValue()
		});
	}
	function toRl(){
		window.location.href = 'jsp-system-tools-calendar?edit=1';
	}
	function typeRender(value){
		return value==1?'休': '班';
	}
</script>
</head>
<body>
	<div xtype="toolbar" config="type:'head'">
		<span xtype="button" config="onClick:doAdd,text:'添加' , itype :'add' "></span>
		<span xtype="button"
			config="onClick:doEdit,text:'修改' , itype :'edit' "></span> <span
			xtype="button" config="onClick:doDelete,text:'删除' , itype :'delete' "></span>
		<span
			xtype="button" config="onClick:toRl,text:'日历视图'  "></span>
	</div>
	<table xtype="form" id="queryForm" style="width:400px;">
		<tr>
			<td xtype="label">日期：</td>
			<td><span xtype="text" config=" name : 'feastDay'"></span></td>
			<td width=100><span xtype="button"
				config="onClick: doQuery ,text:'查询' , itype :'query' "></span></td>
		</tr>
	</table> 
	<div id="pagelist" xtype="pagelist"
		config=" url: 'system-FeastDay-queryPagingData' ,column : [
			{
				name : 'type' ,
				text : '类型',
				render : typeRender,
				width : 50
			},
		
		
			{
				name : 'feastDay' ,
				text : '日期'
			}
		
	]">
	</div>
</body>
</html>