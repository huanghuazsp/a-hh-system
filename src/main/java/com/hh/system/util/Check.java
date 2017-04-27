package com.hh.system.util;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Check {
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(List list) {
		if (list == null ? false : list.size() == 0 ? false : true) {
			return false;
		}
		return true;
	}

	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isEmpty(Long str) {
		if (str == null || "".equals(str)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isEmpty(Date date) {
		return date == null;
	}

	public static boolean isEmpty(Object object) {
		return object == null ? true : object.toString().equals("") ? true
				: false;
	}

	public static boolean isNumber(String str) {
		Pattern pattern = Pattern.compile("[0-9]{1,}");
		Matcher matcher = pattern.matcher((CharSequence) str);
		return matcher.matches();
	}

	public static boolean isNumber(Object str) {
		if (Check.isEmpty(str)) {
			return false;
		}
		Pattern pattern = Pattern.compile("[0-9]{1,}");
		Matcher matcher = pattern.matcher((CharSequence) str.toString());
		return matcher.matches();
	}

	public static boolean isDecimal(String str) {
		return Pattern.compile("([1-9]+[0-9]*|0)(\\.[\\d]+)?").matcher(str)
				.matches();
	}

	public static boolean isNoEmpty(List list) {
		return !isEmpty(list);
	}

	public static boolean isNoEmpty(String str) {
		return !isEmpty(str);
	}

	public static boolean isNoEmpty(Long lo) {
		return !isEmpty(lo);
	}

	public static boolean isNoEmpty(Date date) {
		return !isEmpty(date);
	}

	public static boolean isNoEmpty(Object object) {
		return !isEmpty(object);
	}
	
	public static <T> T getEmptyDefaultValue(T object,T defaultValue) {
		if(Check.isNumber(object)){
			return Convert.toInt(object)==0?defaultValue:object;
		}else {
			return isEmpty(object)?defaultValue:object;
		}
	}
}
