package com.hh.system.util;

import org.apache.log4j.Logger;

public class LogUtil {
	private static final Logger logger = Logger.getLogger(LogUtil.class);

	public static void info(String str) {
		logger.info(str);
	}

	public static void error(String str) {
		logger.error(str);
	}
}
