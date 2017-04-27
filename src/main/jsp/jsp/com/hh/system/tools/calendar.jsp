<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>日历</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<style type='text/css'>
/*<![CDATA[*/
@import "/hhcommon/opensource/calendar/inc/unical.css";
/*]]>*/
</style>
<script type="text/javascript" src="/hhcommon/opensource/jquery/web/jquery-1.9.1.js"></script> 

<script type="text/javascript"
	src="/hhcommon/opensource/calendar/inc/unical.js"></script>

<style type="text/css">
.myButton {
	background-color:#45b8c7;
	-moz-border-radius:28px;
	-webkit-border-radius:28px;
	border-radius:28px;
	border:1px solid #198eab;
	display:inline-block;
	cursor:pointer;
	color:#ffffff;
	font-family:Arial;
	font-size:12px;
	padding:12px 24px;
	text-decoration:none;
	text-shadow:0px 1px 0px #2f6627;
}
.myButton:hover {
	background-color:#2aacbd;
}
.myButton:active {
	position:relative;
	top:1px;
}
</style>
<%
	String edit = request.getParameter("edit");
%>
<script type="text/javascript">
	function changeDate() {
		var sm = $('#sm').val()+'';
		if(sm.length==1){
			sm="0"+sm;
		}
		cld = new calendar(document.getElementById("sy").selectedIndex + 1900,document.getElementById("sm").selectedIndex);
		$.ajax({
			type : "POST",
			url : 'system-FeastDay-queryFeastDay',
			data : {
				'feastDay': $('#sy').val()+'-'+sm
			},
			error : function(resulttext) {
			},
			success : function(resulttext) {
				var dataList = eval(resulttext);
				var data1 = {};
				for(var i=0;i<dataList.length;i++){
					var data = dataList[i];
					data1[data.feastDay] = data.type;
				}
				render(data1);
			}
		});
	}
	
	function render(data111){
		$('.one,.aorange,.agreen').each(function(){
			if($(this).text() && $(this).text()!=' '){
				//if($(this).data('dateStr')==null){
					var d = $(this).html() - 1;
					if(cld[d]){
						var Month = cld[d].sMonth+"";
						var Day = cld[d].sDay+"";
						var Year = cld[d].sYear+"";
						if(Day.length==1){
							Day="0"+Day;
						}
						if(Month.length==1){
							Month="0"+Month;
						}
						var dateStr = Year +"-"+ Month + "-" +Day;
						$(this).data('dateStr',dateStr);
						if(data111[dateStr]){
							$(this).css('cursor', 'pointer');
							var lid = $(this).attr('id').replace('s','l');
							var btnTd= $('#'+lid);
							if(data111[dateStr]==1){
								btnTd.html('<font color=\"red\">[休]</font>'+btnTd.html());
							}else if(data111[dateStr]==2){
								btnTd.html('<font color=\"blue\">[班]</font>'+btnTd.html());
							}
							$(this).data('type',data111[dateStr]||0);
						}
						<%if("1".equals(edit)){%>
									$(this).unbind("click");
									$(this).click(function(){
										var lid = $(this).attr('id').replace('s','l');
										var tdd = $(this);
										var datatd= $('#'+lid);
										var type_ =  1;
										if($(this).data('type')==1){
											type_=2;
										}else if($(this).data('type')==2){
											type_=0;
										}
										var dateStr = $(this).data('dateStr');
										$.ajax({
											type : "POST",
											url : 'system-FeastDay-updateFeastDay',
											data : {
												'feastDay': dateStr,
												 type : type_
											},
											error : function(resulttext) {
											},
											success : function(resulttext) {
												var hhh = datatd.html().replace('<font color=\"red\">[休]</font>','').replace('<font color=\"blue\">[班]</font>','')
												if(type_==1){
													datatd.html('<font color=\"red\">[休]</font>'+hhh);
												}else if(type_==2){
													datatd.html('<font color=\"blue\">[班]</font>'+hhh);
												}else{
													datatd.html(hhh);
												}
												tdd.data('type',type_);
											}
										});
										
									});
						<%}%>
					}
					
				//}
				
			}
		});
	}
	
	function toList(){
		window.location.href = 'jsp-system-feastday-FeastDayList';
	}
