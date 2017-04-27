package com.hh.system.util.document;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class ReadExcel {
	public static void main(String[] args) {
		File file = new File("D:\\人员管理2012年12月12日.xls");
		List<Map<String, Object>> mapList = excelFileToMapList(file, 3);
		System.out.println(mapList);
	}

	public static List<Map<String, Object>> excelFileToMapList(File file,
			int titleRow) {
		try {
			List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
			Workbook book = Workbook.getWorkbook(file);
			Sheet sheet = book.getSheet(0); // 获得第一个工作表对象
			for (int i = titleRow ; i < sheet.getRows(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (int j = 0; j < sheet.getColumns(); j++) {
					Cell cell1 = sheet.getCell(j, i);
					String string = cell1.getContents();
					map.put(sheet.getCell(j, titleRow - 1).getContents(),
							string);
				}
				mapList.add(map);
			}
			return mapList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
