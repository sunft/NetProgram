package com.sunft.net.nio.buffer;

import java.nio.IntBuffer;

/**
 * 测试其他API
 * @author sunft
 *
 */
public class TestOther {

	public static void main(String[] args) {
		IntBuffer buf1 = IntBuffer.allocate(10);
		int[] arr = new int[]{1, 2, 5};
		buf1.put(arr);
		System.out.println(buf1);
		//一种复制方法
		IntBuffer buf3 = buf1.duplicate();
		System.out.println(buf3);
		
		//设置buf1的位置属性
		//buf1.position(0);
		buf1.flip();
		System.out.println(buf1);
		
		System.out.println("可读数据大小为：" + buf1.remaining());
		
		int[] arr2 = new int[buf1.remaining()];
		//将缓冲区数据放入arr2数组中去
		buf1.get(arr2);
		for(int i : arr2) {
			System.out.print(Integer.toString(i) + ",");
		}
	}

}


