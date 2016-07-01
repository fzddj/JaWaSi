package com.makedream.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池管理工具类
 */
public class ThreadUtil {

	/**
	 * 非固定数量线程池
	 */
	private static ExecutorService moreExecutorService;

	private static ExecutorService fixedExecutorService;


	/**
	 * 非固定数量线程池
	 * @param command
	 */
	public static void executeMore(Runnable command) {
		if (moreExecutorService == null)
			moreExecutorService = Executors.newCachedThreadPool();
		moreExecutorService.execute(command);
	}

	public static void executeSingle(Runnable command) {
		if (fixedExecutorService == null)
			fixedExecutorService = Executors.newFixedThreadPool(1);
		fixedExecutorService.execute(command);
	}


}
