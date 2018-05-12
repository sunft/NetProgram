package com.sunft.net.netty.httpjson.codec;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.CharsetUtil;

import java.util.List;

import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;

/**
 * Created by carl.yu on 2016/12/16.
 */
public class HttpJsonRequestDecoder extends AbstractHttpJsonDecoder<FullHttpRequest> {

    public HttpJsonRequestDecoder(Class<?> clazz) {
        this(clazz, false);
    }

    /**
     * ������
     *
     * @param clazz   ����Ķ�����Ϣ
     * @param isPrint �Ƿ���Ҫ��ӡ
     */
    public HttpJsonRequestDecoder(Class<?> clazz, boolean isPrint) {
        super(clazz, isPrint);
    }

    /**
     * @param ctx channel������
     * @param msg ��Ϣ
     * @param out �������
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, FullHttpRequest msg, List<Object> out) throws Exception {
        if (!msg.decoderResult().isSuccess()) {
            sendError(ctx, HttpResponseStatus.BAD_REQUEST);
            return;
        }
        HttpJsonRequest request = new HttpJsonRequest(msg, decode0(ctx, msg.content()));
        out.add(request);
    }


    /**
     * ���ԵĻ���ֱ�ӷ�װ��ʵս����Ҫ����׳�Ĵ���
     */
    private static void sendError(ChannelHandlerContext ctx,
                                  HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
                status, Unpooled.copiedBuffer("Failure: " + status.toString()
                + "\r\n", CharsetUtil.UTF_8));
        response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
}

