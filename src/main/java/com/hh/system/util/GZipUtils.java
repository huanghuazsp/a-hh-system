package com.hh.system.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public abstract class GZipUtils {

	public static final int BUFFER = 1024;
	public static final String EXT = ".gz";

	/**
	 * 数据压缩
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] compress(byte[] data) {
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		// 压缩
		try {
			compress(bais, baos);
			byte[] output = baos.toByteArray();

			baos.flush();
			return output;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				baos.close();
				bais.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 文件压缩
	 * 
	 * @param file
	 * @throws Exception
	 */
	public static void compress(File file) throws Exception {
		compress(file, true);
	}

	/**
	 * 文件压缩
	 * 
	 * @param file
	 * @param delete
	 *            是否删除原始文件
	 * @throws Exception
	 */
	public static void compress(File file, boolean delete) throws Exception {
		FileInputStream fis = new FileInputStream(file);
		FileOutputStream fos = new FileOutputStream(file.getPath() + EXT);

		compress(fis, fos);

		fis.close();
		fos.flush();
		fos.close();

		if (delete) {
			file.delete();
		}
	}

	/**
	 * 数据压缩
	 * 
	 * @param is
	 * @param os
	 * @throws Exception
	 */
	public static void compress(InputStream is, OutputStream os)
			throws Exception {

		GZIPOutputStream gos = new GZIPOutputStream(os);

		int count;
		byte data[] = new byte[BUFFER];
		while ((count = is.read(data, 0, BUFFER)) != -1) {
			gos.write(data, 0, count);
		}

		gos.finish();

		gos.flush();
		gos.close();
	}

	/**
	 * 文件压缩
	 * 
	 * @param path
	 * @throws Exception
	 */
	public static void compress(String path) throws Exception {
		compress(path, true);
	}

	/**
	 * 文件压缩
	 * 
	 * @param path
	 * @param delete
	 *            是否删除原始文件
	 * @throws Exception
	 */
	public static void compress(String path, boolean delete) throws Exception {
		File file = new File(path);
		compress(file, delete);
	}

	/**
	 * 数据解压缩
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] decompress(byte[] data) throws Exception {
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		// 解压缩

		decompress(bais, baos);

		data = baos.toByteArray();

		baos.flush();
		baos.close();

		bais.close();

		return data;
	}

	/**
	 * 文件解压缩
	 * 
	 * @param file
	 * @throws Exception
	 */
	public static void decompress(File file) throws Exception {
		decompress(file, true);
	}

	/**
	 * 文件解压缩
	 * 
	 * @param file
	 * @param delete
	 *            是否删除原始文件
	 * @throws Exception
	 */
	public static void decompress(File file, boolean delete) throws Exception {
		FileInputStream fis = new FileInputStream(file);
		FileOutputStream fos = new FileOutputStream(file.getPath().replace(EXT,
				""));
		decompress(fis, fos);
		fis.close();
		fos.flush();
		fos.close();

		if (delete) {
			file.delete();
		}
	}

	/**
	 * 数据解压缩
	 * 
	 * @param is
	 * @param os
	 * @throws Exception
	 */
	public static void decompress(InputStream is, OutputStream os)
			throws Exception {

		GZIPInputStream gis = new GZIPInputStream(is);

		int count;
		byte data[] = new byte[BUFFER];
		while ((count = gis.read(data, 0, BUFFER)) != -1) {
			os.write(data, 0, count);
		}

		gis.close();
	}

	/**
	 * 文件解压缩
	 * 
	 * @param path
	 * @throws Exception
	 */
	public static void decompress(String path) throws Exception {
		decompress(path, true);
	}

	/**
	 * 文件解压缩
	 * 
	 * @param path
	 * @param delete
	 *            是否删除原始文件
	 * @throws Exception
	 */
	public static void decompress(String path, boolean delete) throws Exception {
		File file = new File(path);
		decompress(file, delete);
	}

	public static final void testDataCompress() throws Exception {
		String inputStr = "zlex@zlex.org,snowolf@zlex.org,zlex.snowolf@zlex.org";
		System.err.println("原文:\t" + inputStr);
		byte[] input = inputStr.getBytes();
		System.err.println("长度:\t" + input.length);
		byte[] data = GZipUtils.compress(input);
		System.err.println("压缩后:\t");
		System.err.println("长度:\t" + data.length);
		byte[] output = GZipUtils.decompress(data);
		String outputStr = new String(output);
		System.err.println("解压缩后:\t" + outputStr);
		System.err.println("长度:\t" + output.length);

	}

	public static String gzip(String str) {
		byte[] input = str.getBytes();
		byte[] data;
		try {
			data = GZipUtils.compress(input);
			return new String(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static final void testFileCompress() throws Exception {
		String inputStr = "zlex@zlex.org,snowolf@zlex.org,zlex.snowolf@zlex.org";
		FileOutputStream fos = new FileOutputStream("d:/f.txt");
		fos.write(inputStr.getBytes());
		fos.flush();
		fos.close();
		GZipUtils.compress("d:/f.txt", false);
		GZipUtils.decompress("d:/f.txt.gz", false);
		File file = new File("d:/f.txt");
		FileInputStream fis = new FileInputStream(file);
		DataInputStream dis = new DataInputStream(fis);
		byte[] data = new byte[(int) file.length()];
		dis.readFully(data);
		fis.close();
		String outputStr = new String(data);
	}

	public static void main(String[] args) {
		System.out.println(GZipUtils.gzip("ddd"));
	}
}