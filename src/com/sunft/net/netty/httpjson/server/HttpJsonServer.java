package com.sunft.net.netty.httpjson.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import java.net.InetSocketAddress;

import com.sunft.net.netty.httpjson.codec.HttpJsonRequestDecoder;
import com.sunft.net.netty.httpjson.codec.HttpJsonResponseEncoder;
import com.sunft.net.netty.httpjson.pojo.Order;

/**
 * Created by carl.yu on 2016/12/16.
 */
public class HttpJsonServer {
    public void run(final int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch)
                                throws Exception {
                            //����HttpJsonRequest����Ҫ��Ӧ������
                            //ByteBuf->FullHttpRequest-> HttpJsonRequestDecoder
                            //���HttpJsonResponse����Ҫ��Ӧ������
                            //HttpResponseEncoder->FullHttpResponse-> HttpJsonResponseEncoder
                            ch.pipeline().addLast("http-decoder", new HttpRequestDecoder());
                            ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
                            ch.pipeline().addLast("json-decoder", new HttpJsonRequestDecoder(Order.class, true));
                            ch.pipeline().addLast("http-encoder", new HttpResponseEncoder());
                            ch.pipeline().addLast("json-encoder", new HttpJsonResponseEncoder());
                            ch.pipeline().addLast("jsonServerHandler", new HttpJsonServerHandler());
                        }
                    });
            ChannelFuture future = b.bind(new InetSocketAddress(port)).sync();
            System.out.println("HTTP������������������ַ�� : " + "http://localhost:"
                    + port);
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        new HttpJsonServer().run(port);
    }
}

