package com.sunft.net.netty.ende01;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ServerHandler extends ChannelHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		//这里可以直接转成String
		String request = (String) msg;
		System.out.println("Server:" + request);
		String response = "我是响应数据$_";
		
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
		System.out.println("服务器端数据读完了。。。。。。");
	}
	
}
