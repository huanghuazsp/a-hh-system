package com.hh.system.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import com.hh.system.util.date.DateFormat;
import com.hh.system.util.pk.PrimaryKey;

public class FileUtil {

	public static void mkdirs(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	public static void getFilePathListByFileRoot(String root,
			List<String> filePathList) {
		File file = new File(root);
		File[] subFile = file.listFiles();
		for (int i = 0; i < subFile.length; i++) {
			if (subFile[i].isDirectory()) {
				getFilePathListByFileRoot(subFile[i].getAbsolutePath(),
						filePathList);
			} else {
				String filename = subFile[i].getName();
				if (filename == null ? false : filename.length() < 3 ? false
						: true) {
					if (".js".equals(filename.substring(filename.length() - 3))) {
						filePathList.add(root.replace("\\", "/") + "/"
								+ filename);
					}
				}
			}
		}
	}

	public static void getRelativityFilePathListByFileRoot(String filepath,
			List<String> filePathList, String pathString) {
		File file = new File(filepath);
		File[] subFile = file.listFiles();
		for (int i = 0; i < subFile.length; i++) {
			if (subFile[i].isDirectory()) {
				getFilePathListByFileRoot(subFile[i].getAbsolutePath(),
						filePathList);
			} else {
				String filename = subFile[i].getName();
				if (filename == null ? false : true) {
					if (filename.indexOf("(") < 0 && filename.indexOf(")") < 0) {
						filePathList.add(pathString + "/" + filename);
					}
				}
			}
		}
	}

	public static byte[] stream2Byte(InputStream inputStream) {
		BufferedInputStream in = null;
		ByteArrayOutputStream out = null;
		try {
			in = new BufferedInputStream(inputStream);
			out = new ByteArrayOutputStream(1024);

			byte[] temp = new byte[1024];
			int size = 0;
			while ((size = in.read(temp)) != -1) {
				out.write(temp, 0, size);
			}
			byte[] content = out.toByteArray();
			return content;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public void saveFileFromInputStream(InputStream stream, String path,
			String filename) {
		FileOutputStream fs = null;
		try {

			fs = new FileOutputStream(path + "/" + filename);
			byte[] buffer = new byte[1024 * 1024];
			int bytesum = 0;
			int byteread = 0;
			while ((byteread = stream.read(buffer)) != -1) {
				bytesum += byteread;
				fs.write(buffer, 0, byteread);
				fs.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fs != null) {
					fs.close();
				}
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws IOException {
		File inFile = new File("java.txt");
		File outputFile = new File("result.txt");
		String fileContextStr = readFile(inFile);
		writeToResultFile(outputFile, fileContextStr);
	}

	public static void writeToResultFile(File file, String result)
			throws IOException {
		FileOutputStream fo = new FileOutputStream(file);
		try {
			fo.write(result.getBytes("utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			fo.close();
		}

	}

	public static String readFile(File file) throws FileNotFoundException,
			IOException {
		InputStream in = new FileInputStream(file);

		return readInputStream(in);
	}

	public static String readInputStream(InputStream in)
			throws UnsupportedEncodingException, IOException {
		StringBuffer sb = new StringBuffer();
		InputStreamReader inputStreamReader = new InputStreamReader(in, "utf-8");
		BufferedReader bf = new BufferedReader(inputStreamReader);
		try {
			String context = null;
			do {
				context = bf.readLine();
				if (context != null) {
					sb.append(context);
				}
			} while (context != null);

			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			bf.close();
			in.close();
			inputStreamReader.close();
		}
		return "";
	}

	public static void newFolder(String folderPath) {
		try {
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.mkdir();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void newFile(String filePathAndName, String fileContent) {
		FileWriter resultFile = null;
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.createNewFile();
			}
			resultFile = new FileWriter(myFilePath);
			PrintWriter myFile = new PrintWriter(resultFile);
			String strContent = fileContent;
			myFile.println(strContent);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultFile != null) {
					resultFile.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static void delFile(String filePathAndName) {
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			java.io.File myDelFile = new java.io.File(filePath);
			myDelFile.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void delAllFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
			}
		}
	}

	public static void copyFile(String oldPath, String newPath) {
		if (newPath.lastIndexOf("/")>-1) {
			mkdirs(newPath.substring(0,newPath.lastIndexOf("/")));
		}
		InputStream inStream = null;
		FileOutputStream fs = null;
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				inStream = new FileInputStream(oldPath); // 读入原文件
				fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					fs.write(buffer, 0, byteread);
				}
				fs.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fs != null) {
				try {
					fs.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void copyFolder(String oldPath, String newPath) {
		FileInputStream input = null;
		FileOutputStream output = null;
		try {
			(new File(newPath)).mkdirs(); // 如果文件夹不存在则建立新文件夹
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}

				if (temp.isFile()) {
					input = new FileInputStream(temp);
					output = new FileOutputStream(newPath + "/"
							+ (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
				}
				if (temp.isDirectory()) {// 如果是子文件夹
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static void moveFile(String oldPath, String newPath) {
		copyFile(oldPath, newPath);
		delFile(oldPath);

	}

	public static void moveFolder(String oldPath, String newPath) {
		copyFolder(oldPath, newPath);
		delFolder(oldPath);
	}
	
	public static String uploadFile(File file, String fileName,
			String filePathModel) {
		if (file == null) {
			return null;
		}
		String filepath = StaticVar.filepath + "/";

		for (String path : filePathModel.split("/")) {
			filepath += path + "/";
		}
		filepath += DateFormat.getDate("yyyyMMdd")+ "/";

		// String newFileName = PrimaryKey.getPrimaryKeyUUID();
		// pathList.add(newFileName);
		FileUtil.mkdirs(filepath);

		int index_ = fileName.lastIndexOf(".");

		String fileName1 = fileName.substring(0, index_)
				+ PrimaryKey.getUUID();
		String fileName2 = fileName.substring(index_);
		fileName = fileName1 + fileName2;
		String fullNewFileName = filepath + (fileName);
		InputStream input = null;
		OutputStream output = null;
		try {
			input = new BufferedInputStream(new FileInputStream(file));
			File file2 = new File(fullNewFileName);
			output = new BufferedOutputStream(new FileOutputStream(file2));
			int size = 1024;
			byte[] b = new byte[size];
			int j = 0;
			while ((j = input.read(b, 0, size)) > 0) {
				output.write(b, 0, j);
				output.flush();
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e.getClass() + e.getMessage());
		} catch (IOException e) {
			throw new RuntimeException(e.getClass() + e.getMessage());
		} finally {
			try {
				if (output != null) {
					output.close();
				}
			} catch (IOException e) {
				throw new RuntimeException(e.getClass() + e.getMessage());
			}
			try {
				input.close();
			} catch (IOException e) {
				throw new RuntimeException(e.getClass() + e.getMessage());
			}
		}
		return fullNewFileName.replace(StaticVar.filepath, "");
	}


}
