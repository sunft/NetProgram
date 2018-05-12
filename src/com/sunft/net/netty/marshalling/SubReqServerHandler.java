package com.sunft.net.netty.marshalling;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

@ChannelHandler.Sharable
public class SubReqServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        //����������handler ObjectDecoder�Ľ��룬
        //SubReqServerHandler���յ���������Ϣ�Ѿ����Զ�����ΪSubscribeReq���󣬿���ֱ��ʹ�á�
        SubscribeReq req = (SubscribeReq) msg;
        if ("Lilinfeng".equalsIgnoreCase(req.getUserName())) {
            System.out.println("Service accept client subscribe req : ["
                    + req.toString() + "]");
            //�Զ����ߵ��û������кϷ���У�飬У��ͨ�����ӡ����������Ϣ�����충���ɹ�Ӧ����Ϣ�������͸��ͻ��ˡ�
            ctx.writeAndFlush(resp(req.getSubReqID()));
        }
    }

    private SubscribeResp resp(int subReqID) {
        SubscribeResp resp = new SubscribeResp();
        resp.setSubReqID(subReqID);
        resp.setRespCode(0);
        resp.setDesc("Netty book order succeed, 3 days later, sent to the designated address");
        return resp;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();// �����쳣���ر���·
    }
}