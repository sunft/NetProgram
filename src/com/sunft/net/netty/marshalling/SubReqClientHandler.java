package com.sunft.net.netty.marshalling;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class SubReqClientHandler extends ChannelHandlerAdapter {

    public SubReqClientHandler() {
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        //����·�����ʱ��ѭ������10������������Ϣ�����һ���Եط��͸�����ˡ�
        for (int i = 0; i < 10; i++) {
            ctx.write(subReq(i));
        }
        ctx.flush();
    }

    private SubscribeReq subReq(int i) {
        SubscribeReq req = new SubscribeReq();
        req.setAddress("�Ͼ��н�������ɽ���ҵ��ʹ�԰");
        req.setPhoneNumber("138xxxxxxxxx");
        req.setProductName("Netty For Marshalling");
        req.setSubReqID(i);
        req.setUserName("Lilinfeng");
        return req;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        //���ڶ���������Ѿ��Զ�������Ӧ����Ϣ�������Զ����룬
        //��ˣ�SubReqClientHandler���յ�����Ϣ�Ѿ��ǽ���ɹ���Ķ���Ӧ����Ϣ��
        System.out.println("Receive server response : [" + msg + "]");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}