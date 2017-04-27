<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.BaseSystemUtil"%>
<%=BaseSystemUtil.getBaseDoctype()%>
<html>
<head>
<title></title>
<link rel="stylesheet" type="text/css"
	href="/hhcommon/opensource/jquery/hhjqueryui.css" />
</head>
<%
%>
<body>
	<table cellpadding="0" cellspacing="0" border="0" width="100%"
		height="100%">
		<tr>
			<td align="center" valign="middle">
				<table>
					<tr>
						<td><div class="hh_blue"
								style="border-radius: 8px; -moz-border-radius: 8px; margin-top: 2px; padding: 10px 15px;">
								<table>
									<tr>
										<td width="50px"><div class="hh_img_blue"></div></td>
										<td style="font-weight: bold; font-size: 25px;"><%=request.getParameter("text")%>
										</td>
									</tr>
								</table>
							</div></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>