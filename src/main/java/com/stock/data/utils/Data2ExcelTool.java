package com.stock.data.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

import com.stock.data.pojos.StockData;

//14 *　11
public class Data2ExcelTool {

	public static void main(String[] args) throws Exception {
		StockData stockData = new StockData();
		stockData.setStockNum("M1805");
		stockData.setTime(new Date());
		// stockName=豆粕1805, volume=324080, holdings=0, highPrice=3068, lowPrice=3045
		stockData.setStockName("豆粕1805");
		stockData.setVolume(324080);
		stockData.setHoldings(0);
		stockData.setHighPrice(3068);
		stockData.setLowPrice(3068);
//		stockData.setStockDirectory("d:/stock/");
		stockData2Excel(stockData);
		// createExcel();
		// CreateExcelDemo1();
	}

	public static void stockData2Excel(StockData stockData) throws Exception {
		// 如果没有设定文件夹，那么默认在c:/stock/文件夹中
		if (null == stockData.getStockDirectory()) {
			stockData.setStockDirectory("c:/stock/");
			if (!new File("c:/stock/").exists()) {
				// 如果c:/stock/文件夹都不存在，那么创建c:/stock/文件夹
				boolean mkdirs = new File("c:/stock/").mkdirs();
				if (!mkdirs) {
					throw new Exception("创建c:/stock/文件夹失败");
				}
			}
			if (!new File(stockData.getStockDirectory() + stockData.getStockNum() + ".xls").exists()) {
				// 如果在c:/stock/不存在该期货xls文件，则要创建一个新的模板，在插入数据
				buildStockDataExcelModule(stockData);
			}
		} else {
			if (!new File(stockData.getStockDirectory() + stockData.getStockNum() + ".xls").exists()) {
				buildStockDataExcelModule(stockData);
			}
		}
		// 插入数据stockData数据
		insertStockData2Excel(stockData);

	}

