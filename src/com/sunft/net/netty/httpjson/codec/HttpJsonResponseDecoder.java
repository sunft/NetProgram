package com.sunft.net.netty.httpjson.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpResponse;

import java.util.List;


/**
 * Created by carl.yu on 2016/12/16.
 */
public class HttpJsonResponseDecoder extends AbstractHttpJsonDecoder<FullHttpResponse> {

    public HttpJsonResponseDecoder(Class<?> clazz) {
        this(clazz, false);
    }

    /**
     * ������
     *
     * @param clazz   ����Ķ�����Ϣ
     * @param isPrint �Ƿ���Ҫ��ӡ
     */
    public HttpJsonResponseDecoder(Class<?> clazz, boolean isPrint) {
        super(clazz, isPrint);
    }

    /**
     * @param ctx channel������
     * @param msg ��Ϣ
     * @param out �������
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, FullHttpResponse msg, List<Object> out) throws Exception {
        System.out.println("��ʼ����...");
        out.add(
        		//����retain()�ᱨ�쳣
                new HttpJsonResponse(msg.retain(), decode0(ctx, msg.content()))
        );
    }

}