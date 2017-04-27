<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.BaseSystemUtil"%>
<%=BaseSystemUtil.getBaseDoctype()%>
<html>
<head>
<%=BaseSystemUtil.getBaseJs("checkform")%>
<script type="text/javascript">
	function initMenuAndUser() {
		Dialog.confirm({
			message : '您确认要初始化菜单和系统用户吗？',
			yes : function(result) {
				Request.request('usersystem-System-initMenuAndUser');
			}
		});
	}
	
	function initDataList() {
		Dialog.confirm({
			message : '您确认要初始化数据字典吗？',
			yes : function(result) {
				Request.request('usersystem-System-initDataList');
			}
		});
	}
	
	

	function fileCopyDeleteTask() {
		Dialog.confirm({
			message : '您确认要处理无效附件数据吗？',
			yes : function(result) {
				Request.request('usersystem-System-fileCopyDeleteTask');
			}
		});
		
	}

	
	function init() {
		for ( var p in $.hh) {
			print('$.hh.' + p);
		}
		for ( var p in Request) {
			print('Request.' + p);
		}
		for ( var p in Doing) {
			print('Doing.' + p);
		}
		for ( var p in Dialog) {
			print('Dialog.' + p);
		}
		$('[xtype=hh_content]').renderAll();
	}

	function print(text) {
		$('table').append('<tr><td>' + text + '</td></tr>');
	}
</script>
</head>
<body>
	<div xtype="toolbar" config="type:'head'">
		<span xtype="button"
			config="text:'初始化菜单和系统用户' , onClick : initMenuAndUser "></span>
			
		<span xtype="button"
			config="text:'初始化数据字典' , onClick : initDataList "></span>
			
		<span xtype="button"
			config="text:'处理无效附件数据' , onClick : fileCopyDeleteTask "></span>
			
	</div>
	<div xtype="hh_content">
		<form id="form" xtype="form" class="form">
			<table xtype="form" style="width: 700px;" align=center>
			</table>
		</form>
	</div>
</body>
</html>