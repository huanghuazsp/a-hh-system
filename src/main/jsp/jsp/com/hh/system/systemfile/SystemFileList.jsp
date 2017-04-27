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
			action : 'system-SystemFile-deleteByIds'
		});
	}
	function doAdd() {
		Dialog.open({
			url : 'jsp-system-systemfile-SystemFileEdit',
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
				url : 'jsp-system-systemfile-SystemFileEdit',
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
	
	function renderFileSize(value,data){
		var fileSize = data.fileSize || '';

		fileSize = Math.round(fileSize / 1024);
		var suffix = 'KB';
		if (fileSize > 1000) {
			fileSize = Math.round(fileSize / 1000);
			suffix = 'MB';
		}
		var fileSizeParts = fileSize.toString().split('.');
		fileSize = fileSizeParts[0];
		if (fileSizeParts.length > 1) {
			fileSize += '.' + fileSizeParts[1].substr(0, 2);
		}
		fileSize += suffix;
		return fileSize;
	}
	function doView(){
		Request.openwin('system-tools-viewFile?id=');
	}
	function renderText(value,data){
		return $.hh.property.getFileTypeIcon(data.fileType)+value+'&nbsp;&nbsp;<a href="javascript:Request.download(\''+data.id+'\');">下载</a>&nbsp;&nbsp;<a href="javascript:Request.viewFile(\''+data.id+'\');">查看</a>'
	}
</script>
</head>
<body>
	<div xtype="toolbar" config="type:'head'">
		  <span
			xtype="button" config="onClick:doDelete,text:'删除' , itype :'delete' "></span>
	</div>
	<table xtype="form" id="queryForm" style="width:600px;">
		<tr>
			<td xtype="label">名称：</td>
			<td><span xtype="text" config=" name : 'text'"></span></td>
			<td><span xtype="button"
				config="onClick: doQuery ,text:'查询' , itype :'query' "></span></td>
		</tr>
	</table>
	<div id="pagelist" xtype="pagelist"
		config=" url: 'system-SystemFile-queryPagingData' ,column : [
		
		{
				name : 'text' ,
				text : '附件名称',
				align:'left' ,
				render : renderText
			},{
			name : 'createTime' ,
			text : '时间',
			render:'datetime',
			width:120
		},
		{
				name : 'fileType' ,
				text : '附件类型',
				width:80
			},
			{
				name : 'fileSize' ,
				text : '附件大小',
				render : renderFileSize,
				width:120
			},
		
			{
				name : 'type' ,
				text : '所属模块',
				width:80
			}
		
	]">
	</div>
</body>
</html>