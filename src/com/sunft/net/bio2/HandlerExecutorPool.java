package com.sunft.net.bio2;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * �Զ����̳߳�
 * @author sunft
 *
 */
public class HandlerExecutorPool {
	
	private ExecutorService executor;

	public HandlerExecutorPool(int maxPoolSize, int queueSize) {
		this.executor = new ThreadPoolExecutor(
				Runtime.getRuntime()
				.availableProcessors(),//��ȡ���е�CPU����
				maxPoolSize,
				120L,
				TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(queueSize));
	}
	
	public void execute(Runnable task) {
		this.executor.execute(task);
	}

}


