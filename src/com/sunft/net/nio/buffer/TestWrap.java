package com.sunft.net.nio.buffer;

import java.nio.IntBuffer;

/**
 * ��ʾwrap��ʹ��
 * @author sunft
 *
 */
public class TestWrap {

	public static void main(String[] args) {
		// wrap����ʹ��
		// wrap���������һ�����飺һ�������÷������ȳ�ʼ���������ĳ���,��Ϊû������,
		// ��󻹻ᱻwrap�����������鸲�ǵ�,����wrap�����޸Ļ����������ʱ��,���鱾��Ҳ����ŷ����仯��
		int[] arr = new int[] { 1, 2, 5 };
		IntBuffer buf1 = IntBuffer.wrap(arr);
		System.out.println(buf1);

		//�ӵ�0��λ�ð�����2��λ��,������0�͵�1��λ��
		IntBuffer buf2 = IntBuffer.wrap(arr, 0, 2);
		// ����ʹ�ñ�ʾ����Ϊ����arr�ĳ���,���ǿɲ�����Ԫ��ֻ��ʵ�ʽ��뻺������Ԫ�س���
		System.out.println(buf2);
	}

}
