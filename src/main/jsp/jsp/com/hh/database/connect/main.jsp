<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.BaseSystemUtil"%>
<%@page import="com.hh.system.util.pk.PrimaryKey"%>
<%=BaseSystemUtil.getBaseDoctype()%>

<html>
<head>
<title>数据列表</title>
<%=BaseSystemUtil.getBaseJs("layout","ztree", "ztree_edit")%>
<%
	String iframeId = PrimaryKey.getUUID();
%>
<style type="text/css">
.ztree li span.button.conn_ico_docu{margin-right:2px; background: url(/hhcommon/images/icons/folder/folder_database.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.conn_ico_open{margin-right:2px; background: url(/hhcommon/images/icons/folder/folder_database.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.conn_ico_close{margin-right:2px; background: url(/hhcommon/images/icons/folder/folder_database.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}

.ztree li span.button.table_ico_docu{margin-right:2px; background: url(/hhcommon/images/icons/table/table.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.table_ico_open{margin-right:2px; background: url(/hhcommon/images/icons/table/table.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.table_ico_close{margin-right:2px; background: url(/hhcommon/images/icons/table/table.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}

.ztree li span.button.field_ico_docu{margin-right:2px; background: url(/hhcommon/images/extjsico/ascx.gif) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.field_ico_open{margin-right:2px; background: url(/hhcommon/images/extjsico/ascx.gif) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.field_ico_close{margin-right:2px; background: url(/hhcommon/images/extjsico/ascx.gif) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}

.ztree li span.button.primary_ico_docu{margin-right:2px; background: url(/hhcommon/images/extjsico/access.gif) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.primary_ico_open{margin-right:2px; background: url(/hhcommon/images/extjsico/access.gif) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.primary_ico_close{margin-right:2px; background: url(/hhcommon/images/extjsico/access.gif) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}


</style>
<script type="text/javascript">
	var iframeId = '<%=iframeId%>';
	var selectConnNode = null;
	function doAdd() {
		Dialog.open({
			url : 'jsp-database-connect-ConnectEdit',
			params : {
				selectNode : null,
				callback : function() {
					$.hh.tree.refresh('tree');
				}
			}
		});
	}
	function doEdit(treeNode) {
		Dialog.open({
			url : 'jsp-database-connect-ConnectEdit?id='+treeNode.id,
			params : {
				object : treeNode,
				callback : function() {
					$.hh.tree.refresh('tree');
				}
			}
		});
	}
	function doDelete(treeNode) {
		$.hh.tree.deleteData({
			pageid : 'tree',
			action : 'database-Connect-deleteTreeByIds',
			id : treeNode.id,
			callback : function(result) {
				if (result.success!=false) {
					$('#centerdiv').disabled('请选择要编辑的树节点或添加新的数据！');
				}
			}
		});
	}
	function showBtn(treeid,node){
		return node.iconSkin=='conn' && node.id!='system';
	}
	function treeClick(treeNode) {
		if(treeNode.iconSkin=='conn'){
			selectConnNode=treeNode;
			$('#connNameDiv').html(treeNode.name);
		}
	}
	function doExec(){
		if(selectConnNode){
			var sqlStr = $('#execSql').val();
			var param = {
					type: selectConnNode.type,
					connect:selectConnNode.connect,
					user:selectConnNode.user,
					password:selectConnNode.password,
					sql:sqlStr
				};
			if(sqlStr.indexOf('update ')>-1 || sqlStr.indexOf('delete ')>-1){
				Request.request('database-Connect-execConn', {
					data : param,
					callback : function(result) {
						if(result.returnType=='error'){
							$('#msgDiv').html(result.msg);
						}else{
							$('#msgDiv').html('执行成功！受影响：'+result.count);
						}
					}
				});
			}else{
				$('#pagelist').loadData({
					column : [],
					params : param,
					url : 'database-Connect-queryConn'
				});
			}
		}else{
			Dialog.infomsg('请选择数据源！');
		}
	}
	function init(){
	}
</script>
</head>
<body>
	<div xtype="border_layout">
		<div config="render : 'west' ,width:250">
			<div xtype="toolbar" config="type:'head'">
				<span xtype="button" config="onClick: doAdd ,text:'添加连接'"></span> <span
					xtype="button"
					config="onClick: $.hh.tree.doUp , params:{treeid:'tree',action:'database-Connect-order'}  , textHidden : true,text:'上移' ,icon : 'hh_up' "></span>
				<span xtype="button"
					config="onClick: $.hh.tree.doDown , params:{treeid:'tree',action:'database-Connect-order'} , textHidden : true,text:'下移' ,icon : 'hh_down' "></span>
				<span xtype="button"
					config="onClick : $.hh.tree.refresh,text : '刷新' ,params: 'tree'  "></span>
			</div>
			<span xtype="tree"
				config=" showRenameBtn : showBtn , showRemoveBtn : showBtn ,id:'tree', url:'database-Connect-queryTreeList' ,remove: doDelete ,edit : doEdit , onClick : treeClick  "></span>
		</div>
		<div style="overflow: visible;" id=centerdiv>
			<div xtype="border_layout">
				<div config="render : 'north' ,width:135" >
				<textarea style="width:99%;height:70%;" id="execSql"></textarea>
				<div xtype="toolbar" config="type:'head'">
					<span xtype="button" config="onClick: doExec ,text:'执行'"></span>
					<span id="connNameDiv" style="margin-top:5px;">请选择数据库</span>
				</div>
				</div>
				<div style="overflow: auto;" id=centerdiv>
					<div id="msgDiv" style="padding:5px;text-align:center;"></div>
					<div id="pagelist" xtype="pagelist"
						config=" url: '' ,column : []">
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>