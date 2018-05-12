package com.sunft.net.mina.helloworld;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

/**
 * Mina �ͻ���
 * @author alienware
 *
 */
public class ClientMain {

	public static void main(String[] args) {
		//�����ͻ�������
		NioSocketConnector connnector = new NioSocketConnector();
		
		connnector.getFilterChain().
		addLast("logger", new LoggingFilter());
		
		connnector.getFilterChain().
		addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
		
		//�������ӳ�ʱ���ʱ��
		connnector.setConnectTimeoutCheckInterval(30);
		connnector.setHandler(new TimeClientHandler());
		
		//��������
		ConnectFuture cf = connnector.connect(new InetSocketAddress("127.0.0.1", 6488));
		
		//�ȴ����Ӵ������
		cf.awaitUninterruptibly();
		
		cf.getSession().write("Hi Server!");
		cf.getSession().write("quit");
		
		//�ȴ����ӶϿ����ͷ�����
		cf.getSession().getCloseFuture().awaitUninterruptibly();
		connnector.dispose();
		
	}
}
