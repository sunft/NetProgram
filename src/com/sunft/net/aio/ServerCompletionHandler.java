package com.sunft.net.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;

/**
 * 自定义Handler处理请求
 * @author sunft
 *
 */
public class ServerCompletionHandler implements
		CompletionHandler<AsynchronousSocketChannel, Server> {

	/**
	 * attachment:Server中的this
	 */
	@Override
	public void completed(AsynchronousSocketChannel result, Server attachment) {
		//当下有一个客户端接入的时候直接调用Server的accept方法,这样反复执行下去,保证多个客户端都可以阻塞
		attachment.assc.accept(attachment, this);
		read(result);
	}

	/**
	 * 读取方法
	 * @param result
	 */
	private void read(final AsynchronousSocketChannel result) {
		//读取数据
		ByteBuffer buf = ByteBuffer.allocate(1024);
		result.read(buf, buf, new CompletionHandler<Integer, ByteBuffer>(){

			@Override
			public void completed(Integer resultSize, ByteBuffer attachment) {
				//进行读取之后,重置标识位
				attachment.flip();
				//获取读取的字节
				System.out.println("Server -> " + "收到客户端的数据长度为:" + resultSize);
				//获取读取的数据
				String resultData = new String(attachment.array()).trim();
				System.out.println("Server -> " + "收到客户端的数据信息为:" + resultData);
				String response = "服务器响应,收到了客户端发来的数据:" + resultData;
				write(result, response);
			}

		
			@Override
			public void failed(Throwable exc, ByteBuffer attachment) {
				exc.printStackTrace();
			}
		});
	}
	
	private void write(AsynchronousSocketChannel result, String response) {
		try {
			ByteBuffer buf = ByteBuffer.allocate(1024);
			buf.put(response.getBytes());
			buf.flip();
			//write返回的是Future对象,获取即可
			result.write(buf).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 处理失败后的调用方法
	 */
	@Override
	public void failed(Throwable exc, Server attachment) {
		exc.printStackTrace();
	}

}


