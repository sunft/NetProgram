package com.sunft.net.aio;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * AIO�����
 * @author sunft
 *
 */
public class Server {
	//�̳߳�
	private ExecutorService executorService;
	//�߳���
	private AsynchronousChannelGroup threadGroup;
	//�첽������ͨ��
	public AsynchronousServerSocketChannel assc;
	
	public Server(int port) {
		try {
			//����һ�������
			executorService = Executors.newCachedThreadPool();
			//�����߳���
			threadGroup = AsynchronousChannelGroup.withCachedThreadPool(executorService, 1);
			//����������ͨ��
			assc = AsynchronousServerSocketChannel.open(threadGroup);
			//���а�
			assc.bind(new InetSocketAddress(port));
			
			System.out.println("Server start, port:" + port);
			//����,�Զ���Handler���д���
			assc.accept(this, new ServerCompletionHandler());
			//һֱ����,���÷�����ֹͣ
			Thread.sleep(Integer.MAX_VALUE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		Server server = new Server(8765);
	}

}
