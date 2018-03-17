package com.stock.data.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.stock.data.pojos.StockData;
import com.stock.data.thread.Task;

/**
 * Servlet implementation class StockDataServlet
 */
public class StockDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final StockDataManager stockDataManager = new StockDataManager();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StockData stockData = new StockData();
		String stockNum =  request.getParameter("stockNum");
		response.setContentType("text/html");
		String timeIntervalMinStr = request.getParameter("timeIntervalMinutes");
		String directory = request.getParameter("stockDirectory");
		String responseStr = null;
		if(null != stockNum && !stockNum.trim().equals("")) {
			stockData.setStockNum(stockNum);
			if(null != timeIntervalMinStr && timeIntervalMinStr.trim().equals("")) {
				int timeIntervalMinutes = Integer.parseInt(timeIntervalMinStr);
				if(timeIntervalMinutes > 0 && timeIntervalMinutes < 300) {
					if(null != directory) {
						stockData.setStockDirectory(directory);
					}else {
						response.getWriter().println("期货数据存放文件目录为空，默认把这些数据存放到c:/stock/中，请到该目录查看。");
					}
					//创建一个任务Task
					Task task = new Task(stockData, timeIntervalMinutes);
					//加到线程中执行
					
					String addTaskMassage = stockDataManager.addTask(task);
					//添加任务返回信息，看是否添加成功，添加是否重复
					response.getWriter().println(addTaskMassage);
				}else {
					response.getWriter().println("时间间隔数值大于0，小于300，单位为分钟。");
				}
			}else {
				response.getWriter().println("时间间隔不能为空，单位为分钟。");
			}
		}else {
			response.getWriter().println("期货序号不能为空。");
		}
		response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println(request.getContextPath());
		response.sendRedirect(request.getServletContext().getContextPath() +"/stockdata.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
