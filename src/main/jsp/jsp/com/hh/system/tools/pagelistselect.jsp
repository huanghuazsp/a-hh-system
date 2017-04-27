<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.BaseSystemUtil"%>
<%=BaseSystemUtil.getBaseDoctype()%>
<html>
<head>
<title>请选择</title>
<%=BaseSystemUtil.getBaseJs("layout")%>
<script type="text/javascript">
	var params = $.hh.getIframeParams();
	var hiddenField = params.hiddenField;
	var textField = params.textField;
	var selectUserList = [];
	var pagelistobject = null;
	var pageconfig = params.config.pageconfig;
	pageconfig.itemChange=itemChange;
	pageconfig.dataLoad=dataLoad;
	pageconfig.radio = params.config.many==true?false:true;
	
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
		if(data.clickDom=='checkbox'){
			if (checked) {
				addSelectUserList(rowdata);
			} else {
				removeSelectUserList(rowdata.id);
			}
		}else{
			if (checked) {
				selectUserList=[rowdata];
				renderSpanUserHtml();
			}else {
				removeSelectUserList(rowdata.id);
			}
		}
	}

	function init() {
		pagelistobject = $('#pagelist').getWidget();
		renderSpanUserHtml();
		
		var queryHtml = pageconfig.queryHtml;
		if(queryHtml){
			var queryTable = $(queryHtml);
			queryTable.renderAll();
			$('#queryDiv').append(queryTable);
		}
	}
	function doQuery() {
		$('#pagelist').loadData({
			params : $('#queryForm').getValue()
		});
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
			
			if(params.config.onChange){
				
				if(pageconfig.radio){
					params.config.onChange(selectUserList[0]);
				}else{
					params.config.onChange(selectUserList);
				}
				
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
</script>
</head>
<body xtype="border_layout">
		<div>
			<div id="queryDiv"></div>
			<div id="pagelist" xtype="pagelist" configVar="pageconfig"></div>
		</div>
		<div config="render : 'east' ,width : 130 ,open:0 " >
			<div style="padding-top:5px;text-align:center;"><span xtype="button" config="text:'确定' , icon :'hh_img_green' , onClick : ok "></span></div>
			<span id="userspan"></span>
		</div>
</body>
</html>