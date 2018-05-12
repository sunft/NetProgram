package com.sunft.net.netty.test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * ����ʹ���̳߳�����ʱ����
 * @author sunft
 *
 */
public class TestTimeJob {

	public static void main(String[] args) {
		Temp command = new Temp();
		
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		//5���Ӻ�ʼִ��,�Ժ�ÿ��1��ִ��һ��
		ScheduledFuture<?> scheduleTask = scheduler
				.scheduleWithFixedDelay(command, 5, 1, TimeUnit.SECONDS);
	}

}

class Temp extends Thread {

	@Override
	public void run() {
		System.out.println("run");
	}
	
}
