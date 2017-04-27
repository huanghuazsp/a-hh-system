<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.BaseSystemUtil"%>
<%=BaseSystemUtil.getBaseDoctype()%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>颜色选择器</title>

<meta content="MSHTML 6.00.2900.5583" name="GENERATOR">
<style media="screen">
body {
	color: #999;
	font: 300 100.01%/1.2 "Segoe UI", "Helvetica Neue", Helvetica,
		"Arial Unicode", Arial, sans-serif;
	margin: 0 30px;
}

#content {
	width: 640px;
	height: 480px;
	position: relative;
}

h1 {
	font-weight: 300;
	font-size: 3em;
	position: absolute;
	right: 10px;
	bottom: 50px;
}

p {
	font-size: 2em;
}

#benefits {
	margin-top: 350px;
}

#copy a {
	color: #666;
	text-decoration: none;
}

#picker2 {
	width: 300px;
	position: absolute;
	top: 250px;
	left: 50%;
	margin-left: -150px;
}

#benefits {
	margin-bottom: 0;
}

#output {
	background: #eee;
	position: absolute;
	font-size: 30px;
	bottom: 10px;
	left: 20px;
	font-family: monospace;
	margin-top: -20px;
}

#copy {
	position: absolute;
	right: 10px;
	bottom: 10px;
	margin: 0;
	font-size: .6em;
	color: #666;
}

#values {
	position: absolute;
	left: 20px;
	top: 363px;
	font-size: .7em;
}
</style>
<script src="/hhcommon/opensource/raphael/raphael.js"></script>
<script src="/hhcommon/opensource/raphael/colorpicker.js"></script>
<script src="/hhcommon/opensource/raphael/colorwheel.js"></script>
<%=BaseSystemUtil.getBaseJs()%>
<script>
	var params = $.hh.getIframeParams();
	var textField = params.textField;

	var basecolor = textField.val() ||  "#eee";
	
	Raphael(function() {
		var out = document.getElementById("output"), vr = document
				.getElementById("vr"), vg = document.getElementById("vg"), vb = document
				.getElementById("vb"), vh = document.getElementById("vh"), vh2 = document
				.getElementById("vh2"), vs = document.getElementById("vs"), vs2 = document
				.getElementById("vs2"), vv = document.getElementById("vv"), vl = document
				.getElementById("vl"),

		// this is where colorpicker created
		cp = Raphael.colorpicker(40, 20, 300, basecolor), cp2 = Raphael
				.colorwheel(360, 20, 300, basecolor),

		clr = Raphael.color(basecolor);
		vr.innerHTML = clr.r;
		vg.innerHTML = clr.g;
		vb.innerHTML = clr.b;
		vh.innerHTML = vh2.innerHTML = Math.round(clr.h * 360) + "°";
		vs.innerHTML = vs2.innerHTML = Math.round(clr.s * 100) + "%";
		vv.innerHTML = Math.round(clr.v * 100) + "%";
		vl.innerHTML = Math.round(clr.l * 100) + "%";
		out.onkeyup = function() {
			cp.color(this.value);
			cp2.color(this.value);
		};
		// assigning onchange event handler
		var onchange = function(item) {
			return function(clr) {
				out.value = clr.replace(/^#(.)\1(.)\2(.)\3$/, "#$1$2$3");
				item.color(clr);
				out.style.background = clr;
				out.style.color = Raphael.rgb2hsb(clr).b < .5 ? "#fff" : "#000";
				clr = Raphael.color(clr);
				vr.innerHTML = clr.r;
				vg.innerHTML = clr.g;
				vb.innerHTML = clr.b;
				vh.innerHTML = vh2.innerHTML = Math.round(clr.h * 360) + "°";
				vs.innerHTML = vs2.innerHTML = Math.round(clr.s * 100) + "%";
				vv.innerHTML = Math.round(clr.v * 100) + "%";
				vl.innerHTML = Math.round(clr.l * 100) + "%";
			};
		};
		cp.onchange = onchange(cp2);
		cp2.onchange = onchange(cp);
		// that’s it. Too easy
	});
	
	function init(){
		$('#output').val(textField.val());
	}
	function ok(){
		if($('#output').val()){
			textField.val($('#output').val());
			textField.css({background: textField.val()});
			Dialog.close();
			textField.focus();
		}else{
			Dialog.infomsg("请选择颜色！！");
		}
	}
	
</script>

</head>
<body leftmargin="0" topmargin="0">
	<div xtype="hh_content">
		<div id="content">
			<h1 id="h1">颜色选择器</h1>
			<table id="values">
				<tr>
					<th>R</th>
					<td id="vr"></td>
					<th>H</th>
					<td id="vh"></td>
					<th>H</th>
					<td id="vh2"></td>
				</tr>
				<tr>
					<th>G</th>
					<td id="vg"></td>
					<th>S</th>
					<td id="vs"></td>
					<th>S</th>
					<td id="vs2"></td>
				</tr>
				<tr>
					<th>B</th>
					<td id="vb"></td>
					<th>B</th>
					<td id="vv"></td>
					<th>L</th>
					<td id="vl"></td>
				</tr>
			</table>
			<input type="text" id="output" >
		</div>
		<div id="picker2"></div>
	</div>
	<div xtype="toolbar">
		<span xtype="button" config="text:'确定' , onClick : ok "></span>
	</div>
</body>
</html>