</script>
</head>
<body onload="initial()">
	<div style="padding:10px;">
	<%if("1".equals(edit)){%>
	<a href="javascript:toList();" class="myButton">列表视图</a>
	<%}%>
	</div>
	<div style="margin: 0 auto; text-align: center; width:600px; padding-top: 10px;">
		<div id="date">
			<p>
				公历 <select onchange="changeCld()" id="sy"><option>1900</option>
					<option>1901</option>
					<option>1902</option>
					<option>1903</option>
					<option>1904</option>
					<option>1905</option>
					<option>1906</option>
					<option>1907</option>
					<option>1908</option>
					<option>1909</option>
					<option>1910</option>
					<option>1911</option>
					<option>1912</option>
					<option>1913</option>
					<option>1914</option>
					<option>1915</option>
					<option>1916</option>
					<option>1917</option>
					<option>1918</option>
					<option>1919</option>
					<option>1920</option>
					<option>1921</option>
					<option>1922</option>
					<option>1923</option>
					<option>1924</option>
					<option>1925</option>
					<option>1926</option>
					<option>1927</option>
					<option>1928</option>
					<option>1929</option>
					<option>1930</option>
					<option>1931</option>
					<option>1932</option>
					<option>1933</option>
					<option>1934</option>
					<option>1935</option>
					<option>1936</option>
					<option>1937</option>
					<option>1938</option>
					<option>1939</option>
					<option>1940</option>
					<option>1941</option>
					<option>1942</option>
					<option>1943</option>
					<option>1944</option>
					<option>1945</option>
					<option>1946</option>
					<option>1947</option>
					<option>1948</option>
					<option>1949</option>
					<option>1950</option>
					<option>1951</option>
					<option>1952</option>
					<option>1953</option>
					<option>1954</option>
					<option>1955</option>
					<option>1956</option>
					<option>1957</option>
					<option>1958</option>
					<option>1959</option>
					<option>1960</option>
					<option>1961</option>
					<option>1962</option>
					<option>1963</option>
					<option>1964</option>
					<option>1965</option>
					<option>1966</option>
					<option>1967</option>
					<option>1968</option>
					<option>1969</option>
					<option>1970</option>
					<option>1971</option>
					<option>1972</option>
					<option>1973</option>
					<option>1974</option>
					<option>1975</option>
					<option>1976</option>
					<option>1977</option>
					<option>1978</option>
					<option>1979</option>
					<option>1980</option>
					<option>1981</option>
					<option>1982</option>
					<option>1983</option>
					<option>1984</option>
					<option>1985</option>
					<option>1986</option>
					<option>1987</option>
					<option>1988</option>
					<option>1989</option>
					<option>1990</option>
					<option>1991</option>
					<option>1992</option>
					<option>1993</option>
					<option>1994</option>
					<option>1995</option>
					<option>1996</option>
					<option>1997</option>
					<option>1998</option>
					<option>1999</option>
					<option>2000</option>
					<option>2001</option>
					<option>2002</option>
					<option>2003</option>
					<option>2004</option>
					<option>2005</option>
					<option>2006</option>
					<option>2007</option>
					<option>2008</option>
					<option>2009</option>
					<option>2010</option>
					<option>2011</option>
					<option>2012</option>
					<option>2013</option>
					<option>2014</option>
					<option>2015</option>
					<option>2016</option>
					<option>2017</option>
					<option>2018</option>
					<option>2019</option>
					<option>2020</option>
					<option>2021</option>
					<option>2022</option>
					<option>2023</option>
					<option>2024</option>
					<option>2025</option>
					<option>2026</option>
					<option>2027</option>
					<option>2028</option>
					<option>2029</option>
					<option>2030</option>
					<option>2031</option>
					<option>2032</option>
					<option>2033</option>
					<option>2034</option>
					<option>2035</option>
					<option>2036</option>
					<option>2037</option>
					<option>2038</option>
					<option>2039</option>
					<option>2040</option>
					<option>2041</option>
					<option>2042</option>
					<option>2043</option>
					<option>2044</option>
					<option>2045</option>
					<option>2046</option>
					<option>2047</option>
					<option>2048</option>
					<option>2049</option></select> 年 <select onchange="changeCld()" id="sm"><option>1</option>
					<option>2</option>
					<option>3</option>
					<option>4</option>
					<option>5</option>
					<option>6</option>
					<option>7</option>
					<option>8</option>
					<option>9</option>
					<option>10</option>
					<option>11</option>
					<option>12</option></select> 月 <span id="gz">&nbsp;</span>
			</p>
		</div>
		<div id="calendar">
			<div id="detail">
				<div id="datedetail"></div>
				<div id="festival"></div>
			</div>
			<table id="calendarhead">
				<tr>
					<td>日</td>
					<td>一</td>
					<td>二</td>
					<td>三</td>
					<td>四</td>
					<td>五</td>
					<td>六</td>
				</tr>
			</table>
			<table id="week">
				<tr class="tr1">
					<td class="aorange" onmouseout="mOut()" onmouseover="mOvr(0)"
						id="sd0"></td>
					<td class="one" onmouseout="mOut()" onmouseover="mOvr(1)" id="sd1"></td>
					<td class="one" onmouseout="mOut()" onmouseover="mOvr(2)" id="sd2"></td>
					<td class="one" onmouseout="mOut()" onmouseover="mOvr(3)" id="sd3"></td>
					<td class="one" onmouseout="mOut()" onmouseover="mOvr(4)" id="sd4"></td>
					<td class="one" onmouseout="mOut()" onmouseover="mOvr(5)" id="sd5"></td>
					<td class="agreen" onmouseout="mOut()" onmouseover="mOvr(6)"
						id="sd6"></td>
				</tr>
				<tr class="tr2">
					<td onmouseout="mOut()" onmouseover="mOvr(0)" id="ld0"></td>
					<td onmouseout="mOut()" onmouseover="mOvr(1)" id="ld1"></td>
					<td onmouseout="mOut()" onmouseover="mOvr(2)" id="ld2"></td>
					<td onmouseout="mOut()" onmouseover="mOvr(3)" id="ld3"></td>
					<td onmouseout="mOut()" onmouseover="mOvr(4)" id="ld4"></td>
					<td onmouseout="mOut()" onmouseover="mOvr(5)" id="ld5"></td>
					<td onmouseout="mOut()" onmouseover="mOvr(6)" id="ld6"></td>
				</tr>
				<tr class="tr1">
					<td class="aorange" onmouseout="mOut()" onmouseover="mOvr(7)"
						id="sd7"></td>
					<td class="one" onmouseout="mOut()" onmouseover="mOvr(8)" id="sd8"></td>
					<td class="one" onmouseout="mOut()" onmouseover="mOvr(9)" id="sd9"></td>
					<td class="one" onmouseout="mOut()" onmouseover="mOvr(10)"
						id="sd10"></td>
					<td class="one" onmouseout="mOut()" onmouseover="mOvr(11)"
						id="sd11"></td>
					<td class="one" onmouseout="mOut()" onmouseover="mOvr(12)"
						id="sd12"></td>
					<td class="aorange" onmouseout="mOut()" onmouseover="mOvr(13)"
						id="sd13"></td>
				</tr>
				<tr class="tr2">
					<td onmouseout="mOut()" onmouseover="mOvr(7)" id="ld7"></td>
					<td onmouseout="mOut()" onmouseover="mOvr(8)" id="ld8"></td>
					<td onmouseout="mOut()" onmouseover="mOvr(9)" id="ld9"></td>
					<td onmouseout="mOut()" onmouseover="mOvr(10)" id="ld10"></td>
					<td onmouseout="mOut()" onmouseover="mOvr(11)" id="ld11"></td>
					<td onmouseout="mOut()" onmouseover="mOvr(12)" id="ld12"></td>
					<td onmouseout="mOut()" onmouseover="mOvr(13)" id="ld13"></td>
				</tr>
				<tr class="tr1">
					<td class="aorange" onmouseout="mOut()" onmouseover="mOvr(14)"
						id="sd14"></td>
					<td class="one" onmouseout="mOut()" onmouseover="mOvr(15)"
						id="sd15"></td>
					<td class="one" onmouseout="mOut()" onmouseover="mOvr(16)"
						id="sd16"></td>
					<td class="one" onmouseout="mOut()" onmouseover="mOvr(17)"
						id="sd17"></td>
					<td class="one" onmouseout="mOut()" onmouseover="mOvr(18)"
						id="sd18"></td>
					<td class="one" onmouseout="mOut()" onmouseover="mOvr(19)"
						id="sd19"></td>
					<td class="agreen" onmouseout="mOut()" onmouseover="mOvr(20)"
						id="sd20"></td>
				</tr>
				<tr class="tr2">
					<td onmouseout="mOut()" onmouseover="mOvr(14)" id="ld14"></td>
					<td onmouseout="mOut()" onmouseover="mOvr(15)" id="ld15"></td>
					<td onmouseout="mOut()" onmouseover="mOvr(16)" id="ld16"></td>
					<td onmouseout="mOut()" onmouseover="mOvr(17)" id="ld17"></td>
					<td onmouseout="mOut()" onmouseover="mOvr(18)" id="ld18"></td>
					<td onmouseout="mOut()" onmouseover="mOvr(19)" id="ld19"></td>
					<td onmouseout="mOut()" onmouseover="mOvr(20)" id="ld20"></td>
				</tr>
				<tr class="tr1">
					<td class="aorange" onmouseout="mOut()" onmouseover="mOvr(21)"
						id="sd21"></td>
					<td class="one" onmouseout="mOut()" onmouseover="mOvr(22)"
						id="sd22"></td>
					<td class="one" onmouseout="mOut()" onmouseover="mOvr(23)"
						id="sd23"></td>
					<td class="one" onmouseout="mOut()" onmouseover="mOvr(24)"
						id="sd24"></td>
					<td class="one" onmouseout="mOut()" onmouseover="mOvr(25)"
						id="sd25"></td>
					<td class="one" onmouseout="mOut()" onmouseover="mOvr(26)"
						id="sd26"></td>
					<td class="aorange" onmouseout="mOut()" onmouseover="mOvr(27)"
						id="sd27"></td>
				</tr>
				<tr class="tr2">
					<td onmouseout="mOut()" onmouseover="mOvr(21)" id="ld21"></td>
					<td onmouseout="mOut()" onmouseover="mOvr(22)" id="ld22"></td>
					<td onmouseout="mOut()" onmouseover="mOvr(23)" id="ld23"></td>
					<td onmouseout="mOut()" onmouseover="mOvr(24)" id="ld24"></td>
					<td onmouseout="mOut()" onmouseover="mOvr(25)" id="ld25"></td>
					<td onmouseout="mOut()" onmouseover="mOvr(26)" id="ld26"></td>
					<td onmouseout="mOut()" onmouseover="mOvr(27)" id="ld27"></td>
				</tr>
				<tr class="tr1">
					<td class="aorange" onmouseout="mOut()" onmouseover="mOvr(28)"
						id="sd28"></td>
					<td class="one" onmouseout="mOut()" onmouseover="mOvr(29)"
						id="sd29"></td>
					<td class="one" onmouseout="mOut()" onmouseover="mOvr(30)"
						id="sd30"></td>
					<td class="one" onmouseout="mOut()" onmouseover="mOvr(31)"
						id="sd31"></td>
					<td class="one" onmouseout="mOut()" onmouseover="mOvr(32)"
						id="sd32"></td>
					<td class="one" onmouseout="mOut()" onmouseover="mOvr(33)"
						id="sd33"></td>
					<td class="agreen" onmouseout="mOut()" onmouseover="mOvr(34)"
						id="sd34"></td>
				</tr>
				<tr class="tr2">
					<td onmouseout="mOut()" onmouseover="mOvr(28)" id="ld28"></td>
					<td onmouseout="mOut()" onmouseover="mOvr(29)" id="ld29"></td>
					<td onmouseout="mOut()" onmouseover="mOvr(30)" id="ld30"></td>
					<td onmouseout="mOut()" onmouseover="mOvr(31)" id="ld31"></td>
					<td onmouseout="mOut()" onmouseover="mOvr(32)" id="ld32"></td>
					<td onmouseout="mOut()" onmouseover="mOvr(33)" id="ld33"></td>
					<td onmouseout="mOut()" onmouseover="mOvr(34)" id="ld34"></td>
				</tr>
				<tr class="tr1">
					<td class="aorange" onmouseout="mOut()" onmouseover="mOvr(35)"
						id="sd35"></td>
					<td class="one" onmouseout="mOut()" onmouseover="mOvr(36)"
						id="sd36"></td>
					<td class="one" onmouseout="mOut()" onmouseover="mOvr(37)"
						id="sd37"></td>
					<td class="one" onmouseout="mOut()" onmouseover="mOvr(38)"
						id="sd38"></td>
					<td class="one" onmouseout="mOut()" onmouseover="mOvr(39)"
						id="sd39"></td>
					<td class="one" onmouseout="mOut()" onmouseover="mOvr(40)"
						id="sd40"></td>
					<td class="aorange" onmouseout="mOut()" onmouseover="mOvr(41)"
						id="sd41"></td>
				</tr>
				<tr class="tr2">
					<td onmouseout="mOut()" onmouseover="mOvr(35)" id="ld35"></td>
					<td onmouseout="mOut()" onmouseover="mOvr(36)" id="ld36"></td>
					<td onmouseout="mOut()" onmouseover="mOvr(37)" id="ld37"></td>
					<td onmouseout="mOut()" onmouseover="mOvr(38)" id="ld38"></td>
					<td onmouseout="mOut()" onmouseover="mOvr(39)" id="ld39"></td>
					<td onmouseout="mOut()" onmouseover="mOvr(40)" id="ld40"></td>
					<td onmouseout="mOut()" onmouseover="mOvr(41)" id="ld41"></td>
				</tr>
			</table>
		</div>
		<div id="panel">
			<div onclick="pushBtm('YU')">上一年↑</div>
			<div onclick="pushBtm('YD')">下一年↓</div>
			<div onclick="pushBtm('MU')">上一月↑</div>
			<div onclick="pushBtm('MD')">下一月↓</div>
			<div onclick="pushBtm('')">当前月</div>
		</div>
	</div>
</body>
</html>