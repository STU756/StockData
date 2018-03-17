package com.stock.data.thread;

import java.text.SimpleDateFormat;

import com.stock.data.pojos.StockData;
import com.stock.data.utils.Data2ExcelTool;
import com.stock.data.utils.StockDataSpider;

public class Task extends Thread {
	private StockData stockData;
	// 间隔时间获取数据
	private int timeIntervalMinutes;

	public StockData getStockData() {
		return stockData;
	}

	public void setStockData(StockData stockData) {
		this.stockData = stockData;
	}

	public Task(StockData stockData, int timeIntervalMinutes) {
		super(stockData.getStockNum());
		this.stockData = stockData;
		this.timeIntervalMinutes = timeIntervalMinutes;
	}

	public int getTimeIntervalMinutes() {
		return timeIntervalMinutes * 60;
	}

	@Override
	public void run() {
		try {
			StockData spider4SinaStockData = StockDataSpider.spiderStockData4Sina(stockData);
			if (null == spider4SinaStockData) {
				System.out.println(stockData.getStockNum() + "期货没有数据，请检查期货号码。");
				Thread.currentThread().interrupt();
			} else {
				stockData.setStockName(spider4SinaStockData.getStockName());
				stockData.setVolume(spider4SinaStockData.getVolume());
				stockData.setHighPrice(spider4SinaStockData.getHighPrice());
				stockData.setLowPrice(spider4SinaStockData.getLowPrice());
				Data2ExcelTool.stockData2Excel(stockData);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				System.out.println(stockData.getVolume() + "期货在" + sdf.format(stockData.getTime()) + "时间存放在"
						+ stockData.getStockDirectory() + "文件夹中。");
				System.out.println("等待" + this.timeIntervalMinutes + "分钟再获取数据.");
			}
			Thread.sleep(getTimeIntervalMinutes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		for (int i = 1; i < 6; i++) {
			new Task(new StockData("M180" + i, "c://stock//"), 10).start();
		}
	}
}
