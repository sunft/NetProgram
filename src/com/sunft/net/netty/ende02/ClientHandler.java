package com.sunft.net.netty.ende02;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

public class ClientHandler extends ChannelHandlerAdapter {
	/**
	 * ���շ���˻��͵�����
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		try {
			//�������ֱ��ת��String
			String response = (String) msg;
			System.out.println("Client:" + response);
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Client Channel Active ...");
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.out.println("�ͻ��˶�ȡ������ϡ�����������");
	}
}


