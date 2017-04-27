<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.BaseSystemUtil"%>
<%=BaseSystemUtil.getBaseDoctype()%>
<html>
<head>
<title>请选择</title>
<%=BaseSystemUtil.getBaseJs("layout","ztree", "ztree_check","ztree_hide")%>
<script type="text/javascript">
	var params = $.hh.getIframeParams();
	var span = params.span;
	var hiddenField = params.hiddenField;
	var baseValue = hiddenField.val();
	var textField = params.textField;
	var url = params.url;
	var noCheckParent = params.config.noCheckParent;
	var noCheckLeaf = params.config.noCheckLeaf;
	params.params = params.params || {};
	$.extend(params.params, span.data('params'));

	var selectType = params.config.many == true ? 'many' : 'radio';

	var selectUserList = [];
	
	var selectKey =  params.config.selectKey || 'id';
	var selectValue =params.config.selectValue || 'text';

	var check = {
		enable : true,
		chkStyle : "radio",
		radioType : 'all'
	};
	if (selectType == 'many') {
		check = {
			enable : true,
			chkboxType : {
				"Y" : "",
				"N" : ""
			}
		}
	}

	var treeconfig = {
		id : 'tree',
		check : check,
		url : url,
		onDblClick : onDblClick,
		params : params.params,
		onCheck : onCheck,
		dataLoad : dataLoad
	};
	function onDblClick(treeNode) {
		//ok();
	}
	function dataLoad(treeNode) {
		if (treeNode == null) {
			var zTree = $.hh.tree.getTree('tree')
			checkNode(zTree, zTree.getNodes());
		} else {
			var children = treeNode.children;
			checkNode($.hh.tree.getTree('tree'), children);
		}
	}

	function checkNode(zTree, treeNodes) {
		if (treeNodes) {
			for (var j = 0; j < treeNodes.length; j++) {
				var treeNode = treeNodes[j];
				if (treeNode) {
					if(treeNode.nocheck==true){
						//zTree.hideNode(treeNode);
						//continue;
					}
					if (noCheckParent == true) {
						if (treeNode.leaf == 0) {
							treeNode.nocheck = true;
							zTree.updateNode(treeNode);
						}
					}
					if(noCheckLeaf==true){
						if (treeNode.leaf != 0) {
							treeNode.nocheck = true;
							zTree.updateNode(treeNode);
						}
					}
					var children = treeNode.children;
					checkNode(zTree, children);
				}
				if (isInSelectUserList(treeNode[selectKey])) {
					zTree.checkNode(treeNode, true, true);
				}
			}
		}
	}
	function onCheck(treeNode) {
		if (treeNode.checked) {
			addSelectUserList(treeNode);
		} else {
			removeSelectUserList(treeNode[selectKey]);
		}
	}
	function ok() {
		var id = "";
		var text = "";
		if (selectUserList.length > 0) {
			for (var i = 0; i < selectUserList.length; i++) {
				var selectUser = selectUserList[i];
				id += selectUser[selectKey] + ",";
				text += selectUser[selectValue] + ",";
			}
			if (id != "") {
				id = id.substr(0, id.length - 1);
				text = text.substr(0, text.length - 1);
			}
		}
		hiddenField.val(id);
		textField.val(text);
		if (selectType == 'radio') {
			params.onChange(selectUserList[0], baseValue != id);
		} else {
			params.onChange(selectUserList, baseValue != id);
		}
		Dialog.close();
		textField.focus();
	}

	var ids = hiddenField.val();
	var texts = textField.val();
	var idsarr = ids.split(",");
	var textsarr = texts.split(",");
	for (var i = 0; i < idsarr.length; i++) {
		if (idsarr[i] != null && idsarr[i] != '' && idsarr[i] != 'root') {
			selectUserList.push({
				id : idsarr[i],
				text : textsarr[i]
			});
		}
	}

	function init() {
		renderSpanUserHtml();
	}

	function renderSpanUserHtml() {
		var html = "<div style='padding:3px;'><table  xtype=form>";
		
		for (var i = 0; i < selectUserList.length; i++) {
			var selectUser = selectUserList[i];
			html += '<tr ><td><img style="cursor:pointer;" src="/hhcommon/images/extjsico/delete2.gif" onclick="unSelect(\''
					+ selectUser[selectKey] + '\')" />' + selectUser[selectValue]+'</td></tr>';
		}
		html+='</table></div>'
		
		$('#userspan').empty();
		$('#userspan').append($(html));
		$('#userspan').find('table').render();
	}
	function removeSelectUserList(id) {
		var userList = [];
		for (var i = 0; i < selectUserList.length; i++) {
			var selectUser = selectUserList[i];
			if (selectUser[selectKey] != id) {
				userList.push(selectUser);
			}
		}
		selectUserList = userList;
		renderSpanUserHtml();
	}

	function isInSelectUserList(id) {
		var userList = [];
		for (var i = 0; i < selectUserList.length; i++) {
			var selectUser = selectUserList[i];
			if (selectUser[selectKey] == id) {
				return true;
			}
		}
		return false;
	}
	function unSelect(id) {
		//pagelistobject.unSelectRow('id', id);
		removeSelectUserList(id);
	}
	function addSelectUserList(object) {
		if (selectType == 'radio') {
			selectUserList = [ object ];
			renderSpanUserHtml();
		} else {
			var isin = isInSelectUserList(object[selectKey]);
			if (isin == false) {
				selectUserList.push(object);
				renderSpanUserHtml();
			}
		}
	}
	function querytree(){
		var param_ = {text:$('#span_treeText').getValue()};
		$.extend(param_,treeconfig.params);
		$('#span_tree').loadData({
			params : param_
		});
	}
</script>
</head>
<body xtype="border_layout">
	<div>
		<div xtype="toolbar" config="type:'head'">
		<table style="font-size: 12" width=100%  cellspacing="0" cellpadding="0" ><tr>
		<td >
		<span xtype="text" config=" name : 'treeText' ,enter: querytree"></span>
		</td>
		<td width="40px" style="text-align:right;">
		<span xtype="button" config=" icon :'hh_img_query' , onClick : querytree "></span>
		</td><tr></table>
		</div>
		<span xtype="tree" configVar="treeconfig"></span>
	</div>
	<div config="render : 'east' ,width : 130 ,open:0 " >
		<div style="padding-top:5px;text-align:center;"><span xtype="button" config="text:'确定' , icon :'hh_img_green' , onClick : ok "></span></div>
		<span id="userspan"></span>
	</div>
</body>
</html>