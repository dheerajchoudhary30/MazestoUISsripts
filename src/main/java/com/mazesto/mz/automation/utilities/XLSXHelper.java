package com.mazesto.mz.automation.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class XLSXHelper {
	private static Logger log = LogManager.getLogger("XLSXHelper");

	public XLSXHelper() {
	}

	private static Workbook newWorkbook;


	
	public static String getSpecificCellValue(String filePath, int columnNumber, String sheetName, int rowNumber) {

		try {
			newWorkbook = new XSSFWorkbook(new FileInputStream(new File(filePath)));
		} catch (IOException e) {
			log.error(e.getMessage());
			log.error(e.getStackTrace());
		}
		for (int i = 0; i < newWorkbook.getNumberOfSheets(); i++) {
			Sheet firstSheet = newWorkbook.getSheetAt(i);
			if (firstSheet.getSheetName().toUpperCase().contains(sheetName.toUpperCase())) {

				Row row = firstSheet.getRow(rowNumber - 1);

				for (Cell cell : row) {
					int columnIndex = cell.getColumnIndex();
					if (columnIndex == columnNumber) {
						return getCellValue(row.getCell(columnIndex));
					}
				}

				return null;
			}
		}

		return null;
	}

	public static void setSpecificCellValue(String filePath, int columnNumber, String sheetName, int rowNumber,
			String valueToSet) throws IOException {

		try {
			newWorkbook = new XSSFWorkbook(new FileInputStream(new File(filePath)));
		} catch (IOException e) {
			log.error(e.getMessage());
			log.error(e.getStackTrace());
		}
		for (int i = 0; i < newWorkbook.getNumberOfSheets(); i++) {
			Sheet firstSheet = newWorkbook.getSheetAt(i);
			if (firstSheet.getSheetName().toUpperCase().contains(sheetName.toUpperCase())) {

				Row row = firstSheet.getRow(rowNumber - 1);

				for (Cell cell : row) {
					int columnIndex = cell.getColumnIndex();
					if (columnIndex == columnNumber) {
						cell.setCellValue(valueToSet);
					}
				}

			}
		}

		FileOutputStream out = new FileOutputStream(new File(filePath));
		newWorkbook.write(out);
		out.close();
		System.out.println("DiscoverUtility.xlsx written successfully on disk.");
		newWorkbook.setForceFormulaRecalculation(true);
		

	}

	public static void setNewCellValueInAColumn(String filePath, int columnNumber, String sheetName,
			List<String> valueToSet) throws IOException {

		try {
			newWorkbook = new XSSFWorkbook(new FileInputStream(new File(filePath)));
		} catch (IOException e) {
			log.error(e.getMessage());
			log.error(e.getStackTrace());
		}
		for (int i = 0; i < newWorkbook.getNumberOfSheets(); i++) {
			Sheet firstSheet = newWorkbook.getSheetAt(i);
			if (firstSheet.getSheetName().toUpperCase().contains(sheetName.toUpperCase())) {
				int rownum = firstSheet.getPhysicalNumberOfRows();

				Row row = firstSheet.createRow(rownum);
				int cellnum = 0;
				for (String str : valueToSet) {
					Cell cell = row.createCell(cellnum++);
					cell.setCellValue(str);
				}
			}

		}

		FileOutputStream out = new FileOutputStream(new File(filePath));
		newWorkbook.write(out);
		out.close();
		System.out.println("DiscoverUtility.xlsx written successfully on disk.");

	}

	
	public static String readSpecificColumnValue(String uniqueId, String columnName, String sheetName,
			String filePath) {
		try {
			newWorkbook = new XSSFWorkbook(new FileInputStream(new File(filePath)));
		} catch (IOException e) {
			log.error(e.getMessage());
			log.error(e.getStackTrace());
		}
		for (int i = 0; i < newWorkbook.getNumberOfSheets(); i++) {
			Sheet firstSheet = newWorkbook.getSheetAt(i);
			if (firstSheet.getSheetName().toUpperCase().contains(sheetName.toUpperCase())) {
				return getCellValue(uniqueId, columnName, firstSheet);
			}
		}

		return null;
	}

	public static String getCellValue(String uniqueId, String columnName, Sheet firstSheet) {
		Row row = firstSheet.getRow(0);
		int columnIndex = 0;
		for (Cell cell : row) {
			if (columnName.equalsIgnoreCase(cell.getStringCellValue())) {
				columnIndex = cell.getColumnIndex();
				break;
			}
		}

		for (Row tempRow : firstSheet) {
			String uniqueRowId = tempRow.getCell(0).getStringCellValue();

			if (uniqueRowId.equalsIgnoreCase(uniqueId)) {
				return getCellValue(tempRow.getCell(columnIndex));
			}
		}
		return null;
	}


	private static String getCellValue(Cell cell) {
		String cellValue = null;
		if (cell != null) {
			switch (cell.getCellType()) {
			case BOOLEAN:
				cellValue = String.valueOf(cell.getBooleanCellValue());
				break;
			case NUMERIC:
				cellValue = String.valueOf(cell.getNumericCellValue());
				break;
			case STRING:
				cellValue = cell.getStringCellValue();
				break;
			case BLANK:
				break;
			case ERROR:
				cellValue = String.valueOf(cell.getErrorCellValue());
				break;
			case FORMULA:
				cellValue = cell.getStringCellValue();
				break;
			default:
				cellValue = cell.getStringCellValue();
			}
		}

		return cellValue;
	}

	public static int getNumberOfRowsInASheet(String filePath, String sheetName) throws IOException {

		try {
			newWorkbook = new XSSFWorkbook(new FileInputStream(new File(filePath)));
		} catch (IOException e) {
			log.error(e.getMessage());
			log.error(e.getStackTrace());
		}
		for (int i = 0; i < newWorkbook.getNumberOfSheets(); i++) {
			Sheet firstSheet = newWorkbook.getSheetAt(i);
			if (firstSheet.getSheetName().toUpperCase().contains(sheetName.toUpperCase())) {
				int rownum = firstSheet.getPhysicalNumberOfRows();

				return rownum;
			}
		}

		return 0;
	}

	public static List<String> getColumnValueasList(int columnNumber, String filePath, String sheetName) throws IOException {
		int rowNumber = 0;
		List<String> list = new ArrayList<String>();

		try {
			newWorkbook = new XSSFWorkbook(new FileInputStream(new File(filePath)));
		} catch (IOException e) {
			log.error(e.getMessage());
			log.error(e.getStackTrace());
		}
		for (int i = 0; i < newWorkbook.getNumberOfSheets(); i++) {
			Sheet firstSheet = newWorkbook.getSheetAt(i);
			if (firstSheet.getSheetName().toUpperCase().contains(sheetName.toUpperCase())) {
				int rownum = firstSheet.getPhysicalNumberOfRows();
				for(int j = 0; j <rownum;j++) {
				Row row = firstSheet.getRow(rowNumber++);

				for (Cell cell : row) {
					int columnIndex = cell.getColumnIndex();
					if (columnIndex == columnNumber) {
						list.add(getCellValue(row.getCell(columnIndex)));
					}
				}
				}
			}
		}

		return list;
	}

}
