package com.stock.data.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.stock.data.pojos.StockData;

public class StockDataSpider {
	// http://hq.sinajs.cn/list=M1805
	public static void main(String[] args) throws IOException {
//		String urlStr = "http://hq.sinajs.cn/list=M1805";
//		System.out.println(getStockData2Str(urlStr));
		StockData stockData = new StockData();
		stockData.setStockNum("M1805");
		StockData spiderStockData4Sina = spiderStockData4Sina(stockData);
		System.out.println(spiderStockData4Sina.toString());

	}

	private static final String URLPREFIXSTR = "http://hq.sinajs.cn/list=";

	public static StockData spiderStockData4Sina(StockData stockData) {
		String url = URLPREFIXSTR + stockData.getStockNum();
		String resultContent = getStockData2Str(url);
		return parseData2StockData(resultContent);
	}

	private static String getStockData2Str(String urlStr) {
		BufferedReader reader = null;
		try {
			URL url = new URL(urlStr);
			reader = new BufferedReader(new InputStreamReader(url.openStream(), "GBK"));
			String dataStr = new String(reader.readLine().getBytes("UTF-8"));
			return dataStr; 
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 解析期货数据，封装成StockData所需要的数据对象返回 0：豆粕连续，名字 1：145958，不明数字（难道是数据提供商代码？） 2：3170，开盘价
	 * 3：3190，最高价 4：3145，最低价 5：3178，昨日收盘价 （2013年6月27日） 6：3153，买价，即“买一”报价
	 * 7：3154，卖价，即“卖一”报价 8：3154，最新价，即收盘价 9：3162，结算价 10：3169，昨结算 11：1325，买 量 12：223，卖
	 * 量 13：1371608，持仓量 14：1611074，成交量 15：连，大连商品交易所简称 16：豆粕，品种名简称 17：2013-06-28，日期
	 * var
	 * hq_str_M1805="豆粕1805,221710,3087,3091,3043,3097,3048,3049,3049,3062,3115,371,400,1753900,659738,连,豆粕,2018-03-09,1,3158.000,3078.000,3159.000,2959.000,3159.000,2757.000,3159.000,2720.000,37.641";
	 * 
	 * @param dataStr
	 * @return StockData
	 */
	static Pattern p = Pattern.compile("(.*\")(.*)(\".*)");

	private static StockData parseData2StockData(String dataStr) {
		StockData stockData = new StockData();
		Matcher m = p.matcher(dataStr);
		String str = null;
		while (m.find()) {
			str = m.group(2);
			if(str.trim().equals("")) return null;
		}
		if (str != null) {
			String[] datas = str.split(",");
			stockData.setStockName(datas[0]);
			stockData.setVolume(Integer.parseInt(datas[14]));
			stockData.setHighPrice(Integer.parseInt(datas[13]));
			stockData.setHighPrice(Integer.parseInt(datas[3]));
			stockData.setLowPrice(Integer.parseInt(datas[4]));
			stockData.setTime(new Date());
		}
		return stockData;
	}
}
