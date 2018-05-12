package com.sunft.net.mina.helloworld;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class TimeClientHandler extends IoHandlerAdapter {

	/**
	 * �����յ��ͻ��˵�������Ϣ�󴥷��˷���
	 */
	public void messageReceived(IoSession session, Object message) throws Exception {
		String content = message.toString();
		System.out.println("client receive a message is :" + content);
	}
	
	/**
	 * ����Ϣ�Ѿ����͸��ͻ��˺󴥷��˷���
	 */
	public void messageSent(IoSession session, Object message) throws Exception {
		System.out.println("message - > : " + message);
	}
}
