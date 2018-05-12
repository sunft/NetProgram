package com.sunft.net.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * �ͻ���
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
			System.out.println("׼�������ͻ����׽���");
			socket = new Socket(ADDRESS, PORT);
			in = new BufferedReader(
					new InputStreamReader(
							socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			
			//���������������
			out.println("���յ��ͻ��˵���������...");
			//��ȡ����˻��͵���Ӧ
			String response = in.readLine();
			//��ӡ�ӷ��������յ�����Ӧ
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



