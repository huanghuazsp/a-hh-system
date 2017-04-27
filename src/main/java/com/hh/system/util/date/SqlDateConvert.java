package com.hh.system.util.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.Converter;

public class SqlDateConvert implements Converter {


	public Object convert(Class c, Object value) {
		if (value instanceof String) {
			String t = (String) value;
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
			DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
			Date d = null;
			try {
				d = format.parse(t);
			} catch (ParseException e) {
				try {
					d=format2.parse(t);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}
			return new java.sql.Date(d.getTime());
		}
		return null;
	}

}
