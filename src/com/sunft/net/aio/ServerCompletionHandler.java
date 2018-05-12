package com.sunft.net.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;

/**
 * �Զ���Handler��������
 * @author sunft
 *
 */
public class ServerCompletionHandler implements
		CompletionHandler<AsynchronousSocketChannel, Server> {

	/**
	 * attachment:Server�е�this
	 */
	@Override
	public void completed(AsynchronousSocketChannel result, Server attachment) {
		//������һ���ͻ��˽����ʱ��ֱ�ӵ���Server��accept����,��������ִ����ȥ,��֤����ͻ��˶���������
		attachment.assc.accept(attachment, this);
		read(result);
	}

	/**
	 * ��ȡ����
	 * @param result
	 */
	private void read(final AsynchronousSocketChannel result) {
		//��ȡ����
		ByteBuffer buf = ByteBuffer.allocate(1024);
		result.read(buf, buf, new CompletionHandler<Integer, ByteBuffer>(){

			@Override
			public void completed(Integer resultSize, ByteBuffer attachment) {
				//���ж�ȡ֮��,���ñ�ʶλ
				attachment.flip();
				//��ȡ��ȡ���ֽ�
				System.out.println("Server -> " + "�յ��ͻ��˵����ݳ���Ϊ:" + resultSize);
				//��ȡ��ȡ������
				String resultData = new String(attachment.array()).trim();
				System.out.println("Server -> " + "�յ��ͻ��˵�������ϢΪ:" + resultData);
				String response = "��������Ӧ,�յ��˿ͻ��˷���������:" + resultData;
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
			//write���ص���Future����,��ȡ����
			result.write(buf).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ����ʧ�ܺ�ĵ��÷���
	 */
	@Override
	public void failed(Throwable exc, Server attachment) {
		exc.printStackTrace();
	}

}


