package com.sunft.net.netty.upd;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.ThreadLocalRandom;

public class ServerHandler extends
	SimpleChannelInboundHandler<DatagramPacket> {

    // �����б�
    private static final String[] DICTIONARY = { 
    	"ֻҪ���������ĥ���롣",
	    "��ʱ��л��ǰ�࣬����Ѱ�����ռҡ�", 
	    "�������������ʣ�һƬ�����������",
	    "һ�����һ��𣬴������������",
	    "����������־��ǧ���ʿĺ�꣬׳�Ĳ���!"
    };

    private String nextQuote() {
		int quoteId = ThreadLocalRandom.current().nextInt(DICTIONARY.length);
		return DICTIONARY[quoteId];
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, DatagramPacket packet)
	    throws Exception {
		String req = packet.content().toString(CharsetUtil.UTF_8);
		System.out.println(req);
		if ("�����ֵ��ѯ?".equals(req)) {
		    ctx.writeAndFlush(
		    		new DatagramPacket(Unpooled.copiedBuffer("�����ѯ���: " + nextQuote(),
		    		CharsetUtil.UTF_8), packet.sender()));
		}
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
	    throws Exception {
		ctx.close();
		cause.printStackTrace();
    }
}
