package com.sunft.net.netty.sigar;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;

/**
 * �ο����ͣ�https://www.cnblogs.com/javahr/p/8284198.html
 * @author sunft
 *
 */
public class SigarTest {

	public static void main(String[] args) {
		
		Sigar sigar = new Sigar();
		try{
		  Mem mem = sigar.getMem();
		  CpuPerc cpuCerc = sigar.getCpuPerc();
		  System.out.println("*****��ǰCPUʹ����� ��");
		  System.out.println("#��ʹ����: " + cpuCerc.getCombined() * 100 + "%");
		  System.out.println("#�û�ʹ����(user): " + cpuCerc.getUser() * 100 + "%");
		  System.out.println("#ϵͳʹ����(sys): " + cpuCerc.getSys() * 100 + "%");
		  System.out.println("#��ǰ������(idel): " + cpuCerc.getIdle() * 100 + "%");
		  System.out.println("\n*****��ǰ�ڴ�ʹ����� ��");
		  System.out.println("#�ڴ�������" + mem.getTotal() / 1024 / 1024 + "M");
		  System.out.println("#��ʹ���ڴ棺" + mem.getUsed() / 1024 / 1024 + "M");
		  System.out.println("#ʣ���ڴ棺" + mem.getFree() / 1024 / 1024 + "M");
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

}

