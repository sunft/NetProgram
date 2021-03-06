package com.sunft.net.bio2;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 自定义线程池
 * @author sunft
 *
 */
public class HandlerExecutorPool {
	
	private ExecutorService executor;

	public HandlerExecutorPool(int maxPoolSize, int queueSize) {
		this.executor = new ThreadPoolExecutor(
				Runtime.getRuntime()
				.availableProcessors(),//获取运行的CPU核数
				maxPoolSize,
				120L,
				TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(queueSize));
	}
	
	public void execute(Runnable task) {
		this.executor.execute(task);
	}

}


