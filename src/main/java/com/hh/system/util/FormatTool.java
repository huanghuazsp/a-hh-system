package com.hh.system.util;

import java.util.ArrayList;

public class FormatTool {
	/**
	 * json字符串的格式化
	 * 
	 * @author peiyuxin
	 * @param json
	 *            需要格式的json串
	 * @param fillStringUnit每一层之前的占位符号比如空格
	 *            制表符
	 * @return
	 */
	public static String formatJson(String json, String fillStringUnit) {
		if (json == null || json.trim().length() == 0) {
			return null;
		}

		int fixedLenth = 0;
		ArrayList<String> tokenList = new ArrayList<String>();
		{
			String jsonTemp = json;
			// 预读取
			while (jsonTemp.length() > 0) {
				String token = getToken(jsonTemp);
				jsonTemp = jsonTemp.substring(token.length());
				token = token.trim();
				tokenList.add(token);
			}
		}

		for (int i = 0; i < tokenList.size(); i++) {
			String token = tokenList.get(i);
			int length = token.getBytes().length;
			if (length > fixedLenth && i < tokenList.size() - 1
					&& tokenList.get(i + 1).equals(":")) {
				fixedLenth = length;
			}
		}

		StringBuilder buf = new StringBuilder();
		int count = 0;
		for (int i = 0; i < tokenList.size(); i++) {

			String token = tokenList.get(i);

			if (token.equals(",")) {
				buf.append(token);
				doFill(buf, count, fillStringUnit);
				continue;
			}
			if (token.equals(":")) {
				buf.append(" ").append(token).append(" ");
				continue;
			}
			if (token.equals("{")) {
				String nextToken = tokenList.get(i + 1);
				if (nextToken.equals("}")) {
					i++;
					buf.append("{ }");
				} else {
					count++;
					buf.append(token);
					doFill(buf, count, fillStringUnit);
				}
				continue;
			}
			if (token.equals("}")) {
				count--;
				doFill(buf, count, fillStringUnit);
				buf.append(token);
				continue;
			}
			if (token.equals("[")) {
				String nextToken = tokenList.get(i + 1);
				if (nextToken.equals("]")) {
					i++;
					buf.append("[ ]");
				} else {
					count++;
					buf.append(token);
					doFill(buf, count, fillStringUnit);
				}
				continue;
			}
			if (token.equals("]")) {
				count--;
				doFill(buf, count, fillStringUnit);
				buf.append(token);
				continue;
			}

			buf.append(token);
			// 左对齐
			if (i < tokenList.size() - 1 && tokenList.get(i + 1).equals(":")) {
				int fillLength = fixedLenth - token.getBytes().length;
				if (fillLength > 0) {
					for (int j = 0; j < fillLength; j++) {
						buf.append(" ");
					}
				}
			}
		}
		return buf.toString();
	}

	private static String getToken(String json) {
		StringBuilder buf = new StringBuilder();
		boolean isInYinHao = false;
		while (json.length() > 0) {
			String token = json.substring(0, 1);
			json = json.substring(1);

			if (!isInYinHao
					&& (token.equals(":") || token.equals("{")
							|| token.equals("}") || token.equals("[")
							|| token.equals("]") || token.equals(","))) {
				if (buf.toString().trim().length() == 0) {
					buf.append(token);
				}

				break;
			}

			if (token.equals("\\")) {
				buf.append(token);
				buf.append(json.substring(0, 1));
				json = json.substring(1);
				continue;
			}
			if (token.equals("\"")) {
				buf.append(token);
				if (isInYinHao) {
					break;
				} else {
					isInYinHao = true;
					continue;
				}
			}
			buf.append(token);
		}
		return buf.toString();
	}

	private static void doFill(StringBuilder buf, int count,
			String fillStringUnit) {
		buf.append("\n");
		for (int i = 0; i < count; i++) {
			buf.append(fillStringUnit);
		}
	}

	public static void main(String[] args) {
		String string = FormatTool
				.formatJson(
						"[{'className':\"Ext.form.field.Number\",'id':\"UUIDC5E8BF1596600001EBA3128011\",'xtype':\"numberfield\",'name':\"UUIDC5A0A3359E9000012E49143250\",'columnWidth':1,'fieldLabel':\"天数\",'height':25,'allowBlank':1,'minLength':0,'maxLength':19},{'className':\"Ext.form.field.HtmlEditor\",'id':\"UUIDC5E8BF1599900001CBB415FF95\",'xtype':\"htmleditor\",'name':\"UUIDC5AE815C24800001AB561C615C\",'columnWidth':1,'fieldLabel':\"文本编辑器\",'height':150,'allowBlank':1,'minLength':0,'maxLength':100000000},{'className':\"Ext.form.field.Text\",'id':\"UUIDC5E8BF15A750000147A9F8611B\",'xtype':\"textfield\",'name':\"UUIDC5B3F89D340000014EE1105520\",'columnWidth':0.5,'fieldLabel':\"文本框\",'height':25,'allowBlank':1,'minLength':0,'maxLength':128},{'className':\"Ext.form.field.Date\",'id':\"UUIDC5E8BF15AC1000019D6F1020C6\",'xtype':\"datefield\",'name':\"UUIDC5B3F9E5B37000015315DFB555\",'columnWidth':0.5,'fieldLabel':\"时间控件1\",'height':25,'allowBlank':1,'minLength':0,'maxLength':32,'format':\"Y年m月d日 H时i分s秒\",'showTimer':1},{'className':\"Ext.form.field.Date\",'id':\"UUIDC5E8BF15B1300001F17B160E1B\",'xtype':\"datefield\",'name':\"UUIDC5B3F9E742D00001B8D6A360A5\",'columnWidth':0.5,'fieldLabel':\"日期控件\",'height':25,'allowBlank':1,'minLength':0,'maxLength':32,'format':\"Y年m月d日\",'showTimer':null},{'className':\"com.hh.global.widget.RadioGroup\",'id':\"UUIDC5E8BF15B7700001CD8819401A\",'xtype':\"widgetRadioGroup\",'name':\"UUIDC5B3F9E9C1700001E9371E313E\",'columnWidth':\"1\",'fieldLabel':\"单选框\",'height':25,'allowBlank':1,'minLength':\"0\",'maxLength':\"256\",'action':\"system-GlobalRadio-queryItem\",'extraParams':\"{'table_name':''}\",'paramType':\"paramsMap\",'displayField':\"text\",'valueField':\"id\",'data':\"[{'id':'key','text':'value'},{'id':'sadf','text':'asdfasdf'}]\"},{'className':\"com.hh.global.widget.FileGridField\",'id':\"UUIDC5E8BF15BF30000148101F901B\",'xtype':\"widgetFileGridField\",'name':\"UUIDC5B3F9ECE0500001DC64196E19\",'columnWidth':1,'fieldLabel':\"附件\",'height':120,'allowBlank':1,'minLength':0,'maxLength':100000000,'filePath':\"default\"}]",
						"");
		System.out.println(string);
	}

}
