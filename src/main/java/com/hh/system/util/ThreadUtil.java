package com.hh.system.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadUtil {
	public static int threadUtilMax = 150;
	// 创建一个可重用固定线程数的线程池
	public static ExecutorService threadPool = Executors.newFixedThreadPool(threadUtilMax);
	public static ExecutorService getThreadPool() {
		return threadPool;
	}
	public static void setThreadPool(ExecutorService threadPool) {
		ThreadUtil.threadPool = threadPool;
	}
	
}
