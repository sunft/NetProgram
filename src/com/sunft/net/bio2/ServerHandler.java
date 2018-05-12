package com.sunft.net.bio2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 被封装的服务端任务
 * @author sunft
 *
 */
public class ServerHandler implements Runnable {
	
	private Socket socket;
	
	public ServerHandler(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		BufferedReader in = null;
		PrintWriter out = null;
		
		try {
			in = new BufferedReader(
					new InputStreamReader(
							this.socket.getInputStream()));
			//第二个参数为自动flush
			out = new PrintWriter(this.socket.getOutputStream(), true);
			String body = null;
			while(true) {
				//从客户端读取信息
				body = in.readLine();
				if(body == null) break;
				//打印从客户端接收到的信息
				System.out.println("Server:" + body);
				//向客户端发送信息
				out.println("Server response");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if(out != null) {
				out.close();
			}
			
			if(socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	} 

}
