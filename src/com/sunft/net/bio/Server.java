package com.sunft.net.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	final static int PORT = 8765;
	
	public static void main(String[] args) {
		ServerSocket server = null;
		try {
			/**
			 * 默认端口为127.0.0.1
			 */
			server = new ServerSocket(PORT);
			System.out.println("Server start...");
			//进行阻塞
			Socket socket = server.accept();
			
			//新建一个线程执行客户端的任务
			new Thread(new ServerHandler(socket)).start();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(server != null) {
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			server = null;
		}
				
	}

}
