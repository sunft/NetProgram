package com.sunft.net.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 客户端
 * @author sunft
 *
 */
public class Client {

	//需要一个Selector
	public static void main(String[] args) {
		//创建连接的地址
		InetSocketAddress address 
			= new InetSocketAddress("127.0.0.1", 8765);
		
		//声明连接通道
		SocketChannel sc = null;
		
		//建立缓冲区
		ByteBuffer buf = ByteBuffer.allocate(1024);
		
		try {
			//打开通道
			sc = SocketChannel.open();
			//进行连接,这里并不是TCP三次握手的行为,而是
			//一个注册的行为
			sc.connect(address);
			
			while(true) {
				//定义一个字节数组,然后使用系统录入功能
				byte[] bytes = new byte[1024];
				//控制台输入
				System.in.read(bytes);
				
				//把数据放到缓冲区中
				buf.put(bytes);
				//对缓冲区进行复位
				buf.flip();
				//写出数据
				sc.write(buf);
				//清空缓冲区
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
