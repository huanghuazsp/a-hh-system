package com.hh.system.util.document;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFComment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import com.hh.system.util.FileUtil;

public class WordUtil {
	public static void main(String[] args) {
		// gettext();
		// exportWord();
		// settext();

	}

	private static void gettext() {
		try {
			// ----------------------------------------------------------------------------------------------------
			// 提取.docx正文文本
			String filePath = "C:\\Users\\Administrator\\Desktop\\表结构.docx";
			// 得到.docx文件提取器
			org.apache.poi.xwpf.extractor.XWPFWordExtractor docx = new XWPFWordExtractor(
					POIXMLDocument.openPackage(filePath));
			// 提取.docx正文文本
			String text = docx.getText();
			// 提取.docx批注
			org.apache.poi.xwpf.usermodel.XWPFComment[] comments = ((XWPFDocument) docx
					.getDocument()).getComments();
			for (XWPFComment comment : comments) {
				comment.getId();// 提取批注Id
				comment.getAuthor();// 提取批注修改人
				comment.getText();// 提取批注内容
			}
			// ----------------------------------------------------------------------------------------------------
			// 五：利用POI提取Word总页数、总字符数...
			// 97-2003
			// WordExtractor doc = new WordExtractor(new
			// FileInputStream(filePath));//.doc格式Word文件提取器
			// int pages = doc.getSummaryInformation().getPageCount();//总页数
			// int wordCount = doc.getSummaryInformation().getWordCount();//总字符数
			//
			// XWPFDocument docx2 = new
			// XWPFDocument(POIXMLDocument.openPackage(filePath));
			//
			// int pages2 =
			// docx2.getProperties().getExtendedProperties().getUnderlyingProperties().getPages();//总页数
			// int characters =
			// docx2.getProperties().getExtendedProperties().getUnderlyingProperties().getCharacters();//
			// 忽略空格的总字符数 另外还有getCharactersWithSpaces()方法获取带空格的总字数。

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void settext() {
		try {
			OPCPackage pack = POIXMLDocument.openPackage("D:/aaa.docx");
			XWPFDocument doc = new XWPFDocument(pack);
			List<XWPFTable> itList = doc.getTables();
			for (XWPFTable table : itList) {
				List<XWPFTableRow> rows = table.getRows();
				for (XWPFTableRow row : rows) {
					List<XWPFTableCell> cells = row.getTableCells();
					for (XWPFTableCell cell : cells) {

						if (cell.getText().equals("test")) {
							cell.setText("<table width='100%' border=1><tr><td>序号</td><td>性别</td><td>状态</td><td>用户名称</td><td>电子邮件</td><td>联系电话</td><td>生日</td></tr><tr><td>1</td><td>男<img src='/hhcommon/opensource/ext/shared/icons/fam/user.gif' />&nbsp;&nbsp;</td><td>正常</td><td>test</td><td>test@qq.ddd</td><td>3</td><td>2012年10月13日</td></tr><tr><td>2</td><td>男<img src='opensource/ext/shared/icons/fam/user.gif' />&nbsp;&nbsp;</td><td>正常</td><td>黄华</td><td>405038567@qq.com</td><td>11111111111112</td><td>2012年06月10日</td></tr><tr><td>3</td><td>男<img src='opensource/ext/shared/icons/fam/user.gif' />&nbsp;&nbsp;</td><td>正常</td><td>超级管理员</td><td>admin@hh.com</td><td>13</td><td>2012年07月17日</td></tr><tr><td>4</td><td>男<img src='opensource/ext/shared/icons/fam/user.gif' />&nbsp;&nbsp;</td><td>正常</td><td>张飞</td><td>zf@hh.com</td><td>1111111111111</td><td>2012年06月10日</td></tr><tr><td>5</td><td>女<img src='opensource/ext/shared/icons/fam/user_female.gif' />&nbsp;&nbsp;</td><td>正常</td><td>蜘蛛精</td><td>zzj@hh.com</td><td>1111111111111</td><td>2012年06月10日</td></tr><tr><td>6</td><td>男<img src='opensource/ext/shared/icons/fam/user.gif' />&nbsp;&nbsp;</td><td>正常</td><td>赵云</td><td>zy@qq.com</td><td>1111111111111</td><td>2012年06月10日</td></tr><tr><td>7</td><td>男<img src='opensource/ext/shared/icons/fam/user.gif' />&nbsp;&nbsp;</td><td>正常</td><td>黄忠</td><td>hz@hh.com</td><td>1111111111111</td><td>2012年06月10日</td></tr><tr><td>8</td><td>男<img src='opensource/ext/shared/icons/fam/user.gif' />&nbsp;&nbsp;</td><td>正常</td><td>魏延</td><td>wy@hh.com</td><td>1111111111111</td><td>2012年06月10日</td></tr><tr><td>9</td><td>男<img src='opensource/ext/shared/icons/fam/user.gif' />&nbsp;&nbsp;</td><td>正常</td><td>董卓</td><td>dz@hh.com</td><td>1111111111111</td><td>2012年06月10日</td></tr><tr><td>10</td><td>女<img src='opensource/ext/shared/icons/fam/user_female.gif' />&nbsp;&nbsp;</td><td>正常</td><td>白骨精</td><td>bgj@hh.com</td><td>1111111111111</td><td>2012年06月10日</td></tr><tr><td>11</td><td>男<img src='pensource/ext/shared/icons/fam/user.gif' />&nbsp;&nbsp;</td><td>正常</td><td>沙僧</td><td>ss@hh.com</td><td>1111111111111</td><td>2012年06月10日</td></tr><tr><td>12</td><td>男<img src='opensource/ext/shared/icons/fam/user.gif' />&nbsp;&nbsp;</td><td>正常</td><td>唐僧</td><td>ts@hh.com</td><td>1111111111111</td><td>2012年06月10日</td></tr><tr><td>13</td><td>男<img src='opensource/ext/shared/icons/fam/user.gif' />&nbsp;&nbsp;</td><td>正常</td><td>诸葛亮</td><td>zgl@qq.com</td><td>1111111111111</td><td>2012年06月10日</td></tr><tr><td>14</td><td>男<img src='opensource/ext/shared/icons/fam/user.gif' />&nbsp;&nbsp;</td><td>正常</td><td>刘备</td><td>lb@hh.com</td><td>1111111111111</td><td>2012年06月10日</td></tr><tr><td>15</td><td>男<img src='opensource/ext/shared/icons/fam/user.gif' />&nbsp;&nbsp;</td><td>正常</td><td>花荣</td><td>hr@hh.com</td><td>1111111111111</td><td>2012年06月10日</td></tr></table>");
							List<XWPFParagraph> pars = cell.getParagraphs();
							for (XWPFParagraph par : pars) {
								List<XWPFRun> runs = par.getRuns();
								for (XWPFRun run : runs) {
									run.removeBreak();
								}
							}
						}
					}
				}
			}

			FileOutputStream fos = new FileOutputStream("d:/bbb.docx");
			doc.write(fos);
			fos.flush();
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void exportWord(String docStr, String path) {
		try {
			InputStream  bais = null;
			FileOutputStream fos = null;
			POIFSFileSystem poifs = null;
			String fileName = path;
			try {
				byte b[] =docStr.getBytes("utf-16");
				bais = new ByteArrayInputStream(b);
				poifs = new POIFSFileSystem();
				DirectoryEntry directory = poifs.getRoot();
				DocumentEntry documentEntry = directory.createDocument(
						"WordDocument", bais);
				// System.out.println("documentEntry:" +
				// documentEntry.getName());
				fos = new FileOutputStream(fileName);
				poifs.writeFilesystem(fos);
				// File file = new File(fileName);
				// file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (fos != null)
					fos.close();
				if (bais != null)
					bais.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static byte[] exportWordByte(String docStr, String path) {
		exportWord(docStr, path);
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(new File(path));
			return FileUtil.stream2Byte(fileInputStream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static String readDoc(String doc) throws Exception {
		// 创建输入流读取DOC文件
		FileInputStream in = new FileInputStream(new File(doc));
		WordExtractor extractor = null;
		String text = null;
		// 创建WordExtractor
		extractor = new WordExtractor(in);
		// 对DOC文件进行提取
		text = extractor.getText();
		return text;
	}
}
