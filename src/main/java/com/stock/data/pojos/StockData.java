package com.stock.data.pojos;

import java.util.Date;

/**
 * 股票数据 第一，成交量，第二持仓量，第三，最高价，第四，最低价
 * 
 * @author Administrator
 *
 */
public class StockData {

	// 获取数据时间
	private Date time;

	// 期货名称
	private String stockName;

	private String stockNum;

	// 成交量
	private int volume;
	// 持仓量
	private int holdings;
	// 最高价
	private int highPrice;
	// 最低价
	private int lowPrice;

	// 存放数据的目录
	private String stockDirectory;

	public StockData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StockData(String stockNum, String stockDirectory) {
		super();
		this.stockNum = stockNum;
		this.stockDirectory = stockDirectory;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public String getStockNum() {
		return stockNum;
	}

	public void setStockNum(String stockNum) {
		this.stockNum = stockNum;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public int getHoldings() {
		return holdings;
	}

	public void setHoldings(int holdings) {
		this.holdings = holdings;
	}

	public int getHighPrice() {
		return highPrice;
	}

	public void setHighPrice(int highPrice) {
		this.highPrice = highPrice;
	}

	public int getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(int lowPrice) {
		this.lowPrice = lowPrice;
	}

	public String getStockDirectory() {
		return stockDirectory;
	}

	public void setStockDirectory(String stockDirectory) {
		this.stockDirectory = stockDirectory;
	}

	@Override
	public String toString() {
		return "StockData [stockName=" + stockName + ", volume=" + volume + ", holdings=" + holdings + ", highPrice="
				+ highPrice + ", lowPrice=" + lowPrice + "]";
	}

}
