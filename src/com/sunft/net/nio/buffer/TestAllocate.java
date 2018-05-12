package com.sunft.net.nio.buffer;

import java.nio.IntBuffer;

/**
 * 演示allocate方法的使用
 * @author sunft
 *
 */
public class TestAllocate {

	public static void main(String[] args) {
		
		//基本操作
		//创建指定长度的缓冲区
		IntBuffer buf = IntBuffer.allocate(10);
		buf.put(13);//position位置：0 -> 1
		buf.put(21);//position位置：1 -> 2
		buf.put(35);//position位置：2 -> 3
		//把位置复位为0,也就是position位置：3 -> 0
		//执行复位操作,才可以从0位置读取操作
		buf.flip();
		System.out.println("使用flip复位：" + buf);
		//容量一旦初始化后不允许改变(wrap方法包裹数组除外)
		System.out.println("容量为：" + buf.capacity());
		//由于只装载了三个元素,所以可读取或者操作的元素为3,则limit=3
		System.out.println("限制为：" + buf.limit());
		
		//读操作并不改变position的位置
		System.out.println("获取下标为1的元素：" + buf.get(1));
		System.out.println("get(index)方法,position位置不改变：" + buf);
		
		//将第一个元素的值由21改变为4
		buf.put(1, 4);
		System.out.println("put(index, change)方法,position位置不变:" + buf);
		
		//limit经过复位后会变为buf的实际大小
		//如果不进行复位,则会抛异常
		for (int i = 0; i < buf.limit(); i ++) {
			//调用get方法会使其缓冲区位置(position)向后递增一位
			System.out.println(buf.get() + "\t");
		}
		
		System.out.println("buf对象遍历之后：" + buf);
		
	}

}


