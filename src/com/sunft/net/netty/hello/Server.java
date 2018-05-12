package com.sunft.net.netty.hello;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * �����
 * @author sunft
 * ����ChannelOption.SO_BACKLOG�Ľ���:
 * ��������TCP�ں�ģ��ά����2������,���ǳ�֮ΪA,B��
 * �ͻ����������connec��ʱ��,�ᷢ�ʹ���SYN��־��ֵ(��һ������),
 * �������յ��ͻ��˷��͵�SYNʱ,��ͻ��˷���SYN ACKȷ��(�ڶ�������),
 * ��ʱTCP�ں�ģ��ѿͻ������Ӽ��뵽A������,Ȼ��������յ��ͻ��˷�������ACKʱ(����������),
 * TCP�ں�ģ��ѿͻ������Ӵ�A�����Ƶ�B����,�������,Ӧ�ó����accept�᷵��,
 * Ҳ����˵accept��B������ȡ������������ֵ����ӡ�
 * A���к�B���г���֮����backlog����A,B���еĳ���֮�ʹ���backlogʱ,�����ӽ��ᱻTCP�ں˾ܾ���
 * ����,���backlog��С,���ܻ����accept�ٶȸ�����,A��B��������,�����µĿͻ����޷�����,
 * Ҫע�����:backlog�Գ���֧�ֵ�����������Ӱ��,backlogӰ���ֻ�ǻ�û�б�acceptȡ�������ӡ�
 * 
 * 
 */
public class Server {

	public static void main(String[] args) throws Exception {
		EventLoopGroup pGroup = new NioEventLoopGroup();//һ�����ô�����������˽��տͻ������ӵ�
		EventLoopGroup cGroup = new NioEventLoopGroup();//һ���ǽ�������ͨ�ŵ�(�����д��)
		ServerBootstrap b = new ServerBootstrap();//2����������������,���ڷ�����ͨ����һϵ������
		b.group(pGroup, cGroup)							//�������߳���
		.channel(NioServerSocketChannel.class)          //ָ��NIOģʽ
		.option(ChannelOption.SO_BACKLOG, 1024) 		//����TCP������,����������С
		.option(ChannelOption.SO_SNDBUF, 32*1024)		//���÷��ͻ����С
		.option(ChannelOption.SO_RCVBUF, 32*1024)		//���ý��ջ�������С
		.option(ChannelOption.SO_KEEPALIVE, true)
		.childHandler(new ChannelInitializer<SocketChannel> () {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new ServerHandler());//3�����������þ������ݽ��շ����Ĵ���
			}
		});
		
		ChannelFuture cf1 = b.bind(8765).sync();//4�����а�
		//��һ���˿�
		ChannelFuture cf2 = b.bind(8764).sync();
		
		System.out.println("Server start...");
		
		//Thread.sleep(Integer.MAX_VALUE)
		//���������޵ȴ�
		cf1.channel().closeFuture().sync();		//5���ȴ��ر�
		
		cf2.channel().closeFuture().sync();
		
		pGroup.shutdownGracefully();
		cGroup.shutdownGracefully();
	}

}
