package com.stock.data.controller;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.stock.data.thread.Task;

public class StockDataManager {

	private static ExecutorService executors = Executors.newCachedThreadPool();
	private static int numTask = 0;

	private static Set<String> tasks = new HashSet<String>();

	/**
	 * 返回该任务是否添加的信息
	 * @param task
	 * @return
	 */
	public static synchronized String addTask(Task task) {
		String taskName = task.getName();
		if (tasks.contains(taskName)) {
			return "该期货已经加入到任务池中，请不要重复添加。";
		} else {
			executors.submit(task);
			++numTask;
			return "添加一个任务：" + task.getStockData().getVolume() + ".当前任务数为" + numTask + "个。";
		}
	}

	/**
	 * 
	 */
	public static synchronized void shutdownStock() {
		executors.shutdown();
	}

	public static ExecutorService getExecutors() {
		return executors;
	}

	public static void setExecutors(ExecutorService executors) {
		StockDataManager.executors = executors;
	}

	public static int getNumTask() {
		return numTask;
	}

	public static void setNumTask(int numTask) {
		StockDataManager.numTask = numTask;
	}

}
