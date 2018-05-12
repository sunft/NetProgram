package com.sunft.net.bio2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * ����װ�ķ��������
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
			//�ڶ�������Ϊ�Զ�flush
			out = new PrintWriter(this.socket.getOutputStream(), true);
			String body = null;
			while(true) {
				//�ӿͻ��˶�ȡ��Ϣ
				body = in.readLine();
				if(body == null) break;
				//��ӡ�ӿͻ��˽��յ�����Ϣ
				System.out.println("Server:" + body);
				//��ͻ��˷�����Ϣ
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
