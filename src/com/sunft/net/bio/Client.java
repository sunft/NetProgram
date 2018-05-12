package com.sunft.net.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 客户端
 * @author sunft
 *
 */
public class Client {
	
	final static String ADDRESS = "127.0.0.1";
	final static int PORT = 8765;

	public static void main(String[] args) {
		
		Socket socket = null;
		BufferedReader in = null;
		PrintWriter out = null;
		
		try {
			System.out.println("准备创建客户端套接字");
			socket = new Socket(ADDRESS, PORT);
			in = new BufferedReader(
					new InputStreamReader(
							socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			
			//向服务器发送数据
			out.println("接收到客户端的请求数据...");
			//读取服务端回送的响应
			String response = in.readLine();
			//打印从服务器接收到的响应
			System.out.println("Client:" + response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if(out != null) {
						try {
							out.close();
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
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
			}
		}
		
	}

}




