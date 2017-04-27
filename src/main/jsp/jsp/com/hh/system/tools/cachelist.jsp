<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.BaseSystemUtil"%>
<%=BaseSystemUtil.getBaseDoctype()%>
<html>
<head>
<title>缓存管理</title>
<%=BaseSystemUtil.getBaseJs()%>
<script type="text/javascript">
	function doDelete() {
		$.hh.pagelist.callRows("pagelist", function(rows) {
			Dialog.confirm({
				message : '您确认要清除选择的缓存吗？',
				yes : function(result) {
					var ids = $.hh.objsToStr(rows);
					Request.request('usersystem-System-deleteCacheByIds', {
						data : {
							ids : ids
						}
					}, function(result) {
						if (result.success!=false) {
							$("#pagelist").loadData();
						}
					});
				}
			});
		});
	}

</script>
</head>
<body>
	<div xtype="toolbar" config="type:'head'">
		<span xtype="button" config="onClick:doDelete,text:'清除缓存'"></span>
	</div>
	<div id="pagelist" xtype="pagelist"
		config=" paging:false , url: 'usersystem-System-queryCacheListPage' ,column : [
		{
			name : 'id' ,
			text : '缓存名称'
		}
	]">
	</div>
</body>
</html>