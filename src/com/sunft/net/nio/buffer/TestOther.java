package com.sunft.net.nio.buffer;

import java.nio.IntBuffer;

/**
 * ��������API
 * @author sunft
 *
 */
public class TestOther {

	public static void main(String[] args) {
		IntBuffer buf1 = IntBuffer.allocate(10);
		int[] arr = new int[]{1, 2, 5};
		buf1.put(arr);
		System.out.println(buf1);
		//һ�ָ��Ʒ���
		IntBuffer buf3 = buf1.duplicate();
		System.out.println(buf3);
		
		//����buf1��λ������
		//buf1.position(0);
		buf1.flip();
		System.out.println(buf1);
		
		System.out.println("�ɶ����ݴ�СΪ��" + buf1.remaining());
		
		int[] arr2 = new int[buf1.remaining()];
		//�����������ݷ���arr2������ȥ
		buf1.get(arr2);
		for(int i : arr2) {
			System.out.print(Integer.toString(i) + ",");
		}
	}

}


