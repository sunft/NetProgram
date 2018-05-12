package com.sunft.net.netty.heartbeat;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

public class ClientHeartBeatHandler extends ChannelHandlerAdapter {
	
	//�����̳߳�
	private ScheduledExecutorService scheduler 
			= Executors.newScheduledThreadPool(1);
	
	//����
	private java.util.concurrent.ScheduledFuture<?> heartBeat;
	
	//�����������������֤��Ϣ
	private InetAddress addr;
	
	private static final String SUCCESS_KEY = "auth_success_key";

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		//ע��IP
		addr = InetAddress.getLocalHost();
		String ip = addr.getHostAddress();
		
		System.out.println(ip);
		String key = "1234";
		//֤��,�����������Ϊ����֤
		String auth = ip + "," + key;
		//����֤���������
		ctx.writeAndFlush(auth);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		try {
			//�Աȷ��������͹���������,�ж��Ƿ����ֳɹ�
			if(msg instanceof String) {
				String ret = (String) msg;
				if(SUCCESS_KEY.equals(ret)) {
					//���ֳɹ�,��������������Ϣ
					this.heartBeat = this.scheduler.scheduleWithFixedDelay(new HeartBeatTask(ctx), 0, 2, TimeUnit.SECONDS);
					System.out.println(msg);
				} else {
					System.out.println(msg);
				}
			}
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}
	
	/**
	 * ����������
	 * @author sunft
	 *
	 */
	private class HeartBeatTask implements Runnable {
		//���߳��ڲ�����,��������Ϊfinal
		private final ChannelHandlerContext ctx;
		
		public HeartBeatTask(final ChannelHandlerContext ctx) {
			this.ctx = ctx;
		}

		@Override
		public void run() {
			try {
				RequestInfo info = new RequestInfo();
				//ip
				info.setIp(addr.getHostAddress());
				Sigar sigar = new Sigar();
				//cpu prec
				CpuPerc cpuPerc = sigar.getCpuPerc();
				HashMap<String, Object> cpuPercMap = new HashMap<String, Object>();
				cpuPercMap.put("combined", cpuPerc.getCombined());
				cpuPercMap.put("user", cpuPerc.getUser());
				cpuPercMap.put("sys", cpuPerc.getSys());
				cpuPercMap.put("wait", cpuPerc.getWait());
				cpuPercMap.put("idle", cpuPerc.getIdle());
				
				//memory
				Mem mem = sigar.getMem();
				HashMap<String, Object> memoryMap = new HashMap<String, Object>();
				memoryMap.put("total", mem.getTotal() / 1024L);
				memoryMap.put("used", mem.getUsed() / 1024L);
				memoryMap.put("free", mem.getFree() / 1024L);
				info.setCpuPerMap(cpuPercMap);
				info.setMemoryMap(memoryMap);
				
				//ʹ�������Ľ��ͻ�����Ϣд�ظ�������
				ctx.writeAndFlush(info);
			} catch (SigarException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		if(heartBeat != null) {
			heartBeat.cancel(true);
			heartBeat = null;
		}
		ctx.fireExceptionCaught(cause);
	}

}

