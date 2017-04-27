package com.hh.system.util.document;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
	private static HSSFWorkbook wb;

	private static CellStyle titleStyle; // 标题行样式
	private static Font titleFont; // 标题行字体
	private static CellStyle dateStyle; // 日期行样式
	private static Font dateFont; // 日期行字体
	private static CellStyle headStyle; // 表头行样式
	private static Font headFont; // 表头行字体
	private static CellStyle contentStyle; // 内容行样式
	private static Font contentFont; // 内容行字体

	/**
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @Description: 将Map里的集合对象数据输出Excel数据流
	 */
	@SuppressWarnings({ "unchecked" })
	public static void export2Excel(ExportSetInfo setInfo)
			throws IOException, IllegalArgumentException, IllegalAccessException {
		init();
		Set<Entry<String, List<Map<String, Object>>>> set = setInfo.getObjsMap().entrySet();
		String[] sheetNames = new String[setInfo.getObjsMap().size()];
		int sheetNameNum = 0;
		for (Entry<String, List<Map<String, Object>>> entry : set) {
			sheetNames[sheetNameNum] = entry.getKey();
			sheetNameNum++;
		}
		HSSFSheet[] sheets = getSheets(setInfo.getObjsMap().size(), sheetNames);
		int sheetNum = 0;
		for (Entry<String, List<Map<String, Object>>> entry : set) {
			// Sheet
			List<Map<String, Object>> objs = entry.getValue();
			// 标题行
			createTableTitleRow(setInfo, sheets, sheetNum);
			// 日期行
//			createTableDateRow(setInfo, sheets, sheetNum);
			// 表头
			creatTableHeadRow(setInfo, sheets, sheetNum);
			// 表体
			String[] fieldNames = setInfo.getFieldNames().get(sheetNum);
			int rowNum = 2;
			for (Map<String, Object> obj : objs) {
				HSSFRow contentRow = sheets[sheetNum].createRow(rowNum);
				contentRow.setHeight((short) 300);
				HSSFCell[] cells = getCells(contentRow, setInfo.getFieldNames().get(sheetNum).length);
//				int cellNum = 0; // 去掉一列序号，因此从1开始
				if (fieldNames != null) {
					for (int num = 0; num < fieldNames.length; num++) {
						// Object value = ClassReflex.invokeGetterMethod(obj,
						// fieldNames[num]);
						Object value = obj.get(fieldNames[num]);
						cells[num].setCellValue(value == null ? "" : value.toString());
//						cellNum++;
					}
				}
				rowNum++;
			}
			// adjustColumnSize(sheets, sheetNum, fieldNames); // 自动调整列宽
			sheetNum++;
		}
		wb.write(setInfo.getOut());
	}

	/**
	 * @Description: 初始化
	 */
	private static void init() {
		wb = new HSSFWorkbook();

		titleFont = wb.createFont();
		titleStyle = wb.createCellStyle();
		dateStyle = wb.createCellStyle();
		dateFont = wb.createFont();
		headStyle = wb.createCellStyle();
		headFont = wb.createFont();
		contentStyle = wb.createCellStyle();
		contentFont = wb.createFont();

		initTitleCellStyle();
		initTitleFont();
		initDateCellStyle();
		initDateFont();
		initHeadCellStyle();
		initHeadFont();
		initContentCellStyle();
		initContentFont();
	}

	/**
	 * @Description: 自动调整列宽
	 */
	@SuppressWarnings("unused")
	private static void adjustColumnSize(HSSFSheet[] sheets, int sheetNum, String[] fieldNames) {
		for (int i = 0; i < fieldNames.length + 1; i++) {
			sheets[sheetNum].autoSizeColumn(i, true);
		}
	}

	/**
	 * @Description: 创建标题行(需合并单元格)
	 */
	private static void createTableTitleRow(ExportSetInfo setInfo, HSSFSheet[] sheets, int sheetNum) {

		CellRangeAddress titleRange = new CellRangeAddress(0, 0, 0, setInfo.getFieldNames().get(sheetNum).length-1);
		sheets[sheetNum].addMergedRegion(titleRange);

		HSSFRow titleRow = sheets[sheetNum].createRow(0);
		titleRow.setHeight((short) 800);
		HSSFCell titleCell = titleRow.createCell(0);
		titleCell.setCellStyle(titleStyle);
		titleCell.setCellValue(setInfo.getTitles()[sheetNum]);
	}

	/**
	 * @Description: 创建日期行(需合并单元格)
	 */
	private static void createTableDateRow(ExportSetInfo setInfo, HSSFSheet[] sheets, int sheetNum) {
		CellRangeAddress dateRange = new CellRangeAddress(1, 1, 0, setInfo.getFieldNames().get(sheetNum).length);
		sheets[sheetNum].addMergedRegion(dateRange);
		HSSFRow dateRow = sheets[sheetNum].createRow(1);
		dateRow.setHeight((short) 350);
		HSSFCell dateCell = dateRow.createCell(0);
		dateCell.setCellStyle(dateStyle);
		dateCell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
	}

	/**
	 * @Description: 创建表头行(需合并单元格)
	 */
	private static void creatTableHeadRow(ExportSetInfo setInfo, HSSFSheet[] sheets, int sheetNum) {
		// 表头
		HSSFRow headRow = sheets[sheetNum].createRow(1);
		headRow.setHeight((short) 350);
		// 序号列
//		HSSFCell snCell = headRow.createCell(0);
//		snCell.setCellStyle(headStyle);
//		snCell.setCellValue("序号");
		// 列头名称
		for (int num = 0; num < setInfo.getHeadNames().get(sheetNum).length;  num++) {
			HSSFCell headCell = headRow.createCell(num);
			headCell.setCellStyle(headStyle);
			headCell.setCellValue(setInfo.getHeadNames().get(sheetNum)[num ]);
		}
	}

	/**
	 * @Description: 创建所有的Sheet
	 */
	private static HSSFSheet[] getSheets(int num, String[] names) {
		HSSFSheet[] sheets = new HSSFSheet[num];
		for (int i = 0; i < num; i++) {
			if (names[i] != null) {
				sheets[i] = wb.createSheet(names[i]);
				sheets[i].setDefaultColumnWidth(20);
			}
		}
		return sheets;
	}

	/**
	 * @Description: 创建内容行的每一列(附加一列序号)
	 */
	private static HSSFCell[] getCells(HSSFRow contentRow, int num) {
		HSSFCell[] cells = new HSSFCell[num ];

		for (int i = 0, len = cells.length; i < len; i++) {
			cells[i] = contentRow.createCell(i);
			cells[i].setCellStyle(contentStyle);
		}
		// 设置序号列值，因为出去标题行和日期行，所有-2
		cells[0].setCellValue(contentRow.getRowNum() - 2);

		return cells;
	}

	/**
	 * @Description: 初始化标题行样式
	 */
	private static void initTitleCellStyle() {
		titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
		titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		titleStyle.setFont(titleFont);
		titleStyle.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
	}

	/**
	 * @Description: 初始化日期行样式
	 */
	private static void initDateCellStyle() {
		dateStyle.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);
		dateStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		dateStyle.setFont(dateFont);
		dateStyle.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
	}

	/**
	 * @Description: 初始化表头行样式
	 */
	private static void initHeadCellStyle() {
		headStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		headStyle.setFont(headFont);
		headStyle.setFillBackgroundColor(IndexedColors.YELLOW.getIndex());
		headStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
		headStyle.setBorderBottom(CellStyle.BORDER_THIN);
		headStyle.setBorderLeft(CellStyle.BORDER_THIN);
		headStyle.setBorderRight(CellStyle.BORDER_THIN);
		headStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		headStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		headStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		headStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
	}

	/**
	 * @Description: 初始化内容行样式
	 */
	private static void initContentCellStyle() {
		contentStyle.setAlignment(CellStyle.ALIGN_CENTER);
		contentStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		contentStyle.setFont(contentFont);
		contentStyle.setBorderTop(CellStyle.BORDER_THIN);
		contentStyle.setBorderBottom(CellStyle.BORDER_THIN);
		contentStyle.setBorderLeft(CellStyle.BORDER_THIN);
		contentStyle.setBorderRight(CellStyle.BORDER_THIN);
		contentStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		contentStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		contentStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		contentStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		contentStyle.setWrapText(true); // 字段换行
	}

	/**
	 * @Description: 初始化标题行字体
	 */
	private static void initTitleFont() {
		titleFont.setFontName("华文楷体");
		titleFont.setFontHeightInPoints((short) 20);
		titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		titleFont.setCharSet(Font.DEFAULT_CHARSET);
		titleFont.setColor(IndexedColors.BLACK.getIndex());
	}

	/**
	 * @Description: 初始化日期行字体
	 */
	private static void initDateFont() {
		dateFont.setFontName("隶书");
		dateFont.setFontHeightInPoints((short) 10);
		dateFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		dateFont.setCharSet(Font.DEFAULT_CHARSET);
		dateFont.setColor(IndexedColors.BLACK.getIndex());
	}

	/**
	 * @Description: 初始化表头行字体
	 */
	private static void initHeadFont() {
		headFont.setFontName("宋体");
		headFont.setFontHeightInPoints((short) 10);
		headFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headFont.setCharSet(Font.DEFAULT_CHARSET);
		headFont.setColor(IndexedColors.BLACK.getIndex());
	}

	/**
	 * @Description: 初始化内容行字体
	 */
	private static void initContentFont() {
		contentFont.setFontName("宋体");
		contentFont.setFontHeightInPoints((short) 10);
		contentFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
		contentFont.setCharSet(Font.DEFAULT_CHARSET);
		contentFont.setColor(IndexedColors.BLACK.getIndex());
	}

	public static void main(String[] args) {
		// ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// List<String[]> headNames = new ArrayList<String[]>();
		// headNames.add(new String[] { "用户名", "密码", "电子邮件", "类型", "角色" });
		// List<String[]> fieldNames = new ArrayList<String[]>();
		// fieldNames.add(new String[] { "userName", "pwd", "email", "typeStr",
		// "roleStr" });
		//
		// ExportSetInfo setInfo = new ExportSetInfo();
		// // setInfo.setObjsMap(userService.getExportData());
		// setInfo.setFieldNames(fieldNames);
		// setInfo.setTitles(new String[] { "馋八戒后台用户信息" });
		// setInfo.setHeadNames(headNames);
		// setInfo.setOut(baos);
		//
		// // 将需要导出的数据输出到baos
		// try {
		// export2Excel(setInfo);
		// } catch (IllegalArgumentException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// } catch (IllegalAccessException e) {
		// e.printStackTrace();
		// }
		//
		// new ByteArrayInputStream(baos.toByteArray());
		File file = new File(
				"D:\\kaifa\\apache-tomcat-7.0.65\\wtpwebapps\\uploadfile\\temp\\file\\org\\2015年12月13日\\40d22ca3b5b04407bed7300b764f0117机构基本信息.xls");

		try {
			System.out.println(getMapList(file, 1));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// String[][] result;
		// try {
		// result = getData(file, 0);
		// int rowLength = result.length;
		// for (int i = 0; i < rowLength; i++) {
		// for (int j = 0; j < result[i].length; j++) {
		// System.out.print(result[i][j] + "\t\t");
		// }
		// System.out.println();
		// }
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

	}

	public static List<Map<String, Object>> getMapList(File file, int row) throws Exception {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		String[][] result = getData(file, row);
		String[] titleArr = result[0];
		Map<Integer, String> titleMap = new HashMap<Integer, String>();
		for (int i = 0; i < titleArr.length; i++) {
			titleMap.put(i, titleArr[i]);
		}
		int rowLength = result.length;
		for (int i = 1; i < rowLength; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			for (int j = 0; j < result[i].length; j++) {
				map.put(titleMap.get(j), result[i][j]);
			}
			mapList.add(map);
		}
		return mapList;
	}

	public static String[][] getData(File file, int ignoreRows) throws FileNotFoundException, IOException {
		List<String[]> result = new ArrayList<String[]>();
		int rowSize = 0;
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
		// 打开HSSFWorkbook
		// POIFSFileSystem fs = new POIFSFileSystem(in);
		Workbook wb = null;

		if (file.getPath().endsWith("xlsx")) {
			try {
				wb = new XSSFWorkbook(in);
			} catch (Exception ex) {
				wb = new HSSFWorkbook(in);
			}
		} else {
			try {
				wb = new HSSFWorkbook(in);
			} catch (Exception ex) {
				wb = new XSSFWorkbook(in);
			}
		}

		Cell cell = null;
		for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
			Sheet st = wb.getSheetAt(sheetIndex);
			// 第一行为标题，不取
			for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
				Row row = st.getRow(rowIndex);
				if (row == null) {
					continue;
				}
				int tempRowSize = row.getLastCellNum() + 1;
				if (tempRowSize > rowSize) {
					rowSize = tempRowSize;
				}
				String[] values = new String[rowSize];
				Arrays.fill(values, "");
				boolean hasValue = false;
				for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
					String value = "";
					cell = row.getCell(columnIndex);
					if (null != cell) {
						// 以下是判断数据的类型
						switch (cell.getCellType()) {
						case HSSFCell.CELL_TYPE_NUMERIC: // 数字
							double cellValue = cell.getNumericCellValue();
							value = new DecimalFormat("#").format(cellValue);   
							break;

						case HSSFCell.CELL_TYPE_STRING: // 字符串
							value = cell.getStringCellValue();
							break;

						case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
							value = cell.getBooleanCellValue() + "";
							break;

						case HSSFCell.CELL_TYPE_FORMULA: // 公式
							value = cell.getCellFormula() + "";
							break;

						case HSSFCell.CELL_TYPE_BLANK: // 空值
							value = "";
							break;

						case HSSFCell.CELL_TYPE_ERROR: // 故障
							value = "";
							break;
						default:
							value = "";
							break;
						}
					}

					values[columnIndex] = rightTrim(value);
					hasValue = true;
				}

				if (hasValue) {
					result.add(values);
				}
			}
		}
		in.close();
		String[][] returnArray = new String[result.size()][rowSize];
		for (int i = 0; i < returnArray.length; i++) {
			returnArray[i] = (String[]) result.get(i);
		}
		return returnArray;
	}

	public static String rightTrim(String str) {
		if (str == null) {
			return "";
		}
		int length = str.length();
		for (int i = length - 1; i >= 0; i--) {
			if (str.charAt(i) != 0x20) {
				break;
			}
			length--;
		}
		return str.substring(0, length);
	}

}