	private static void insertStockData2Excel(StockData stockData) {
		FileInputStream fileInputStream = null;
		FileOutputStream fos = null;
		try {
			fileInputStream = new FileInputStream(
					new File(stockData.getStockDirectory() + stockData.getStockNum() + ".xls"));
			HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
			// 获取第一个Sheet表
			HSSFSheet sheet1 = workbook.getSheetAt(0);// 或者 HSSFSheet sheet = workbook.getSheet("Sheet1");

			// 风格 居中加粗
			HSSFCellStyle style = workbook.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
			HSSFFont font = workbook.createFont();
			font.setBoldweight((short) 0x2bc);
			// font.setBold(true);
			style.setFont(font);
			HSSFCellStyle style2 = workbook.createCellStyle();
			style2.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
			style2.setFont(font);
			style2.setFillForegroundColor(HSSFColor.PINK.index2);
			style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			HSSFRow row = sheet1.getRow(2);
			// 查询没有插入数据开始的列
			int col = 2;
			while (null != row.getCell(col)) {
				++col;
			}
			// 从第col-1行开始没有插入数据
//			--col;

			HSSFCell cell = row.createCell(col);
			cell.setCellValue(stockData.getTime().getHours() + ":" + stockData.getTime().getMinutes());
			cell.setCellStyle(style2);

			row = sheet1.getRow(3);
			cell = row.createCell(col);
			cell.setCellValue(stockData.getVolume());
			cell.setCellStyle(style);

			row = sheet1.getRow(4);
			cell = row.createCell(col);
			cell.setCellValue(stockData.getHoldings());
			cell.setCellStyle(style);

			row = sheet1.getRow(5);
			cell = row.createCell(col);
			cell.setCellValue(stockData.getHighPrice());
			cell.setCellStyle(style);

			row = sheet1.getRow(6);
			cell = row.createCell(col);
			cell.setCellValue(stockData.getLowPrice());
			cell.setCellStyle(style);

			//写出去
			fos = new FileOutputStream(new File(stockData.getStockDirectory() + stockData.getStockNum() + ".xls"));
			workbook.write(fos);
		} catch (FileNotFoundException e) {
			System.err.println(stockData.getStockDirectory() + stockData.getStockNum() + "文件没有找到。");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println(stockData.getStockDirectory() + stockData.getStockNum() + "文件读取发生问题。");
			e.printStackTrace();
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// 创建一个期货的模板
	private static void buildStockDataExcelModule(StockData stockData) throws Exception {
		// 新建工作簿2007+
		// XSSFWorkbook workbook = new XSSFWorkbook();
		// XSSFSheet sheet1 = workbook.createSheet("M1805");
		// 新建工作表2003
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet1 = workbook.createSheet(stockData.getStockNum());

		// 风格 居中加粗
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		HSSFFont font = workbook.createFont();
		// //设置上下左右四个边框宽度
		/*
		 * style.setBorderTop(HSSFBorderFormatting.BORDER_THIN);
		 * style.setBorderBottom(HSSFBorderFormatting.BORDER_THIN);
		 * style.setBorderLeft(HSSFBorderFormatting.BORDER_THIN);
		 * style.setBorderRight(HSSFBorderFormatting.BORDER_THIN);
		 */
		font.setBoldweight((short) 0x2bc);
		// font.setBold(true);
		style.setFont(font);

		// 粉色不加粗字体
		HSSFFont font2 = workbook.createFont();
		font2.setColor(HSSFColor.PINK.index);
		//
		// 风格 居中加粗 cell粉色
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		// style2.setBorderBottom(HSSFColor.BLUE_GREY.index);
		// style2.setBorderLeft(HSSFColor.BLUE_GREY.index);
		// style2.setBorderRight(HSSFColor.BLUE_GREY.index);
		// style2.setBorderTop(HSSFColor.BLUE_GREY.index);

		/*
		 * style2.setBorderTop(HSSFBorderFormatting.BORDER_THIN);
		 * style2.setBorderBottom(HSSFBorderFormatting.BORDER_THIN);
		 * style2.setBorderLeft(HSSFBorderFormatting.BORDER_THIN);
		 * style2.setBorderRight(HSSFBorderFormatting.BORDER_THIN);
		 */

//		style2.setFillForegroundColor(HSSFColor.PINK.index2);
//		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setFont(font);
		// 背景blue 居中
		HSSFCellStyle style3 = workbook.createCellStyle();
		style3.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
//		style3.setFillForegroundColor(HSSFColor.BLUE.index);
//		style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		// 空一行
		// 第一行
		HSSFRow row = sheet1.createRow(1);
		HSSFCell cell = row.createCell(0);
		cell.setCellValue("日期");
		cell.setCellStyle(style);
		cell = row.createCell(1);
		cell.setCellValue(stockData.getStockNum());
		cell.setCellStyle(style);
		cell = row.createCell(7);
		cell.setCellValue(stockData.getStockNum() + "jifajfiajfjiajia");
		cell.setCellStyle(style);

		// 第二行
		row = sheet1.createRow(2);
		cell = row.createCell(0);
		Date date = new Date();
		cell.setCellValue(new HSSFRichTextString((date.getMonth() + 1) + "月" + date.getDate() + "日"));
		cell.setCellStyle(style);
		cell = row.createCell(1);
		cell.setCellValue("时间");
		cell.setCellStyle(style);

//		cell = row.createCell(10);
//		cell.setCellValue("备注");
//		cell.setCellStyle(style2);

		// 第三行
		row = sheet1.createRow(3);
		cell = row.createCell(1);
		cell.setCellValue("成交量原始数据");
		cell.setCellStyle(style);

		// 第四行
		row = sheet1.createRow(4);
		cell = row.createCell(1);
		cell.setCellValue("持仓量原始数据");
		cell.setCellStyle(style);
		// 第五行
		row = sheet1.createRow(5);
		cell = row.createCell(1);
		cell.setCellValue("最高价");
		cell.setCellStyle(style);
		// 第六行
		row = sheet1.createRow(6);
		cell = row.createCell(1);
		cell.setCellValue("最低价");
		cell.setCellStyle(style);

		row = sheet1.createRow(7);
		cell = row.createCell(1);
		cell.setCellValue("以下核算");
		cell.setCellStyle(style);

		row = sheet1.createRow(8);
		cell = row.createCell(1);
		cell.setCellValue("成交量");
		cell.setCellStyle(style);

		row = sheet1.createRow(9);
		cell = row.createCell(1);
		cell.setCellValue("每小时");
		cell.setCellStyle(style);

		row = sheet1.createRow(10);
		cell = row.createCell(1);
		cell.setCellValue("每1%");
		cell.setCellStyle(style);

		row = sheet1.createRow(11);
		cell = row.createCell(1);
		cell.setCellValue("持仓量");
		cell.setCellStyle(style);

		row = sheet1.createRow(12);
		cell = row.createCell(1);
		cell.setCellValue("涨");
		cell.setCellStyle(style);

		row = sheet1.createRow(13);
		cell = row.createCell(1);
		cell.setCellValue("每小时/每1%");
		cell.setCellStyle(style);

		// 创建表格合并表格
		CellRangeAddress cellRangeAddress2 = new CellRangeAddress(1, 1, 1, 6);
		sheet1.addMergedRegion(cellRangeAddress2);
		CellRangeAddress cellRangeAddress3 = new CellRangeAddress(1, 1, 7, 10);
		sheet1.addMergedRegion(cellRangeAddress3);

		CellRangeAddress cellRangeAddress = new CellRangeAddress(2, 13, 0, 0);
		sheet1.addMergedRegion(cellRangeAddress);
		CellRangeAddress cellRangeAddress4 = new CellRangeAddress(7, 7, 1, 10);
		sheet1.addMergedRegion(cellRangeAddress4);

		FileOutputStream fileOutputStream = null;
		try {
			if (null != stockData.getStockDirectory()) {
				File file = new File(stockData.getStockDirectory());
				if (!file.exists()) {
					file.mkdirs();
				}
				if (null == stockData.getStockNum() || stockData.getStockNum().trim().equals("")) {
					fileOutputStream = new FileOutputStream(
							stockData.getStockDirectory() + new Date().getTime() + ".xls");
				} else {
					fileOutputStream = new FileOutputStream(
							stockData.getStockDirectory() + stockData.getStockNum() + ".xls");
				}
				workbook.write(fileOutputStream);
			} else {
				throw new Exception("存放" + stockData.getStockNum() + "数据目录不能为空");
			}

		} catch (IOException e) {
			System.out.println("文件路径出错！");
			e.printStackTrace();
		} finally {
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
