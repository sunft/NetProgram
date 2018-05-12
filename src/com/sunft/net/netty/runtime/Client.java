package com.sunft.net.netty.runtime;

import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 * 测试超过5秒未进行通信就关闭管道
 * @author sunft
 *
 */
public class Client  {
	
	private static class SingletonHolder {
		static final Client instance = new Client();
	}
	
	//静态内部类单例
	public static Client getInstance() {
		return SingletonHolder.instance;
	}
	
	private EventLoopGroup group;
	private Bootstrap b;
	private ChannelFuture cf;
	
	private Client() {
		group = new NioEventLoopGroup();
		b = new Bootstrap();
		b.group(group)
		.channel(NioSocketChannel.class)
		.handler(new LoggingHandler(LogLevel.INFO))
		.handler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
				ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
				//超时handler(当服务器与客户端在指定时间以上没有进行任何通信,则会关闭响应的通道)
				ch.pipeline().addLast(new ReadTimeoutHandler(5));
				ch.pipeline().addLast(new ClientHandler());
			}
			
		});
	}
	
	public void connect() {
		try {
			this.cf = b.connect("127.0.0.1", 8765).sync();
			System.out.println("远称服务器已经连接,可以进行数据交换...");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public ChannelFuture getChannelFuture() {
		
		System.out.println("this.cf:" + this.cf);
		
		if(this.cf == null) {
			this.connect();
		}
		
		System.out.println("this.cf.channel().isActive():" + this.cf.channel().isActive());
		
		//判断通道是否是激活的
		if(!this.cf.channel().isActive()) {
			this.connect();
		}
		
		return this.cf;
	}
	
	public static void main(String[] args) throws Exception {
		final Client c = Client.getInstance();
		//c.connect();
		
		//建立连接
		ChannelFuture cf = c.getChannelFuture();
		for(int i = 1; i <= 3; i ++) {
			Request request = new Request();
			request.setId("" + i);
			request.setName("pro" + i);
			request.setRequestMessage("数据通信" + i);
			cf.channel().writeAndFlush(request);
			TimeUnit.SECONDS.sleep(4);
		}
		
		//这行代码会阻塞,执行完上面的代码,又过了5秒会断开连接
		cf.channel().closeFuture().sync();
		
		//-------------------------
		//注释掉下面这段代码进行测试,发现超过5秒不通信,管道会自动关闭
		new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					System.out.println("进入子线程...");
					//重新建立连接
					ChannelFuture cf = c.getChannelFuture();
					System.out.println(cf.channel().isActive());
					System.out.println(cf.channel().isOpen());
					
					//再次发送数据
					Request request = new Request();
					request.setId("" + 4);
					request.setName("pro" + 4);
					request.setRequestMessage("数据信息" + 4);
					cf.channel().writeAndFlush(request);
					//执行完上面的代码,5秒之后又会断开连接
					cf.channel().closeFuture().sync();
					System.out.println("子线程结束.");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}).start();
		//---------------------
		
		System.out.println("断开连接,主线程结束...");
	}
}
