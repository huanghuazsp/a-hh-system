<%@page import="com.hh.system.util.request.Request"%>
<%@page import="com.hh.system.util.StaticVar"%>
<%@page import="com.hh.system.util.Convert"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.BaseSystemUtil"%>
<%=BaseSystemUtil.getBaseDoctype()%>
<html>
<head>
<title>浏览</title>
<%=BaseSystemUtil.getBaseJs()%>
<%
	String type = Convert.toString(request.getParameter("type"));
	String id = Convert.toString(request.getParameter("id"));
%>
<script type="text/javascript">
	var params = $.hh.getIframeParams();
	var type = '<%=type%>';
	function init() {
		
			Request.request('system-SystemFile-findObjectById',{
				data:{
					'id':'<%=id%>'
				},
				defaultMsg : false,
				callback:function(data){
					loadPage(data.fileType,data);
				}
			});
		
	}
	
	function loadPage(type,data){
		type = type.toLowerCase();
		$('#hh_main_content').empty();
		var contextPath = '<%=Request.getServerPathAll()+StaticVar.filebasefolder%>';
		if((type=='doc' || type=='docx' || type=='xls' || type=='xlsx') && $.hh.browser.isIE){
			var iframe = $('<iframe  width=100%  frameborder=0 src=""  ></iframe>');
			$('#hh_main_content').append(iframe);
			iframe.attr('src','/hhcommon/opensource/weboffice7/office.jsp?fileId=<%=id%>&contextPath='+contextPath+'&path='+data.path);
		}else if((type=='pdf') && $.hh.browser.isIE){
			var iframe = $('<iframe  width=100%  frameborder=0 src=""  ></iframe>');
			$('#hh_main_content').append(iframe);
			iframe.attr('src','/hhcommon/opensource/TrustedPDF/pdf.jsp?fileId=<%=id%>&contextPath='+contextPath+'&path='+data.path);
		}else if('gif|jpg|jpeg|png|bmp'.indexOf(type)>-1){
			$('#hh_main_content').append('<img src="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+"/"+StaticVar.filebasefolder%>'+data.path+'" />');
		}else if(type=='mp4' || type=='flv'|| type=='swf'|| type=='mkv'|| type=='avi'|| type=='rm'|| type=='rmvb'
			 || type=='mpeg'|| type=='mpg'|| type=='ogg'|| type=='ogv'|| type=='mov'|| type=='wmv'
				 || type=='webm'|| type=='mp3'|| type=='wav'|| type=='mid' ){
			var iframe = $('<iframe  width=100%  frameborder=0 src=""  ></iframe>');
			$('#hh_main_content').append(iframe);
			iframe.attr('src','/hhcommon/opensource/video/video.jsp?fileType='+type+'&fileId=<%=id%>&contextPath='+contextPath+'&path='+data.path);
		}else {
			window.location.href = contextPath+data.path;
		}
		
		$.hh.iframeLoad($('iframe'),function(){
			$('iframe').height($('#hh_main_content').height()-3);
		});
	}
	function setHeight(hh){
		$('iframe').height(hh-3);
	}
</script>
</head>
<body>
	<div xtype="hh_main_content" id="hh_main_content" style="text-align:center;">
	</div>
</body>
</html>