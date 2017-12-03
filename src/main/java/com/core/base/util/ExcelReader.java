package com.core.base.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelReader {
	private Workbook workbook = null;
	private Sheet sheet = null;
	private FormulaEvaluator evaluator  =null;
	public void init(InputStream is) {
		try {
			workbook = WorkbookFactory.create(is);
			evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getNumberOfSheet() {
		return workbook.getNumberOfSheets();
	}

	public Sheet getSheet(int sheetNo) {
		sheet = workbook.getSheetAt(sheetNo - 1);
		return sheet;
	}

	public String readText(int rowNo, int colNo) {
		String s = null;
		Row row = sheet.getRow(rowNo - 1);
		if (row != null) {
			
			Cell cell = row.getCell(colNo - 1);
			if (cell != null) {

				if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
					s = cell.getStringCellValue();
				}

			}
		}
		return s;
	}

	public String readContent(int rowNo, int colNo) {
		String s = null;
		Row row = sheet.getRow(rowNo - 1);
		if (row != null) {
			Cell cell = row.getCell(colNo - 1);
			if (cell != null) {

				if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
					s = readFormula(rowNo, colNo);
				}
				if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
					s = readText(rowNo, colNo);
				}
				if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					s = readNumeric(rowNo, colNo);
				}

			}
		}
		return s;
	}

	public String readFormula(int rowNo, int colNo) {
		String s = null;
		Row row = sheet.getRow(rowNo - 1);
		if (row != null) {

			Cell cell = row.getCell(colNo - 1);
			if (cell != null) {

				if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
					double d = evaluator.evaluate(cell).getNumberValue();
					Integer i = new Integer((int) d);
					s = i.toString();
				}

			}
		}
		return s;
	}

	public String readNumeric(int rowNo, int colNo) {
		String result = null;
		Row row = sheet.getRow(rowNo - 1);
		if (row != null) {
			Cell cell = row.getCell(colNo - 1);
			if (cell != null) {

				// 判断数据类型
				if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					if (DateUtil.isCellDateFormatted(cell)) {
						Date d = cell.getDateCellValue();
						DateFormat formater = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						result = formater.format(d);
					} else {
						Double d = cell.getNumericCellValue();
						//去除科学计数，格式化输出
						NumberFormat   formatter   =   NumberFormat.getNumberInstance();
				        formatter.setMaximumFractionDigits(10);//设置最大小数位(10位)
				        formatter.setGroupingUsed(false);//不使用分组显示
				        result =  formatter.format(d);
					}
				}
			}
		}
		return result;
	}

	public String getCellType(int rowNo, int colNo) {
		Row row = sheet.getRow(rowNo - 1);
		if (row != null) {
			Cell cell = row.getCell(colNo - 1);
			if (cell != null) {
				if (cell.getCellType() == 0) {
					return "numeric";
				} else if (cell.getCellType() == 1) {
					return "string";
				} else {
					return "other";
				}
			} else {
				return null;
			}
		} else {
			System.out.println(" row is null");
			return null;
		}
	}

	public boolean isEnd(int rowNo) {
		Row row = sheet.getRow(rowNo - 1);
		if (row == null) {
			return true;
		} else {
			return false;
		}
	}

}
