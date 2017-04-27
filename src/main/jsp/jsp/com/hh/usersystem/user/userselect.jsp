<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.BaseSystemUtil"%>
<%=BaseSystemUtil.getBaseDoctype()%>
<html>
<head>
<title>用户选择</title>
<%=BaseSystemUtil.getBaseJs("layout", "ztree")%>
<script type="text/javascript"
	src="/hhcommon/opensource/jquery/layout/jquery.layout.resizePaneAccordions-latest.js"></script>
<script type="text/javascript">
	var params = $.hh.getIframeParams();
	
	var selectTypeRadio = params.config.many==true ? false : true;
	var userIds = params.config.userIds ||'';

	var hiddenField = params.hiddenField || $('<input />');
	var textField = params.textField|| $('<input />');
	var selectUserList = [];
	var pagelistobject = null;
	
	var baseSelectType = (userIds==null || userIds=='') ?  1:3;
	
	var pageconfig = {
		toolbarList : [],
		itemChange : itemChange,
		dataLoad : dataLoad,
		toolbarType : 'min',
		radio : selectTypeRadio,
		url : $.hh.property.userQuery || 'usersystem-user-queryPagingData',
		params : {
			nxb : 2,
			selectType : baseSelectType,
			currOrg:1,
			ids:userIds
		},
		column : [ {
			name : 'text',
			text : '名称',
			align : 'left',
			width : 200,
			render : function(value, data) {
				var orgIdText = (data.orgIdText  || '');
				var deptIdText = (data.deptIdText  || '');
				var jobIdText = (data.jobIdText  || '');
				
				if(orgIdText && (deptIdText || jobIdText)){
					orgIdText= orgIdText+'、';
				}
				if(deptIdText && jobIdText){
					deptIdText= deptIdText+'、';
				}
				
				var org = orgIdText+deptIdText+jobIdText;
				if(org){
					org='【'+org+'】';
				}
				return '<img src="/hhcommon/images/myimage/sex/'
						+ (data.nxb == 1 ? 'nan' : 'nv')
						+ '.png" />&nbsp;' + value+org;
			}
		} ]
	};

	var ids = hiddenField.val();
	var texts = textField.val();
	var idsarr = ids.split(",");
	var textsarr = texts.split(",");
	for (var i = 0; i < idsarr.length; i++) {
		if (idsarr[i] != null && idsarr[i] != '') {
			selectUserList.push({
				id : idsarr[i],
				text : textsarr[i]
			});
		}
	}

	function dataLoad() {
		pagelistobject.selectRow('id', selectUserList);
	}
	function itemChange(data) {
		var checked = data.checked;
		var rowdata = data.rowdata;
		if (data.clickDom == 'checkbox') {
			if (checked) {
				addSelectUserList(rowdata);
			} else {
				removeSelectUserList(rowdata.id);
			}
		} else {
			if (checked) {
				selectUserList = [ rowdata ];
				renderSpanUserHtml();
			} else {
				removeSelectUserList(rowdata.id);
			}
		}
	}

	function init() {
		pagelistobject = $('#pagelist').getWidget();
		renderSpanUserHtml();
	}

	function ok() {
		if (selectUserList.length > 0) {
			var id = "";
			var text = "";
			for (var i = 0; i < selectUserList.length; i++) {
				var selectUser = selectUserList[i];
				id += selectUser.id + ",";
				text += selectUser.text + ",";
			}
			if (id != "") {
				id = id.substr(0, id.length - 1);
				text = text.substr(0, text.length - 1);
			}
			hiddenField.val(id);
			textField.val(text);
			if(params.onChange){
				params.onChange({
					'id' : id,
					'text' : text
				});
			}
			textField.focus();
			Dialog.close();
		} else {
			Dialog.infomsg("请选中一条数据！！");
		}
	}

	function renderSpanUserHtml() {
		var html = "<div style='padding:3px;'><table  xtype=form>";
		
		for (var i = 0; i < selectUserList.length; i++) {
			var selectUser = selectUserList[i];
			html += '<tr ><td><img style="cursor:pointer;" src="/hhcommon/images/extjsico/delete2.gif" onclick="unSelect(\''
					+ selectUser.id + '\')" />' + selectUser.text+'</td></tr>';
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
			if (selectUser.id != id) {
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
			if (selectUser.id == id) {
				return true;
			}
		}
		return false;
	}
	function unSelect(id) {
		pagelistobject.unSelectRow('id', id);
		removeSelectUserList(id);
	}
	function addSelectUserList(object) {
		if (pageconfig.radio) {
			selectUserList = [ {
				id : object.id,
				text : object.text
			} ];
			renderSpanUserHtml();
		} else {

			var isin = isInSelectUserList(object.id);
			if (isin == false) {
				selectUserList.push({
					id : object.id,
					text : object.text
				});
				renderSpanUserHtml();
			}
		}
	}


	function orgTreeClick(treeNode) {
		$('#span_selectType').setValue(3);
		$('#pagelist').loadData({
			params : {
				orgs : treeNode.id,
				nxb : 2,
				selectType : 3,
				currOrg:1,
				ids:userIds
			}
		});
	}

	function roleitemclick(data) {
		$('#span_selectType').setValue(3);
		$('#pagelist').loadData({
			params : {
				roles : data.rowdata.id,
				nxb : 2,
				selectType : 3,
				currOrg:1,
				ids:userIds
			}
		});
	}

	function groupitemclick(data) {
		$('#span_selectType').setValue(3);
		if(data.type=='sysgroup'){
			$('#pagelist').loadData({
				params : {
					groups : data.id,
					nxb : 2,
					selectType : 3,
					currOrg:1,
					ids:userIds
				}
			});
		}else if(data.type=='usgroup'){
			$('#pagelist').loadData({
				params : {
					usgroups : data.id,
					nxb : 2,
					selectType : 3,
					currOrg:1,
					ids:userIds
				}
			});
		}
		
	}

	function query() {
		$('#span_selectType').setValue(3);
		$('#pagelist').loadData({
			params : {
				text : $('#span_text').getValue(),
				nxb : 2,
				selectType : $('#span_selectType').getValue(),
				currOrg:1,
				ids:userIds
			}
		});
	}
	
	function selectTypeChange(){
		$('#pagelist').loadData({
			params : {
				text : $('#span_text').getValue(),
				nxb : 2,
				selectType : $('#span_selectType').getValue(),
				currOrg:1,
				ids:userIds
			}
		});
	}

	var rolerender = false;
	var grouprender = false;

	function activate(ui) {
		var newPanel = ui.newPanel;
		var id = newPanel.attr('id');
		if (id == 'rloediv' && rolerender == false) {
			rolerender = true;
			$('#role_pagelist').render();
		} else if (id == 'groupdiv' && grouprender == false) {
			grouprender = true;
			$('#group_pagelist').render();
		}
	}

	var orgConfig = {
		id : 'orgTree',
		url : $.hh.property.orgQuery || 'usersystem-Org-queryStateTreeList',
		onClick : orgTreeClick
	}
	var roleConfig = {
		params : {
			state : 1
		},
		url : $.hh.property.roleQuery || 'usersystem-role-queryPagingData',
		toolbarType : 'min',
		radio : true,
		itemClick : roleitemclick,
		column : [ {
			name : 'text',
			width : 200,
			text : '名称'
		} ],
		title : false,
		render : false
	}
	var groupConfig = {
		id : 'groupTree',
		url : $.hh.property.groupQuery || 'usersystem-Group-queryListAndUserGroup?andUser=0',
		onClick : groupitemclick,
		render : false
	}
	
	function setHeight(height){
		$('#pagelist_main').height(height-60);
	}
</script>
</head>
<body xtype="border_layout">
	<div config="render : 'west' ,width : 270 " style="overflow-y: hidden">
		<div xtype="accordion" config="activate : activate">
			<h3>机构</h3>
			<div>
				<span xtype="tree" configVar=" orgConfig"></span>
			</div>
			<h3>角色</h3>
			<div id="rloediv">
				<div id="role_pagelist" xtype="pagelist" configVar="roleConfig">
				</div>
			</div>
			<h3>用户组</h3>
			<div id="groupdiv">
				<span id="group_pagelist" xtype="tree" configVar="groupConfig"></span>
			</div>
		</div>
	</div>
	<div style="overflow-y: hidden">
		<div style="text-align:center;"><span xtype="radio"
						config="name: 'selectType' ,onChange:selectTypeChange,value : baseSelectType, data :[{id:1,text:'本部门'},{id:2,text:'本机构'},{id:3,text:'所有'}]"></span></div>
		<table><tr><td>
			<span xtype="text" config=" name : 'text' ,width:240 ,enter : query"></span>
			<span xtype="button" config=" icon :'hh_img_query' , onClick : query "></span>
			
		</td></tr></table>
		<div id="pagelist_main" style="overflow: auto;">
		<div id="pagelist" xtype="pagelist" configVar="pageconfig"></div>
		</div>
	</div>
	<div config="render : 'east' ,width : 130 ,open:0 " >
		<div style="padding-top:5px;text-align:center;"><span xtype="button" config="text:'确定' , icon :'hh_img_green' , onClick : ok "></span></div>
		<span id="userspan"></span>
	</div>
</body>
</html>