<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.BaseSystemUtil"%>
<%=BaseSystemUtil.getBaseDoctype()%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>网页颜色选择器 - 颜色代码对照表</title>

<style type="text/css">
TD {
	FONT-SIZE: 12px;
	WORD-BREAK: break-all;
	FONT-FAMILY: "宋体"
}

BODY {
	SCROLLBAR-FACE-COLOR: #eeeaeb;
	SCROLLBAR-HIGHLIGHT-COLOR: #fbfdfc;
	SCROLLBAR-SHADOW-COLOR: #170708;
	COLOR: black;
	SCROLLBAR-3DLIGHT-COLOR: #170708;
	SCROLLBAR-ARROW-COLOR: #000000;
	SCROLLBAR-DARKSHADOW-COLOR: #ffffff;
	SCROLLBAR-BASE-COLOR: #170708
}
</style>

<script language="JavaScript">
function ChangeColor(form, ColorName)
    { var ColorValue = " ";
      if (ColorName == 'aliceblue')       ColorValue = "#F0F8FF";
      if (ColorName == 'antiquewhite')  ColorValue = "#FAEBD7";
      if (ColorName == 'aqua')       ColorValue = "#00FFFF";
      if (ColorName == 'aquamarine')  ColorValue = "#7FFFD4";
      if (ColorName == 'azure')  ColorValue = "#F0FFFF";
      if (ColorName == 'beige')  ColorValue = "#F5F5DC";
      if (ColorName == 'bisque')  ColorValue = "#FFE4C4";
      if (ColorName == 'black')  ColorValue = "#000000";
      if (ColorName == 'blanchedalmond')  ColorValue = "#FFEBCD";
      if (ColorName == 'blue')  ColorValue = "#0000FF";
      if (ColorName == 'blueviolet')  ColorValue = "#8A2BE2";
      if (ColorName == 'brown')  ColorValue = "#A52A2A";
      if (ColorName == 'burlywood')  ColorValue = "#DEB887";
      if (ColorName == 'cadetblue')  ColorValue = "#5F9EA0";
      if (ColorName == 'chartreuse')  ColorValue = "#7FFF00";
      if (ColorName == 'chocolate')  ColorValue = "#D2691E";
      if (ColorName == 'coral')  ColorValue = "#FF7F50";
      if (ColorName == 'cornflowerblue')  ColorValue = "#6495ED";
      if (ColorName == 'cornsilk')  ColorValue = "#FFF8DC";
      if (ColorName == 'crimson')  ColorValue = "#DC143C";
      if (ColorName == 'cyan')  ColorValue = "#00FFFF";
      if (ColorName == 'darkblue')  ColorValue = "#00008B";
      if (ColorName == 'darkcyan')  ColorValue = "#008B8B";
      if (ColorName == 'darkgoldenrod')  ColorValue = "#B8860B";
      if (ColorName == 'darkgray')  ColorValue = "#A9A9A9";
      if (ColorName == 'darkgreen')  ColorValue = "#006400";
      if (ColorName == 'darkkhaki')  ColorValue = "#BDB76B";
      if (ColorName == 'darkmagenta')  ColorValue = "#8B008B";
      if (ColorName == 'darkolivegreen')  ColorValue = "#556B2F";
      if (ColorName == 'darkorange')  ColorValue = "#FF8C00";
      if (ColorName == 'darkorchid')  ColorValue = "#9932CC";
      if (ColorName == 'darkred')  ColorValue = "#8B0000";
      if (ColorName == 'darksalmon')  ColorValue = "#E9967A";
      if (ColorName == 'darkseagreen')  ColorValue = "#8FBC8F";
      if (ColorName == 'darkslateblue')  ColorValue = "#483D8B";
      if (ColorName == 'darkslategray')  ColorValue = "#2F4F4F";
      if (ColorName == 'darkturquoise')  ColorValue = "#00CED1";
      if (ColorName == 'darkviolet')  ColorValue = "#9400D3";
      if (ColorName == 'deeppink')  ColorValue = "#FF1493";
      if (ColorName == 'deepskyblue')  ColorValue = "#00BFFF";
      if (ColorName == 'dimgray')  ColorValue = "#696969";
      if (ColorName == 'dodgerblue')  ColorValue = "#1E90FF";
      if (ColorName == 'firebrick')  ColorValue = "#B22222";
      if (ColorName == 'floralwhite')  ColorValue = "#FFFAF0";
      if (ColorName == 'forestgreen')  ColorValue = "#228B22";
      if (ColorName == 'fuchsia')  ColorValue = "#FF00FF";
      if (ColorName == 'gainsboro')  ColorValue = "#DCDCDC";
      if (ColorName == 'ghostwhite')  ColorValue = "#F8F8FF";
      if (ColorName == 'gold')  ColorValue = "#FFD700";
      if (ColorName == 'goldenrod')  ColorValue = "#DAA520";
      if (ColorName == 'gray')  ColorValue = "#808080";
      if (ColorName == 'green')  ColorValue = "#008000";
      if (ColorName == 'greenyellow')  ColorValue = "#ADFF2F";
      if (ColorName == 'honeydew')  ColorValue = "#F0FFF0";
      if (ColorName == 'hotpink')  ColorValue = "#FF69B4";
      if (ColorName == 'indianred')  ColorValue = "#CD5C5C";
      if (ColorName == 'indigo')  ColorValue = "#4B0082";
      if (ColorName == 'ivory')  ColorValue = "#FFFFF0";
      if (ColorName == 'khaki')  ColorValue = "#F0E68C";
      if (ColorName == 'lavender')  ColorValue = "#E6E6FA";
      if (ColorName == 'lavenderblush')  ColorValue = "#FFF0F5";
      if (ColorName == 'lawngreen')  ColorValue = "#7CFC00";
      if (ColorName == 'lemonchiffon')  ColorValue = "#FFFACD";
      if (ColorName == 'lightblue')  ColorValue = "#ADD8E6";
      if (ColorName == 'lightcoral')  ColorValue = "#F08080";
      if (ColorName == 'lightcyan')  ColorValue = "#E0FFFF";
      if (ColorName == 'lightgoldenrodyellow')  ColorValue = "#FAFAD2";
      if (ColorName == 'lightgreen')  ColorValue = "#90EE90";
      if (ColorName == 'lightgrey')  ColorValue = "#D3D3D3";
      if (ColorName == 'lightpink')  ColorValue = "#FFB6C1";
      if (ColorName == 'lightsalmon')  ColorValue = "#FFA07A";
      if (ColorName == 'lightseagreen')  ColorValue = "#20B2AA";
      if (ColorName == 'lightskyblue')  ColorValue = "#87CEFA";
      if (ColorName == 'lightslategray')  ColorValue = "#778899";
      if (ColorName == 'lightsteelblue')  ColorValue = "#B0C4DE";
      if (ColorName == 'lightyellow')  ColorValue = "#FFFFE0";
      if (ColorName == 'lime')  ColorValue = "#00FF00";
      if (ColorName == 'limegreen')  ColorValue = "#32CD32";
      if (ColorName == 'linen')  ColorValue = "#FAF0E6";
      if (ColorName == 'magenta')  ColorValue = "#FF00FF";
      if (ColorName == 'maroon')  ColorValue = "#800000";
      if (ColorName == 'mediumaquamarine')  ColorValue = "#66CDAA";
      if (ColorName == 'mediumblue')  ColorValue = "#0000CD";
      if (ColorName == 'mediumorchid')  ColorValue = "#BA55D3";
      if (ColorName == 'mediumpurple')  ColorValue = "#9370DB";
      if (ColorName == 'mediumseagreen')  ColorValue = "#3CB371";
      if (ColorName == 'mediumslateblue')  ColorValue = "#7B68EE";
      if (ColorName == 'mediumspringgreen')  ColorValue = "#00FA9A";
      if (ColorName == 'mediumturquoise')  ColorValue = "#48D1CC";
      if (ColorName == 'mediumvioletred')  ColorValue = "#C71585";
      if (ColorName == 'midnightblue')  ColorValue = "#191970";
      if (ColorName == 'mintcream')  ColorValue = "#F5FFFA";
      if (ColorName == 'mistyrose')  ColorValue = "#FFE4E1";
      if (ColorName == 'moccasin')  ColorValue = "#FFE4B5";
      if (ColorName == 'navajowhite')  ColorValue = "#FFDEAD";
      if (ColorName == 'navy')  ColorValue = "#000080";
      if (ColorName == 'oldlace')  ColorValue = "#FDF5E6";
      if (ColorName == 'olive')  ColorValue = "#808000";
      if (ColorName == 'olivedrab')  ColorValue = "#6B8E23";
      if (ColorName == 'orange')  ColorValue = "#FFA500";
      if (ColorName == 'orangered')  ColorValue = "#FF4500";
      if (ColorName == 'orchid')  ColorValue = "#DA70D6";
      if (ColorName == 'palegoldenrod')  ColorValue = "#EEE8AA";
      if (ColorName == 'palegreen')  ColorValue = "#98FB98";
      if (ColorName == 'paleturquoise')  ColorValue = "#AFEEEE";
      if (ColorName == 'palevioletred')  ColorValue = "#DB7093";
      if (ColorName == 'papayawhip')  ColorValue = "#FFEFD5";
      if (ColorName == 'peachpuff')  ColorValue = "#FFDAB9";
      if (ColorName == 'peru')  ColorValue = "#CD853F";
      if (ColorName == 'pink')  ColorValue = "#FFC0CB";
      if (ColorName == 'plum')  ColorValue = "#DDA0DD";
      if (ColorName == 'powderblue')  ColorValue = "#B0E0E6";
      if (ColorName == 'purple')  ColorValue = "#800080";
      if (ColorName == 'red')  ColorValue = "#FF0000";
      if (ColorName == 'rosybrown')  ColorValue = "#BC8F8F";
      if (ColorName == 'royalblue')  ColorValue = "#4169E1";
      if (ColorName == 'saddlebrown')  ColorValue = "#8B4513";
      if (ColorName == 'salmon')  ColorValue = "#FA8072";
      if (ColorName == 'sandybrown')  ColorValue = "#F4A460";
      if (ColorName == 'seagreen')  ColorValue = "#2E8B57";
      if (ColorName == 'seashell')  ColorValue = "#FFF5EE";
      if (ColorName == 'sienna')  ColorValue = "#A0522D";
      if (ColorName == 'silver')  ColorValue = "#C0C0C0";
      if (ColorName == 'skyblue')  ColorValue = "#87CEEB";
      if (ColorName == 'slateblue')  ColorValue = "#6A5ACD";
      if (ColorName == 'slategray')  ColorValue = "#708090";
      if (ColorName == 'snow')  ColorValue = "#FFFAFA";
      if (ColorName == 'springgreen')  ColorValue = "#00FF7F";
      if (ColorName == 'steelblue')  ColorValue = "#4682B4";
      if (ColorName == 'tan')  ColorValue = "#D2B48C";
      if (ColorName == 'teal')  ColorValue = "#008080";
      if (ColorName == 'thistle')  ColorValue = "#D8BFD8";
      if (ColorName == 'tomato')  ColorValue = "#FF6347";
      if (ColorName == 'turquoise')  ColorValue = "#40E0D0";
      if (ColorName == 'violet')  ColorValue = "#EE82EE";
      if (ColorName == 'wheat')  ColorValue = "#F5DEB3";
      if (ColorName == 'white')  ColorValue = "#FFFFFF";
      if (ColorName == 'whitesmoke')  ColorValue = "#F5F5F5";
      if (ColorName == 'yellow')  ColorValue = "#FFFF00";
      if (ColorName == 'yellowgreen')  ColorValue = "#9ACD32";
      document.bgColor = ColorName;
     // form.CName.value = ColorName;
     // form.CValue.value = ColorValue;
    }

