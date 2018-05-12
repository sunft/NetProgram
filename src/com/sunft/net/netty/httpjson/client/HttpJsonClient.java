package com.sunft.net.netty.httpjson.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;

import java.net.InetSocketAddress;

import com.sunft.net.netty.httpjson.codec.HttpJsonRequestEncoder;
import com.sunft.net.netty.httpjson.codec.HttpJsonResponseDecoder;
import com.sunft.net.netty.httpjson.pojo.Order;

/**
 * Created by carl.yu on 2016/12/16.
 */
public class HttpJsonClient {
    public void connect(int port) throws Exception {
        // ���ÿͻ���NIO�߳���
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch)
                                throws Exception {
                            ch.pipeline().addLast("http-decoder",
                                    new HttpResponseDecoder());
                            ch.pipeline().addLast("http-aggregator",
                                    new HttpObjectAggregator(65536));
                            // json������
                            ch.pipeline().addLast("json-decoder", new HttpJsonResponseDecoder(Order.class, true));
                            ch.pipeline().addLast("http-encoder",
                                    new HttpRequestEncoder());
                            ch.pipeline().addLast("json-encoder",
                                    new HttpJsonRequestEncoder());
                            ch.pipeline().addLast("jsonClientHandler",
                                    new HttpJsonClientHandler());
                        }
                    });

            // �����첽���Ӳ���
            ChannelFuture f = b.connect(new InetSocketAddress(port)).sync();

            // �����ͻ�����·�ر�
            f.channel().closeFuture().sync();
        } finally {
            // �����˳����ͷ�NIO�߳���
            group.shutdownGracefully();
        }
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                // ����Ĭ��ֵ
            }
        }
        new HttpJsonClient().connect(port);
    }
}