package com.sunft.net.netty.hello;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * ҵ�����
 * @author sunft
 *
 */
public class ServerHandler extends ChannelHandlerAdapter {

	/**
	 * �������˶�ȡ����ʹ�õ��Ǹ÷���,�ӹܵ��������������
	 * ctx:ͨ��������
	 * msg���ͻ��˴�����������
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		
		//�ֽ�����ʹ��utf-8ת���ַ���
		String body = new String(req, "utf-8");
		System.out.println("Server:" + body);
		
		//����˸��ͻ��˵���Ӧ
		String response = "Hi Client!";
		//���ֽ�����ת����buffer,���͸��ͻ���
		//��д�������Զ��ͷ����ü�������msg;���û��д����,��Ҫ�ֹ��ͷ�msg
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
		System.out.println("������");
		ctx.flush();
	}
	
}
