package com.hh.system.util.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.Converter;

public class UtilDateConvert implements Converter{


	public Object convert(Class c, Object value) {
		if (value instanceof String) {
			String t = (String) value;
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
			DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
			Date d = null;
			try {
				d = format.parse(t);
			} catch (Exception e) {
				try {
					d=format2.parse(t);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			return d;
		}
		return null;
	}

}
