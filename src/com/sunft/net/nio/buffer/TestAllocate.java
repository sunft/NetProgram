package com.sunft.net.nio.buffer;

import java.nio.IntBuffer;

/**
 * ��ʾallocate������ʹ��
 * @author sunft
 *
 */
public class TestAllocate {

	public static void main(String[] args) {
		
		//��������
		//����ָ�����ȵĻ�����
		IntBuffer buf = IntBuffer.allocate(10);
		buf.put(13);//positionλ�ã�0 -> 1
		buf.put(21);//positionλ�ã�1 -> 2
		buf.put(35);//positionλ�ã�2 -> 3
		//��λ�ø�λΪ0,Ҳ����positionλ�ã�3 -> 0
		//ִ�и�λ����,�ſ��Դ�0λ�ö�ȡ����
		buf.flip();
		System.out.println("ʹ��flip��λ��" + buf);
		//����һ����ʼ��������ı�(wrap���������������)
		System.out.println("����Ϊ��" + buf.capacity());
		//����ֻװ��������Ԫ��,���Կɶ�ȡ���߲�����Ԫ��Ϊ3,��limit=3
		System.out.println("����Ϊ��" + buf.limit());
		
		//�����������ı�position��λ��
		System.out.println("��ȡ�±�Ϊ1��Ԫ�أ�" + buf.get(1));
		System.out.println("get(index)����,positionλ�ò��ı䣺" + buf);
		
		//����һ��Ԫ�ص�ֵ��21�ı�Ϊ4
		buf.put(1, 4);
		System.out.println("put(index, change)����,positionλ�ò���:" + buf);
		
		//limit������λ����Ϊbuf��ʵ�ʴ�С
		//��������и�λ,������쳣
		for (int i = 0; i < buf.limit(); i ++) {
			//����get������ʹ�仺����λ��(position)������һλ
			System.out.println(buf.get() + "\t");
		}
		
		System.out.println("buf�������֮��" + buf);
		
	}

}


