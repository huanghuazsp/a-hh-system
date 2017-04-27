package com.hh.system.util;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

import com.yahoo.platform.yui.compressor.CssCompressor;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;


public abstract class ResourceUtil {

	static int linebreakpos = -1;
	static boolean munge = true;
	static boolean verbose = false;
	static boolean preserveAllSemiColons = false;
	static boolean disableOptimizations = false;

	/**
	 * 压缩js方法
	 * 
	 * @param jsString
	 *            js字符串
	 * @return
	 */
	public static String outJs(String jsString) {
		Reader in = new StringReader(jsString);
		Writer out = new StringWriter();
		String jsStringZip = null;
		JavaScriptCompressor jscompressor;

		try {
			jscompressor = new JavaScriptCompressor(in, new ErrorReporter() {
				public void warning(String message, String sourceName,
						int line, String lineSource, int lineOffset) {
					if (line < 0) {
						System.err.println("\n[WARNING] " + message);
					} else {
						System.err.println("\n[WARNING] " + line + ':'
								+ lineOffset + ':' + message);
					}
				}

				public void error(String message, String sourceName, int line,
						String lineSource, int lineOffset) {
					if (line < 0) {
						System.err.println("\n[ERROR] " + message);
					} else {
						System.err.println("\n[ERROR] " + line + ':'
								+ lineOffset + ':' + message);
					}
				}

				public EvaluatorException runtimeError(String message,
						String sourceName, int line, String lineSource,
						int lineOffset) {
					error(message, sourceName, line, lineSource, lineOffset);
					return new EvaluatorException(message);
				}
			});
			jscompressor.compress(out, linebreakpos, munge, verbose,
					preserveAllSemiColons, disableOptimizations);
			jsStringZip = out.toString();
			out.close();
			in.close();
		} catch (EvaluatorException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return jsStringZip;
	}

	/**
	 * 压缩css方法
	 * 
	 * @param cssString
	 *            css字符串
	 * @return
	 */
	public static String outCss(String cssString) {
		Reader in = new StringReader(cssString);
		Writer out = new StringWriter();
		String cssStringZip = null;
		CssCompressor csscompressor;
		try {
			csscompressor = new CssCompressor(in);
			csscompressor.compress(out, linebreakpos);
			cssStringZip = out.toString();
			out.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cssStringZip;
	}

	public static void main(String[] args) {
		String jsString = "var data = {};/* 公共js方法列表    0.全局  表格对象，"
				+ "表格中各行背景色 */data.call_back = callback;if(json)"
				+ "{data.queryData = json;}";

		String cssString = ".red a:link,.red a:visited{color:red;text-decoration: none}.red a:hover "
				+ "{color:#C30000;text-decoration: none}/* 数字按钮框样式 */#imgTitle {FILTER:ALPHA"
				+ "(opacity=70);    position:relative;left:0px;   text-align:left;overflow: hidden;}";

		System.out.println(ResourceUtil.outJs(jsString));
		// System.out.println(t.outJs(cssString));
		System.out.println(ResourceUtil.outCss(cssString));
	}
}