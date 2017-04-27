<%@page import="com.hh.system.util.Convert"%>
<%@page import="com.hh.system.service.impl.BeanFactoryHelper"%>
<%@page import="com.hh.hibernate.dao.inf.IHibernateDAO"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.BaseSystemUtil"%>
<%=BaseSystemUtil.getBaseDoctype()%>
<html>
<head>
<title>用户选择</title>
<%=BaseSystemUtil.getBaseJs("layout", "ztree")%>
<% 

	IHibernateDAO dao = BeanFactoryHelper.getBean(IHibernateDAO.class);
	
	List<Map<String,Object>> roleMapList = Convert.dTox(dao.queryListBySql("select * from us_role ", new HashMap<String,Object>()));

	StringBuffer selectStr = new StringBuffer();
	selectStr.append("<select  style=\"width:80px;\">");
	selectStr.append("<option value=\"\" >选择角色</option>");
	for(Map<String,Object> map : roleMapList){
		selectStr.append("<option value=\""+map.get("id")+"\" >"+map.get("text")+"</option>");
	}
	selectStr.append("</select>");
	
%>
<script type="text/javascript"
	src="/hhcommon/opensource/jquery/layout/jquery.layout.resizePaneAccordions-latest.js"></script>
<script type="text/javascript">
	var params = $.hh.getIframeParams();
	var width = 750;
	var selectTypeRadio = false;

	var textField = params.textField;
	var selectUserList = [];
	var pagelistobject = null;
	var pageconfig = {
		toolbarList : [],
		render : false,
		toolbarType : 'min',
		radio : selectTypeRadio,
		url : $.hh.property.userQuery || 'usersystem-user-queryPagingData',
		params : {
			nxb : 2,
			selectType : 3,
			currOrg : 1
		},
		column : [ {
			name : 'text',
			text : '名称',
			align : 'left',
			render : function(value, data) {
				var orgIdText = (data.orgIdText || '');
				var deptIdText = (data.deptIdText || '');
				var jobIdText = (data.jobIdText || '');

				if (orgIdText && (deptIdText || jobIdText)) {
					orgIdText = orgIdText + '、';
				}
				if (deptIdText && jobIdText) {
					deptIdText = deptIdText + '、';
				}

				var org = orgIdText + deptIdText + jobIdText;
				if (org) {
					org = '【' + org + '】';
				}
				return '<img src="/hhcommon/images/myimage/sex/'
						+ (data.nxb == 1 ? 'nan' : 'nv') + '.png" />&nbsp;'
						+ value + org;
			}
		},{
			name : 'id',
			width : 40,
			text : '操作',
			render:function(value,data){
				return '<a href="javascript:selectValue(\''+data.id+'\',\''+data.text+'\',\'user\');">选择</a>';
			}
		} ]
	};

	function init() {
		pagelistobject = $('#pagelist').getWidget();

		var texts = textField.val();
		if(texts){
			selectUserList=$.hh.toObject(texts);
		}

		renderSpanUserHtml();
	}

	function ok() {
		if (selectUserList.length > 0) {
			for(var i =0;i<selectUserList.length;i++){
				selectUserList[i].cond = $('#select_'+selectUserList[i].id).val();
				
				var role_select = $('#role_select_'+selectUserList[i].id);
				
				selectUserList[i].roleId = role_select.val();
				if(selectUserList[i].roleId){
					selectUserList[i].roleText = role_select.find('[value='+selectUserList[i].roleId+']').text();
				}else{
					selectUserList[i].roleText = '';
				}
				
			}
			
			
			textField.val($.hh.toString(selectUserList));
			if(params.onChange){
				params.onChange(textField.val());
			}
			Dialog.close();
			textField.focus();
		} else {
			Dialog.infomsg("请选中一条数据！！");
		}
	}

	function renderSpanUserHtml() {
		var html = "<div style='padding:3px;'><table  xtype=form>";

		for (var i = 0; i < selectUserList.length; i++) {
			var selectUser = selectUserList[i];
			var type = '';
			if (selectUser.type == 'dept') {
				type = '部门';
			} else if (selectUser.type == 'org') {
				type = '机构';
			} else if (selectUser.type == 'role') {
				type = '角色';
			}else if (selectUser.type == 'sysgroup') {
				type = '系统组';
			}else if (selectUser.type == 'usgroup') {
				type = '自定义组';
			}else if (selectUser.type == 'user') {
				type = '用户';
			}else if (selectUser.type == 'custom') {
				type = '自定义';
			}
			
			var and_selected = '';
			var or_selected = '';
			
			if(selectUser.cond=='or'){
				or_selected=' selected=true ';
			}else if(selectUser.cond=='and'){
				and_selected=' selected=true ';
			}
			
			var select = "<select id='select_"+selectUser.id+"' style='width:45px;'>"+
			"<option value='and' "+and_selected+">和</option>"+
			"<option value='or' "+or_selected+">或</option>"
			+"</select>";
			
			var roleSelect = $('<%=selectStr%>');
			
			roleSelect.attr('id','role_select_'+selectUser.id)
			
			roleSelect.find('[value='+selectUser.roleId+']').attr('selected',true);
			
			var roleSelectHtml =roleSelect[0].outerHTML;
			
			if(selectUser.type == 'dept' || selectUser.type == 'org'|| selectUser.id == 'currOrg'|| selectUser.id == 'currDept'){
				
			}else{
				roleSelectHtml='';
			}
			
			html += '<tr ><td>'
					+'<img style="cursor:pointer;" src="/hhcommon/opensource/jquery/image/16/up.gif" onclick="up(\''+selectUser.id+'\')" >'
					+'<img style="cursor:pointer;" src="/hhcommon/opensource/jquery/image/16/down.gif" onclick="down(\''+selectUser.id+'\')" >'
					+'<img style="cursor:pointer;" src="/hhcommon/images/extjsico/delete2.gif" onclick="unSelect(\''
					+ selectUser.id
					+ '\')" />'
					+select
					+ '【'
					+ type
					+ '】'
					+ selectUser.text + roleSelectHtml + '</td></tr>';
		}
		html += '</table></div>'

		$('#userspan').empty();
		$('#userspan').append($(html));
		$('#userspan').find('table').render();
	}
	
	function up(id){
		selectUserList = upArray(selectUserList,id);
		renderSpanUserHtml();
	}
	
	function down(id){
		selectUserList = downArray(selectUserList,id);
		renderSpanUserHtml();
	}
	
	function upArray(data, removeid) {
		var resultData = [];
		var j = 0;
		for (var i = 0; i < data.length; i++) {
			if (data[i].id != removeid || j == 0) {
				resultData[j] = data[i];
				j++;
			} else {
				var old = resultData[j - 1];
				resultData[j - 1] = data[i];
				resultData.push(old);
				j++;
			}
		}
		return resultData;
	}
	function downArray(data, removeid) {
		var resultData = [];
		var j = 0;
		var asdf = null;
		for (var i = 0; i < data.length; i++) {
			if (data[i].id != removeid || data.length == i + 1) {
				if (j == asdf) {
					resultData[j - 1] = data[i];
				} else {
					resultData[j] = data[i];
				}
				j++;
			} else {
				asdf = j + 1;
				resultData[j + 1] = data[i];
				j++;
			}
		}
		return resultData;
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
		var isin = isInSelectUserList(object.id);
		if (isin == false) {
			selectUserList.push({
				id : object.id,
				text : object.text,
				type : object.type,
				cond : object.cond
			});
			renderSpanUserHtml();
		}
	}

	function loadUserList(result) {
		var ids = $.hh.objsToStr(result);
		if (ids == '') {
			ids = '0';
		}
		$('#pagelist').loadData({
			params : {
				ids : ids,
				nxb : 2,
				currOrg : 1
			}
		});
	}



	function query() {
		$('#span_selectType').setValue(3);
		$('#pagelist').loadData({
			params : {
				text : $('#span_text').getValue(),
				nxb : 2,
				selectType : $('#span_selectType').getValue(),
				currOrg : 1
			}
		});
	}

	var rolerender = false;
	var grouprender = false;
	var userrender = false;
	var orgrender = false;

	function activate(ui) {
		var newPanel = ui.newPanel;
		var id = newPanel.attr('id');
		if (id == 'rloediv' && rolerender == false) {
			rolerender = true;
			$('#role_pagelist').render();
		} else if (id == 'groupdiv' && grouprender == false) {
			grouprender = true;
			$('#group_pagelist').render();
		}else if (id == 'userdiv' && userrender == false) {
			userrender = true;
			$('#pagelist').render();
		}else if (id == 'orgsdiv' && orgrender == false) {
			orgrender = true;
			$('#org_tree').render();
		}
		
		
	}
	
	function selectValue(id,text,type){
		addSelectUserList({
			id : id,
			text : text,
			type : type,
			cond : 'and'
		})
	}
	
	var currConfig ={
			id : 'currTree',
			data : [
				{ id:'currOrg',  name:'登录人所在机构'},
				{ id:'currDept',  name:'登录人所在部门'},
				{ id:'currManager',  name:'登录人主管领导'},
				{ id:'currOrgManager',  name:'登录人所在机构主管领导'},
				{ id:'currDeptManager',  name:'登录人所在部门主管领导'},
				{ id:'currManagerUser',  name:'登录人主管的人员'}
				
			],
			addDiyDom : function(treeId, treeNode) {
				var aObj = $("#" + treeNode.tId + '_a');
				var selectA = $('<a href="javascript:void(0);">选择</a>');

				aObj.after(selectA);
				selectA.click(function() {
					addSelectUserList({
						id : treeNode.id,
						text : treeNode.name,
						type : 'custom',
						cond : 'and'
					})
				});
			}
	}

	var orgConfig = {
		id : 'orgTree',
		render:false,
		url : $.hh.property.orgQuery || 'usersystem-Org-queryStateTreeList',
		addDiyDom : function(treeId, treeNode) {
			var aObj = $("#" + treeNode.tId + '_a');
			var selectA = $('<a href="javascript:void(0);">选择</a>');

			aObj.after(selectA);
			selectA.click(function() {
				addSelectUserList({
					id : treeNode.id,
					text : treeNode.text,
					type : treeNode.iconSkin,
					cond : 'and'
				})
			});
		}
	}
	var roleConfig = {
		params : {
			state : 1
		},
		url : $.hh.property.roleQuery || 'usersystem-role-queryPagingData',
		toolbarType : 'min',
		radio : true,
		column : [ {
			name : 'text',
			text : '名称'
		},{
			name : 'id',
			width : 40,
			text : '操作',
			render:function(value,data){
				return '<a href="javascript:selectValue(\''+data.id+'\',\''+data.text+'\',\'role\');">选择</a>';
			}
		}  ],
		title : false,
		render : false
	}
	var groupConfig = {
		id : 'groupTree',
		url : $.hh.property.groupQuery
				|| 'usersystem-Group-queryListAndUserGroup?andUser=0',
		render : false,
		addDiyDom : function(treeId, treeNode) {
			var aObj = $("#" + treeNode.tId + '_a');
			var selectA = $('<a href="javascript:void(0);">选择</a>');

			aObj.after(selectA);
			selectA.click(function() {
				if(treeNode.type!='zdy'){
					addSelectUserList({
						id : treeNode.id,
						text : treeNode.text,
						type : treeNode.type,
						cond : 'and'
					})
				}
				
			});
		}
	}

	function setHeight(height) {
	}
</script>
</head>
<body xtype="border_layout">
	<div config="render : 'west' ,width : 350 " style="overflow-y: hidden">
		<div xtype="accordion" config="activate : activate">
			<h3>当前</h3>
			<div>
				<span xtype="tree" configVar=" currConfig"></span>
			</div>
			<h3>机构</h3>
			<div id="orgsdiv">
				<span id="org_tree" xtype="tree" configVar=" orgConfig"></span>
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
			<h3>用户</h3>
			<div id="userdiv">
				<table>
					<tr>
						<td><span xtype="text"
							config=" name : 'text' ,width:270 ,enter : query"></span> <span
							xtype="button" config=" icon :'hh_img_query' , onClick : query "></span>

						</td>
					</tr>
				</table>
				<div id="pagelist_main" style="overflow: auto;">
					<div id="pagelist" xtype="pagelist" configVar="pageconfig"></div>
				</div>
			</div>
		</div>
	</div>
	<div style="overflow-y: hidden">
		<div style="padding-top: 5px; text-align: center;">
			<span xtype="button"
				config="text:'确定' , icon :'hh_img_green' , onClick : ok "></span>
		</div>
		<span id="userspan"></span>
	</div>
</body>
</html>