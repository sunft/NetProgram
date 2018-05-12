package com.sunft.net.netty.ende01;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ServerHandler extends ChannelHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		//�������ֱ��ת��String
		String request = (String) msg;
		System.out.println("Server:" + request);
		String response = "������Ӧ����$_";
		
		ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes()));
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channel Active ...");
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.out.println("�����������ݶ����ˡ�����������");
	}
	
}
