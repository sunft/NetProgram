package com.sunft.net.netty.httpfile;
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
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author lilinfeng
 * @version 1.0
 * @date 2014��2��14��
 */
public class HttpFileServer {

    private static final String DEFAULT_URL = "/";

    public void run(final int port, final String url) throws Exception {
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
                            ch.pipeline().addLast("http-decoder",
                                    new HttpRequestDecoder()); // ������Ϣ������
                            ch.pipeline().addLast("http-aggregator",
                                    new HttpObjectAggregator(65536));// Ŀ���ǽ������Ϣת��Ϊ��һ��request����response����
                            ch.pipeline().addLast("http-encoder",
                                    new HttpResponseEncoder());//��Ӧ������
                            ch.pipeline().addLast("http-chunked",
                                    new ChunkedWriteHandler());//Ŀ����֧���첽���ļ����䣨��
                            ch.pipeline().addLast("fileServerHandler",
                                    new HttpFileServerHandler(url));// ҵ���߼�
                        }
                    });
            ChannelFuture future = b.bind("127.0.0.1", port).sync();
            System.out.println("HTTP�ļ�Ŀ¼��������������ַ�� : " + "http://127.0.0.1:"
                    + port + url);
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
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
        String url = DEFAULT_URL;
        if (args.length > 1)
            url = args[1];
        new HttpFileServer().run(port, url);
    }
}