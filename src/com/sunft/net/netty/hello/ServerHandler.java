package com.sunft.net.netty.hello;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * 业务代码
 * @author sunft
 *
 */
public class ServerHandler extends ChannelHandlerAdapter {

	/**
	 * 服务器端读取数据使用的是该方法,从管道里面读出数据来
	 * ctx:通道上下文
	 * msg：客户端传过来的数据
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		
		//字节数组使用utf-8转成字符串
		String body = new String(req, "utf-8");
		System.out.println("Server:" + body);
		
		//服务端给客户端的响应
		String response = "Hi Client!";
		//将字节数组转换成buffer,回送给客户端
		//有写操作会自动释放引用计数对象msg;如果没有写操作,需要手工释放msg
		ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes()));
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Server channel active...");
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.out.println("读完了");
		ctx.flush();
	}
	
}
