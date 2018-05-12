package com.sunft.net.netty.ende01;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class Server {
	
	public static void main(String[] args) throws Exception {
		//1������2���߳���,һ���Ǹ�����տͻ��˵�����,һ���Ǹ���������ݴ����
		EventLoopGroup pGroup = new NioEventLoopGroup();
		EventLoopGroup cGroup = new NioEventLoopGroup();
		
		//2������������������
		ServerBootstrap b = new ServerBootstrap();
		b.group(pGroup, cGroup)
		.channel(NioServerSocketChannel.class)
		.option(ChannelOption.SO_BACKLOG, 1024)
		.option(ChannelOption.SO_SNDBUF, 32*1024)
		.option(ChannelOption.SO_RCVBUF, 32*1024)
		.childHandler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				//��������ָ���
				ByteBuf buf = Unpooled.copiedBuffer("$_".getBytes());
				ch.pipeline().addLast(
						new DelimiterBasedFrameDecoder(1024, buf));
				//�����ַ�����ʽ�Ľ���
				ch.pipeline().addLast(new StringDecoder());
				ch.pipeline().addLast(new ServerHandler());
			}
		});
		
		//4��������
		ChannelFuture cf = b.bind(8765).sync();
		
		//5���ȴ������������˿ڹر�
		cf.channel().closeFuture().sync();
		pGroup.shutdownGracefully();
		cGroup.shutdownGracefully();
		
	}
	
}

