package com.hh.system.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {
	public static String getMessage(Exception e) {
		String message = "";
		StringWriter sw = null;
		PrintWriter printWriter = null;
		try {
			sw = new StringWriter();
			printWriter = new PrintWriter(sw, true);
			e.printStackTrace(printWriter);
			message = sw.toString();
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (sw != null) {
				try {
					sw.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if (printWriter != null) {
				printWriter.close();
			}
		}
		return message;
	}
}