</script>

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
<script>
        Raphael(function () {
            var out = document.getElementById("output"),
                vr = document.getElementById("vr"),
                vg = document.getElementById("vg"),
                vb = document.getElementById("vb"),
                vh = document.getElementById("vh"),
                vh2 = document.getElementById("vh2"),
                vs = document.getElementById("vs"),
                vs2 = document.getElementById("vs2"),
                vv = document.getElementById("vv"),
                vl = document.getElementById("vl"),
            
            // this is where colorpicker created
                cp = Raphael.colorpicker(40, 20, 300, "#eee"),
                cp2 = Raphael.colorwheel(360, 20, 300, "#eee"),
            
                clr = Raphael.color("#eee");
            vr.innerHTML = clr.r;
            vg.innerHTML = clr.g;
            vb.innerHTML = clr.b;
            vh.innerHTML = vh2.innerHTML = Math.round(clr.h * 360) + "°";
            vs.innerHTML = vs2.innerHTML = Math.round(clr.s * 100) + "%";
            vv.innerHTML = Math.round(clr.v * 100) + "%";
            vl.innerHTML = Math.round(clr.l * 100) + "%";
            out.onkeyup = function () {
                cp.color(this.value);
                cp2.color(this.value);
            };
            // assigning onchange event handler
            var onchange = function (item) {
                return function (clr) {
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
        </script>

</head>
<body leftmargin="0" topmargin="0">
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
		<input type="text" id="output" value="#eeeeee">
	</div>
	<div id="picker2"></div>
	<table cellspacing="1" cellpadding="1" width="770" align="center"
		border="1">
		<tbody>
			<form name="bgcolor" method="post"></form>
			<tr>
				<td align="middle" colspan="2"><b>颜色名称:</b> <input
					onmouseover="this.focus()" onfocus="this.select()" size="15"
					value="snow" name="CName"></td>
				<td align="middle" colspan="2"><b>颜色数值:</b> <input
					onmouseover="this.focus()" onfocus="this.select()" size="15"
					value="#FFFAFA" name="CValue"></td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;white&#39;)" type="radio"
					name="bgcolor"> 白色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;whitesmoke&#39;)" type="radio"
					name="bgcolor"> 烟白色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;yellow&#39;)" type="radio"
					name="bgcolor"> 黄色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;yellowgreen&#39;)"
					type="radio" name="bgcolor"> 黄绿色</td>
			</tr>
			<tr>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;aliceblue&#39;)" type="radio"
					name="bgcolor"> 艾利斯兰</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;antiquewhite&#39;)"
					type="radio" name="bgcolor"> 古董白</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;aqua&#39;)" type="radio"
					name="bgcolor"> 浅绿色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;aquamarine&#39;)" type="radio"
					name="bgcolor"> 碧绿色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;azure&#39;)" type="radio"
					name="bgcolor"> 天蓝色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;beige&#39;)" type="radio"
					name="bgcolor"> 米色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;bisque&#39;)" type="radio"
					name="bgcolor"> 桔黄色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;black&#39;)" type="radio"
					name="bgcolor"> 黑色</td>
			</tr>
			<tr>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;blanchedalmond&#39;)"
					type="radio" name="bgcolor"> 白杏色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;blue&#39;)" type="radio"
					name="bgcolor"> 蓝色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;blueviolet&#39;)" type="radio"
					name="bgcolor"> 紫罗兰色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;brown&#39;)" type="radio"
					name="bgcolor"> 褐色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;burlywood&#39;)" type="radio"
					name="bgcolor"> 实木色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;cadetblue&#39;)" type="radio"
					name="bgcolor"> 军兰色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;chartreuse&#39;)" type="radio"
					name="bgcolor"> 黄绿色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;chocolate&#39;)" type="radio"
					name="bgcolor"> 巧可力色</td>
			</tr>
			<tr>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;coral&#39;)" type="radio"
					name="bgcolor"> 珊瑚色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;cornflowerblue&#39;)"
					type="radio" name="bgcolor"> 菊兰色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;cornsilk&#39;)" type="radio"
					name="bgcolor"> 米绸色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;crimson&#39;)" type="radio"
					name="bgcolor"> 暗深红色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;cyan&#39;)" type="radio"
					name="bgcolor"> 青色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;darkblue&#39;)" type="radio"
					name="bgcolor"> 暗蓝色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;darkcyan&#39;)" type="radio"
					name="bgcolor"> 暗青色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;darkgoldenrod&#39;)"
					type="radio" name="bgcolor"> 暗金黄色</td>
			</tr>
			<tr>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;darkgray&#39;)" type="radio"
					name="bgcolor"> 暗灰色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;darkgreen&#39;)" type="radio"
					name="bgcolor"> 暗绿色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;darkkhaki&#39;)" type="radio"
					name="bgcolor"> 暗黄褐色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;darkmagenta&#39;)"
					type="radio" name="bgcolor"> 暗洋红</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;darkolivegreen&#39;)"
					type="radio" name="bgcolor"> 暗橄榄绿</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;darkorange&#39;)" type="radio"
					name="bgcolor"> 暗桔黄色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;darkorchid&#39;)" type="radio"
					name="bgcolor"> 暗紫色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;darkred&#39;)" type="radio"
					name="bgcolor"> 暗红色</td>
			</tr>
			<tr>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;darksalmon&#39;)" type="radio"
					name="bgcolor"> 暗肉色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;darkseagreen&#39;)"
					type="radio" name="bgcolor"> 暗海兰色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;darkslateblue&#39;)"
					type="radio" name="bgcolor"> 暗灰蓝色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;darkslategray&#39;)"
					type="radio" name="bgcolor"> 墨绿色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;darkturquoise&#39;)"
					type="radio" name="bgcolor"> 暗宝石绿</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;darkviolet&#39;)" type="radio"
					name="bgcolor"> 暗紫罗兰色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;deeppink&#39;)" type="radio"
					name="bgcolor"> 深粉红色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;deepskyblue&#39;)"
					type="radio" name="bgcolor"> 深天蓝色</td>
			</tr>
			<tr>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;dimgray&#39;)" type="radio"
					name="bgcolor"> 暗灰色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;dodgerblue&#39;)" type="radio"
					name="bgcolor"> 闪兰色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;firebrick&#39;)" type="radio"
					name="bgcolor"> 火砖色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;floralwhite&#39;)"
					type="radio" name="bgcolor"> 花白色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;forestgreen&#39;)"
					type="radio" name="bgcolor"> 森林绿</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;fuchsia&#39;)" type="radio"
					name="bgcolor"> 紫红色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;gainsboro&#39;)" type="radio"
					name="bgcolor"> 淡灰色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;ghostwhite&#39;)" type="radio"
					name="bgcolor"> 幽灵白</td>
			</tr>
			<tr>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;gold&#39;)" type="radio"
					name="bgcolor"> 金色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;goldenrod&#39;)" type="radio"
					name="bgcolor"> 金麒麟色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;gray&#39;)" type="radio"
					name="bgcolor"> 灰色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;green&#39;)" type="radio"
					name="bgcolor"> 绿色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;greenyellow&#39;)"
					type="radio" name="bgcolor"> 黄绿色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;honeydew&#39;)" type="radio"
					name="bgcolor"> 蜜色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;hotpink&#39;)" type="radio"
					name="bgcolor"> 热粉红色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;indianred&#39;)" type="radio"
					name="bgcolor"> 印第安红</td>
			</tr>
			<tr>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;indigo&#39;)" type="radio"
					name="bgcolor"> 靛青色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;ivory&#39;)" type="radio"
					name="bgcolor"> 象牙色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;khaki&#39;)" type="radio"
					name="bgcolor"> 黄褐色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;lavender&#39;)" type="radio"
					name="bgcolor"> 淡紫色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;lavenderblush&#39;)"
					type="radio" name="bgcolor"> 淡紫红</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;lawngreen&#39;)" type="radio"
					name="bgcolor"> 草绿色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;lemonchiffon&#39;)"
					type="radio" name="bgcolor"> 柠檬绸色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;lightblue&#39;)" type="radio"
					name="bgcolor"> 亮蓝色</td>
			</tr>
			<tr>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;lightcoral&#39;)" type="radio"
					name="bgcolor"> 亮珊瑚色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;lightcyan&#39;)" type="radio"
					name="bgcolor"> 亮青色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;lightgoldenrodyellow&#39;)"
					type="radio" name="bgcolor"> 亮金黄色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;lightgreen&#39;)" type="radio"
					name="bgcolor"> 亮绿色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;lightgrey&#39;)" type="radio"
					name="bgcolor"> 亮灰色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;lightpink&#39;)" type="radio"
					name="bgcolor"> 亮粉红色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;lightsalmon&#39;)"
					type="radio" name="bgcolor"> 亮肉色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;lightseagreen&#39;)"
					type="radio" name="bgcolor"> 亮海蓝色</td>
			</tr>
			<tr>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;lightskyblue&#39;)"
					type="radio" name="bgcolor"> 亮天蓝色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;lightslategray&#39;)"
					type="radio" name="bgcolor"> 亮蓝灰</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;lightsteelblue&#39;)"
					type="radio" name="bgcolor"> 亮钢兰色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;lightyellow&#39;)"
					type="radio" name="bgcolor"> 亮黄色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;lime&#39;)" type="radio"
					name="bgcolor"> 酸橙色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;limegreen&#39;)" type="radio"
					name="bgcolor"> 橙绿以</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;linen&#39;)" type="radio"
					name="bgcolor"> 亚麻色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;magenta&#39;)" type="radio"
					name="bgcolor"> 红紫色</td>
			</tr>
			<tr>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;maroon&#39;)" type="radio"
					name="bgcolor"> 粟色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;mediumaquamarine&#39;)"
					type="radio" name="bgcolor"> 间绿色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;mediumblue&#39;)" type="radio"
					name="bgcolor"> 间兰色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;mediumorchid&#39;)"
					type="radio" name="bgcolor"> 间紫色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;mediumpurple&#39;)"
					type="radio" name="bgcolor"> 间紫色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;mediumseagreen&#39;)"
					type="radio" name="bgcolor"> 间海蓝</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;mediumslateblue&#39;)"
					type="radio" name="bgcolor"> 间暗蓝色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;mediumspringgreen&#39;)"
					type="radio" name="bgcolor"> 间春绿色</td>
			</tr>
			<tr>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;mediumturquoise&#39;)"
					type="radio" name="bgcolor"> 间绿宝石</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;mediumvioletred&#39;)"
					type="radio" name="bgcolor"> 间紫罗兰色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;midnightblue&#39;)"
					type="radio" name="bgcolor"> 中灰兰色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;mintcream&#39;)" type="radio"
					name="bgcolor"> 薄荷色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;mistyrose&#39;)" type="radio"
					name="bgcolor"> 浅玫瑰色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;moccasin&#39;)" type="radio"
					name="bgcolor"> 鹿皮色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;navajowhite&#39;)"
					type="radio" name="bgcolor"> 纳瓦白</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;navy&#39;)" type="radio"
					name="bgcolor"> 海军色</td>
			</tr>
			<tr>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;oldlace&#39;)" type="radio"
					name="bgcolor"> 老花色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;olive&#39;)" type="radio"
					name="bgcolor"> 橄榄色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;olivedrab&#39;)" type="radio"
					name="bgcolor"> 深绿褐色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;orange&#39;)" type="radio"
					name="bgcolor"> 橙色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;orangered&#39;)" type="radio"
					name="bgcolor"> 红橙色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;orchid&#39;)" type="radio"
					name="bgcolor"> 淡紫色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;palegoldenrod&#39;)"
					type="radio" name="bgcolor"> 苍麒麟色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;palegreen&#39;)" type="radio"
					name="bgcolor"> 苍绿色</td>
			</tr>
			<tr>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;paleturquoise&#39;)"
					type="radio" name="bgcolor"> 苍宝石绿</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;palevioletred&#39;)"
					type="radio" name="bgcolor"> 苍紫罗兰色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;papayawhip&#39;)" type="radio"
					name="bgcolor"> 番木色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;peachpuff&#39;)" type="radio"
					name="bgcolor"> 桃色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;peru&#39;)" type="radio"
					name="bgcolor"> 秘鲁色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;pink&#39;)" type="radio"
					name="bgcolor"> 粉红色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;plum&#39;)" type="radio"
					name="bgcolor"> 洋李色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;powderblue&#39;)" type="radio"
					name="bgcolor"> 粉蓝色</td>
			</tr>
			<tr>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;purple&#39;)" type="radio"
					name="bgcolor"> 紫色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;red&#39;)" type="radio"
					name="bgcolor"> 红色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;rosybrown&#39;)" type="radio"
					name="bgcolor"> 褐玫瑰红</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;royalblue&#39;)" type="radio"
					name="bgcolor"> 皇家蓝</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;saddlebrown&#39;)"
					type="radio" name="bgcolor"> 重褐色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;salmon&#39;)" type="radio"
					name="bgcolor"> 鲜肉色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;sandybrown&#39;)" type="radio"
					name="bgcolor"> 沙褐色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;seagreen&#39;)" type="radio"
					name="bgcolor"> 海绿色</td>
			</tr>
			<tr>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;seashell&#39;)" type="radio"
					name="bgcolor"> 海贝色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;sienna&#39;)" type="radio"
					name="bgcolor"> 赭色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;silver&#39;)" type="radio"
					name="bgcolor"> 银色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;skyblue&#39;)" type="radio"
					name="bgcolor"> 天蓝色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;slateblue&#39;)" type="radio"
					name="bgcolor"> 石蓝色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;slategray&#39;)" type="radio"
					name="bgcolor"> 灰石色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;snow&#39;)" type="radio"
					checked="" name="bgcolor"> 雪白色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;springgreen&#39;)"
					type="radio" name="bgcolor"> 春绿色</td>
			</tr>
			<tr>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;steelblue&#39;)" type="radio"
					name="bgcolor"> 钢兰色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;tan&#39;)" type="radio"
					name="bgcolor"> 茶色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;teal&#39;)" type="radio"
					name="bgcolor"> 水鸭色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;thistle&#39;)" type="radio"
					name="bgcolor"> 蓟色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;tomato&#39;)" type="radio"
					name="bgcolor"> 西红柿色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;turquoise&#39;)" type="radio"
					name="bgcolor"> 青绿色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;violet&#39;)" type="radio"
					name="bgcolor"> 紫罗兰色</td>
				<td width="12.5%"><input
					onclick="ChangeColor(this.form, &#39;wheat&#39;)" type="radio"
					name="bgcolor"> 浅黄色</td>
			</tr>
		</tbody>
	</table>
	<p>
	<table cellspacing="0" cellpadding="5" width="600" align="center"
		border="1">
		<tbody>
			<tr>
				<td align="middle" colspan="10"><b>颜色代码表Ⅰ</b></td>
			</tr>
			<tr>
				<td width="60" bgcolor="#000000"></td>
				<td width="50">#000000</td>
				<td width="60" bgcolor="#2f0000"></td>
				<td width="50">#2F0000</td>
				<td width="60" bgcolor="#600030"></td>
				<td width="50">#600030</td>
				<td width="60" bgcolor="#460046"></td>
				<td width="50">#460046</td>
				<td width="60" bgcolor="#28004d"></td>
				<td width="50">#28004D</td>
			</tr>
			<tr>
				<td bgcolor="#272727"></td>
				<td>#272727</td>
				<td bgcolor="#4d0000"></td>
				<td>#4D0000</td>
				<td bgcolor="#820041"></td>
				<td>#820041</td>
				<td bgcolor="#5e005e"></td>
				<td>#5E005E</td>
				<td bgcolor="#3a006f"></td>
				<td>#3A006F</td>
			</tr>
			<tr>
				<td bgcolor="#3c3c3c"></td>
				<td>#3C3C3C</td>
				<td bgcolor="#600000"></td>
				<td>#600000</td>
				<td bgcolor="#9f0050"></td>
				<td>#9F0050</td>
				<td bgcolor="#750075"></td>
				<td>#750075</td>
				<td bgcolor="#4b0091"></td>
				<td>#4B0091</td>
			</tr>
			<tr>
				<td bgcolor="#4f4f4f"></td>
				<td>#4F4F4F</td>
				<td bgcolor="#750000"></td>
				<td>#750000</td>
				<td bgcolor="#bf0060"></td>
				<td>#BF0060</td>
				<td bgcolor="#930093"></td>
				<td>#930093</td>
				<td bgcolor="#5b00ae"></td>
				<td>#5B00AE</td>
			</tr>
			<tr>
				<td bgcolor="#5b5b5b"></td>
				<td>#5B5B5B</td>
				<td bgcolor="#930000"></td>
				<td>#930000</td>
				<td bgcolor="#d9006c"></td>
				<td>#D9006C</td>
				<td bgcolor="#ae00ae"></td>
				<td>#AE00AE</td>
				<td bgcolor="#6f00d2"></td>
				<td>#6F00D2</td>
			</tr>
			<tr>
				<td bgcolor="#6c6c6c"></td>
				<td>#6C6C6C</td>
				<td bgcolor="#ae0000"></td>
				<td>#AE0000</td>
				<td bgcolor="#f00078"></td>
				<td>#F00078</td>
				<td bgcolor="#d200d2"></td>
				<td>#D200D2</td>
				<td bgcolor="#8600ff"></td>
				<td>#8600FF</td>
			</tr>
			<tr>
				<td bgcolor="#7b7b7b"></td>
				<td>#7B7B7B</td>
				<td bgcolor="#ce0000"></td>
				<td>#CE0000</td>
				<td bgcolor="#ff0080"></td>
				<td>#FF0080</td>
				<td bgcolor="#e800e8"></td>
				<td>#E800E8</td>
				<td bgcolor="#921aff"></td>
				<td>#921AFF</td>
			</tr>
			<tr>
				<td bgcolor="#8e8e8e"></td>
				<td>#8E8E8E</td>
				<td bgcolor="#ea0000"></td>
				<td>#EA0000</td>
				<td bgcolor="#ff359a"></td>
				<td>#FF359A</td>
				<td bgcolor="#ff00ff"></td>
				<td>#FF00FF</td>
				<td bgcolor="#9f35ff"></td>
				<td>#9F35FF</td>
			</tr>
			<tr>
				<td bgcolor="#9d9d9d"></td>
				<td>#9D9D9D</td>
				<td bgcolor="#ff0000"></td>
				<td>#FF0000</td>
				<td bgcolor="#ff60af"></td>
				<td>#FF60AF</td>
				<td bgcolor="#ff44ff"></td>
				<td>#FF44FF</td>
				<td bgcolor="#b15bff"></td>
				<td>#B15BFF</td>
			</tr>
			<tr>
				<td bgcolor="#adadad"></td>
				<td>#ADADAD</td>
				<td bgcolor="#ff2d2d"></td>
				<td>#FF2D2D</td>
				<td bgcolor="#ff79bc"></td>
				<td>#FF79BC</td>
				<td bgcolor="#ff77ff"></td>
				<td>#FF77FF</td>
				<td bgcolor="#be77ff"></td>
				<td>#BE77FF</td>
			</tr>
			<tr>
				<td bgcolor="#bebebe"></td>
				<td>#BEBEBE</td>
				<td bgcolor="#ff5151"></td>
				<td>#FF5151</td>
				<td bgcolor="#ff95ca"></td>
				<td>#FF95CA</td>
				<td bgcolor="#ff8eff"></td>
				<td>#FF8EFF</td>
				<td bgcolor="#ca8eff"></td>
				<td>#CA8EFF</td>
			</tr>
			<tr>
				<td bgcolor="#d0d0d0"></td>
				<td>#d0d0d0</td>
				<td bgcolor="#ff7575"></td>
				<td>#ff7575</td>
				<td bgcolor="#ffaad5"></td>
				<td>#ffaad5</td>
				<td bgcolor="#ffa6ff"></td>
				<td>#ffa6ff</td>
				<td bgcolor="#d3a4ff"></td>
				<td>#d3a4ff</td>
			</tr>
			<tr>
				<td bgcolor="#e0e0e0"></td>
				<td>#E0E0E0</td>
				<td bgcolor="#ff9797"></td>
				<td>#FF9797</td>
				<td bgcolor="#ffc1e0"></td>
				<td>#FFC1E0</td>
				<td bgcolor="#ffbfff"></td>
				<td>#FFBFFF</td>
				<td bgcolor="#dcb5ff"></td>
				<td>#DCB5FF</td>
			</tr>
			<tr>
				<td bgcolor="#f0f0f0"></td>
				<td>#F0F0F0</td>
				<td bgcolor="#ffb5b5"></td>
				<td>#FFB5B5</td>
				<td bgcolor="#ffd9ec"></td>
				<td>#FFD9EC</td>
				<td bgcolor="#ffd0ff"></td>
				<td>#FFD0FF</td>
				<td bgcolor="#e6caff"></td>
				<td>#E6CAFF</td>
			</tr>
			<tr>
				<td bgcolor="#fcfcfc"></td>
				<td>#FCFCFC</td>
				<td bgcolor="#ffd2d2"></td>
				<td>#FFD2D2</td>
				<td bgcolor="#ffecf5"></td>
				<td>#FFECF5</td>
				<td bgcolor="#ffe6ff"></td>
				<td>#FFE6FF</td>
				<td bgcolor="#f1e1ff"></td>
				<td>#F1E1FF</td>
			</tr>
			<tr>
				<td bgcolor="#ffffff"></td>
				<td>#FFFFFF</td>
				<td bgcolor="#ffecec"></td>
				<td>#FFECEC</td>
				<td bgcolor="#fff7fb"></td>
				<td>#FFF7FB</td>
				<td bgcolor="#fff7ff"></td>
				<td>#FFF7FF</td>
				<td bgcolor="#faf4ff"></td>
				<td>#FAF4FF</td>
			</tr>
			<tr>
				<td bgcolor="#000079"></td>
				<td>#000079</td>
				<td bgcolor="#000079"></td>
				<td>#000079</td>
				<td bgcolor="#003e3e"></td>
				<td>#003E3E</td>
				<td bgcolor="#006030"></td>
				<td>#006030</td>
				<td bgcolor="#006000"></td>
				<td>#006000</td>
			</tr>
			<tr>
				<td bgcolor="#000093"></td>
				<td>#000093</td>
				<td bgcolor="#003d79"></td>
				<td>#003D79</td>
				<td bgcolor="#005757"></td>
				<td>#005757</td>
				<td bgcolor="#01814a"></td>
				<td>#01814A</td>
				<td bgcolor="#007500"></td>
				<td>#007500</td>
			</tr>
			<tr>
				<td bgcolor="#0000c6"></td>
				<td>#0000C6</td>
				<td bgcolor="#004b97"></td>
				<td>#004B97</td>
				<td bgcolor="#007979"></td>
				<td>#007979</td>
				<td bgcolor="#019858"></td>
				<td>#019858</td>
				<td bgcolor="#009100"></td>
				<td>#009100</td>
			</tr>
			<tr>
				<td bgcolor="#0000c6"></td>
				<td>#0000C6</td>
				<td bgcolor="#005ab5"></td>
				<td>#005AB5</td>
				<td bgcolor="#009393"></td>
				<td>#009393</td>
				<td bgcolor="#01b468"></td>
				<td>#01B468</td>
				<td bgcolor="#00a600"></td>
				<td>#00A600</td>
			</tr>
			<tr>
				<td bgcolor="#0000e3"></td>
				<td>#0000E3</td>
				<td bgcolor="#0066cc"></td>
				<td>#0066CC</td>
				<td bgcolor="#00aeae"></td>
				<td>#00AEAE</td>
				<td bgcolor="#02c874"></td>
				<td>#02C874</td>
				<td bgcolor="#00bb00"></td>
				<td>#00BB00</td>
			</tr>
			<tr>
				<td bgcolor="#2828ff"></td>
				<td>#2828FF</td>
				<td bgcolor="#0072e3"></td>
				<td>#0072E3</td>
				<td bgcolor="#00caca"></td>
				<td>#00CACA</td>
				<td bgcolor="#02df82"></td>
				<td>#02DF82</td>
				<td bgcolor="#00db00"></td>
				<td>#00DB00</td>
			</tr>
			<tr>
				<td bgcolor="#4a4aff"></td>
				<td>#4A4AFF</td>
				<td bgcolor="#0080ff"></td>
				<td>#0080FF</td>
				<td bgcolor="#00e3e3"></td>
				<td>#00E3E3</td>
				<td bgcolor="#02f78e"></td>
				<td>#02F78E</td>
				<td bgcolor="#00ec00"></td>
				<td>#00EC00</td>
			</tr>
			<tr>
				<td bgcolor="#6a6aff"></td>
				<td>#6A6AFF</td>
				<td bgcolor="#2894ff"></td>
				<td>#2894FF</td>
				<td bgcolor="#00ffff"></td>
				<td>#00FFFF</td>
				<td bgcolor="#1afd9c"></td>
				<td>#1AFD9C</td>
				<td bgcolor="#28ff28"></td>
				<td>#28FF28</td>
			</tr>
			<tr>
				<td bgcolor="#7d7dff"></td>
				<td>#7D7DFF</td>
				<td bgcolor="#46a3ff"></td>
				<td>#46A3FF</td>
				<td bgcolor="#4dffff"></td>
				<td>#4DFFFF</td>
				<td bgcolor="#4efeb3"></td>
				<td>#4EFEB3</td>
				<td bgcolor="#53ff53"></td>
				<td>#53FF53</td>
			</tr>
			<tr>
				<td bgcolor="#9393ff"></td>
				<td>#9393FF</td>
				<td bgcolor="#66b3ff"></td>
				<td>#66B3FF</td>
				<td bgcolor="#80ffff"></td>
				<td>#80FFFF</td>
				<td bgcolor="#7afec6"></td>
				<td>#7AFEC6</td>
				<td bgcolor="#79ff79"></td>
				<td>#79FF79</td>
			</tr>
			<tr>
				<td bgcolor="#aaaaff"></td>
				<td>#AAAAFF</td>
				<td bgcolor="#84c1ff"></td>
				<td>#84C1FF</td>
				<td bgcolor="#a6ffff"></td>
				<td>#A6FFFF</td>
				<td bgcolor="#96fed1"></td>
				<td>#96FED1</td>
				<td bgcolor="#93ff93"></td>
				<td>#93FF93</td>
			</tr>
			<tr>
				<td bgcolor="#b9b9ff"></td>
				<td>#B9B9FF</td>
				<td bgcolor="#97cbff"></td>
				<td>#97CBFF</td>
				<td bgcolor="#bbffff"></td>
				<td>#BBFFFF</td>
				<td bgcolor="#adfedc"></td>
				<td>#ADFEDC</td>
				<td bgcolor="#a6ffa6"></td>
				<td>#A6FFA6</td>
			</tr>
			<tr>
				<td bgcolor="#ceceff"></td>
				<td>#CECEFF</td>
				<td bgcolor="#acd6ff"></td>
				<td>#ACD6FF</td>
				<td bgcolor="#caffff"></td>
				<td>#CAFFFF</td>
				<td bgcolor="#c1ffe4"></td>
				<td>#C1FFE4</td>
				<td bgcolor="#bbffbb"></td>
				<td>#BBFFBB</td>
			</tr>
			<tr>
				<td bgcolor="#ddddff"></td>
				<td>#DDDDFF</td>
				<td bgcolor="#c4e1ff"></td>
				<td>#C4E1FF</td>
				<td bgcolor="#d9ffff"></td>
				<td>#D9FFFF</td>
				<td bgcolor="#d7ffee"></td>
				<td>#D7FFEE</td>
				<td bgcolor="#ceffce"></td>
				<td>#CEFFCE</td>
			</tr>
			<tr>
				<td bgcolor="#ececff"></td>
				<td>#ECECFF</td>
				<td bgcolor="#d2e9ff"></td>
				<td>#D2E9FF</td>
				<td bgcolor="#ecffff"></td>
				<td>#ECFFFF</td>
				<td bgcolor="#e8fff5"></td>
				<td>#E8FFF5</td>
				<td bgcolor="#dfffdf"></td>
				<td>#DFFFDF</td>
			</tr>
			<tr>
				<td bgcolor="#fbfbff"></td>
				<td>#FBFBFF</td>
				<td bgcolor="#ecf5ff"></td>
				<td>#ECF5FF</td>
				<td bgcolor="#fdffff"></td>
				<td>#FDFFFF</td>
				<td bgcolor="#fbfffd"></td>
				<td>#FBFFFD</td>
				<td bgcolor="#f0fff0"></td>
				<td>#F0FFF0</td>
			</tr>
			<tr>
				<td bgcolor="#467500"></td>
				<td>#467500</td>
				<td bgcolor="#424200"></td>
				<td>#424200</td>
				<td bgcolor="#5b4b00"></td>
				<td>#5B4B00</td>
				<td bgcolor="#844200"></td>
				<td>#844200</td>
				<td bgcolor="#642100"></td>
				<td>#642100</td>
			</tr>
			<tr>
				<td bgcolor="#548c00"></td>
				<td>#548C00</td>
				<td bgcolor="#5b5b00"></td>
				<td>#5B5B00</td>
				<td bgcolor="#796400"></td>
				<td>#796400</td>
				<td bgcolor="#9f5000"></td>
				<td>#9F5000</td>
				<td bgcolor="#842b00"></td>
				<td>#842B00</td>
			</tr>
			<tr>
				<td bgcolor="#64a600"></td>
				<td>#64A600</td>
				<td bgcolor="#737300"></td>
				<td>#737300</td>
				<td bgcolor="#977c00"></td>
				<td>#977C00</td>
				<td bgcolor="#bb5e00"></td>
				<td>#BB5E00</td>
				<td bgcolor="#a23400"></td>
				<td>#A23400</td>
			</tr>
			<tr>
				<td bgcolor="#73bf00"></td>
				<td>#73BF00</td>
				<td bgcolor="#8c8c00"></td>
				<td>#8C8C00</td>
				<td bgcolor="#ae8f00"></td>
				<td>#AE8F00</td>
				<td bgcolor="#d26900"></td>
				<td>#D26900</td>
				<td bgcolor="#bb3d00"></td>
				<td>#BB3D00</td>
			</tr>
			<tr>
				<td bgcolor="#82d900"></td>
				<td>#82D900</td>
				<td bgcolor="#a6a600"></td>
				<td>#A6A600</td>
				<td bgcolor="#c6a300"></td>
				<td>#C6A300</td>
				<td bgcolor="#ea7500"></td>
				<td>#EA7500</td>
				<td bgcolor="#d94600"></td>
				<td>#D94600</td>
			</tr>
			<tr>
				<td bgcolor="#8cea00"></td>
				<td>#8CEA00</td>
				<td bgcolor="#c4c400"></td>
				<td>#C4C400</td>
				<td bgcolor="#d9b300"></td>
				<td>#D9B300</td>
				<td bgcolor="#ff8000"></td>
				<td>#FF8000</td>
				<td bgcolor="#f75000"></td>
				<td>#F75000</td>
			</tr>
			<tr>
				<td bgcolor="#9aff02"></td>
				<td>#9AFF02</td>
				<td bgcolor="#e1e100"></td>
				<td>#E1E100</td>
				<td bgcolor="#eac100"></td>
				<td>#EAC100</td>
				<td bgcolor="#ff9224"></td>
				<td>#FF9224</td>
				<td bgcolor="#ff5809"></td>
				<td>#FF5809</td>
			</tr>
			<tr>
				<td bgcolor="#a8ff24"></td>
				<td>#A8FF24</td>
				<td bgcolor="#f9f900"></td>
				<td>#F9F900</td>
				<td bgcolor="#ffd306"></td>
				<td>#FFD306</td>
				<td bgcolor="#ffa042"></td>
				<td>#FFA042</td>
				<td bgcolor="#ff8040"></td>
				<td>#FF8040</td>
			</tr>
			<tr>
				<td bgcolor="#b7ff4a"></td>
				<td>#B7FF4A</td>
				<td bgcolor="#ffff37"></td>
				<td>#FFFF37</td>
				<td bgcolor="#ffdc35"></td>
				<td>#FFDC35</td>
				<td bgcolor="#ffaf60"></td>
				<td>#FFAF60</td>
				<td bgcolor="#ff8f59"></td>
				<td>#FF8F59</td>
			</tr>
			<tr>
				<td bgcolor="#c2ff68"></td>
				<td>#C2FF68</td>
				<td bgcolor="#ffff6f"></td>
				<td>#FFFF6F</td>
				<td bgcolor="#ffe153"></td>
				<td>#FFE153</td>
				<td bgcolor="#ffbb77"></td>
				<td>#FFBB77</td>
				<td bgcolor="#ff9d6f"></td>
				<td>#FF9D6F</td>
			</tr>
			<tr>
				<td bgcolor="#ccff80"></td>
				<td>#CCFF80</td>
				<td bgcolor="#ffff93"></td>
				<td>#FFFF93</td>
				<td bgcolor="#ffe66f"></td>
				<td>#FFE66F</td>
				<td bgcolor="#ffc78e"></td>
				<td>#FFC78E</td>
				<td bgcolor="#ffad86"></td>
				<td>#FFAD86</td>
			</tr>
			<tr>
				<td bgcolor="#d3ff93"></td>
				<td>#D3FF93</td>
				<td bgcolor="#ffffaa"></td>
				<td>#FFFFAA</td>
				<td bgcolor="#ffed97"></td>
				<td>#FFED97</td>
				<td bgcolor="#ffd1a4"></td>
				<td>#FFD1A4</td>
				<td bgcolor="#ffbd9d"></td>
				<td>#FFBD9D</td>
			</tr>
			<tr>
				<td bgcolor="#deffac"></td>
				<td>#DEFFAC</td>
				<td bgcolor="#ffffb9"></td>
				<td>#FFFFB9</td>
				<td bgcolor="#fff0ac"></td>
				<td>#FFF0AC</td>
				<td bgcolor="#ffdcb9"></td>
				<td>#FFDCB9</td>
				<td bgcolor="#ffcbb3"></td>
				<td>#FFCBB3</td>
			</tr>
			<tr>
				<td bgcolor="#e8ffc4"></td>
				<td>#E8FFC4</td>
				<td bgcolor="#ffffce"></td>
				<td>#FFFFCE</td>
				<td bgcolor="#fff4c1"></td>
				<td>#FFF4C1</td>
				<td bgcolor="#ffe4ca"></td>
				<td>#FFE4CA</td>
				<td bgcolor="#ffdac8"></td>
				<td>#FFDAC8</td>
			</tr>
			<tr>
				<td bgcolor="#efffd7"></td>
				<td>#EFFFD7</td>
				<td bgcolor="#ffffdf"></td>
				<td>#FFFFDF</td>
				<td bgcolor="#fff8d7"></td>
				<td>#FFF8D7</td>
				<td bgcolor="#ffeedd"></td>
				<td>#FFEEDD</td>
				<td bgcolor="#ffe6d9"></td>
				<td>#FFE6D9</td>
			</tr>
			<tr>
				<td bgcolor="#f5ffe8"></td>
				<td>#F5FFE8</td>
				<td bgcolor="#fffff4"></td>
				<td>#FFFFF4</td>
				<td bgcolor="#fffcec"></td>
				<td>#FFFCEC</td>
				<td bgcolor="#fffaf4"></td>
				<td>#FFFAF4</td>
				<td bgcolor="#fff3ee"></td>
				<td>#FFF3EE</td>
			</tr>
			<tr>
				<td bgcolor="#613030"></td>
				<td>#613030</td>
				<td bgcolor="#616130"></td>
				<td>#616130</td>
				<td bgcolor="#336666"></td>
				<td>#336666</td>
				<td bgcolor="#484891"></td>
				<td>#484891</td>
				<td bgcolor="#6c3365"></td>
				<td>#6C3365</td>
			</tr>
			<tr>
				<td bgcolor="#743a3a"></td>
				<td>#743A3A</td>
				<td bgcolor="#707038"></td>
				<td>#707038</td>
				<td bgcolor="#3d7878"></td>
				<td>#3D7878</td>
				<td bgcolor="#5151a2"></td>
				<td>#5151A2</td>
				<td bgcolor="#7e3d76"></td>
				<td>#7E3D76</td>
			</tr>
			<tr>
				<td bgcolor="#804040"></td>
				<td>#804040</td>
				<td bgcolor="#808040"></td>
				<td>#808040</td>
				<td bgcolor="#408080"></td>
				<td>#408080</td>
				<td bgcolor="#5a5aad"></td>
				<td>#5A5AAD</td>
				<td bgcolor="#8f4586"></td>
				<td>#8F4586</td>
			</tr>
			<tr>
				<td bgcolor="#984b4b"></td>
				<td>#984B4B</td>
				<td bgcolor="#949449"></td>
				<td>#949449</td>
				<td bgcolor="#4f9d9d"></td>
				<td>#4F9D9D</td>
				<td bgcolor="#7373b9"></td>
				<td>#7373B9</td>
				<td bgcolor="#9f4d95"></td>
				<td>#9F4D95</td>
			</tr>
			<tr>
				<td bgcolor="#ad5a5a"></td>
				<td>#AD5A5A</td>
				<td bgcolor="#a5a552"></td>
				<td>#A5A552</td>
				<td bgcolor="#5cadad"></td>
				<td>#5CADAD</td>
				<td bgcolor="#8080c0"></td>
				<td>#8080C0</td>
				<td bgcolor="#ae57a4"></td>
				<td>#AE57A4</td>
			</tr>
			<tr>
				<td bgcolor="#b87070"></td>
				<td>#B87070</td>
				<td bgcolor="#afaf61"></td>
				<td>#AFAF61</td>
				<td bgcolor="#6fb7b7"></td>
				<td>#6FB7B7</td>
				<td bgcolor="#9999cc"></td>
				<td>#9999CC</td>
				<td bgcolor="#b766ad"></td>
				<td>#B766AD</td>
			</tr>
			<tr>
				<td bgcolor="#c48888"></td>
				<td>#C48888</td>
				<td bgcolor="#b9b973"></td>
				<td>#B9B973</td>
				<td bgcolor="#81c0c0"></td>
				<td>#81C0C0</td>
				<td bgcolor="#a6a6d2"></td>
				<td>#A6A6D2</td>
				<td bgcolor="#c07ab8"></td>
				<td>#C07AB8</td>
			</tr>
			<tr>
				<td bgcolor="#cf9e9e"></td>
				<td>#CF9E9E</td>
				<td bgcolor="#c2c287"></td>
				<td>#C2C287</td>
				<td bgcolor="#95caca"></td>
				<td>#95CACA</td>
				<td bgcolor="#b8b8dc"></td>
				<td>#B8B8DC</td>
				<td bgcolor="#ca8ec2"></td>
				<td>#CA8EC2</td>
			</tr>
			<tr>
				<td bgcolor="#d9b3b3"></td>
				<td>#D9B3B3</td>
				<td bgcolor="#cdcd9a"></td>
				<td>#CDCD9A</td>
				<td bgcolor="#a3d1d1"></td>
				<td>#A3D1D1</td>
				<td bgcolor="#c7c7e2"></td>
				<td>#C7C7E2</td>
				<td bgcolor="#d2a2cc"></td>
				<td>#D2A2CC</td>
			</tr>
			<tr>
				<td bgcolor="#e1c4c4"></td>
				<td>#E1C4C4</td>
				<td bgcolor="#d6d6ad"></td>
				<td>#D6D6AD</td>
				<td bgcolor="#b3d9d9"></td>
				<td>#B3D9D9</td>
				<td bgcolor="#d8d8eb"></td>
				<td>#D8D8EB</td>
				<td bgcolor="#dab1d5"></td>
				<td>#DAB1D5</td>
			</tr>
			<tr>
				<td bgcolor="#ebd6d6"></td>
				<td>#EBD6D6</td>
				<td bgcolor="#dedebe"></td>
				<td>#DEDEBE</td>
				<td bgcolor="#c4e1e1"></td>
				<td>#C4E1E1</td>
				<td bgcolor="#e6e6f2"></td>
				<td>#E6E6F2</td>
				<td bgcolor="#e2c2de"></td>
				<td>#E2C2DE</td>
			</tr>
			<tr>
				<td bgcolor="#f2e6e6"></td>
				<td>#F2E6E6</td>
				<td bgcolor="#e8e8d0"></td>
				<td>#E8E8D0</td>
				<td bgcolor="#d1e9e9"></td>
				<td>#D1E9E9</td>
				<td bgcolor="#f3f3fa"></td>
				<td>#F3F3FA</td>
				<td bgcolor="#ebd3e8"></td>
				<td>#EBD3E8</td>
			</tr>
		</tbody>
	</table>
	</p>
	<p>
	<table cellspacing="0" cellpadding="5" width="600" align="center"
		border="1">
		<tbody>
			<tr>
				<td align="middle" colspan="6"><b>颜色代码表Ⅱ</b></td>
			</tr>
			<tr>
				<td align="middle" bgcolor="red" cellspacing="2">
					<div align="center">
						<font color="#ffff00">red</font>
					</div>
				</td>
				<td align="middle" bgcolor="green" cellspacing="2">
					<div align="center">
						<font color="#000000">green</font>
					</div>
				</td>
				<td align="middle" bgcolor="blue" cellspacing="2">
					<div align="center">
						<font color="#ffff00">blue</font>
					</div>
				</td>
				<td align="middle" bgcolor="magenta" cellspacing="2">
					<div align="center">
						<font color="#ffff00">magenta</font>
					</div>
				</td>
				<td align="middle" bgcolor="yellow" cellspacing="2">
					<div align="center">
						<font color="#000000">yellow</font>
					</div>
				</td>
				<td align="middle" bgcolor="chocolate" cellspacing="2">
					<div align="center">
						<font color="#000000">chocolate</font>
					</div>
				</td>
			</tr>
			<tr>
				<td align="middle" bgcolor="black" cellspacing="2">
					<div align="center">
						<font color="#ffff00">black</font>
					</div>
				</td>
				<td align="middle" bgcolor="aquamarine" cellspacing="2">
					<div align="center">
						<font color="#000000">aquamarine</font>
					</div>
				</td>
				<td align="middle" bgcolor="lime" cellspacing="2">
					<div align="center">
						<font color="#000000">lime</font>
					</div>
				</td>
				<td align="middle" bgcolor="fuchsia" cellspacing="2">
					<div align="center">
						<font color="#ffff00">fuchsia</font>
					</div>
				</td>
				<td align="middle" bgcolor="#b0a00" cellspacing="2">
					<div align="center">
						<font color="#ffff00">brass</font>
					</div>
				</td>
				<td align="middle" bgcolor="azure" cellspacing="2">
					<div align="center">
						<font color="#ff0000">azure</font>
					</div>
				</td>
			</tr>
			<tr>
				<td align="middle" bgcolor="brown" cellspacing="2">
					<div align="center">
						<font color="#ffff00">brown</font>
					</div>
				</td>
				<td align="middle" bgcolor="#b0000e" cellspacing="2">
					<div align="center">
						<font color="#ffff00">bronze</font>
					</div>
				</td>
				<td align="middle" bgcolor="deeppink" cellspacing="2">
					<div align="center">
						<font color="#000000">deeppink</font>
					</div>
				</td>
				<td align="middle" bgcolor="aliceblue" cellspacing="2">
					<div align="center">
						<font color="#000000">aliceblue</font>
					</div>
				</td>
				<td align="middle" bgcolor="gray" cellspacing="2">
					<div align="center">
						<font color="#ffff00">gray</font>
					</div>
				</td>
				<td align="middle" bgcolor="#c000e0" cellspacing="2">
					<div align="center">
						<font color="#ffff00">copper</font>
					</div>
				</td>
			</tr>
			<tr>
				<td align="middle" bgcolor="coral" cellspacing="2">
					<div align="center">
						<font color="#000000">coral</font>
					</div>
				</td>
				<td align="middle" bgcolor="#fed0a0" cellspacing="2">
					<div align="center">
						<font color="#ff0000">feldspar</font>
					</div>
				</td>
				<td align="middle" bgcolor="orange" cellspacing="2">
					<div align="center">
						<font color="#000000">orange</font>
					</div>
				</td>
				<td align="middle" bgcolor="orchid" cellspacing="2">
					<div align="center">
						<font color="#000000">orchid</font>
					</div>
				</td>
				<td align="middle" bgcolor="pink" cellspacing="2">
					<div align="center">
						<font color="#ff0000">pink</font>
					</div>
				</td>
				<td align="middle" bgcolor="plum" cellspacing="2">
					<div align="center">
						<font color="#000000">plum</font>
					</div>
				</td>
			</tr>
			<tr>
				<td align="middle" bgcolor="#00a000" cellspacing="2">
					<div align="center">
						<font color="#ffff00">quartz</font>
					</div>
				</td>
				<td align="middle" bgcolor="purple" cellspacing="2">
					<div align="center">
						<font color="#ffff00">purple</font>
					</div>
				</td>
				<td align="middle" bgcolor="aliceblue" cellspacing="2">
					<div align="center">
						<font color="#000000">aliceblue</font>
					</div>
				</td>
				<td align="middle" bgcolor="#a00000" cellspacing="2">
					<div align="center">
						<font color="#ffff00">antiquewith</font>
					</div>
				</td>
				<td align="middle" bgcolor="blanchedalmond" cellspacing="2">
					<div align="center">
						<font color="#ff0000">blanchedalmond</font>
					</div>
				</td>
				<td align="middle" bgcolor="blueviolet" cellspacing="2">
					<div align="center">
						<font color="#000000">blueviolet</font>
					</div>
				</td>
			</tr>
			<tr>
				<td align="middle" bgcolor="beige" cellspacing="2">
					<div align="center">
						<font color="#ff0000">beige</font>
					</div>
				</td>
				<td align="middle" bgcolor="burlywood" cellspacing="2">
					<div align="center">
						<font color="#ffff00">burlywood</font>
					</div>
				</td>
				<td align="middle" bgcolor="bisque" cellspacing="2">
					<div align="center">
						<font color="#000000">bisque</font>
					</div>
				</td>
				<td align="middle" bgcolor="cadetblue" cellspacing="2">
					<div align="center">
						<font color="#ffff00">cadetblue</font>
					</div>
				</td>
				<td align="middle" bgcolor="pink" cellspacing="2">
					<div align="center">
						<font color="#000000">pink</font>
					</div>
				</td>
				<td align="middle" bgcolor="saddlebrown" cellspacing="2">
					<div align="center">
						<font color="#ffff00">saddlebrown</font>
					</div>
				</td>
			</tr>
			<tr>
				<td align="middle" bgcolor="royalblue" cellspacing="2">
					<div align="center">
						<font color="#ffff00">royalblue</font>
					</div>
				</td>
				<td align="middle" bgcolor="rosybrown" cellspacing="2">
					<div align="center">
						<font color="#000000">rosybrown</font>
					</div>
				</td>
				<td align="middle" bgcolor="purple" cellspacing="2">
					<div align="center">
						<font color="#ffff00">purple</font>
					</div>
				</td>
				<td align="middle" bgcolor="#0e0eed" cellspacing="2">
					<div align="center">
						<font color="#ffff00">orengered</font>
					</div>
				</td>
				<td align="middle" bgcolor="olivedrab" cellspacing="2">
					<div align="center">
						<font color="#000000">olivedrab</font>
					</div>
				</td>
				<td align="middle" bgcolor="powderblue" cellspacing="2">
					<div align="center">
						<font color="#ff0000">powderblue</font>
					</div>
				</td>
			</tr>
			<tr>
				<td align="middle" bgcolor="peachpuff" cellspacing="2">
					<div align="center">
						<font color="#000000">peachpuff</font>
					</div>
				</td>
				<td align="middle" bgcolor="papayawhip" cellspacing="2">
					<div align="center">
						<font color="#ff0000">papayawhip</font>
					</div>
				</td>
				<td align="middle" bgcolor="paleturquoise" cellspacing="2">
					<div align="center">
						<font color="#ff0000">paleturquoise</font>
					</div>
				</td>
				<td align="middle" bgcolor="palevioletred" cellspacing="2">
					<div align="center">
						<font color="#000000">palevioletred</font>
					</div>
				</td>
				<td align="middle" bgcolor="palegreen" cellspacing="2">
					<div align="center">
						<font color="#ff0000">palegreen</font>
					</div>
				</td>
				<td align="middle" bgcolor="#a0b0e0" cellspacing="2">
					<div align="center">
						<font color="#ffff00">navyblue</font>
					</div>
				</td>
			</tr>
			<tr>
				<td align="middle" bgcolor="navajowhite" cellspacing="2">
					<div align="center">
						<font color="#000000">navajowhite</font>
					</div>
				</td>
				<td align="middle" bgcolor="#a00d00" cellspacing="2">
					<div align="center">
						<font color="#ffff00">palegodenrod</font>
					</div>
				</td>
				<td align="middle" bgcolor="#00e0ed" cellspacing="2">
					<div align="center">
						<font color="#ff0000">violetred</font>
					</div>
				</td>
				<td align="middle" bgcolor="yellowgreen" cellspacing="2">
					<div align="center">
						<font color="#ff0000">yellowgreen</font>
					</div>
				</td>
				<td align="middle" bgcolor="tomato" cellspacing="2">
					<div align="center">
						<font color="#ffff00">tomato</font>
					</div>
				</td>
				<td align="middle" bgcolor="turquoise" cellspacing="2">
					<div align="center">
						<font color="#ff0000">turquoise</font>
					</div>
				</td>
			</tr>
			<tr>
				<td align="middle" bgcolor="thistle" cellspacing="2">
					<div align="center">
						<font color="#ff0000">thistle</font>
					</div>
				</td>
				<td align="middle" bgcolor="springgreen" cellspacing="2">
					<div align="center">
						<font color="#ff0000">springgreen</font>
					</div>
				</td>
				<td align="middle" bgcolor="steelblue" cellspacing="2">
					<div align="center">
						<font color="#000000">steelblue</font>
					</div>
				</td>
				<td align="middle" bgcolor="salmon" cellspacing="2">
					<div align="center">
						<font color="#000000">salmon</font>
					</div>
				</td>
				<td align="middle" bgcolor="#ca0e00" cellspacing="2">
					<div align="center">
						<font color="#ffff00">scarlet</font>
					</div>
				</td>
				<td align="middle" bgcolor="sienna" cellspacing="2">
					<div align="center">
						<font color="#ffff00">sienna</font>
					</div>
				</td>
			</tr>
			<tr>
				<td align="middle" bgcolor="silver" cellspacing="2">
					<div align="center">
						<font color="#000000">silver</font>
					</div>
				</td>
				<td align="middle" bgcolor="tan" cellspacing="2">
					<div align="center">
						<font color="#ffff00">tan</font>
					</div>
				</td>
				<td align="middle" bgcolor="thistle" cellspacing="2">
					<div align="center">
						<font color="#000000">thistle</font>
					</div>
				</td>
				<td align="middle" bgcolor="turquoise" cellspacing="2">
					<div align="center">
						<font color="#ff0000">turquoise</font>
					</div>
				</td>
				<td align="middle" bgcolor="violet" cellspacing="2">
					<div align="center">
						<font color="#ffff00">violet</font>
					</div>
				</td>
				<td align="middle" bgcolor="snow" cellspacing="2">
					<div align="center">
						<font color="#ff0000">snow</font>
					</div>
				</td>
			</tr>
			<tr>
				<td align="middle" bgcolor="salmon" cellspacing="2">
					<div align="center">
						<font color="#000000">salmon</font>
					</div>
				</td>
				<td align="middle" bgcolor="#ca0e00" cellspacing="2">
					<div align="center">
						<font color="#ffff00">scarlet</font>
					</div>
				</td>
				<td align="middle" bgcolor="sienna" cellspacing="2">
					<div align="center">
						<font color="#ffff00">sienna</font>
					</div>
				</td>
				<td align="middle" bgcolor="silver" cellspacing="2">
					<div align="center">
						<font color="#000000">silver</font>
					</div>
				</td>
				<td align="middle" bgcolor="tan" cellspacing="2">
					<div align="center">
						<font color="#ffff00">tan</font>
					</div>
				</td>
				<td align="middle" bgcolor="thistle" cellspacing="2">
					<div align="center">
						<font color="#000000">thistle</font>
					</div>
				</td>
			</tr>
			<tr>
				<td align="middle" bgcolor="turquoise" cellspacing="2">
					<div align="center">
						<font color="#ff0000">turquoise</font>
					</div>
				</td>
				<td align="middle" bgcolor="violet" cellspacing="2">
					<div align="center">
						<font color="#ffff00">violet</font>
					</div>
				</td>
				<td align="middle" bgcolor="chartreuse" cellspacing="2">
					<div align="center">
						<font color="#ff0000">chartreuse</font>
					</div>
				</td>
				<td align="middle" bgcolor="darkslategray" cellspacing="2">
					<div align="center">
						<font color="#ffff00">darkslategray</font>
					</div>
				</td>
				<td align="middle" bgcolor="darkseagreen" cellspacing="2">
					<div align="center">
						<font color="#ffff00">darkseagreen</font>
					</div>
				</td>
				<td align="middle" bgcolor="darkred" cellspacing="2">
					<div align="center">
						<font color="#ffff00">darkred</font>
					</div>
				</td>
			</tr>
			<tr>
				<td align="middle" bgcolor="mediumslateblue" cellspacing="2">
					<div align="center">
						<font color="#000000">mediumslateblue</font>
					</div>
				</td>
				<td align="middle" bgcolor="mediumvioletred" cellspacing="2">
					<div align="center">
						<font color="#ffff00">mediumvioletred</font>
					</div>
				</td>
				<td align="middle" bgcolor="oldlace" cellspacing="2">
					<div align="center">
						<font color="#ff0000">oldlace</font>
					</div>
				</td>
				<td align="middle" bgcolor="#0a0000" cellspacing="2">
					<div align="center">
						<font color="#ffff00">maroom</font>
					</div>
				</td>
				<td align="middle" bgcolor="goldenrod" cellspacing="2">
					<div align="center">
						<font color="#ffff00">goldenrod</font>
					</div>
				</td>
				<td align="middle" bgcolor="wheat" cellspacing="2">
					<div align="center">
						<font color="#ff0000">wheat</font>
					</div>
				</td>
			</tr>
			<tr>
				<td align="middle" bgcolor="whitesmoke" cellspacing="2">
					<div align="center">
						<font color="#ff0000">whitesmoke</font>
					</div>
				</td>
				<td align="middle" bgcolor="orange" cellspacing="2">
					<div align="center">
						<font color="#000000">orange</font>
					</div>
				</td>
				<td align="middle" bgcolor="moccasin" cellspacing="2">
					<div align="center">
						<font color="#ff0000">moccasin</font>
					</div>
				</td>
				<td align="middle" bgcolor="mistyrose" cellspacing="2">
					<div align="center">
						<font color="#ff0000">mistyrose</font>
					</div>
				</td>
				<td align="middle" bgcolor="mintcream" cellspacing="2">
					<div align="center">
						<font color="#000000">mintcream</font>
					</div>
				</td>
				<td align="middle" bgcolor="midnightblue" cellspacing="2">
					<div align="center">
						<font color="#ffffff">midnightblue</font>
					</div>
				</td>
			</tr>
			<tr>
				<td align="middle" bgcolor="dimgray" cellspacing="2">
					<div align="center">
						<font color="#000000">dimgray</font>
					</div>
				</td>
				<td align="middle" bgcolor="darksalmon" cellspacing="2">
					<div align="center">
						<font color="#ff0000">darksalmon</font>
					</div>
				</td>
				<td align="middle" bgcolor="slategray" cellspacing="2">
					<div align="center">
						<font color="#000000">slategray</font>
					</div>
				</td>
				<td align="middle" bgcolor="skyblue" cellspacing="2">
					<div align="center">
						<font color="#ff0000">skyblue</font>
					</div>
				</td>
				<td align="middle" bgcolor="sienna" cellspacing="2">
					<div align="center">
						<font color="#ffff00">sienna</font>
					</div>
				</td>
				<td align="middle" bgcolor="seashell" cellspacing="2">
					<div align="center">
						<font color="#000000">seashell</font>
					</div>
				</td>
			</tr>
			<tr>
				<td align="middle" bgcolor="salmon" cellspacing="2">
					<div align="center">
						<font color="#ffff00">salmon</font>
					</div>
				</td>
				<td align="middle" bgcolor="seagreen" cellspacing="2">
					<div align="center">
						<font color="#ffff00">seagreen</font>
					</div>
				</td>
				<td align="middle" bgcolor="sandybrown" cellspacing="2">
					<div align="center">
						<font color="#000000">sandybrown</font>
					</div>
				</td>
				<td align="middle" bgcolor="firebrick" cellspacing="2">
					<div align="center">
						<font color="#ffff00">firebrick</font>
					</div>
				</td>
				<td align="middle" bgcolor="gold" cellspacing="2">
					<div align="center">
						<font color="#000000">gold</font>
					</div>
				</td>
				<td align="middle" bgcolor="khaki" cellspacing="2">
					<div align="center">
						<font color="#ff0000">khaki</font>
					</div>
				</td>
			</tr>
			<tr>
				<td align="middle" bgcolor="#0a0000" cellspacing="2">
					<div align="center">
						<font color="#ffff00">maroom</font>
					</div>
				</td>
				<td align="middle" bgcolor="goldenrod" cellspacing="2">
					<div align="center">
						<font color="#ffff00">goldenrod</font>
					</div>
				</td>
				<td align="middle" bgcolor="wheat" cellspacing="2">
					<div align="center">
						<font color="#ff0000">wheat</font>
					</div>
				</td>
				<td align="middle" bgcolor="whitesmoke" cellspacing="2">
					<div align="center">
						<font color="#ff0000">whitesmoke</font>
					</div>
				</td>
				<td align="middle" bgcolor="mediumturquoise" cellspacing="2">
					<div align="center">
						<font color="#000000">mediumturquoise</font>
					</div>
				</td>
				<td align="middle" bgcolor="navy" cellspacing="2">
					<div align="center">
						<font color="#ffff00">navy</font>
					</div>
				</td>
			</tr>
			<tr>
				<td align="middle" bgcolor="mediumspringgreen" cellspacing="2">
					<div align="center">
						<font color="#ff0000">mediumspringgreen</font>
					</div>
				</td>
				<td align="middle" bgcolor="mediumseagreen" cellspacing="2">
					<div align="center">
						<font color="#000000">mediumseagreen</font>
					</div>
				</td>
				<td align="middle" bgcolor="#ed0000" cellspacing="2">
					<div align="center">
						<font color="#ffff00">mediumpurpul</font>
					</div>
				</td>
				<td align="middle" bgcolor="peru" cellspacing="2">
					<div align="center">
						<font color="#ff0000">peru</font>
					</div>
				</td>
				<td align="middle" bgcolor="mediumorchid" cellspacing="2">
					<div align="center">
						<font color="#000000">mediumorchid</font>
					</div>
				</td>
				<td align="middle" bgcolor="mediumblue" cellspacing="2">
					<div align="center">
						<font color="#ffff00">mediumblue</font>
					</div>
				</td>
			</tr>
			<tr>
				<td align="middle" bgcolor="mediumaquamarine" cellspacing="2">
					<div align="center">
						<font color="#000000">mediumaquamarine</font>
					</div>
				</td>
				<td align="middle" bgcolor="maroon" cellspacing="2">
					<div align="center">
						<font color="#ffff00">maroon</font>
					</div>
				</td>
				<td align="middle" bgcolor="limegreen" cellspacing="2">
					<div align="center">
						<font color="#ffff00">limegreen</font>
					</div>
				</td>
				<td align="middle" bgcolor="lightyellow" cellspacing="2">
					<div align="center">
						<font color="#ff0000">lightyellow</font>
					</div>
				</td>
				<td align="middle" bgcolor="lightsteelblue" cellspacing="2">
					<div align="center">
						<font color="#ffff00">lightsteelblue</font>
					</div>
				</td>
				<td align="middle" bgcolor="magenta" cellspacing="2">
					<div align="center">
						<font color="#ffff00">magenta</font>
					</div>
				</td>
			</tr>
			<tr>
				<td align="middle" bgcolor="#0000b0" cellspacing="2">
					<div align="center">
						<font color="#ffff00">lightslateblue</font>
					</div>
				</td>
				<td align="middle" bgcolor="lightslategray" cellspacing="2">
					<div align="center">
						<font color="#ffff00">lightslategray</font>
					</div>
				</td>
				<td align="middle" bgcolor="lightskyblue" cellspacing="2">
					<div align="center">
						<font color="#000000">lightskyblue</font>
					</div>
				</td>
				<td align="middle" bgcolor="#00e0" cellspacing="2">
					<div align="center">
						<font color="#ff0000">inen</font>
					</div>
				</td>
				<td align="middle" bgcolor="lightseagreen" cellspacing="2">
					<div align="center">
						<font color="#ffff00">lightseagreen</font>
					</div>
				</td>
				<td align="middle" bgcolor="lightsalmon" cellspacing="2">
					<div align="center">
						<font color="#ffff00">lightsalmon</font>
					</div>
				</td>
			</tr>
			<tr>
				<td align="middle" bgcolor="lightpink" cellspacing="2">
					<div align="center">
						<font color="#ff0000">lightpink</font>
					</div>
				</td>
				<td align="middle" bgcolor="plum" cellspacing="2">
					<div align="center">
						<font color="#ffff00">plum</font>
					</div>
				</td>
				<td align="middle" bgcolor="#0000a0" cellspacing="2">
					<div align="center">
						<font color="#ffff00">lightgray</font>
					</div>
				</td>
				<td align="middle" bgcolor="lightgreen" cellspacing="2">
					<div align="center">
						<font color="#ff0000">lightgreen</font>
					</div>
				</td>
				<td align="middle" bgcolor="#00dee0" cellspacing="2">
					<div align="center">
						<font color="#ffff00">lightgodenrodyellow</font>
					</div>
				</td>
				<td align="middle" bgcolor="indianred" cellspacing="2">
					<div align="center">
						<font color="#ffff00">indianred</font>
					</div>
				</td>
			</tr>
			<tr>
				<td align="middle" bgcolor="lavender" cellspacing="2">
					<div align="center">
						<font color="#ff0000">lavender</font>
					</div>
				</td>
				<td align="middle" bgcolor="lightblue" cellspacing="2">
					<div align="center">
						<font color="#000000">lightblue</font>
					</div>
				</td>
				<td align="middle" bgcolor="lavenderblush" cellspacing="2">
					<div align="center">
						<font color="#ff0000">lavenderblush</font>
					</div>
				</td>
				<td align="middle" bgcolor="lightcoral" cellspacing="2">
					<div align="center">
						<font color="#ff0000">lightcoral</font>
					</div>
				</td>
				<td align="middle" bgcolor="lightcyan" cellspacing="2">
					<div align="center">
						<font color="#000000">lightcyan</font>
					</div>
				</td>
				<td align="middle" bgcolor="#00ded0" cellspacing="2">
					<div align="center">
						<font color="#ffff00">lightgodenrod</font>
					</div>
				</td>
			</tr>
			<tr>
				<td align="middle" bgcolor="hotpink" cellspacing="2">
					<div align="center">
						<font color="#000000">hotpink</font>
					</div>
				</td>
				<td align="middle" bgcolor="greenyellow" cellspacing="2">
					<div align="center">
						<font color="#ff0000">greenyellow</font>
					</div>
				</td>
				<td align="middle" bgcolor="lemonchiffon" cellspacing="2">
					<div align="center">
						<font color="#ff0000">lemonchiffon</font>
					</div>
				</td>
				<td align="middle" bgcolor="lawngreen" cellspacing="2">
					<div align="center">
						<font color="#000000">lawngreen</font>
					</div>
				</td>
				<td align="middle" bgcolor="darkorchid" cellspacing="2">
					<div align="center">
						<font color="#ffff00">darkorchid</font>
					</div>
				</td>
				<td align="middle" bgcolor="deepskyblue" cellspacing="2">
					<div align="center">
						<font color="#ffff00">deepskyblue</font>
					</div>
				</td>
			</tr>
			<tr>
				<td align="middle" bgcolor="honeydew" cellspacing="2">
					<div align="center">
						<font color="#000000">honeydew</font>
					</div>
				</td>
				<td align="middle" bgcolor="#00e00d" cellspacing="2">
					<div align="center">
						<font color="#ff0000">golenrod</font>
					</div>
				</td>
				<td align="middle" bgcolor="forestgreen" cellspacing="2">
					<div align="center">
						<font color="#000000">forestgreen</font>
					</div>
				</td>
				<td align="middle" bgcolor="#00000e" cellspacing="2">
					<div align="center">
						<font color="#ffff00">gostwhite</font>
					</div>
				</td>
				<td align="middle" bgcolor="greenyellow" cellspacing="2">
					<div align="center">
						<font color="#ff0000">greenyellow</font>
					</div>
				</td>
				<td align="middle" bgcolor="gainsboro" cellspacing="2">
					<div align="center">
						<font color="#000000">gainsboro</font>
					</div>
				</td>
			</tr>
			<tr>
				<td align="middle" bgcolor="firebrick" cellspacing="2">
					<div align="center">
						<font color="#ffff00">firebrick</font>
					</div>
				</td>
				<td align="middle" bgcolor="dodgerblue" cellspacing="2">
					<div align="center">
						<font color="#ffff00">dodgerblue</font>
					</div>
				</td>
				<td align="middle" bgcolor="darkturquoise" cellspacing="2">
					<div align="center">
						<font color="#000000">darkturquoise</font>
					</div>
				</td>
				<td align="middle" bgcolor="darkslateblue" cellspacing="2">
					<div align="center">
						<font color="#ffff00">darkslateblue</font>
					</div>
				</td>
				<td align="middle" bgcolor="darkslategray" cellspacing="2">
					<div align="center">
						<font color="#ffffff">darkslategray</font>
					</div>
				</td>
				<td align="middle" bgcolor="darkseagreen" cellspacing="2">
					<div align="center">
						<font color="#ffff00">darkseagreen</font>
					</div>
				</td>
			</tr>
			<tr>
				<td align="middle" bgcolor="darkred" cellspacing="2">
					<div align="center">
						<font color="#ffff00">darkred</font>
					</div>
				</td>
				<td align="middle" bgcolor="darkorchid" cellspacing="2">
					<div align="center">
						<font color="#000000">darkorchid</font>
					</div>
				</td>
				<td align="middle" bgcolor="#da000e" cellspacing="2">
					<div align="center">
						<font color="#ffff00">darkorenge</font>
					</div>
				</td>
				<td align="middle" bgcolor="darkslateblue" cellspacing="2">
					<div align="center">
						<font color="#ffff00">darkslateblue</font>
					</div>
				</td>
				<td align="middle" bgcolor="darkviolet" cellspacing="2">
					<div align="center">
						<font color="#000000">darkviolet</font>
					</div>
				</td>
				<td align="middle" bgcolor="floralwhite" cellspacing="2">
					<div align="center">
						<font color="#ff0000">floralwhite</font>
					</div>
				</td>
			</tr>
			<tr>
				<td align="middle" bgcolor="cyan" cellspacing="2">
					<div align="center">
						<font color="#ff0000">cyan</font>
					</div>
				</td>
				<td align="middle" bgcolor="darkgoldenrod" cellspacing="2">
					<div align="center">
						<font color="#000000">darkgoldenrod</font>
					</div>
				</td>
				<td align="middle" bgcolor="cornsilk" cellspacing="2">
					<div align="center">
						<font color="#ff0000">cornsilk</font>
					</div>
				</td>
				<td align="middle" bgcolor="darkolivegreen" cellspacing="2">
					<div align="center">
						<font color="#ffff00">darkolivegreen</font>
					</div>
				</td>
				<td align="middle" width="60" bgcolor="#b0ed00" cellspacing="2">
					<p align="center">
						<font color="#000000">bisquedarkgray</font>
					</p>
				</td>
				<td align="middle" bgcolor="darkblue" cellspacing="2">
					<div align="center">
						<font color="#ffff00">darkblue</font>
					</div>
				</td>
			</tr>
			<tr>
				<td align="middle" bgcolor="darkcyan" cellspacing="2">
					<div align="center">
						<font color="#ffff00">darkcyan</font>
					</div>
				</td>
				<td align="middle" bgcolor="darkgreen" cellspacing="2">
					<div align="center">
						<font color="#000000">darkgreen</font>
					</div>
				</td>
				<td align="middle" bgcolor="#da0000" cellspacing="2">
					<div align="center">
						<font color="#ffff00">darkhaki</font>
					</div>
				</td>
				<td align="middle" bgcolor="ivory" cellspacing="2">
					<div align="center">
						<font color="#ff0000">ivory</font>
					</div>
				</td>
				<td align="middle" bgcolor="darkmagenta" cellspacing="2">
					<div align="center">
						<font color="#ffff00">darkmagenta</font>
					</div>
				</td>
				<td align="middle" bgcolor="darkgray" cellspacing="2">
					<div align="center">
						<font color="#ffff00">darkgray</font>
					</div>
				</td>
			</tr>
			<tr>
				<td align="middle" bgcolor="#c000b0" cellspacing="2">
					<div align="center">
						<font color="#ff0000">cornfloewrblue</font>
					</div>
				</td>
				<td align="middle" bgcolor="#c000b0" cellspacing="2">
					<div align="center">
						<font color="#000000">cornfloewrblue</font>
					</div>
				</td>
				<td align="middle" bgcolor="darkviolet" cellspacing="2">
					<div align="center">
						<font color="#ffff00">darkviolet</font>
					</div>
				</td>
				<td align="middle" bgcolor="floralwhite" cellspacing="2">
					<div align="center">
						<font color="#ff0000">floralwhite</font>
					</div>
				</td>
				<td align="middle" bgcolor="#da000e" cellspacing="2">
					<div align="center">
						<font color="#ffff00">darkorenge</font>
					</div>
				</td>
				<td align="middle" bgcolor="darkslateblue" cellspacing="2">
					<div align="center">
						<font color="#ffff00">darkslateblue</font>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
	</p>
</body>
</html>