package com.sunft.net.nio.buffer;

import java.nio.IntBuffer;

/**
 * 演示wrap的使用
 * @author sunft
 *
 */
public class TestWrap {

	public static void main(String[] args) {
		// wrap方法使用
		// wrap方法会包裹一个数组：一般这种用法不会先初始化缓存对象的长度,因为没有意义,
		// 最后还会被wrap所包裹的数组覆盖掉,并且wrap方法修改缓冲区对象的时候,数组本身也会跟着发生变化。
		int[] arr = new int[] { 1, 2, 5 };
		IntBuffer buf1 = IntBuffer.wrap(arr);
		System.out.println(buf1);

		//从第0个位置包裹到2个位置,包含第0和第1个位置
		IntBuffer buf2 = IntBuffer.wrap(arr, 0, 2);
		// 这样使用表示容量为数组arr的长度,但是可操作的元素只有实际进入缓存区的元素长度
		System.out.println(buf2);
	}

}
