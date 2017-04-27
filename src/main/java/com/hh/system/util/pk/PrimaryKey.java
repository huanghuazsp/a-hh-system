package com.hh.system.util.pk;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PrimaryKey {
	private static Long key = new Date().getTime();
	
//	private static Map<String, Long> mapLong = new HashMap<String, Long>();
//
//	
//	public synchronized static long getTime(String key) {
//		if (mapLong.get(key)==null) {
//			mapLong.put(key, new Date().getTime());
//		}
//		long l = mapLong.get(key);
//		l += 1;
//		mapLong.put(key, l);
//		return l;
//	}
	
	public synchronized static long getTime() {
		key += 1;
		return key;
	}
	
	public static void main(String[] args) {
		System.out.println(getTime());
	}
	
	public  static String getTimeAndUUID() {
		return new Date().getTime()+getUUID();
	}
	
	public static String getUUID() {
		return Numbers.uuid();
	}
}
