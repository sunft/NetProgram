package com.sunft.net.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * �ͻ���
 * @author sunft
 *
 */
public class Client {

	//��Ҫһ��Selector
	public static void main(String[] args) {
		//�������ӵĵ�ַ
		InetSocketAddress address 
			= new InetSocketAddress("127.0.0.1", 8765);
		
		//��������ͨ��
		SocketChannel sc = null;
		
		//����������
		ByteBuffer buf = ByteBuffer.allocate(1024);
		
		try {
			//��ͨ��
			sc = SocketChannel.open();
			//��������,���ﲢ����TCP�������ֵ���Ϊ,����
			//һ��ע�����Ϊ
			sc.connect(address);
			
			while(true) {
				//����һ���ֽ�����,Ȼ��ʹ��ϵͳ¼�빦��
				byte[] bytes = new byte[1024];
				//����̨����
				System.in.read(bytes);
				
				//�����ݷŵ���������
				buf.put(bytes);
				//�Ի��������и�λ
				buf.flip();
				//д������
				sc.write(buf);
				//��ջ�����
				buf.clear();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(sc != null) {
				try {
					sc.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

}
