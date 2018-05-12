package com.sunft.net.netty.heartbeat;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;

/**
 * ������������������
 * @author sunft
 *
 */
public class ServerHeartBeatHandler extends ChannelHandlerAdapter {
	
	/** key:ip value:auth*/
	private static HashMap<String, String> AUTH_IP_MAP = new HashMap<String, String>();
	private static final String SUCCESS_KEY = "auth_success_key";
	
	static {
		//��ʼ��֤��
		AUTH_IP_MAP.put("192.168.248.1", "1234");
	}
	
	/**
	 * ��֤����
	 * @param ctx
	 * @param msg
	 * @return
	 */
	private boolean auth(ChannelHandlerContext ctx, Object msg) {
		String[] ret = ((String)msg).split(",");
		String auth = AUTH_IP_MAP.get(ret[0]);
		if(auth != null && auth.equals(ret[1])) {
			ctx.writeAndFlush(SUCCESS_KEY);
			return true;
		} else {
			ctx.writeAndFlush("auth failure!").addListener(ChannelFutureListener.CLOSE);
			return false;
		}
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		if(msg instanceof String) {
			auth(ctx, msg);
		} else if(msg instanceof RequestInfo) {
			RequestInfo info = (RequestInfo) msg;
			System.out.println("------------------------");
			System.out.println("��ǰ����IPΪ:" + info.getIp());
			System.out.println("��ǰ����CPU�����");
			HashMap<String, Object> cpu = info.getCpuPerMap();
			System.out.println("��ʹ����:" + cpu.get("combined"));
			System.out.println("�û�ʹ����:" + cpu.get("user"));
			System.out.println("ϵͳʹ����:" + cpu.get("sys"));
			System.out.println("�ȴ���:" + cpu.get("wait"));
			System.out.println("������:" + cpu.get("idle"));
			
			System.out.println("��ǰ������Memory�����");
			HashMap<String, Object> memory = info.getMemoryMap();
			System.out.println("�ڴ�����:" + memory.get("total"));
			System.out.println("��ǰ�ڴ�ʹ����:" + memory.get("used"));
			System.out.println("��ǰ�ڴ�ʣ������" + memory.get("free"));
			System.out.println("--------------------------------");
			
			ctx.writeAndFlush("info received!");
		} else {
			//��֤ʧ��,д����Ϣ���ͻ���,���ҶϿ�����
			ctx.writeAndFlush("connect failure!").addListener(ChannelFutureListener.CLOSE);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
	}


	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
	}
	
}


